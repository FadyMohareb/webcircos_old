/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

/**
 *
 * @author s262012
 */
public class HistogramProperties {
    private Integer maxRadius;
    private Integer minRadius;
    private String histogramFillColor; 

    public Integer getMaxRadius() {
        return maxRadius;
    }

    public void setMaxRadius(Integer maxRadius) {
        this.maxRadius = maxRadius;
    }

    public Integer getMinRadius() {
        return minRadius;
    }

    public void setMinRadius(Integer minRadius) {
        this.minRadius = minRadius;
    }

    public String getHistogramFillColor() {
        return histogramFillColor;
    }

    public void setHistogramFillColor(String histogramFillColor) {
        this.histogramFillColor = histogramFillColor;
    }
    
    
}
