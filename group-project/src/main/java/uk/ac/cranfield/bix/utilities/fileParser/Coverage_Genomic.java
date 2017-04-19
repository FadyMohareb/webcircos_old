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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineProperties;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;

/**
 *
 * @author s263839
 */
public class Coverage_Genomic {
    
    public static  List<List<String[]>> VCFLineParser(String VCFfilePath){
        List<List<String[]>> list = new ArrayList();
        List<String[]> meta = new ArrayList();
        List<String[]> VCFlines = new ArrayList();
        String pattern = "##contig=<ID=([.A-Za-z0-9]+),length=([0-9]+)>";
        Pattern p = Pattern.compile(pattern);
        
    try {
            //Reads the VCF file,  splits by tab, adds each Line as a list containg each column entry
            
            BufferedReader br = new BufferedReader(new FileReader(VCFfilePath));
            String line;
            try {
                while ((line = br.readLine()) != null) {
                   
                        if(line.contains("##contig")){
                            Matcher m = p.matcher(line);
                            if(m.find()){
                                meta.add(new String[]{m.group(1), m.group(2)});
                            }                               
                        }else if(line.charAt(0) != '#'){
                            VCFlines.add(line.split("\\t")); 
                        }
                    
                }
                list.add(meta);
                list.add(VCFlines);
            } catch (IOException ex) {
                Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
            }                    
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VCFParsers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    public static ArrayList<Object[]> VCFDepthExtract(List<List<String[]>> li){
       
        ArrayList<Object[]> DPValues = new ArrayList();
        List<String[]> VCFlines = li.get(1);
        
        for(int i = 0; i < VCFlines.size(); i ++){
           String[] Depth = VCFlines.get(i)[7].split(";");
           for(int j = 0; j<Depth.length; j++){
               if(Depth[j].contains("DP")){
                   Object[] VCFdata = new Object[3];
                   
                   VCFdata[0] = (Depth[j].substring(3)); //Dp at SNP
                   VCFdata[1] = VCFlines.get(i)[0]; //chrom
                   VCFdata[2] = VCFlines.get(i)[1]; //Pos
                   DPValues.add(VCFdata);
                   break;
               }                               
            }                                 
        }
        return DPValues;       
    }
    
    
    public static ArrayList<Object[]> SortToBins(List<List<String[]>> li, ArrayList<Object[]> DPValues){
        Integer BinSize = 1000000;
        ArrayList<Integer> BinNumber = new ArrayList();
        List<List<Object[]>> lists = new ArrayList<>();
        ArrayList<Object[]> DataValues = new ArrayList();
        
        List<String[]> meta = li.get(0);
        
         for(int k = 0; k < meta.size(); k++){
            BinNumber.add(Integer.parseInt(meta.get(k)[1])/BinSize);            
        }
         
        for(int i = 0; i < meta.size(); i++){
            List<Object[]> list = new ArrayList();
            System.out.println(meta.get(i)[0]);
                for(int j = 0; j < DPValues.size(); j++){

                    if(DPValues.get(j)[1].equals(meta.get(i)[0])){

                       list.add(DPValues.get(j));                
                    }

                }
            lists.add(list);
        }                        
        Double DepthInBin = 0.00;
        Integer Count = 0;
        Double AvgValue = 0.00;
        for(int i = 0; i < lists.size(); i++){ // for all chromsomes (The number of lists in lists)

            for(int j = 1; j <= BinNumber.get(i); j++){ // for all bins in that chromosome

                    for(int l = 0; l < lists.get(i).size(); l ++){ // for all entries under that chromosomome
             
             
                    if(Integer.parseInt(lists.get(i).get(l)[2].toString()) > (BinSize * (j -1)) && Integer.parseInt(lists.get(i).get(l)[2].toString()) < (BinSize * j )){                       
                        DepthInBin = DepthInBin + Integer.parseInt(lists.get(i).get(l)[0].toString());
                        Count += 1;
                    }
                    else if(Integer.parseInt(lists.get(i).get(l)[2].toString()) > (BinSize * j )){
                        Object[] values = new Object[4];
                        AvgValue = (DepthInBin/Count)/10;
                        String Chrom = meta.get(i)[0];
                        values[0] = (double)Math.round(AvgValue * 100d) / 100d; //Value
                        values[1] = BinSize * (j - 1); // Start
                        values[2] = BinSize * j; //end
                        values[3] = Chrom;
                        DataValues.add(values);
                        DepthInBin = 0.00;
                        Count = 0;
                        break;
                    }
                    }
            }    
        }
        return DataValues;
    }
        public static List<LineDataPoint> CoverageData(ArrayList<Object[]> DataValues){
            Integer BinSize = 1000000;
            List<LineDataPoint> Data = new ArrayList();        
            for(int j = 0; j < DataValues.size(); j ++){

               //append the data to a string in the right format
               LineDataPoint point = new LineDataPoint(DataValues.get(j)[3].toString(), (Integer)DataValues.get(j)[1]+((Integer)DataValues.get(j)[2]-(Integer)DataValues.get(j)[1])/2, (Double) DataValues.get(j)[0]);
               Data.add(point);
            }
            return Data;
        }
        
        public static Line GenomeCoverageWriter(List<LineDataPoint> Data) throws IOException{

        //Create line object
        Line l = new Line();
        //Set line id
        l.setLineId("LINE01");
        
        //Create line properties
        LineProperties prop = new LineProperties();
        prop.setMaxRadius(60);
        prop.setMinRadius(30);
        prop.setLineWidth(1);
        prop.setLineColor("#FF6666");
        //Set line properties
        l.setProperties(prop);
        //Set list of line point 
        l.setLinePoints(Data);  
        
        return l;
    }
    
    
    
    
}
