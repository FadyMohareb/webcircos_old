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
public class HeatMapDataPoint implements Serializable{
    
    private String chr;
    private Integer start;
    private Integer end;
    private String name;
    private String value;

    public HeatMapDataPoint(String chr, Integer start, Integer end, String name, String value) {
        this.chr = chr;
        this.start = start;
        this.end = end;
        this.name = name;
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
         @Override
    public String toString(){
        return "[" + chr +","+ start + end + name + value +"]";
    }
    
}
