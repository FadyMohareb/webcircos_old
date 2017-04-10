/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import java.io.Serializable;

/**
 *
 * @author s262012
 */
public class HistogramDataPoint implements Serializable{
    
    private String chr;
    private Integer start;
    private Integer end;
    private String name;
    private double value;
    private String snpCount;

    public HistogramDataPoint(String chr, Integer start, Integer end, String name, double value, String snpCount) {
        this.chr = chr;
        this.start = start;
        this.end = end;
        this.name = name;
        this.value = value;
        this.snpCount = snpCount;
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

    public String getSnpCount() {
        return snpCount;
    }

    public void setSnpCount(String snpCount) {
        this.snpCount = snpCount;
    }
    
    
    @Override
    public String toString(){
        return "[" + chr +","+ start + end + name + value + snpCount +"]";
    }
    
}
