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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import uk.ac.cranfield.bix.controllers.rest.DataPoint;
import uk.ac.cranfield.bix.controllers.rest.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HistogramProperties;

/**
 *
 * @author Adoni5
 */
public class VCFParsers {
    // initialise variables

    /**
     *
     * @param filepath
     */
    public void VcfToolsSNPS(String filepath, String filename) {
        //Run VCFtools to output an SNP only VCF file, with the filename + SNPsONly as title
        //cmdArray, each string is an argument
        String[] cmdArray = new String[6];
        cmdArray[0] = "C:\\Windows\\System32\\bash.exe";
        cmdArray[1] = "vcftools";
        cmdArray[2] = "--vcf " + filepath; //file extension, reads "--vcf" etc //filepath and path "path/to/example.vcf"
        cmdArray[3] = "--out " + filename + "SNPsOnly";
        cmdArray[4] = "--remove-indels";
        cmdArray[5] = "--recode";
        try {
            //process builder runs the cmd Array
            ProcessBuilder probuilder = new ProcessBuilder(cmdArray);

            Process process = probuilder.start();
            //Process process = Runtime.getRuntime().exec(cmdArray);
        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//run VCF tools to get a SNP density per N bases, lat line of CMD array sets this
    public void VcfToolsSNPDensity(ArrayList<Integer> seq, Integer i, String SnpDenseFilepath, String SNPfilename) {
        //each string is an argument
        String[] cmdArray = new String[8];
        cmdArray[0] = "C:\\Windows\\System32\\bash.exe";
        cmdArray[1] = "vcftools";
        cmdArray[2] = "--vcf " + SnpDenseFilepath; //file extension, reads "--vcf" etc //filepath and path "path/to/example.vcf"
        cmdArray[3] = "--out " + SNPfilename + "SNPsOnly" + i;
        cmdArray[4] = "--chr ";
        cmdArray[5] = i.toString();
        cmdArray[6] = "--SNPdensity ";
        cmdArray[7] = Integer.toString(seq.get(i) / 10);
        try {
            ProcessBuilder probuilder = new ProcessBuilder(cmdArray);

            Process process = probuilder.start();
            //Process process = Runtime.getRuntime().exec(cmdArray);
        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getHistSNPDense(String SnpDenseFilepath, String SNPfilename) {
        //This is just to get the SNP density file, can be replaced with the drag and drop filepath
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("VCF Files", "vcf"));
        chooser.showOpenDialog(chooser);
        chooser.setVisible(true);
        File SNPdense = chooser.getSelectedFile();
        SnpDenseFilepath = SNPdense.getAbsolutePath();
        SNPfilename = SNPdense.getName();
        if (SNPfilename.indexOf(".") > 0) {
            SNPfilename = SNPfilename.substring(0, SNPfilename.lastIndexOf("."));
        }
        System.out.println(SnpDenseFilepath);
        System.out.println("Density file: " + SNPfilename);
        return SnpDenseFilepath;
    }

    public List<DataPoint> HtmlDataPoints(ArrayList<String[]> list) {
        //Creates string that contains all the data points in correct fromat for SNP module in BioCircos
        //However it turns out that there is way too many to display, literally cuases a stack overflow
        List<DataPoint> Data = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            DataPoint point = new DataPoint(list.get(i)[0].substring(list.get(i)[0].length() - 2, list.get(i)[0].length()), Integer.parseInt(list.get(i)[1]), Integer.parseInt(list.get(i)[5]), list.get(i)[4], "rgb(153,102,0)");
            Data.add(point);
        }
        return Data;
    }

    public ArrayList<String[]> VCFparser(String filepath) {
        try {
            //Reads the VCF file,  splits by tab, adds each Line as a list containg each column entry
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            ArrayList<String[]> list = new ArrayList();
            try {
                while ((line = br.readLine()) != null) {

                    if (line.charAt(0) != '#') {
                        list.add(line.split("\\t"));
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
            }
            return list;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<String[]> VCFHistParser(String SnpDenseFilepath) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(SnpDenseFilepath));
        ArrayList<String[]> HistList = new ArrayList();
        //Reads VCF file line by line, adds relevant lines as lists of column elements
        //ArrayList to store MetaData Stores Meta Data
        ArrayList UselessJunk = new ArrayList();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("BIN_START")) {
                    UselessJunk.add(line);
                } else {
                    HistList.add(line.split("\\s"));
                    System.out.println(Arrays.toString(line.split("\\s")));
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return HistList;

        //Once you have it invoke -> HistWriter
    }

    public List<HistogramDataPoint> HistogramData(ArrayList<String[]> HistList, String filename, Integer bean) throws IOException {
        //Interval is the width of the bin for the histogram
        Integer interval = bean;
        //HistData is string that contains the data in the correct format for the Histogram module of BioCircos
        List<HistogramDataPoint> HistData = new ArrayList();
        for (int i = 0; i < HistList.size(); i++) {
            if (HistList.get(i)[1].equalsIgnoreCase("0")) {
                HistogramDataPoint histPoint = new HistogramDataPoint(HistList.get(i)[0], 1, Integer.parseInt(HistList.get(i)[1]) + interval, "Hist", Double.parseDouble(HistList.get(i)[3]));
                HistData.add(histPoint);
            } else {
                HistogramDataPoint histPoint = new HistogramDataPoint(HistList.get(i)[0], Integer.parseInt(HistList.get(i)[1]) + 4, Integer.parseInt(HistList.get(i)[1]) + interval, "Hist", Double.parseDouble(HistList.get(i)[3]));
                HistData.add(histPoint);
            }

        }
        return HistData;
    }

    public static Histogram HistWriter(List<HistogramDataPoint> HistData) {

        //create histogram object
        Histogram hist = new Histogram();

        //create historgram properties object
        HistogramProperties histProperties = new HistogramProperties();
        histProperties.setHistogramFillColor("#FF6666");
        histProperties.setMaxRadius(220);
        histProperties.setMinRadius(185);

        //set values of the object
        hist.setHistId("HISTOGRAM01");
        hist.setProperties(histProperties);
        hist.setHistDataPoint(HistData);

        return hist;
    }

}
