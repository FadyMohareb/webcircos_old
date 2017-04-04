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
public class IndGff {
    
    private String indGffid;
    private IndGffProperties properties;
    private List<GffDataPoint> gffDataPoint;

    public String getIndGffid() {
        return indGffid;
    }

    public void setIndGffid(String indGffid) {
        this.indGffid = indGffid;
    }

    public IndGffProperties getProperties() {
        return properties;
    }

    public void setProperties(IndGffProperties properties) {
        this.properties = properties;
    }

    public List<GffDataPoint> getGffDataPoint() {
        return gffDataPoint;
    }

    public void setGffDataPoint(List<GffDataPoint> gffDataPoint) {
        this.gffDataPoint = gffDataPoint;
    }
    
    
}
