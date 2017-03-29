/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cranfield.bix.controllers.rest.CircosInput;
import uk.ac.cranfield.bix.controllers.rest.CircosOutput;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Sequence;
import static uk.ac.cranfield.bix.utilities.SerializeDeserialize.Deserialize;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffDataPoints;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffParser;
import static uk.ac.cranfield.bix.utilities.fileParser.Gff3Parser.GffWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.VCFParsers.HistWriter;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.createBiocircosGenomeObject;
import static uk.ac.cranfield.bix.utilities.fileParser.fastaParsers.fastaParser;

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

        //Create BiocircosGenome variable
        List<Sequence> seq = (List<Sequence>) Deserialize("C:/Users/solene/Documents/temp/seq2.txt");
        List<Object[]> obj = createBiocircosGenomeObject(seq);
        
        //Create Histogram
        List<HistogramDataPoint> HistData = new ArrayList();
        HistogramDataPoint d1 = new HistogramDataPoint("SL2.50ch00", 1, 1000000, "brt_raw_10pHist", 0.839);
        HistogramDataPoint d2 = new HistogramDataPoint("SL2.50ch00", 1000004, 2000000, "brt_raw_10pHist", 0.791);
        HistData.add(d1);
        HistData.add(d2);

        Histogram h = HistWriter(HistData);

        //Create ARC_01

        CircosOutput circosOutput = new CircosOutput();
        circosOutput.setGenomes(obj);
        circosOutput.setHisto(h);
        return circosOutput;
    }
}
