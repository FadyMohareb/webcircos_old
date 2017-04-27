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
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
     * @return
     */
    public static String VcfToolsSNPS(String filepath) throws InterruptedException, IOException {
        //Run VCFtools to output an SNP only VCF file, with the filename + SNPsONly as title
        //cmdArray, each string is an argument
        File VCF = new File(filepath);
        String filename = VCF.getName();

        //need the file directory. Can take the absolute path and then remove the file name
        int index = filepath.lastIndexOf("/");
        String pathWithoutName = filepath.substring(0, index + 1);

        String path = pathWithoutName + filename;

        String line = "/usr/local/bin/vcftools --vcf " + filepath + " --out " + path + " --remove-indels --recode --recode-INFO-all";

        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec(line);
//            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            while((s = br.readLine()) != null)
//                System.out.println("line: " +s);
            p.waitFor();
            System.out.println("exit:" + p.exitValue());
            p.destroy();

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return path;
    }

//run VCF tools to get a SNP density per N bases, lat line of CMD array sets this
    //The file is the output from above method.
    public static String VcfToolsSNPDensity(String SnpDenseFilepath, String state, List<String[]> meta) throws InterruptedException {
        //each string is an argument
        String[] cmdArray = new String[2];

        File SNPdense = new File(SnpDenseFilepath);

        //get the file name
        String SNPfilename = SNPdense.getName();
        if (SNPfilename.indexOf(".") > 0) {
            SNPfilename = SNPfilename.substring(0, SNPfilename.lastIndexOf("."));
        }

        //need the file directory. Can take the absolute path and then remove the file name
        int index = SnpDenseFilepath.lastIndexOf("/");
        String pathWithoutName = SnpDenseFilepath.substring(0, index + 1);

        String path;
        String line;
        if (state.equals("genome")) {
            path = pathWithoutName + SNPfilename + "SNPsOnly";
            line = "/usr/local/bin/vcftools --vcf " + SnpDenseFilepath + " --out " + path + " --SNPdensity 1000000";
        } else {
            path = pathWithoutName + SNPfilename + "SNPsOnlyChrom";
            Integer bin = 1000000 / meta.size();
            line = "/usr/local/bin/vcftools --vcf " + SnpDenseFilepath + " --out " + path + " --SNPdensity " + bin.toString();
        }

        System.out.println("command " + line);

        String s;
        Process runtime;
        try {

            runtime = Runtime.getRuntime().exec(line);
            BufferedReader br = new BufferedReader(new InputStreamReader(runtime.getInputStream()));
            while ((s = br.readLine()) != null) {
                System.out.println("line: " + s);
            }
            runtime.waitFor();
            System.out.println("exit:" + runtime.exitValue());
            runtime.destroy();

        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }

        return path + ".snpden";
    }

    public static ArrayList<String[]> VCFHistParser(String SnpDenseFilepath) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File(SnpDenseFilepath)));
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

    public static List<HistogramDataPoint> HistogramData(ArrayList<String[]> HistList, String state, List<String[]> meta) throws IOException {
        //Interval is the width of the bin for the histogram
        Integer interval;
        if (state.equals("genome")) {
            interval = 1000000;
        }else{
            interval = 1000000/meta.size();   
        }

        //HistData is string that contains the data in the correct format for the Histogram module of BioCircos
        List<HistogramDataPoint> HistData = new ArrayList();
        for (int i = 0; i < HistList.size(); i++) {
            //take the HistList.get(i)[2] and add it at the end to have the SNP count. 
            if (HistList.get(i)[1].equalsIgnoreCase("0")) {
                HistogramDataPoint histPoint = new HistogramDataPoint(HistList.get(i)[0], 1, Integer.parseInt(HistList.get(i)[1]) + interval, "Hist", Double.parseDouble(HistList.get(i)[3]), HistList.get(i)[2] + " per million");
                HistData.add(histPoint);
            } else {
                HistogramDataPoint histPoint = new HistogramDataPoint(HistList.get(i)[0], Integer.parseInt(HistList.get(i)[1]) + 4, Integer.parseInt(HistList.get(i)[1]) + interval, "Hist", Double.parseDouble(HistList.get(i)[3]), HistList.get(i)[2] + " per million");
                HistData.add(histPoint);
            }

        }
        return HistData;
    }

    public static Histogram HistWriter(List<HistogramDataPoint> HistData, String id) {

        //create histogram object
        Histogram hist = new Histogram();

        //create historgram properties object
        HistogramProperties histProperties = new HistogramProperties();
        histProperties.setHistogramFillColor("#FF6666");
        histProperties.setMaxRadius(100);
        histProperties.setMinRadius(75);

        //set values of the object
      
        hist.setHistId(id);
        hist.setProperties(histProperties);
        hist.setHistDataPoint(HistData);

        return hist;
    }

}
