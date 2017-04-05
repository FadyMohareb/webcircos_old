/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author vmuser
 */
public class LineProperties {
    
    private Integer maxRadius;
    private Integer minRadius;
    @JsonProperty("LineColor")
    private String LineColor;
    @JsonProperty("LineWidth")
    private Integer LineWidth;

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

    public String getLineColor() {
        return LineColor;
    }

    public void setLineColor(String LineColor) {
        this.LineColor = LineColor;
    }

    public Integer getLineWidth() {
        return LineWidth;
    }

    public void setLineWidth(Integer LineWidth) {
        this.LineWidth = LineWidth;
    }
       
    
}
