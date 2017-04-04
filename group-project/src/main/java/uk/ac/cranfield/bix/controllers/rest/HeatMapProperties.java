/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

/**
 *
 * @author solene
 */
public class HeatMapProperties {

    private Integer innerRadius;
    private Integer outerRadius;
    private String maxColor;
    private String minColor;

    public Integer getInnerRadius() {
        return innerRadius;
    }

    public void setInnerRadius(Integer innerRadius) {
        this.innerRadius = innerRadius;
    }

    public Integer getOuterRadius() {
        return outerRadius;
    }

    public void setOuterRadius(Integer outerRadius) {
        this.outerRadius = outerRadius;
    }

    public String getMaxColor() {
        return maxColor;
    }

    public void setMaxColor(String maxColor) {
        this.maxColor = maxColor;
    }

    public String getMinColor() {
        return minColor;
    }

    public void setMinColor(String minColor) {
        this.minColor = minColor;
    }

    public HeatMapProperties(Integer innerRadius, Integer outerRadius, String maxColor, String minColor) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.maxColor = maxColor;
        this.minColor = minColor;
    }

    public HeatMapProperties() {
    }
    
    
    
}
