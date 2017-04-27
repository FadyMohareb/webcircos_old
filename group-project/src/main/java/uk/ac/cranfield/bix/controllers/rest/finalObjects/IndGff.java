/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.GffDataPoint;
import uk.ac.cranfield.bix.controllers.rest.IndGffProperties;

/**
 * This Class is the model for the ARC module from biocircos library. It allows to store the data in the good shape to be drawn.
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
