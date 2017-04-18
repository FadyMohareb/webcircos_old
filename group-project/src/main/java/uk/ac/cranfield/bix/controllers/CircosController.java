/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

import uk.ac.cranfield.bix.services.PathFinder;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import uk.ac.cranfield.bix.controllers.rest.BackProperties;
import uk.ac.cranfield.bix.controllers.rest.CircosInput;
import uk.ac.cranfield.bix.controllers.rest.CircosOutput;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.BackgroundDisplay;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.HeatMap;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Genomic.GenomeCoverageWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.Coverage_Transcriptomic.TranscriptomicCovWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.DifferentialExpression.HeatMapWriter;
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
        circosInput.removeExtensions();
        String userID = "", currentPath;
        String path;
        File dirSequence;
        CircosOutput circosOutput = new CircosOutput();
        

        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            path = new PathFinder().getEntireFilePathNotLogged() + "/";
        } else {
            path = new PathFinder().getEntireFilePathLogged(circosInput.getProjectName());

        }

        if (new File(path + "/sequence/" + circosInput.getReferenceSequence() + ".txt").exists()) {

            //Create BiocircosGenome variable
            List<Sequence> seq = (List<Sequence>) Deserialize(path + "/sequence/" + circosInput.getReferenceSequence() + ".txt");
            List<Object[]> obj = createBiocircosGenomeObject(seq);

            //Create ARC_01
            circosOutput.setGenomes(obj);
        }

        if (new File(path + "/variants/" + circosInput.getSnpdensity() + ".txt").exists()) {

            //Create Histogram
            List<HistogramDataPoint> vcf = (List<HistogramDataPoint>) Deserialize(path + "/variants/" + circosInput.getSnpdensity() + ".txt");
            Histogram h = HistWriter(vcf);
            circosOutput.setHisto(h);

        }

        if (new File(path + "/variants/" + circosInput.getSnpdensity() + "coverage.txt").exists()) {
            List<LineDataPoint> point = (List<LineDataPoint>) Deserialize(path + "/variants/" + circosInput.getSnpdensity() + "coverage.txt");
            Line l = GenomeCoverageWriter(point);
            circosOutput.setGenomicCoverage(l);
            
            BackgroundDisplay back = new BackgroundDisplay("BACKGROUND01", new BackProperties(150,120,"#F2F2F2","#000",0.3, "true",0.1,"#000",0.5,10));
            circosOutput.setBackgroundGenoCov(back);
        }

        if (new File(path + "/annotation/" + circosInput.getAnnotation() + ".txt").exists()) {
            List<GffDataPoint> gff = (List<GffDataPoint>) Deserialize(path + "/annotation/" + circosInput.getAnnotation() + ".txt");
            IndGff GffWriter = GffWriter(gff);
            circosOutput.setArc(GffWriter);
        }
        
          if (new File(path + "/bedcov/" + circosInput.getTranscriptiomicCoverage() + "transcriptomicCov.txt").exists()) {
            List<LineDataPoint> trans = (List<LineDataPoint>) Deserialize(path + "/bedcov/" + circosInput.getTranscriptiomicCoverage() + "transcriptomicCov.txt");
            Line l = TranscriptomicCovWriter(trans);
            circosOutput.setTranscriptomicCoverage(l);
            
            BackgroundDisplay backTC = new BackgroundDisplay("BACKGROUND02", new BackProperties(60,30,"#F2F2F2","#000",0.3, "true",0.2,"#000",0.5,5));
            circosOutput.setBackgroundTranCov(backTC);
        }

        if (new File(path + "/expression/" + circosInput.getGenesExpresion() + "Expression.txt").exists()) {
            List<HeatMapDataPoint> eHeatMap = (List<HeatMapDataPoint>) Deserialize(path + "/expression/" + circosInput.getGenesExpresion() + "Expression.txt");
            HeatMap eMap = HeatMapWriter(eHeatMap, -45, -85);
            circosOutput.setGeneExpressionHeatMap(eMap);
        }

        if (new File(path + "/difExpression/" + circosInput.getDifferentialExpression() + "DExpression.txt").exists()) {
            List<HeatMapDataPoint> eHeatMap = (List<HeatMapDataPoint>) Deserialize(path + "/difExpression/" + circosInput.getDifferentialExpression() + "DExpression.txt");
            HeatMap eMap = HeatMapWriter(eHeatMap, -25, -65);
            circosOutput.setdEHeatMap(eMap);
        }

        return circosOutput;
    }
}
