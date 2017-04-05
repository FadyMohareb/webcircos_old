/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import uk.ac.cranfield.bix.controllers.rest.CircosInput;
import uk.ac.cranfield.bix.controllers.rest.CircosOutput;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.GenomeCoverageWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.HistWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.createBiocircosGenomeObject;

/**
 *
 * @author s262012
 */
@Controller
public class CircosController {

    @RequestMapping(value = "/circos")
    public String getSimpleCircos() {
        return "Circle";
    }

    //Need to add a list of file in the parameter. 
    @RequestMapping(value = "/circos.data", method = RequestMethod.POST)
    public @ResponseBody
    CircosOutput sendData(@RequestBody CircosInput circosInput) throws IOException, ClassNotFoundException {
        String userID = "";
        String path = "";
        CircosOutput circosOutput = new CircosOutput();
        //For tomorrow meeting I need to know where to find data. So I retrieve the session id to set the proper path. 
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            userID = RequestContextHolder.currentRequestAttributes().getSessionId();
            path = "/home/vmuser/temp/" + userID + "/";
        } else {
            userID = SecurityContextHolder.getContext().getAuthentication().getName();
            path = "/home/vmuser/user/" + userID + "/";
        }

        if (new File(path + "sequence/S_lycopersicum_chromosomes.2.50.txt").exists()) {

            //Create BiocircosGenome variable
            List<Sequence> seq = (List<Sequence>) Deserialize(path + "sequence/S_lycopersicum_chromosomes.2.50.txt");
            List<Object[]> obj = createBiocircosGenomeObject(seq);

            circosOutput.setGenomes(obj);  
        }

        if (new File(path + "annotation/ITAG2.4_gene_models.txt").exists()) {
             //Create ARC_01
            List<GffDataPoint> gff = (List<GffDataPoint>) Deserialize(path + "annotation/ITAG2.4_gene_models.txt");
            IndGff GffWriter = GffWriter(gff);
            circosOutput.setArc(GffWriter);
        }
        
        if(new File(path + "variants/MT_raw_10p.txt").exists()){
            
            //Create Histogram
            List<HistogramDataPoint> vcf = (List<HistogramDataPoint>) Deserialize(path + "variants/MT_raw_10p.txt");
            Histogram h = HistWriter(vcf);
            circosOutput.setHisto(h);
   
        }
        
        if(new File(path+"variants/MT_raw_10pcoverage.txt").exists()){
         //Create line chart
         List<LineDataPoint> genomCov = (List<LineDataPoint>) Deserialize(path+"variants/MT_raw_10pcoverage.txt");
         Line l = GenomeCoverageWriter(genomCov);
         circosOutput.setGenomicCoverage(l);
        }

        return circosOutput;
    }
}
