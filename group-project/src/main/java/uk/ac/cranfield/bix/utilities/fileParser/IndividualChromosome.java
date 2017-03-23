/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Adoni5
 */
public class IndividualChromosome {
    StringBuffer IndvData;
    
    

    
    public String ChromDataPoints(ArrayList<String> Chrom, ArrayList<Integer> Seqlen, Integer i){
    
        StringBuffer sb = new StringBuffer();    
        IndvData = (sb.append("[").append("\"").append(Chrom.get(i).trim()).append("\"").append(",").append(Seqlen.get(i)).append("],\n"));
        return IndvData.toString();
    }   
}
