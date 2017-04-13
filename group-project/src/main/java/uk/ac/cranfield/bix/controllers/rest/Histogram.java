/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import java.util.List;

/**
 *
 * @author s262012
 */
public class Histogram {
    private String histId;
    private HistogramProperties properties;
    private List<HistogramDataPoint> histDataPoint;

    public String getHistId() {
        return histId;
    }

    public void setHistId(String histId) {
        this.histId = histId;
    }

    public HistogramProperties getProperties() {
        return properties;
    }

    public void setProperties(HistogramProperties properties) {
        this.properties = properties;
    }

    public List<HistogramDataPoint> getHistDataPoint() {
        return histDataPoint;
    }

    public void setHistDataPoint(List<HistogramDataPoint> histDataPoint) {
        this.histDataPoint = histDataPoint;
    }
    
    
}
