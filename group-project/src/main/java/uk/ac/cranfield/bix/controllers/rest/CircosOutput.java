
package uk.ac.cranfield.bix.controllers.rest;

import uk.ac.cranfield.bix.controllers.rest.finalObjects.BackgroundDisplay;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.DisplayText;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Histogram;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;
import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.HeatMap;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Line;

/**
 * Data structure which is sent back to web application to draw genome and chromosome circos. 
 * Data are already processed and in the good shape to be understood by the biocircos library.
 * @author s262012
 */
public class CircosOutput {

    private List<Object[]> genomes;
    
    private Histogram histo;
    
    private DisplayText text;
    
    private BackgroundDisplay background;
    
    private IndGff arc;
    
    private Line genomicCoverage;
    
    private Line transcriptomicCoverage;
    
    private HeatMap dEHeatMap;
    
    private HeatMap geneExpressionHeatMap;
    
    private BackgroundDisplay backgroundGenoCov;
    
    private BackgroundDisplay backgroundTranCov;
    
    private Histogram snpDenChromView;
    

    public List<Object[]> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Object[]> genomes) {
        this.genomes = genomes;
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

    public HeatMap getdEHeatMap() {
        return dEHeatMap;
    }

    public void setdEHeatMap(HeatMap dEHeatMap) {
        this.dEHeatMap = dEHeatMap;
    }

    public HeatMap getGeneExpressionHeatMap() {
        return geneExpressionHeatMap;
    }

    public void setGeneExpressionHeatMap(HeatMap geneExpressionHeatMap) {
        this.geneExpressionHeatMap = geneExpressionHeatMap;
    }

    public BackgroundDisplay getBackgroundGenoCov() {
        return backgroundGenoCov;
    }

    public void setBackgroundGenoCov(BackgroundDisplay backgroundGenoCov) {
        this.backgroundGenoCov = backgroundGenoCov;
    }

    public BackgroundDisplay getBackgroundTranCov() {
        return backgroundTranCov;
    }

    public void setBackgroundTranCov(BackgroundDisplay backgroundTranCov) {
        this.backgroundTranCov = backgroundTranCov;
    }

    public Histogram getSnpDenChromView() {
        return snpDenChromView;
    }

    public void setSnpDenChromView(Histogram snpDenChromView) {
        this.snpDenChromView = snpDenChromView;
    }

    
  
}
