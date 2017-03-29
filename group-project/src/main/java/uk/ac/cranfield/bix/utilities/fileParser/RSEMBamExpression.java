/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import javax.swing.JFileChooser;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.HeatMap;
import uk.ac.cranfield.bix.controllers.rest.HeatMapProperties;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;

/**
 *
 * @author s263839
 */
public class RSEMBamExpression {

    //The width of the bin
    Integer BinSize = 1000000;

    //adds the GFF file lines, split by Whitespace to the GffFile1 ArrayList. Then return this list. 
    public ArrayList<String[]> RSEMParser(String Gfffilepath, ArrayList<String[]> GffFile1) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(Gfffilepath))) {
            String line;
            try {
                while ((line = br.readLine()) != null) {
                    //this should be fine, used to add to an arrayList, didn't test the current thing
                    if (line.charAt(0) == '#') {

                    } else {
                        if (line.split("\\s")[2].equalsIgnoreCase("gene")) {
                            GffFile1.add(line.split("\\s"));
                            System.out.println(Arrays.toString(line.split("\\s")));
                        }
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
        }
        return GffFile1;
    }

    //sorts the GffFile lines by End position column entries, as they weren't in order
    public ArrayList<String[]> sorter(ArrayList<String[]> GffFile1) {
        //SO this can be replaced with "sort -k 4 -n" on the command line
        //But why would you? This convoluted method is WAAAAAY better, and probably *maybe* just as accurate

        //add the Gffile lines into a HashMap, using the end position as a key
        HashMap<Integer, String[]> tosort = new HashMap();
        for (int i = 0; i < GffFile1.size(); i++) {
            tosort.put(Integer.parseInt(GffFile1.get(i)[4]), GffFile1.get(i));
        }

        //add the HAshMap to a TreeMap, which sorts by Key Value, ordering the file Lines from smallest to largest end position
        Map<Integer, String[]> Sortedmap = new TreeMap<>(tosort);

        //add keys to ArrayList to pull vlaues in next for loop
        Set keys = Sortedmap.keySet();
        ArrayList<Integer> Keys = new ArrayList(keys);
        ArrayList<String[]> GffFile2 = new ArrayList();

        //Add the sorted lines to an arrayList
        for (int i = 0; i < Sortedmap.size(); i++) {

            GffFile2.add(Sortedmap.get(Keys.get(i)));
            System.out.println(Arrays.toString(GffFile2.get(i)));
        }
        return GffFile2;
    }

    //Ok so, this is a little wierd, but this in the end gets the lines from within the RSEM file that are contained in the bin
    public ArrayList<Object[]> RSEMdata(ArrayList<String> Chr, ArrayList<Integer> SeqLen, ArrayList<String[]> GffFile2) {
        
        //ArrayList storing the amount of Bins in each chromosome
        //Basically just the length of each chromosome divided by the width of the bin
        ArrayList<Integer> BinNumber = new ArrayList();
        //populates BinNumber ArrayList with the Number of Bins in Each Chromosome

        //List of lists, that contains a list for each chromomsome, and the lines from the GFF file relating to that chromosome
        List<List<String[]>> lists = new ArrayList<>();

        //Object Array that contains the number RSEM file lines on each bin, the chromosome , Start and stop of each Bin
        ArrayList<Object[]> LineNumbers = new ArrayList();
        for (int k = 0; k < SeqLen.size(); k++) {
            BinNumber.add(SeqLen.get(k) / BinSize);
        }

        //Creates a list for each chromosome and Splits the Sorted GFF file ArrayList by Chromosome;
        for (int i = 0; i < SeqLen.size(); i++) {
            List<String[]> list = new ArrayList();
            System.out.println(Chr.get(i));
            for (int j = 0; j < GffFile2.size(); j++) {

                if (GffFile2.get(j)[0].equals(Chr.get(i))) {

                    list.add(GffFile2.get(j));
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

    //Parse Line by Line the RSEM file
    public ArrayList<String[]> ActualRsemGenesResultsParser(String RsemFilePath) throws IOException {
        //ArrayList for storing the RSEM file lines, split by whitespace
        ArrayList<String[]> RsemFile = new ArrayList();
        try (BufferedReader br2 = new BufferedReader(new FileReader(RsemFilePath))) {
            String line;
            try {
                while ((line = br2.readLine()) != null) {
                    //same as above, if this dosn't work can go back to add to arrayList
                    if (line.contains("gene_id")) {

                    } else {
                        RsemFile.add(line.split("\\s"));
                        System.out.println(line);
                    }

                }

            } catch (IOException ex) {
                Logger.getLogger(RSEMBamExpression.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return RsemFile;
    }

    //Get The Data in the correct format, using some sort of complicated magic
    public List<HistogramDataPoint> javascriptWriter(ArrayList<String[]> RsemFile, ArrayList<Object[]> LineNumbers) {

        StringBuilder sb = new StringBuilder();
        //List of the Transcript per million(Tpm) values for the entries inside the limits of the bin
        ArrayList<ArrayList<Double>> BinTpms = new ArrayList();
        //StringBuilder
        List<HistogramDataPoint> heatMapDataPoint = new ArrayList();
        //The Tpm value       
        Double Tpm;
        //Finally, an ArrayList that contians the values to be displayed in the heatmap
        ArrayList<Double> HeatMapValues = new ArrayList();

        for (int k = 0; k < LineNumbers.size(); k++) {
            //for all the bins
            //Create an ArrayList for the Tpms inside the Bin
            ArrayList<Double> TpmValuesInBin = new ArrayList();

            for (int l = 0; l < Integer.parseInt((String) LineNumbers.get(k)[0]); l++) {
                //for all the RSEM file lines inside the limits of the current bin
                Tpm = Double.valueOf(RsemFile.get(l)[5]); //get the tpm value from the RSEM file for this line
                TpmValuesInBin.add(Tpm);
            }
            //remove already processed lines from files
            for (int j = 0; j < Integer.parseInt((String) LineNumbers.get(k)[0]); j++) {
                RsemFile.remove(0);
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
            HeatMapValues.add((double) Math.round(AverageValue / BinTpms.get(i).size() * 100d) / 100d);

        }
        //for all the HeatMapValues
        for (int j = 0; j < HeatMapValues.size(); j++) {
            //create histogramDataPoint and add them in the listof heatMapDataPoint
            HistogramDataPoint point = new HistogramDataPoint(LineNumbers.get(j)[1].toString(), 4 + (Integer) LineNumbers.get(j)[2], (Integer) LineNumbers.get(j)[3], "RORY", HeatMapValues.get(j));
            heatMapDataPoint.add(point);
        }

        return heatMapDataPoint;

    }

    //write out to a javascript file
    public static HeatMap FileWriter(List<HistogramDataPoint> heatMapDataPoint) throws IOException {

        //Create heatMap 
        HeatMap heatMap = new HeatMap();
        heatMap.setHeatMapId("HEATMAP01");

        //Create heatMapProperties with the default value
        HeatMapProperties properties = new HeatMapProperties(-25, -65, "red", "yellow");
        heatMap.setProperties(properties);

        //Set the dataPoint
        heatMap.setHeatMapDataPoint(heatMapDataPoint);

        return heatMap;

    }
}
