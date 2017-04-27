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
/**
 * 
 * @author vmuser
 * BSA_Parsers - A class to parse 4 vcf files into the correct LINK module format to be displayed by the BioCirocs Library
 */
public class BSA_Parsers {
    
    //Command need to be run on pool1 VCF and Pool2 VCF
    /**
     * BedTools Intersect runs the Bedtools intersect utility to remove commons SNPs from the VCF files by running the correct command on 
     * the command line, returns a list of the VCF file with only Chromosome, position, ALT, and the Allelic frequency value
     * @param pool1VCF
     * @param pool2VCF
     * @return
     * @throws IOException 
     */
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

    public static ArrayList<String[]> VCFParser(List<String> data) {
        
        ArrayList<String[]> ParsedVCF = new ArrayList();
        ParsedVCF.clear();
        for (String list1 : data) {
            String[] line = list1.split("\\t");
            String[] Parsed = new String[4];
            Parsed[0] = line[0]; //chr
            Parsed[1] = line[1]; //pos
            Parsed[2] = line[4]; //ALt
            Parsed[3] = line[7].split(";")[1]; //Allelic frequency
            ParsedVCF.add(Parsed);
        }
        //returns parsed vcf, 
        return ParsedVCF;
    }
    /**
     * Calculates the number of Bins in each chromosome, where they start and stop, and populates them with the respective contents of the Parsed
     * VCF file, ouput by VCF Parser.
     * @param ParsedVCF
     * @param meta
     * @param BinSize
     * @return 
     */
    public static ArrayList<Object[]> SortToBinsIntersectedFiles(ArrayList<String[]> ParsedVCF, List<String[]> meta, Integer BinSize) {
        
        ArrayList<List<Object[]>> lists = new ArrayList();
        ArrayList<Integer> BinNumber = new ArrayList();
        //get the number of Bins in each Chromosome
        for (int k = 0; k < meta.size(); k++) {
            BinNumber.add(Integer.parseInt(meta.get(k)[1]) / BinSize);
        }
        //create a list of lists, with each element list the values for seperated chromosomes
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
        
        for (int i = 0; i < lists.size(); i++) { // for all chromsomes (The number of lists in lists)

            for (int j = 1; j <= BinNumber.get(i); j++) { // for all bins in that chromosome

                for (int l = 0; l < lists.get(i).size(); l++) { // for all entries under that chromosomome bin
                    //if the pos is inside the current Bin, add it to the arraylist
                    if (Integer.parseInt(lists.get(i).get(l)[1].toString()) > (BinSize * (j - 1)) && Integer.parseInt(lists.get(i).get(l)[1].toString()) < (BinSize * j)) {
                        //if the Allelic frequency is 1, add to the number of homozygous SNPs
                        if (Double.valueOf(lists.get(i).get(l)[3].toString().substring(lists.get(i).get(l)[3].toString().length() - 4)) > 0.99) {
                            NoHomoSnp += 1.00;
                            //add to the number of heterozygous snps
                        } else {
                            NoHeteroSnp += 1;
                        }
                        //if the value is outside the current Bins, calculate the MAF and add it to the MAF arraylist reset the Homo and hetero snp counters
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

                        break; // same thing as before, to check for any inconsistencies
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


        BinNumber.clear();
        return MAF;
    }
    /**
     * Method, that compares the Homozygosity ratio of pool 1 against parent 1 and parent 2 and pool 2 against parent 1 and parent 2
     * @param Parent1
     * @param Parent2
     * @param Pool1
     * @param Pool2
     * @return 
     */
    public static ArrayList<Object[]> Comparator(ArrayList<Object[]> Parent1, ArrayList<Object[]> Parent2, ArrayList<Object[]> Pool1, ArrayList<Object[]> Pool2) {

        //Pool 1 vs Parent 1
        ArrayList<Object[]> LinkValues = new ArrayList();
        
        for (int i = 0; i < Pool1.size(); i++) {
            if (i < Parent1.size()) {
                //if the MAF value is more than 0.97 add it to the LINK data as a line 
                if ((Double) Pool1.get(i)[3] > 0.97 && (Double) Parent1.get(i)[3] > 0.97) {
                    Object[] LinkData = new Object[9];
                    LinkData[0] = Pool1.get(i)[0].toString() + "-WT" + " to " + Parent1.get(i)[0] + " : " + Pool1.get(i)[4].toString() + "-" + Pool1.get(i)[5].toString();  //Linkname
                    LinkData[1] = Pool1.get(i)[0] + "Po1";  //Pool Chrom 
                    LinkData[2] = Pool1.get(i)[4].toString();  //Pool Bin Start
                    LinkData[3] = Pool1.get(i)[5].toString();  //Pool Bin End
                    LinkData[4] = "DEADMAU5";  //Pool end name
                    LinkData[5] = Pool1.get(i)[0].toString() + "Pa1";  //Parent Chrom
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
            //if the MAF value is more than 0.97 add it to the LINK data as a line 
            if ((Double) Pool1.get(i)[3] > 0.97 && (Double) Parent2.get(i)[3] > 0.97) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool1.get(i)[0].toString() + "-WT" + " to " + Parent2.get(i)[0] +" : " + Pool1.get(i)[4].toString() + "-" + Pool1.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool1.get(i)[0].toString() + "Po1";  //Pool Chrom 
                LinkData[2] = Pool1.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool1.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Pool1.get(i)[0].toString() + "Pa2";  //Parent Chrom
                LinkData[6] = Parent2.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent2.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }

        //Pool 2 vs Parent 1
        for (int i = 0; i < Pool2.size(); i++) {
            //if the MAF value is more than 0.97 add it to the LINK data as a line 
            if ((Double) Pool2.get(i)[3] > 0.97 && (Double) Parent1.get(i)[3] > 0.97) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool2.get(i)[0].toString() + "-M+" + " to " + Parent1.get(i)[0] + ": " + Pool2.get(i)[4].toString() + "-" + Pool2.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool1.get(i)[0].toString() + "Po2";  //Pool Chrom 
                LinkData[2] = Pool2.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool2.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Pool1.get(i)[0].toString() + "Pa1";  //Parent Chrom
                LinkData[6] = Parent1.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent1.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }
        //Pool 2 vs Parent 2
        for (int i = 0; i < Pool2.size(); i++) {
            //if the MAF value is more than 0.97 add it to the LINK data as a line 
            if ((Double) Pool2.get(i)[3] > 0.97 && (Double) Parent2.get(i)[3] > 0.97) {
                Object[] LinkData = new Object[9];
                LinkData[0] = Pool2.get(i)[0].toString() + "-M+" + " to " + Parent2.get(i)[0] + ": " + Pool2.get(i)[4].toString() + "-" + Pool2.get(i)[5].toString();  //Linkname
                LinkData[1] = Pool1.get(i)[0].toString() + "Po2";  //Pool Chrom 
                LinkData[2] = Pool2.get(i)[4].toString();  //Pool Bin Start
                LinkData[3] = Pool2.get(i)[5].toString();  //Pool Bin End
                LinkData[4] = "DEADMAU5";  //Pool end name
                LinkData[5] = Pool1.get(i)[0].toString() + "Pa2";  //Parent Chrom
                LinkData[6] = Parent2.get(i)[4].toString();  //Parent Bin Start
                LinkData[7] = Parent2.get(i)[5].toString();  //Parent Bin End    
                LinkData[8] = "Rulez";  //Parent End Name    
                LinkValues.add(LinkData);
            }
        }
        return LinkValues;
    }
    /**
     * Method to merge any Adjacent Bins and replace multiple lines with one line in the LinkValues file that will be sent to BioCircos
     * @param LinkValues
     * @param BinSize
     * @return 
     */
    public static ArrayList<Object[]> BinMerger(ArrayList<Object[]> LinkValues, Integer BinSize) {
        ArrayList<Object[]> MergedBins = new ArrayList();
        Object NewEndPoint = 0;
        Integer Count = 0;
        //for all link values
        for (int i = 0; i < LinkValues.size() - 2; i++) {
            //if the end point of one bin and start of ther next are the same
            if (LinkValues.get(i)[3].equals(LinkValues.get(i + 1)[2])) {
                NewEndPoint = LinkValues.get(i + 1)[3]; //new end point is the end of the last non adjacent bin
                Count += 1; //number of bins that are adjacent
                //if the values are finally not the same, add the new line
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
               
                //else if the value has no acjacent bins, just throw it in
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
    /**
     * BSADataWriter adds all the data to an object of type LinkData, in the final format to be sent to the BioCircos functions
     * @param LinkValues
     * @return 
     */
    public static List<LinkData> BSADataWriter(ArrayList<Object[]> LinkValues) {
        List<LinkData> linkData = new ArrayList();

        for (int i = 0; i < LinkValues.size(); i++) {
            LinkData point = new LinkData(LinkValues.get(i)[0].toString(), //linkNAme
                    LinkValues.get(i)[1].toString(),    //link chr 1
                    Integer.parseInt(LinkValues.get(i)[2].toString()), //gene 1
                    Integer.parseInt(LinkValues.get(i)[3].toString()), //start
                    LinkValues.get(i)[4].toString(),//stop
                    LinkValues.get(i)[5].toString(), // chr 2
                    Integer.parseInt(LinkValues.get(i)[6].toString()), //gene 2
                    Integer.parseInt(LinkValues.get(i)[7].toString()), //start
                    LinkValues.get(i)[8].toString()); //stop
            linkData.add(point);
        }
        return linkData;
    }
    
    /**
     * Returns the LinkData to the BioCircos Render function
     * @param LinkData
     * @return
     * @throws IOException 
     */ 
    public static Link BSALinkWriter(List<LinkData> LinkData) throws IOException {     
        Link l = new Link();
        l.setLinkId("LINK01");
        l.setProperties(new LinkProperties(140, "#ff0000", 15, "false", "#B8B8B8", 0.5, 3, "false", "red", 13, 8));
        l.setLinkData(LinkData);
        return l;

    }

}
