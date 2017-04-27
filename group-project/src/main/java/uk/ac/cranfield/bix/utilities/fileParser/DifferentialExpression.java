/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HeatMapProperties;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.HeatMap;

/**
 *
 * @author s263839
 */
public class DifferentialExpression {


    /**
     * GffPArserExpression is a method that parses a GFF file and gets the data and meta data into two separate lists
     * @param GffFilePath
     * @return
     * @throws IOException 
     */
    public static List<List<String[]>> GffParserExpression(String GffFilePath) throws IOException {
        List<String[]> GffFile = new ArrayList();
        List<String[]> metadata = new ArrayList();

        List<List<String[]>> list = new ArrayList();
        //read Gff file
        try (BufferedReader br = new BufferedReader(new FileReader(GffFilePath))) {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    //get metadata into list
                    if (line.charAt(0) == '#') {
                        if (line.startsWith("##sequence")) {
                            //split line by tab and only keep 1 and 3
                            String[] split = line.split("\\s");
                            String[] s = new String[2];
                            s[0] = split[1];
                            s[1] = split[3];
                            metadata.add(s);
                        }
                        //get gene values 
                    } else {
                        if (line.split("\\s")[2].equalsIgnoreCase("gene")) {
                            GffFile.add(line.split("\\s"));
                            System.out.println(Arrays.toString(line.split("\\s")));
                        }
                    }

                }
                list.add(metadata);
                list.add(GffFile);
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
        }
        //list, a list of lists, contains metadata at index 0 and Gene lines at index 1
        return list;
    }
    /**
     * EbSeqParseq parser parsers ebseq files and gets all the values
     * @param EbseqFilePath
     * @return
     * @throws IOException 
     */
    public static ArrayList<String[]> EbSeqParser(String EbseqFilePath) throws IOException {
        ArrayList<String[]> Ebseq = new ArrayList();
        try (BufferedReader br2 = new BufferedReader(new FileReader(EbseqFilePath))) {
            String line;
            try {
                while ((line = br2.readLine()) != null) {
                    //add line data to arraylists split by whitespace
                    if (line.contains("PPEE")) {
                    } else {
                        Ebseq.add(line.split("\\s"));
                        System.out.println(line);
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Ebseq;
    }
    /**
     * gffsorter sorts the Gff file values by position and chromsome
     * @param li
     * @return 
     */
    public static ArrayList<String[]> gffSorter(List<List<String[]>> li) {
        ArrayList<String[]> SortedGff = new ArrayList();
        //SO this can be replaced with "sort -k 4 -n" on the command line
        //But why would you? This convoluted method is WAAAAAY better, and probably *maybe* just as accurate

        //add the Gffile lines into a HashMap, using the end position as a key
        List<String[]> GffFile = li.get(1);
        HashMap<Integer, String[]> tosort = new HashMap();
        for (int i = 0; i < GffFile.size(); i++) {
            tosort.put(Integer.parseInt(GffFile.get(i)[4]), GffFile.get(i));
        }

        //add the HAshMap to a TreeMap, which sorts by Key Value, ordering the file Lines from smallest to largest end position
        Map<Integer, String[]> Sortedmap = new TreeMap<>(tosort);

        //add keys to ArrayList to pull vlaues in next for loop
        Set keys = Sortedmap.keySet();
        ArrayList<Integer> Keys = new ArrayList(keys);

        //Add the sorted lines to an arrayList
        for (int i = 0; i < Sortedmap.size(); i++) {

            SortedGff.add(Sortedmap.get(Keys.get(i)));
            System.out.println(Arrays.toString(SortedGff.get(i)));
        }
        return SortedGff;
    }
    /**
     * EbSeqdata - a method to sort the lines into chromosomes based on line numbers in each file
     * @param li
     * @param SortedGff
     * @param view
     * @return 
     */
    public static ArrayList<Object[]> EbseqData(List<List<String[]>> li, ArrayList<String[]> SortedGff, String view) {
        ArrayList<Integer> BinNumber = new ArrayList();
        Integer BinSize;
        if (view.equals("genome")) {
            BinSize = 1000000;

        }else{
            BinSize = 1000000/li.get(0).size();
        }

        //List of lists, that contains a list for each chromomsome, and the lines from the GFF file relating to that chromosome
        List<List<String[]>> lists = new ArrayList<>();

        //Object Array that contains the number RSEM file lines on each bin, the chromosome , Start and stop of each Bin
        ArrayList<Object[]> LineNumbers = new ArrayList();

        //Retrieve chromosome and length;
        List<String[]> metadata = li.get(0);

        //populates BinNumber ArrayList with the Number of Bins in Each Chromosome
        for (int k = 0; k < metadata.size(); k++) {
            BinNumber.add(Integer.parseInt(metadata.get(k)[1]) / BinSize);
        }

        //Creates a list for each chromosome and Splits the Sorted GFF file ArrayList by Chromosome;
        for (int i = 0; i < metadata.size(); i++) {
            List<String[]> list = new ArrayList();
            System.out.println(metadata.get(i)[0]);
            for (int j = 0; j < SortedGff.size(); j++) {

                if (SortedGff.get(j)[0].equals(metadata.get(i)[0])) {

                    list.add(SortedGff.get(j));
                }

            }
            lists.add(list);
        }
        //lists should now be a list of lists containing lines respective to each chromosome in fasta file
        Integer LineCount = 0;
        //ArrayList LineNumber = new ArrayList();
        for (int i = 0; i < lists.size(); i++) { // for all chromsomes (The number of lists in lists)

            for (int j = 1; j <= BinNumber.get(i); j++) { // for all bins in that chromosome

                for (int l = 0; l < lists.get(i).size(); l++) { // for all entries under that chromosomome

                    //if the end position of this line is inside the Bin Limits, Add one to the Line counter
                    if (Integer.parseInt(lists.get(i).get(l)[4]) > (BinSize * (j - 1)) && Integer.parseInt(lists.get(i).get(l)[4]) < (BinSize * j)) {

                        LineCount = LineCount + 1;
                    } //if the end position exceeds the Bin Limit
                    else if (Integer.parseInt(lists.get(i).get(l)[4]) > (BinSize * j)) {
                        //LineNumber contains the chromosome and the Number of lines in the bin and start and stop positions
                        Object[] string = new Object[4];
                        string[0] = LineCount.toString(); //The number of lines in the bin
                        string[1] = lists.get(i).get(l)[0]; // the Chromosome
                        string[2] = (BinSize * (j - 1)); //Start Position
                        string[3] = (BinSize * j); //Stop Position
                        //add The object array to the lineNumber ArrayList
                        LineNumbers.add(string);
                        //reset LineCount
                        LineCount = 0;

                        break;// break loop for this bin                            
                    }

                }
            }
        }
        return LineNumbers;
    }
    /**
     * DiffJavascriptwriter - a method to get the number of lines in a bin and then process those lines to get expression values for that bin.
     * @param Ebseq
     * @param LineNumbers
     * @param diffExprOrExpr
     * @return 
     */
    public static List<HeatMapDataPoint> DiffJavascriptWriter(ArrayList<String[]> Ebseq, ArrayList<Object[]> LineNumbers, String diffExprOrExpr) {

        //List of the Transcript per million(Tpm) values for the entries inside the limits of the bin
        ArrayList<ArrayList<Double>> BinTpms = new ArrayList();
        //The Tpm value       
        Double Tpm;

        ArrayList<Double> DiffHeatMapValues = new ArrayList();

        for (int k = 0; k < LineNumbers.size(); k++) {
            //for all the bins
            //Create an ArrayList for the Tpms inside the Bin
            ArrayList<Double> TpmValuesInBin = new ArrayList();

            for (int l = 0; l < Integer.parseInt((String) LineNumbers.get(k)[0]); l++) {
                //for all the RSEM file lines inside the limits of the current bin
                if (diffExprOrExpr.equalsIgnoreCase("differential Expression")) {
                    Tpm = Double.valueOf(Ebseq.get(l)[4]); //get the tpm value from the RSEM file for this line
                } else {
                    Tpm = Double.valueOf(Ebseq.get(l)[5]);
                }
                TpmValuesInBin.add(Tpm);
            }

            //Add the values contained in this Bin to the overall List
            BinTpms.add(TpmValuesInBin);

        }

        //get largest value in all Bins               
        //calculate values to be displayed
        // BELOW CODE NORAMALISES THE BINS, SO THERE IS NO VALUE GREATER THAN 1.0
        //for all bins
        for (int i = 0; i < BinTpms.size(); i++) {
            //for all the entries in this bin
            for (int j = 0; j < BinTpms.get(i).size(); j++) {
                //Get the largest value inside the bin
                Double LargestValueInBin = Collections.max(BinTpms.get(i));
                //Divide all the values in each bin, by the largest value in that Bin
                BinTpms.get(i).set(j, BinTpms.get(i).get(j) / LargestValueInBin);
            }
        }
        //BinTpms now contains lists, that hold the values found in each bin reduced to a lowest common denominator
        //So the max value should be 1
        //#hopefully

        //calculate the average value for each bin
        //for all the Bins
        for (int i = 0; i < BinTpms.size(); i++) {

            Double AverageValue = 0.00;
            //for all the elements in that Bin
            for (int j = 0; j < BinTpms.get(i).size(); j++) {
                //sum all the eleemnts in that bin
                AverageValue += BinTpms.get(i).get(j);

            }
            //Round to decimal points, add the sum of all the Bin Elements divided by the size of the bin to the HEatMap values ArrayList
            DiffHeatMapValues.add((double) Math.round(AverageValue / BinTpms.get(i).size() * 100d) / 100d);

        }
        //for all the HeatMapValues
        List<HeatMapDataPoint> heatMapDataPoint = new ArrayList();
        for (int j = 0; j < DiffHeatMapValues.size(); j++) {
            //append the data to a string in the right format
            HeatMapDataPoint point = new HeatMapDataPoint(LineNumbers.get(j)[1].toString(), 4 + (Integer) LineNumbers.get(j)[2], (Integer) LineNumbers.get(j)[3], diffExprOrExpr, DiffHeatMapValues.get(j));
            heatMapDataPoint.add(point);
        }
        return heatMapDataPoint;
    }

    //write out to a javascript file
    /**
     * Heatmapwriter returns the data points to the BioCircos function, in the correct format
     * @param heatMapDataPoint
     * @param innerRadius
     * @param outerRadius
     * @param id
     * @return
     * @throws IOException 
     */
    public static HeatMap HeatMapWriter(List<HeatMapDataPoint> heatMapDataPoint, Integer innerRadius, Integer outerRadius, String id) throws IOException {

        //Create heatMap 
       
        HeatMap heatMap = new HeatMap();
        heatMap.setHeatMapId(id);

        //Create heatMapProperties with the default value
        HeatMapProperties properties = new HeatMapProperties(innerRadius, outerRadius, "red", "yellow");
        heatMap.setProperties(properties);

        //Set the dataPoint
        heatMap.setHeatMapDataPoint(heatMapDataPoint);

        return heatMap;

    }
}
