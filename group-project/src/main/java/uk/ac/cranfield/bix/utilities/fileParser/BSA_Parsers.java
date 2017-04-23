/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.LinkData;
import uk.ac.cranfield.bix.controllers.rest.LinkProperties;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Link;

public class BSA_Parsers {

//    public static String SortVCFFiles(String PathName) throws IOException {
//        String sortedFile = "";
//        String Command = "sort " + PathName + " -k1,1" + "-k2,2n >" + PathName.substring(0, PathName.lastIndexOf(".")) + ".sorted.vcf";
//        ProcessBuilder probuilder = new ProcessBuilder(Command);
//        Process process = probuilder.start();
//        return sortedFile;
//    }
//
//    public static String VCFRemoveIndels(String PathName) throws IOException {
//        
//        String outputFile = PathName.substring(0, PathName.lastIndexOf(".sorted"));
//       
//        String Command = "vcftools --vcf " + PathName + " --out " + PathName.substring(0, PathName.lastIndexOf("/")+1) + "SNPs" + " --removeindels " + " --recode";
//        ProcessBuilder probuilder = new ProcessBuilder(Command);
//        Process process = probuilder.start();
//        return outputFile;
//    }
    //Command need to be run on pool1 VCF and Pool2 VCF
    public static String BedToolsIntersect(String pool1VCF, String pool2VCF) throws IOException {

        String nameP1 = new File(pool1VCF).getName();
        String nameP1WExt = nameP1.substring(0, nameP1.lastIndexOf("."));

        String nameP2 = new File(pool2VCF).getName();
        String nameP2WExt = nameP2.substring(0, nameP2.lastIndexOf("."));

        String outpuPath = pool1VCF.substring(0, pool1VCF.lastIndexOf("/") + 1) + nameP1WExt + nameP2WExt + "intersect.txt";
        String Command = "/usr/bin/bedtools intersect -v -a " + pool1VCF + " -b " + pool2VCF;// + " > " + outpuPath;
        String s;
        Process p;
        try {
            FileOutputStream outputStream = new FileOutputStream(outpuPath);

            p = Runtime.getRuntime().exec(Command);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(outputStream));
            while((s = br.readLine()) != null){
                wr.write(s + "\n");
//                System.out.println("line: " +s);
            }
            wr.flush();
            p.waitFor();
            System.out.println("exit:" + p.exitValue());
            p.destroy();
            outputStream.close();
            wr.close();
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(BSA_Parsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return outpuPath;
    }

    public static ArrayList VCFParser(List<List<String[]>> lists) {
        List<String[]> VCFSplit = lists.get(1);
        ArrayList<String[]> ParsedVCF = new ArrayList();
        ParsedVCF.clear();
        for (String[] list1 : VCFSplit) {
            String[] Parsed = new String[4];
            Parsed[0] = list1[0];
            Parsed[1] = list1[1];
            Parsed[2] = list1[4];
            Parsed[3] = list1[7].split(";")[1];
            ParsedVCF.add(Parsed);
        }

        return ParsedVCF;
    }

    public static ArrayList<Object[]> SortToBinsIntersectedFiles(ArrayList<String[]> ParsedVCF, List<List<String[]>> li, Integer BinSize) {
        List<String[]> meta = li.get(0);
        ArrayList<List<Object[]>> lists = new ArrayList();
        ArrayList<Integer> BinNumber = new ArrayList();
        for (int k = 0; k < meta.size(); k++) {
            BinNumber.add(Integer.parseInt(meta.get(k)[1]) / BinSize);
        }

        for (int i = 0; i < meta.size(); i++) {
            List<Object[]> list = new ArrayList();
//            System.out.println(meta.get(i)[0]);
            for (int j = 0; j < ParsedVCF.size(); j++) {

                if (ParsedVCF.get(j)[0].substring(ParsedVCF.get(j)[0].length()-2,ParsedVCF.get(j)[0].length()).equals(meta.get(i)[0].substring(meta.get(i)[0].length()-2, meta.get(i)[0].length()))) {

                    list.add(ParsedVCF.get(j));
                }

            }
            lists.add(list);
        }
        Double NoHomoSnp = 0.00;
        Integer NoHeteroSnp = 0;
        Double BinMaf = 0.00;
        ArrayList<Object[]> MAF = new ArrayList();
        Integer Sum = 0;
        for (int z = 0; z < BinNumber.size(); z++) {
            Sum += BinNumber.get(z);
        }
//        System.out.println(Sum);
        for (int i = 0; i < lists.size(); i++) { // for all chromsomes (The number of lists in lists)

            for (int j = 1; j <= BinNumber.get(i); j++) { // for all bins in that chromosome

                for (int l = 0; l < lists.get(i).size(); l++) { // for all entries under that chromosomome bin

                    if (Integer.parseInt(lists.get(i).get(l)[1].toString()) > (BinSize * (j - 1)) && Integer.parseInt(lists.get(i).get(l)[1].toString()) < (BinSize * j)) {

                        if (Double.valueOf(lists.get(i).get(l)[3].toString().substring(lists.get(i).get(l)[3].toString().length() - 4)) > 0.99) {
                            NoHomoSnp += 1.00;

                        } else {
                            NoHeteroSnp += 1;
                        }

                    } else if (Integer.parseInt(lists.get(i).get(l)[1].toString()) > (BinSize * j) && Integer.parseInt(lists.get(i).get(l)[1].toString()) < (BinSize * (j + 1))) {
                        BinMaf = NoHomoSnp / (NoHomoSnp + NoHeteroSnp);
                        Object[] values = new Object[6];
                        values[0] = lists.get(i).get(l)[0]; //chrom
                        values[1] = lists.get(i).get(l)[1]; //pos
                        values[2] = lists.get(i).get(l)[2]; //Alt
                        values[3] = BinMaf; //Bin MAF Value
                        values[4] = BinSize * (j - 1);//Beginning position of the bin
                        values[5] = BinSize * j;//end position of the bin
                        MAF.add(values);
                        NoHomoSnp = 0.00;
                        NoHeteroSnp = 0;

                        break;
                    } else if (Integer.parseInt(lists.get(i).get(l)[1].toString()) > (BinSize * j + 1)) {
                        BinMaf = NoHomoSnp / (NoHomoSnp + NoHeteroSnp);
                        Object[] values = new Object[6];
                        values[0] = lists.get(i).get(l)[0]; //chrom
                        values[1] = lists.get(i).get(l)[1]; //pos
                        values[2] = lists.get(i).get(l)[2]; //Alt
                        values[3] = BinMaf; //Bin MAF Value
                        values[4] = BinSize * (j - 1);//Beginning position of the bin
                        values[5] = BinSize * j;//end position of the bin
                        MAF.add(values);
                        NoHomoSnp = 0.00;
                        NoHeteroSnp = 0;
                        break;

                    }

                }
            }
        }

//        System.out.println("");
        BinNumber.clear();
        return MAF;
    }

    public static ArrayList<Object[]> Comparator(ArrayList<Object[]> Parent1, ArrayList<Object[]> Parent2, ArrayList<Object[]> Pool1, ArrayList<Object[]> Pool2) {

        //Pool 1 vs Parent 1
        ArrayList<Object[]> LinkValues = new ArrayList();

        for (int i = 0; i < Pool1.size(); i++) {
            if (i < Parent1.size()) {
                if ((Double) Pool1.get(i)[3] == 1.00 && (Double) Parent1.get(i)[3] == 1.00) {
                    Object[] LinkData = new Object[9];
                    LinkData[0] = Pool1.get(i)[0].toString() + ": " + Pool1.get(i)[4].toString() + "-" + Pool1.get(i)[5].toString();  //Linkname
                    LinkData[1] = Pool1.get(i)[0].toString();  //Pool Chrom 
                    LinkData[2] = Pool1.get(i)[4].toString();  //Pool Bin Start
                    LinkData[3] = Pool1.get(i)[5].toString();  //Pool Bin End
                    LinkData[4] = "DEADMAU5";  //Pool end name
                    LinkData[5] = Parent1.get(i)[0].toString();  //Parent Chrom
                    LinkData[6] = Parent1.get(i)[4].toString();  //Parent Bin Start
                    LinkData[7] = Parent1.get(i)[5].toString();  //Parent Bin End    
                    LinkData[8] = "Rulez";  //Parent End Name    
                    LinkValues.add(LinkData);
                }
            } else {
                break;
            }
        }
        //Pool 1 vs Parent 2
        for (int i = 0; i < Pool1.size(); i++) {
            if ((Double) Pool1.get(i)[3] > 0.98 && (Double) Parent2.get(i)[3] > 0.99) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool1.get(i)[0].toString() + ": " + Pool1.get(i)[4].toString() + "-" + Pool1.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool1.get(i)[0].toString() + "Po1";  //Pool Chrom 
                LinkData[2] = Pool1.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool1.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Parent2.get(i)[0].toString() + "Pa2";  //Parent Chrom
                LinkData[6] = Parent2.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent2.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }

        //Pool 2 vs Parent 1
        for (int i = 0; i < Pool2.size(); i++) {
            if ((Double) Pool2.get(i)[3] > 0.98 && (Double) Parent1.get(i)[3] > 0.99) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool2.get(i)[0].toString() + ": " + Pool2.get(i)[4].toString() + "-" + Pool2.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool2.get(i)[0].toString() + "Po2";  //Pool Chrom 
                LinkData[2] = Pool2.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool2.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Parent1.get(i)[0].toString() + "Pa1";  //Parent Chrom
                LinkData[6] = Parent1.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent1.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }
        //Pool 2 vs Parent 2
        for (int i = 0; i < Pool2.size(); i++) {
            if ((Double) Pool2.get(i)[3] > 0.98 && (Double) Parent2.get(i)[3] > 0.99) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool2.get(i)[0].toString() + ": " + Pool2.get(i)[4].toString() + "-" + Pool2.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool2.get(i)[0].toString() + "Po2";  //Pool Chrom 
                LinkData[2] = Pool2.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool2.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Parent2.get(i)[0].toString() + "Pa2";  //Parent Chrom
                LinkData[6] = Parent2.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent2.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }
        return LinkValues;
    }

    public static ArrayList<Object[]> BinMerger(ArrayList<Object[]> LinkValues, Integer BinSize) {
        ArrayList<Object[]> MergedBins = new ArrayList();
        Object NewEndPoint = 0;
        Integer Count = 0;
        for (int i = 0; i < LinkValues.size() - 2; i++) {

            if (LinkValues.get(i)[3].equals(LinkValues.get(i + 1)[2])) {
                NewEndPoint = LinkValues.get(i + 1)[3];
                Count += 1;
            } else if (i != 0 && LinkValues.get(i - 1)[3].equals(LinkValues.get(i)[2]) && LinkValues.get(i)[3] != LinkValues.get(i + 1)[2]) {
                Object[] MergedValues = new Object[9];
                //Pool1.get(i)[0].toString() + ": " + Pool1.get(i)[4].toString() + "-" + Pool1.get(i)[5].toString();  //Linkname
                MergedValues[0] = LinkValues.get(i - Count)[0].toString() + "; " + LinkValues.get(i - Count)[2].toString() + "-" + NewEndPoint.toString(); //LinkName
                MergedValues[1] = LinkValues.get(i - Count)[1]; //Pool Chr
                MergedValues[2] = LinkValues.get(i - Count)[2]; //Pool Bin Start pos
                MergedValues[3] = NewEndPoint; //Pool Bin End Pos
                MergedValues[4] = LinkValues.get(i - Count)[4]; //Pool gene Name   
                MergedValues[5] = LinkValues.get(i - Count)[5]; //Parent Chr
                MergedValues[6] = LinkValues.get(i - Count)[6]; //Parent Bin Start Pos
                MergedValues[7] = NewEndPoint; // Parent Bin End Pos   
                MergedValues[8] = LinkValues.get(i - Count)[8]; // Parent Gene Name  

                MergedBins.add(MergedValues);
                Count = 0;

            } else {
                Object[] MergedValues = new Object[9];

                MergedValues[0] = LinkValues.get(i)[0]; //LinkName
                MergedValues[1] = LinkValues.get(i)[1]; //Pool Chr
                MergedValues[2] = LinkValues.get(i)[2]; //Pool Bin Start pos
                MergedValues[3] = LinkValues.get(i)[3]; //Pool Bin End Pos
                MergedValues[4] = LinkValues.get(i)[4]; //Pool gene Name   
                MergedValues[5] = LinkValues.get(i)[5]; //Parent Chr
                MergedValues[6] = LinkValues.get(i)[6]; //Parent Bin Start Pos
                MergedValues[7] = LinkValues.get(i)[7]; // Parent Bin End Pos   
                MergedValues[8] = LinkValues.get(i)[8]; // Parent Gene Name  

                MergedBins.add(MergedValues);
                Count = 0;
            }

        }

        return MergedBins;
    }

    public static List<LinkData> BSADataWriter(ArrayList<Object[]> LinkValues) {
        List<LinkData> linkData = new ArrayList();

        for (int i = 0; i < LinkValues.size(); i++) {
            LinkData point = new LinkData(LinkValues.get(i)[0].toString(),
                    LinkValues.get(i)[1].toString(),
                    Integer.parseInt(LinkValues.get(i)[2].toString()),
                    Integer.parseInt(LinkValues.get(i)[3].toString()), 
                    LinkValues.get(i)[4].toString(),
                    LinkValues.get(i)[5].toString(),
                    Integer.parseInt(LinkValues.get(i)[6].toString()),
                    Integer.parseInt(LinkValues.get(i)[7].toString()),
                    LinkValues.get(i)[8].toString());
            linkData.add(point);
        }
        return linkData;
    }

    public static Link BSALinkWriter(List<LinkData> LinkData) throws IOException {

        Link l = new Link();
        l.setLinkId("LINK01");
        l.setProperties(new LinkProperties(140, "#F26223", 3, "false", "#B8B8B8", 0.5, 3, "false", "red", 13, 8));
        l.setLinkData(LinkData);
        return l;

    }

}
