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
public class SnpProperties {

    private Integer maxRadius;
    private Integer minRadius;
    private String SNPFillColor;
    private Integer circleSize;
    private boolean displaySNPAxis;
    private String SNPAxisColor;
    private double SNPAxisWidth;

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

    public String getSNPFillColor() {
        return SNPFillColor;
    }

    public void setSNPFillColor(String SNPFillColor) {
        this.SNPFillColor = SNPFillColor;
    }

    public Integer getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(Integer circleSize) {
        this.circleSize = circleSize;
    }

    public boolean isDisplaySNPAxis() {
        return displaySNPAxis;
    }

    public void setDisplaySNPAxis(boolean displaySNPAxis) {
        this.displaySNPAxis = displaySNPAxis;
    }

    public String getSNPAxisColor() {
        return SNPAxisColor;
    }

    public void setSNPAxisColor(String SNPAxisColor) {
        this.SNPAxisColor = SNPAxisColor;
    }

    public double getSNPAxisWidth() {
        return SNPAxisWidth;
    }

    public void setSNPAxisWidth(double SNPAxisWidth) {
        this.SNPAxisWidth = SNPAxisWidth;
    }
    
    
}
