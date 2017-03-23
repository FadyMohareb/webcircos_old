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
public class DataPoint {
    
    private String chr;
    private Integer pos;
    private Integer value;
    private String des;
    private String color;

    public DataPoint(String chr, Integer pos, Integer value, String des, String color) {
        this.chr = chr;
        this.pos = pos;
        this.value = value;
        this.des = des;
        this.color = color;
    }
    
     public DataPoint(String chr, Integer pos, Integer value, String des) {
        this.chr = chr;
        this.pos = pos;
        this.value = value;
        this.des = des;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    
    
    
}
