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
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HistogramProperties;

/**
 *
 * @author Adoni5
 */
public class IndividualSNPs {

    public ArrayList<String[]> IndvVCFParse(String SnpFilePath, ArrayList<String> Chrom, Integer i) throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(SnpFilePath));
        //Reads VCF file line by line, adds relevant lines as lists of column elements
        //ArrayList to store MetaData Stores Meta Data
        ArrayList UselessJunk = new ArrayList();
        ArrayList<String[]> IndvHistList = new ArrayList();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("BIN_START")) {
                    UselessJunk.add(line);
                } else if (line.contains(Chrom.get(i))) {
                    IndvHistList.add(line.split("\\s"));
                    System.out.println(Arrays.toString(line.split("\\s")));
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IndvHistList;
    }

    public List<HistogramDataPoint> HistogramData(ArrayList<Integer> seqlen, ArrayList<String> Chr, String filename, Integer j, ArrayList<String[]> IndvHistList) throws IOException {
        //Interval is the width of the bin for the histogram
        Integer interval = 1000000;
        //HistData is string that contains the data in the correct format for the Histogram module of BioCircos
        List<HistogramDataPoint> IndvHistdata = new ArrayList();
       
        for (int i = 0; i < IndvHistList.size(); i++) {
            if (IndvHistList.get(i)[1].equalsIgnoreCase("0")) {
                HistogramDataPoint histPoint = new HistogramDataPoint(IndvHistList.get(i)[0],1,Integer.parseInt(IndvHistList.get(i)[1]) + interval,Chr.get(j),Double.parseDouble(IndvHistList.get(i)[3]));
                IndvHistdata.add(histPoint);
            } else {
                HistogramDataPoint histPoint = new HistogramDataPoint(IndvHistList.get(i)[0],Integer.parseInt(IndvHistList.get(i)[1]) + 4,Integer.parseInt(IndvHistList.get(i)[1]) + interval,Chr.get(j),Double.parseDouble(IndvHistList.get(i)[3]));
                IndvHistdata.add(histPoint);
            }
        }
        System.out.println(IndvHistdata);
        return IndvHistdata;
    }

    public Histogram HistWriter(List<HistogramDataPoint> IndvHistdata) {

        Histogram hist = new Histogram();
        
        hist.setHistId("HISTOGRAM01");
        HistogramProperties properties = new HistogramProperties();
        properties.setMaxRadius(220);
        properties.setMinRadius(185);
        properties.setHistogramFillColor("#FF6666");
        hist.setProperties(properties);
        hist.setHistDataPoint(IndvHistdata);

        return hist;
    }
}
