/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.DataPoint;
import uk.ac.cranfield.bix.controllers.rest.SnpProperties;

/**
 *
 * @author s262012
 */
public class Snp {
    private String name;
    private SnpProperties properties;
    private List<DataPoint> dataPoints;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SnpProperties getProperties() {
        return properties;
    }

    public void setProperties(SnpProperties properties) {
        this.properties = properties;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
    
    
}
