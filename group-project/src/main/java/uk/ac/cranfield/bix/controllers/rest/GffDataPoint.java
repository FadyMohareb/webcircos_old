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
public class GffDataPoint implements Serializable{
    
    private String chr;
    private Integer start;
    private Integer end;
    private String color;
    private String des;

    public GffDataPoint(String chr, Integer start, Integer end, String color, String desc) {
        this.chr = chr;
        this.start = start;
        this.end = end;
        this.color = color;
        this.des = desc;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
   @Override
   public String toString(){
        return "[" + chr +","+ start+ end + color +"]";
   }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
   
}
