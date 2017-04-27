/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.HeatMapDataPoint;
import uk.ac.cranfield.bix.controllers.rest.HeatMapProperties;
import uk.ac.cranfield.bix.controllers.rest.HistogramDataPoint;

/**
 * This Class is the model for the HEATMAP module from biocircos library. It allows to store the data in the good shape to be drawn.
 * @author solene
 */
public class HeatMap {
    
    private String heatMapId;
    private HeatMapProperties properties;
    private List<HeatMapDataPoint> heatMapDataPoint;

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

    public List<HeatMapDataPoint> getHeatMapDataPoint() {
        return heatMapDataPoint;
    }

    public void setHeatMapDataPoint(List<HeatMapDataPoint> heatMapDataPoint) {
        this.heatMapDataPoint = heatMapDataPoint;
    }
    
    
    
}
