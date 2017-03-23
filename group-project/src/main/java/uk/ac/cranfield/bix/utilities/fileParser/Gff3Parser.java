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
import uk.ac.cranfield.bix.controllers.rest.IndGff;
import uk.ac.cranfield.bix.controllers.rest.IndGffProperties;

/**
 *
 * @author Adoni5
 */
public class Gff3Parser {

    public ArrayList<String[]> GffParser(String Gff3filepath) {
        ArrayList<String[]> Karyotype = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(Gff3filepath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) != '#') {
                    if (line.split("\\t")[2].equalsIgnoreCase("gene")) {
                        Karyotype.add(line.split("\\t"));
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Gff3Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Karyotype;
    }

    public List<GffDataPoint> GffDataPoints(ArrayList<String[]> Karyotype) {

        List<GffDataPoint> Gff3Data = new ArrayList();
        for (int i = 0; i < Karyotype.size(); i++) {

            if (Karyotype.size() % 2 == 0) {
                GffDataPoint ggfPoint = new GffDataPoint(Karyotype.get(i)[0], Integer.parseInt(Karyotype.get(i)[3]), Integer.parseInt(Karyotype.get(i)[4]), "rgb(255,255,255)");
                Gff3Data.add(ggfPoint);
                Karyotype.remove(0);
            } else {
                GffDataPoint ggfPoint = new GffDataPoint(Karyotype.get(i)[0], Integer.parseInt(Karyotype.get(i)[3]), Integer.parseInt(Karyotype.get(i)[4]), "rgb(0,0,0)");
                Gff3Data.add(ggfPoint);
                Karyotype.remove(0);
            }
        }
        return Gff3Data;
    }

    public static IndGff GffWriter(List<GffDataPoint> Gff3Data) {
        
        //Create object to return
        IndGff gff = new IndGff();
        
        IndGffProperties properties = new IndGffProperties();
        properties.setInnerRadius(-100);
        properties.setOuterRadius(-100);

        gff.setIndGffid("ARC_01");
        gff.setProperties(properties);
        gff.setGffDataPoint(Gff3Data);
        return gff;
    }

}
