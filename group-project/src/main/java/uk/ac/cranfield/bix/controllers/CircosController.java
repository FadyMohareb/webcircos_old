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
import uk.ac.cranfield.bix.controllers.rest.CircosInput;
import uk.ac.cranfield.bix.controllers.rest.CircosOutput;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import uk.ac.cranfield.bix.models.PathFinder;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffWriter;
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
        String path;
        CircosOutput circosOutput = new CircosOutput();
        //For tomorrow meeting I need to know where to find data. So I retrieve the session id to set the proper path. 
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            path = new PathFinder().getEntireFilePathLogged()+"/";
//            userID = RequestContextHolder.currentRequestAttributes().getSessionId();
//            path = "C:/Users/agata/Desktop/WebCircos/temp/" + userID + "/";
        } else {
            path = new PathFinder().getEntireFilePathNotLogged()+"/";
//            userID = SecurityContextHolder.getContext().getAuthentication().getName();
//            path = "C:/Users/agata/Desktop/WebCircos/user/" + userID + "/";
        }

        if (new File(path + "sequence/test.txt").exists()) {

            //Create BiocircosGenome variable
            List<Sequence> seq = (List<Sequence>) Deserialize(path + "sequence/test.txt");
            List<Object[]> obj = createBiocircosGenomeObject(seq);

            //Create Histogram
//        List<HistogramDataPoint> vcf = (List<HistogramDataPoint>) Deserialize("C:/Users/solene/Documents/temp/vcf2.txt");
//        Histogram h = HistWriter(vcf);

            //Create ARC_01
            circosOutput.setGenomes(obj);  
        }

        if (new File(path + "annotation/ITAG2.4_gene_models.txt").exists()) {
            List<GffDataPoint> gff = (List<GffDataPoint>) Deserialize(path + "annotation/ITAG2.4_gene_models.txt");
            IndGff GffWriter = GffWriter(gff);
            circosOutput.setArc(GffWriter);
        }

        return circosOutput;
    }
}
