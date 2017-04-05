/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import uk.ac.cranfield.bix.controllers.rest.finalObjects.BackgroundDisplay;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.DisplayText;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Snp;
import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;

/**
 * This class will contain all the object to draw a circos 
 * @author s262012
 */
public class CircosOutput {

    private List<Object[]> genomes;
    
    private Snp snp;
    
    private Histogram histo;
    
    private DisplayText text;
    
    private BackgroundDisplay background;
    
    private IndGff arc;
    
    private Line genomicCoverage;
    
    private Line transcriptomicCoverage;

    public List<Object[]> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Object[]> genomes) {
        this.genomes = genomes;
    } 

    public Snp getSnp() {
        return snp;
    }

    public void setSnp(Snp snp) {
        this.snp = snp;
    }

    public DisplayText getText() {
        return text;
    }

    public void setText(DisplayText text) {
        this.text = text;
    }

    public BackgroundDisplay getBackground() {
        return background;
    }

    public void setBackground(BackgroundDisplay background) {
        this.background = background;
    }

    public Histogram getHisto() {
        return histo;
    }

    public void setHisto(Histogram histo) {
        this.histo = histo;
    }

    public IndGff getArc() {
        return arc;
    }

    public void setArc(IndGff arc) {
        this.arc = arc;
    }

    public Line getGenomicCoverage() {
        return genomicCoverage;
    }

    public void setGenomicCoverage(Line genomicCoverage) {
        this.genomicCoverage = genomicCoverage;
    }

    public Line getTranscriptomicCoverage() {
        return transcriptomicCoverage;
    }

    public void setTranscriptomicCoverage(Line transcriptomicCoverage) {
        this.transcriptomicCoverage = transcriptomicCoverage;
    }
  
}
