/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class IndvGff3Parser {

    public ArrayList<String[]> GffParser(String GffFilePath, ArrayList<String> Chrom, Integer i) {
        ArrayList<String[]> IndvKaryotype = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader(GffFilePath))) {
            String line;
            ArrayList metadata = new ArrayList();
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    metadata.add(line);
                } else if (line.split("\\t")[2].equalsIgnoreCase("gene") && line.split("\\t")[0].equalsIgnoreCase(Chrom.get(i))) {
                    IndvKaryotype.add(line.split("\\t"));
                    System.out.println(Arrays.toString(line.split("\\t")));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Gff3Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IndvKaryotype;
    }

    public List<GffDataPoint> IndvGffDataPoints(ArrayList<String[]> IndvKaryotype) {

        List<GffDataPoint> IndvGff3Data = new ArrayList();
        

        for (int i = 0; i < IndvKaryotype.size(); i++) {
            String geneName = IndvKaryotype.get(i)[8].split(";")[2].split("=")[1];
            if (IndvKaryotype.size() % 2 == 0) {
                GffDataPoint ggfPoint = new GffDataPoint(IndvKaryotype.get(i)[0], Integer.parseInt(IndvKaryotype.get(i)[3]), Integer.parseInt(IndvKaryotype.get(i)[4]), "rgb(240,231,36)",geneName);
                IndvGff3Data.add(ggfPoint);
            } else {
                GffDataPoint ggfPoint = new GffDataPoint(IndvKaryotype.get(i)[0], Integer.parseInt(IndvKaryotype.get(i)[3]), Integer.parseInt(IndvKaryotype.get(i)[4]), "rgb(0,0,0)",geneName);
                IndvGff3Data.add(ggfPoint);
            }
        }
        return IndvGff3Data;
    }

    public IndGff IndvGffWriter(List<GffDataPoint> IndvGff3Data) {
        //Create object to return 
        IndGff indGff = new IndGff();

        //create properties
        IndGffProperties properties = new IndGffProperties();
        properties.setInnerRadius(-0);
        properties.setOuterRadius(-0);
        
        //Set attributes values
        indGff.setIndGffid("ARC_01");
        indGff.setProperties(properties);
        indGff.setGffDataPoint(IndvGff3Data);
        
        return indGff;
    }
}
