/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.HeatMapProperties;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;

/**
 *
 * @author solene
 */
public class HeatMap {
    
    private String heatMapId;
    private HeatMapProperties properties;
    private List<HistogramDataPoint> heatMapDataPoint;

    public String getHeatMapId() {
        return heatMapId;
    }

    public void setHeatMapId(String heatMapId) {
        this.heatMapId = heatMapId;
    }

    public HeatMapProperties getProperties() {
        return properties;
    }

    public void setProperties(HeatMapProperties properties) {
        this.properties = properties;
    }

    public List<HistogramDataPoint> getHeatMapDataPoint() {
        return heatMapDataPoint;
    }

    public void setHeatMapDataPoint(List<HistogramDataPoint> heatMapDataPoint) {
        this.heatMapDataPoint = heatMapDataPoint;
    }
    
    
    
}
