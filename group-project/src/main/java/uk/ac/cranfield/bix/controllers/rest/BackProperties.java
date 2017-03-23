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
public class BackProperties {
    
    private Integer BginnerRadius;
    private Integer BgouterRadius;
    private String  BgFillColor;
    private String  BgborderColor;
    private double  BgborderSize;

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
    
    
    
}
