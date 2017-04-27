/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineProperties;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;

/**
 *
 * @author s263839
 */
public class Coverage_Genomic {
/**
 * VcfLineParsers returns a List of strings, each element is a line of the vcf 
 * @param VCFfilePath
 * @return 
 */
    public static List<String> VCFLineParser(String VCFfilePath) {
//        List<List<String[]>> list = new ArrayList();
//        List<String[]> meta = new ArrayList();
        List<String> VCFlines = new ArrayList();
//        String pattern = "##contig=<ID=([.A-Za-z0-9]+),length=([0-9]+)>";
//        Pattern p = Pattern.compile(pattern);

        try {
            //Reads the VCF file,

            BufferedReader br = new BufferedReader(new FileReader(VCFfilePath));
            String line;
            try {
                while ((line = br.readLine()) != null) {
//                   
//                        if(line.contains("##contig")){
//                            Matcher m = p.matcher(line);
//                            if(m.find()){
//                                meta.add(new String[]{m.group(1), m.group(2)});
//                            }                               
//                        }else 
                    if (line.charAt(0) != '#') {
                        VCFlines.add(line);
                    }

                }
//                list.add(meta);
//                list.add(VCFlines);
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return VCFlines;
    }
    /**
     * VCFPArserMeta returns the Chromosome length and name from the VCF file, in a string array
     * @param VCFfilePath
     * @return 
     */
    public static List<String[]> VCFParserMeta(String VCFfilePath) {

        List<String[]> meta = new ArrayList();
        String pattern = "##contig=<ID=([.A-Za-z0-9]+),length=([0-9]+)>";
        Pattern p = Pattern.compile(pattern);

        try {
            //Reads the VCF file,  splits by tab, adds each Line as a list containg each column entry

            BufferedReader br = new BufferedReader(new FileReader(VCFfilePath));
            String line;
            try {
                while ((line = br.readLine()) != null) {

                    if (line.contains("##contig")) {
                        Matcher m = p.matcher(line);
                        if (m.find()) {
                            meta.add(new String[]{m.group(1), m.group(2)});
                        }
                    }

                }

                br.close();
            } catch (IOException ex) {
                Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meta;
    }
    /**
     * VCFDepthExtract extracts the depth from the VCF file line list from VCFLineparser
     * @param li
     * @return 
     */
    public static ArrayList<Object[]> VCFDepthExtract(List<String> li) {

        ArrayList<Object[]> DPValues = new ArrayList();

        for (int i = 0; i < li.size(); i++) {
            String[] line = li.get(i).split("\\t");
            String[] Depth = line[7].split(";");
            for (int j = 0; j < Depth.length; j++) {
                if (Depth[j].contains("DP")) {
                    Object[] VCFdata = new Object[3];

                    VCFdata[0] = (Depth[j].substring(3)); //Dp at SNP
                    VCFdata[1] = line[0]; //chrom
                    VCFdata[2] = line[1]; //Pos
                    DPValues.add(VCFdata);
                    break;
                }
            }
        }
        return DPValues;
    }
    /**
     * Sort to bins sorts the coverage into bins of set size 1M bases , returns an object array of the bins, and the average depth in that Bin
     * @param meta
     * @param DPValues
     * @return 
     */
    public static ArrayList<Object[]> SortToBins(List<String[]> meta, ArrayList<Object[]> DPValues) {
        Integer BinSize = 1000000;
        //BinNUmber contains the NUmber of bins in each chromosome 
        ArrayList<Integer> BinNumber = new ArrayList();
       
        List<List<Object[]>> lists = new ArrayList<>();
        ArrayList<Object[]> DataValues = new ArrayList();

        //calculatethe number of bins in each chromosome, add it to binnumber
        for (int k = 0; k < meta.size(); k++) {
            BinNumber.add(Integer.parseInt(meta.get(k)[1]) / BinSize);
        }
        //create a list of the DPValues that match a certain chromosome, and add that list to an overall list of lists
        for (int i = 0; i < meta.size(); i++) {
            List<Object[]> list = new ArrayList();
            System.out.println(meta.get(i)[0]);
            for (int j = 0; j < DPValues.size(); j++) {
                //if The chromsome value is the same for the DP value as the meta data
                if (DPValues.get(j)[1].equals(meta.get(i)[0])) {

                    list.add(DPValues.get(j));
                }

            }
            lists.add(list);
        }
        Double DepthInBin = 0.00;
        Integer Count = 0;
        Double AvgValue = 0.00;
        for (int i = 0; i < lists.size(); i++) { // for all chromsomes (The number of lists in lists)

            for (int j = 1; j <= BinNumber.get(i); j++) { // for all bins in that chromosome

                for (int l = 0; l < lists.get(i).size(); l++) { // for all entries under that chromosomome
                    //if the line pos is inside the value of the current bin, add the depth to the total, and add 1 to the count
                    if (Integer.parseInt(lists.get(i).get(l)[2].toString()) > (BinSize * (j - 1)) && Integer.parseInt(lists.get(i).get(l)[2].toString()) < (BinSize * j)) {
                        DepthInBin = DepthInBin + Integer.parseInt(lists.get(i).get(l)[0].toString());
                        Count += 1;
                        //if the line pos is outside the current bin, calculate the average and add it to the Datavalues array listy
                    } else if (Integer.parseInt(lists.get(i).get(l)[2].toString()) > (BinSize * j)) {
                        Object[] values = new Object[4];
                        AvgValue = (DepthInBin / Count) / 10;
                        String Chrom = meta.get(i)[0];
                        values[0] = (double) Math.round(AvgValue * 100d) / 100d; //Value
                        values[1] = BinSize * (j - 1); // Start
                        values[2] = BinSize * j; //end
                        values[3] = Chrom;
                        DataValues.add(values);
                        DepthInBin = 0.00;
                        Count = 0;
                        break;
                    }
                }
            }
        }
        return DataValues;
    }
/**
 * Method to line data point object to be passed to BioCircos
 * @param DataValues
 * @return 
 */
    public static List<LineDataPoint> CoverageData(ArrayList<Object[]> DataValues) {
        Integer BinSize = 1000000;
        List<LineDataPoint> Data = new ArrayList();
        for (int j = 0; j < DataValues.size(); j++) {

            //append the data to a string in the right format
            LineDataPoint point = new LineDataPoint(DataValues.get(j)[3].toString(), (Integer) DataValues.get(j)[1] + ((Integer) DataValues.get(j)[2] - (Integer) DataValues.get(j)[1]) / 2, (Double) DataValues.get(j)[0]);
            Data.add(point);
        }
        return Data;
    }
    /**
     * Return a fully formatted object for processing by biocircos
     * @param Data
     * @return
     * @throws IOException 
     */
    public static Line GenomeCoverageWriter(List<LineDataPoint> Data) throws IOException {

        //Create line object
        Line l = new Line();
        //Set line id
        l.setLineId("LINE01");

        //Create line properties
        LineProperties prop = new LineProperties();
        prop.setMaxRadius(60);
        prop.setMinRadius(30);
        prop.setLineWidth(1);
        prop.setLineColor("#FF6666");
        //Set line properties
        l.setProperties(prop);
        //Set list of line point 
        l.setLinePoints(Data);

        return l;
    }

}
