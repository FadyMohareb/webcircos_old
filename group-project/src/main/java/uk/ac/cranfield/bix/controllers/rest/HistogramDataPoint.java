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
public class HistogramDataPoint {
    
    private String chr;
    private Integer start;
    private Integer end;
    private String name;
    private double value;

    public HistogramDataPoint(String chr, Integer start, Integer end, String name, double value) {
        this.chr = chr;
        this.start = start;
        this.end = end;
        this.name = name;
        this.value = value;
    }

    public HistogramDataPoint() {
    }
    
    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
}
