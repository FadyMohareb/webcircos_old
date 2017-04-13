/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.LineDataPoint;
import uk.ac.cranfield.bix.controllers.rest.LineProperties;

/**
 *
 * @author vmuser
 */
public class Line {
    
    private String LineId;
    private LineProperties properties;
    private List<LineDataPoint> linePoints;

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String LineId) {
        this.LineId = LineId;
    }

    public LineProperties getProperties() {
        return properties;
    }

    public void setProperties(LineProperties properties) {
        this.properties = properties;
    }

    public List<LineDataPoint> getLinePoints() {
        return linePoints;
    }

    public void setLinePoints(List<LineDataPoint> linePoints) {
        this.linePoints = linePoints;
    }
   
    
    
    
}
