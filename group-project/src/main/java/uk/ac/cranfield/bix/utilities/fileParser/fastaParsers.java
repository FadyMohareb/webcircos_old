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
     *
     * @param filename path of the file to be parsed
     * @return @throws FileNotFoundException
     * @throws IOException Method that parses the file, getting length of
     * sequence, and chromosome name, should work in all situations
     *
     */
    public static List<Sequence> fastaParser(String filename) throws FileNotFoundException, IOException {
        //Might put it in a HashMap 
        List<Sequence> sequence = new ArrayList();
        boolean flag = false;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

            try {
                String line;
                Integer length = 0;
                Sequence seq = new Sequence();
                while ((line = br.readLine()) != null) {

                    if (line.charAt(0) == '>' && !flag) {
                        seq.setSequenceName(line.substring(1).trim());
                        flag = true;

                    } else if (line.charAt(0) == '>') {
                        seq.setSequenceLength(length);
                        sequence.add(seq);
                        seq = new Sequence();
                        seq.setSequenceName(line.substring(1).trim());
                        System.out.println(line + "Working");
                        length = 0;
                    } else {
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

    public static List<Object[]> createBiocircosGenomeObject(List<Sequence> sequences) {

        List<Object[]> obj = new ArrayList();
        for (Sequence seq : sequences) {
            obj.add(new Object[]{seq.getSequenceName(), seq.getSequenceLength()});
        }

        return obj;
    }

}
