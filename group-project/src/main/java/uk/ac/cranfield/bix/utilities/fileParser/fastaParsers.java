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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;

/**
 *
 * @author Adoni5
 */
public class fastaParsers {

    /**
     *fastaParser - method to read a fatsa file line by line, and sum the number of bases in each chromosome
     * @param filename path of the file to be parsed
     * @return @throws FileNotFoundException
     * @throws IOException Method that parses the file, getting length of
     * sequence, and chromosome name, should work in all situations
     *
     */
    public static List<Sequence> fastaParser(String filename) throws FileNotFoundException, IOException {
        //sequence arraylist stores the sequence lenght and names
        List<Sequence> sequence = new ArrayList();
        boolean flag = false;   
        //reads fasta file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            try {
                
                String line;
                Integer length = 0;
                Sequence seq = new Sequence();
                
                while ((line = br.readLine()) != null) {
                    //first Chr name
                    if (line.charAt(0) == '>' && !flag) {
                       //set first chr name
                        seq.setSequenceName(line.substring(1).trim());
                        flag = true;
                        //now until Chr Name
                    } else if (line.charAt(0) == '>') {
                       //set all but last sequence length for the next chromosomes
                        
                        seq.setSequenceLength(length);
                        sequence.add(seq);
                        seq = new Sequence();
                        seq.setSequenceName(line.substring(1).trim());
                        System.out.println(line + "Working");
                        length = 0;
                    } else {
                        //set the last sequecnce length
                        length = length + line.length();
                    }
                }
                seq.setSequenceLength(length);
                sequence.add(seq);
            } catch (IOException ex) {
                Logger.getLogger(fastaParsers.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sequence;
    }
    /**
     * createBioCircosGenomeObject - method to create the BioCircos Genome object, and return is when called, to be passed to render circos 
     * @param sequences
     * @return 
     */
    public static List<Object[]> createBiocircosGenomeObject(List<Sequence> sequences) {

        List<Object[]> obj = new ArrayList();
        //create object in correct format [[name, sequenceLength],[name, SequenceLength]];
        for (Sequence seq : sequences) {
            obj.add(new Object[]{seq.getSequenceName(), seq.getSequenceLength()});
        }

        return obj;
    }

}
