/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author s262012
 */
public class BackProperties {
    
    @JsonProperty("BginnerRadius")
    private Integer BginnerRadius;
    @JsonProperty("BgouterRadius")
    private Integer BgouterRadius;
    @JsonProperty("BgFillColor")
    private String  BgFillColor;
    @JsonProperty("BgborderColor")
    private String  BgborderColor;
    @JsonProperty("BgborderSize")
    private double  BgborderSize;
    @JsonProperty("axisShow")
    private String axisShow;
    @JsonProperty("axisWidth") 
    private double axisWidth;
    @JsonProperty("axisColor")
    private String axisColor;
    @JsonProperty("opacity")
    private double opacity;
    @JsonProperty("axisNum")
    private Integer axisNum;

    public BackProperties(Integer BginnerRadius, Integer BgouterRadius, String BgFillColor, String BgborderColor, double BgborderSize, String axisShow, double axisWidth, String axisColor, double opacity, Integer axisNum) {
        this.BginnerRadius = BginnerRadius;
        this.BgouterRadius = BgouterRadius;
        this.BgFillColor = BgFillColor;
        this.BgborderColor = BgborderColor;
        this.BgborderSize = BgborderSize;
        this.axisShow = axisShow;
        this.axisWidth = axisWidth;
        this.axisColor = axisColor;
        this.opacity = opacity;
        this.axisNum = axisNum;
    }
    

  

    public Integer getBginnerRadius() {
        return BginnerRadius;
    }

    public void setBginnerRadius(Integer BginnerRadius) {
        this.BginnerRadius = BginnerRadius;
    }

    public Integer getBgouterRadius() {
        return BgouterRadius;
    }

    public void setBgouterRadius(Integer BgouterRadius) {
        this.BgouterRadius = BgouterRadius;
    }

    public String getBgFillColor() {
        return BgFillColor;
    }

    public void setBgFillColor(String BgFillColor) {
        this.BgFillColor = BgFillColor;
    }

    public String getBgborderColor() {
        return BgborderColor;
    }

    public void setBgborderColor(String BgborderColor) {
        this.BgborderColor = BgborderColor;
    }

    public double getBgborderSize() {
        return BgborderSize;
    }

    public void setBgborderSize(double BgborderSize) {
        this.BgborderSize = BgborderSize;
    }

    public String getAxisShow() {
        return axisShow;
    }

    public void setAxisShow(String axisShow) {
        this.axisShow = axisShow;
    }

    public double getAxisWidth() {
        return axisWidth;
    }

    public void setAxisWidth(double axisWidth) {
        this.axisWidth = axisWidth;
    }

    public String getAxisColor() {
        return axisColor;
    }

    public void setAxisColor(String axisColor) {
        this.axisColor = axisColor;
    }

    public double getOpacity() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    public Integer getAxisNum() {
        return axisNum;
    }

    public void setAxisNum(Integer axisNum) {
        this.axisNum = axisNum;
    }  
}
