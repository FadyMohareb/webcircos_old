/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.utilities.fileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineProperties;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;


/**
 *
 * @author s263839
 */
public class Coverage_Transcriptomic {
    
    /**
     * GffParser2 parses the Gff file. splitting meta data and annotation data into two separate lists
     * @param Gff3filepath
     * @return 
     */
    public static List<List<String[]>> GffParser2(String Gff3filepath){
        
        List<String[]> Karyotype = new ArrayList();
        List<String[]> metadata = new ArrayList();
        
        List<List<String[]>> list = new ArrayList();
        
        try (BufferedReader br = new BufferedReader(new FileReader(Gff3filepath))) {
            
            String line;
           
            Integer i = 0;
            while((line = br.readLine()) != null){
                
                if(line.startsWith("##sequence")){
                    //split line by tab and only keep 1 and 3
                    String[] split = line.split("\\s");  
                    String[] s = new String[2];
                    s[0] = split[1];
                    s[1] = split[3];
                    metadata.add(s);
                }
                else if (line.charAt(0) != '#'){
                    //get lines containing MRNA
                     if(line.split("\\t")[2].equalsIgnoreCase("mrna")){
                         Karyotype.add(line.split("\\t"));

                     }  
                }
            }
            //return list containing file lines in one element, and metadata in the other line
            list.add(metadata);
            list.add(Karyotype);
            br.close();

        }   catch (IOException ex) {
            Logger.getLogger(Gff3Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
     
     //Find overlapping regions between RSEM file and chromosome from vcf file
     public static ArrayList<Object[]> CoverageParser(List<List<String[]>> list , String CoveragePath){
         //Karyotype is all entries in gff file that have mrna as type
         
         
        ArrayList<Object[]> Coverage = new ArrayList();
        //read the RSEM output file
        try (BufferedReader br = new BufferedReader(new FileReader(new File(CoveragePath)))) {
             String line;
            //lines of GFF file with mrna as type 
            List<String[]> Karyotype = list.get(1);
            while((line = br.readLine()) != null){
                String[] BedCov = line.split("\\s");
                for(int i = 0; i < Karyotype.size(); i++){

                    
                    // if first line of bedcov file = id of gff file entry

                        if((line.split("\\s")[0]).equals(Karyotype.get(i)[8].split(";")[0].substring(3))){
                        String Chromosome = Karyotype.get(i)[0];
                        Integer start = Integer.parseInt(Karyotype.get(i)[3]);
                        Integer end = Integer.parseInt(Karyotype.get(i)[4]);
                        Double AverageGeneCov = ((Double.valueOf(BedCov[3])/(end - start)));
                       
                        //convert to arrayList and add the total coverage for gene to end of List

                        Object[] Data2 = new Object [BedCov.length +5];
                        System.arraycopy(BedCov, 0, Data2, 0, BedCov.length);
                        Data2[BedCov.length] = (double)Math.round(AverageGeneCov * 100d) / 100d;
                        Data2[BedCov.length + 1] = start;
                        Data2[BedCov.length + 2] = end;
                        Data2[BedCov.length + 3] = Chromosome;
                        Data2[BedCov.length + 4] = line.split("\\s")[0];
                        Coverage.add(Data2);
                        //so now we have the total coverage for gene
                        System.out.println(Arrays.toString(Data2));
                        
                        break; 
                    }
                    
                }
            }
        
        
        
        } catch (IOException ex) {
            Logger.getLogger(Coverage_Transcriptomic.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Coverage;
      
     }
     
      //need to add to bins
     /**
      * SorttoBinsTranscriptomics is a method that sorts the content of the coverage list into correct bins, and calculates the average coverage for that bin
      * @param li
      * @param Coverage
      * @return 
      */
     public static ArrayList<Object[]> SortToBinsTranscriptomics(List<List<String[]>> li, ArrayList<Object[]> Coverage){
         
        List<List<Object[]>> lists = new ArrayList<>();
        Integer BinSize = 1000000;
        ArrayList<Integer> BinNumber = new ArrayList();
        ArrayList<Object[]> BinContents = new ArrayList();
        List<String[]> metadata = li.get(0);
         //number of bins in each chromosome, add to a list
         for(int k = 0; k < metadata.size(); k++){
            BinNumber.add(Integer.parseInt(metadata.get(k)[1])/BinSize);            
        }
         //sort the lines by chromosome, add each chromosomes worth into a list, then that list into an overall list
        for(int i = 0; i < metadata.size(); i++){
            
            List<Object[]> list = new ArrayList();
            
            System.out.println(metadata.get(i)[0]);
                for(int j = 0; j < Coverage.size(); j++){

                    if(Coverage.get(j)[7].equals(metadata.get(i)[0])){

                       list.add(Coverage.get(j));                
                    }

                }
            lists.add(list);
        }
         Double TotalValue = 0.00;
         Integer Count = 0;
         for(int i = 0; i < lists.size(); i++){ // for all chromsomes (The number of lists in lists)

            for(int j = 1; j <= BinNumber.get(i); j++){ // for all bins in that chromosome

                    for(int l = 0; l < lists.get(i).size(); l ++){ // for all entries under that chromosomome
             
                        //if the position is inside th current bin width
                    if(Integer.parseInt(lists.get(i).get(l)[6].toString()) > (BinSize * (j -1)) && Integer.parseInt(lists.get(i).get(l)[6].toString()) < (BinSize * j )){
                        //add to the total value
                        TotalValue += Double.valueOf(Coverage.get(j)[4].toString());
                        //add 1 to the count
                        Count = Count + 1;
                    }
                    //if the current position is outside the bin width, add the values object and start for the next bin
                    else if((Integer)lists.get(i).get(l)[6] > (BinSize * j )){
                        Object[] values = new Object[5];
                       values[0] = (double)Math.round((TotalValue/Count) * 100d) / 100d; //value
                       values[1] = lists.get(i).get(j)[7]; //chrom
                       values[2] = BinSize * (j-1); //Start
                       values[3] = BinSize * j; //End
                       values[4] = Coverage.get(j)[8]; //average coverage for that bin
                       BinContents.add(values);
                       TotalValue = 0.00; 
                       Count = 0;
                       break;
                 
                 
                    }
                }
            }        
         }
         return BinContents;
     }
     /**
      * CoverageDataTranscriptomics, is a method that creates the object of the correct type, that will be sent to BioCircos
      * @param BinContents
      * @return 
      */
     public static List<LineDataPoint> CoverageDataTranscriptomics(ArrayList<Object[]> BinContents){
        Integer BinSize = 1000000;

        List<LineDataPoint> linePoint = new ArrayList();
        //create point and add to LIne point object
         for(int j = 0; j < BinContents.size(); j ++){
                LineDataPoint point = new LineDataPoint(BinContents.get(j)[1].toString(),(Integer)BinContents.get(j)[2] + BinSize/2,(Double) BinContents.get(j)[0]);
                linePoint.add(point);
         }
         return linePoint;
     }
     
     /**
      * TranscriptomicCovWriter - this method when called returns the LINE module object for biocircos, in the correct format with set properties
      * @param Data
      * @return
      * @throws IOException 
      */
     public static Line TranscriptomicCovWriter(List<LineDataPoint> Data) throws IOException{
        
         //Create line
         Line l = new Line();
         //set line id
         l.setLineId("LINE02");
         
         //create line properties
         LineProperties prop = new LineProperties();
         prop.setMaxRadius(150);
         prop.setMinRadius(120);
         prop.setLineColor("#EEAD0E");
         prop.setLineWidth(1);
        
         l.setProperties(prop);
         
         //Set line data
         l.setLinePoints(Data);
         
         return l;
    
    }
}
