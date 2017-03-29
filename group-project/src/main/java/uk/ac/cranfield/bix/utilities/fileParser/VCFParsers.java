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
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
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
     * @param filename
     */
    public static void VcfToolsSNPS(String filepath) {
        //Run VCFtools to output an SNP only VCF file, with the filename + SNPsONly as title
        //cmdArray, each string is an argument
        String[] cmdArray = new String[6];
        
        File VCF = new File(filepath);
        String filename = VCF.getName(); 
        
         //need the file directory. Can take the absolute path and then remove the file name
        int index = filepath.lastIndexOf("\\");
        String pathWithoutName = filepath.substring(0, index+1);
        
        cmdArray[0] = "C:\\Windows\\System32\\bash.exe";
        cmdArray[1] = "vcftools";
        cmdArray[2] = "--vcf " + filepath; //file extension, reads "--vcf" etc //filepath and path "path/to/example.vcf"
        cmdArray[3] = "--out " + pathWithoutName + filename + "SNPsOnly"; //choose the user directory.
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
    public static void VcfToolsSNPDensity(ArrayList<Integer> seq, String SnpDenseFilepath) {
        //each string is an argument
        String[] cmdArray = new String[8];
        
        File SNPdense = new File(SnpDenseFilepath);
        
        //get the file name
        String SNPfilename = SNPdense.getName();
            if (SNPfilename.indexOf(".") > 0){
            SNPfilename = SNPfilename.substring(0, SNPfilename.lastIndexOf("."));
            } 
            
        //need the file directory. Can take the absolute path and then remove the file name
        int index = SnpDenseFilepath.lastIndexOf("\\");
        String pathWithoutName = SnpDenseFilepath.substring(0, index+1);
   
        cmdArray[0] = "C:\\Windows\\System32\\bash.exe";
        cmdArray[1] = "vcftools";
        cmdArray[2] = "--vcf " + SnpDenseFilepath; //file extension, reads "--vcf" etc //filepath and path "path/to/example.vcf"
        cmdArray[3] = "--out " + pathWithoutName + SNPfilename + "SNPsOnly"; //choose the right directory
        cmdArray[6] = "--SNPdensity ";
        cmdArray[7] = "1000000";
        try {
            ProcessBuilder probuilder = new ProcessBuilder(cmdArray);

            Process process = probuilder.start();
            //Process process = Runtime.getRuntime().exec(cmdArray);
        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String[]> VCFHistParser(String SnpDenseFilepath) throws FileNotFoundException {
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

    public static List<HistogramDataPoint> HistogramData(ArrayList<String[]> HistList, String filename, Integer bean) throws IOException {
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
