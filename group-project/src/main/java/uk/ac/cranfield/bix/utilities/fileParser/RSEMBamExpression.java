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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To produce heatMap for gene expression methods fron differential expression can be used. Only the parser id different
 * @author s263839
 */
public class RSEMBamExpression {

    //Parse Line by Line the RSEM file
    public static ArrayList<String[]> RsemGenesResultsParser(String RsemFilePath) throws IOException {
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
}
