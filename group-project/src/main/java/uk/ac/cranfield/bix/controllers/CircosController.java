/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers;

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
import uk.ac.cranfield.bix.controllers.rest.Histogram;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;
import uk.ac.cranfield.bix.controllers.rest.IndGff;
import uk.ac.cranfield.bix.controllers.rest.Sequence;
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
    CircosOutput sendData(@RequestBody CircosInput circosInput) {

        //Create BiocircosGenome variable
        
        Sequence s = new Sequence();
        Sequence s2 = new Sequence();
        Sequence s3 = new Sequence();
        Sequence s4 = new Sequence();
        Sequence s5 = new Sequence();
        Sequence s6 = new Sequence();
        Sequence s7 = new Sequence();
        Sequence s8 = new Sequence();
        Sequence s9 = new Sequence();
        Sequence s10 = new Sequence();
        Sequence s11 = new Sequence();
        Sequence s12 = new Sequence();
        Sequence s13 = new Sequence();
        
        s.setSequenceName("SL2.50ch00");
        s.setSequenceLength(21805821);
        s2.setSequenceName("SL2.50ch01");
        s2.setSequenceLength(98543444);
        
        s3.setSequenceName("SL2.50ch02");
        s3.setSequenceLength(55340444);
        
        s4.setSequenceName("SL2.50ch03");
        s4.setSequenceLength(70787664);
        
        s5.setSequenceName("SL2.50ch04");
        s5.setSequenceLength(66470942);
        
        s6.setSequenceName("SL2.50ch05");
        s6.setSequenceLength(65875088);
        
        s7.setSequenceName("SL2.50ch06");
        s7.setSequenceLength(49751636);
        
        s8.setSequenceName("SL2.50ch07");
        s8.setSequenceLength(68045021);
        
        s9.setSequenceName("SL2.50ch08");
        s9.setSequenceLength(65866657);
        s10.setSequenceName("SL2.50ch09");
        s10.setSequenceLength(72482091);
        s11.setSequenceName("SL2.50ch010");
        s11.setSequenceLength(65527505);
        s12.setSequenceName("SL2.50ch011");
        s12.setSequenceLength(56302525);
        s13.setSequenceName("SL2.50ch012");
        s13.setSequenceLength(67145203);

        ArrayList<Sequence> seq = new ArrayList();
        seq.add(s);
        seq.add(s2);
        seq.add(s3);
        seq.add(s4);
        seq.add(s5);
        seq.add(s6);
        seq.add(s7);
        seq.add(s8);
        seq.add(s9);
        seq.add(s10);
        seq.add(s11);
        seq.add(s12);
        seq.add(s13);

        List<Object[]> obj =  createBiocircosGenomeObject(seq);

        //Create Histogram
        List<HistogramDataPoint> HistData = new ArrayList();
        HistogramDataPoint d1 = new HistogramDataPoint("SL2.50ch00", 1, 1000000, "brt_raw_10pHist", 0.839);
        HistogramDataPoint d2 = new HistogramDataPoint("SL2.50ch00", 1000004, 2000000, "brt_raw_10pHist", 0.791);
        HistData.add(d1);
        HistData.add(d2);

        Histogram h = HistWriter(HistData);
        
        //Create ARC_01
       
        List<GffDataPoint> Gff3Data = new ArrayList();
        
        GffDataPoint g1 = new GffDataPoint("SL2.50ch00",16437,18189,"rgb(0,0,0)");
        GffDataPoint g2 = new GffDataPoint("SL2.50ch00",550920,551576,"rgb(10,10,10)");
        GffDataPoint g3 = new GffDataPoint("SL2.50ch00",723746,724018,"rgb(0,0,0)");
        GffDataPoint g4 = new GffDataPoint("SL2.50ch00",791493,792010,"rgb(10,10,10)");
        GffDataPoint g5 = new GffDataPoint("SL2.50ch00",1115784,1117712,"rgb(0,0,0)");
        
        GffDataPoint g6 = new GffDataPoint("SL2.50ch01",23087,26021,"rgb(0,0,0)");
        GffDataPoint g7 = new GffDataPoint("SL2.50ch01",47011,54276,"rgb(10,10,10)");
        GffDataPoint g8 = new GffDataPoint("SL2.50ch01",57359,58350,"rgb(0,0,0)");
        GffDataPoint g9 = new GffDataPoint("SL2.50ch01",65457,66982,"rgb(10,10,10)");
        
        Gff3Data.add(g1);
        Gff3Data.add(g2);
        Gff3Data.add(g3);
        Gff3Data.add(g4);
        Gff3Data.add(g5);
        
        Gff3Data.add(g6);
        Gff3Data.add(g7);
        Gff3Data.add(g8);
        Gff3Data.add(g9);
        IndGff ggf = GffWriter(Gff3Data);
        
        
        CircosOutput circosOutput = new CircosOutput();
        circosOutput.setGenomes(obj);
        circosOutput.setHisto(h);
        circosOutput.setArc(ggf);
        return circosOutput;
    }
}
