/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import java.io.Serializable;

/**
 *
 * @author vmuser
 */
public class LineDataPoint implements Serializable{
    
    private String chr;
    
    private Integer pos;
    
    private Double value;
    
    

    public LineDataPoint(String chr, Integer pos, Double value) {
        this.chr = chr;
        this.pos = pos;
        this.value = value;
        
    }
    

    public String getChr() {
        return chr;
    }

    public void setChr(String chr) {
        this.chr = chr;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
