/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.IndGffProperties;

/**
 *
 * @author Adoni5
 */
public class Gff3Parser {

    /**
     *GffParse Method to read a Gff file and parse line by line, adding only lines about genes to a List of Strings
     * @param Gff3filepath
     * @return
     */
    public static List<String[]> GffParser(String Gff3filepath) {
        List<String[]> Karyotype = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(Gff3filepath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    //split by tab and add genes lines
                    if (line.split("\\t")[2].equalsIgnoreCase("gene")) {
                        Karyotype.add(line.split("\\t"));
                    }
                }
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(Gff3Parser.class.getName()).log(Level.SEVERE, null, ex);
        }   
        //return gene data
        return Karyotype;
    }
    
    /**
     * GffDataPoints, creates a list with all the necessary data that can be organised  to be displayed in circos
     * @param Karyotype
     * @return 
     */
    public static List<GffDataPoint> GffDataPoints(List<String[]> Karyotype) {

        List<GffDataPoint> Gff3Data = new ArrayList();
        for (int i = 0; i < Karyotype.size(); i++) {
            //GeneName
            String geneName = Karyotype.get(i)[8].split(";")[2].split("=")[1];
            //alternate the colour, by sorting odd and even between red and black
            //red
            if (Karyotype.size() % 2 == 0) {
                GffDataPoint ggfPoint = new GffDataPoint(Karyotype.get(i)[0], Integer.parseInt(Karyotype.get(i)[3]), Integer.parseInt(Karyotype.get(i)[4]), "rgb(255,255,255)",geneName);
                Gff3Data.add(ggfPoint);
                Karyotype.remove(0);
                //black
            } else {
                GffDataPoint ggfPoint = new GffDataPoint(Karyotype.get(i)[0], Integer.parseInt(Karyotype.get(i)[3]), Integer.parseInt(Karyotype.get(i)[4]), "rgb(0,0,0)", geneName);
                Gff3Data.add(ggfPoint);
                Karyotype.remove(0);
            }
        }
        //Gff3data is all the data points in the ARC format
        return Gff3Data;
    }
    /**
     * GffWriter - method returns the fully format object for BioCircos
     * @param Gff3Data
     * @return 
     */
    public static IndGff GffWriter(List<GffDataPoint> Gff3Data) {
        
        //Create object to return
        IndGff gff = new IndGff();
        //HOLLAATYA
        //set the Arc porperties
        IndGffProperties properties = new IndGffProperties();
        properties.setInnerRadius(-10);
        properties.setOuterRadius(-40);
        
        gff.setIndGffid("ARC_01");
        gff.setProperties(properties);
        //set the Datapoints
        gff.setGffDataPoint(Gff3Data);
        return gff;
    }

}
