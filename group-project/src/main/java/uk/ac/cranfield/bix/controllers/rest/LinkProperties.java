package uk.ac.cranfield.bix.controllers.rest;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class LinkProperties {
    
    @JsonProperty("LinkRadius")
    private Integer LinkRadius; 
    @JsonProperty("LinkFillColor")
    private String LinkFillColor;
    @JsonProperty("LinkWidth")
    private Integer LinkWidth;
    @JsonProperty("displayLinkAxis")
    private String displayLinkAxis;
    @JsonProperty("LinkAxisColor")
    private String LinkAxisColor;
    @JsonProperty("LinkAxisWidth")
    private double LinkAxisWidth;
    @JsonProperty("LinkAxisPad")
    private Integer LinkAxisPad;
    @JsonProperty("displayLinkLabel")
    private String displayLinkLabel;
    @JsonProperty("LinkLabelColor")
    private String LinkLabelColor;
    @JsonProperty("LinkLabelSize")
    private Integer LinkLabelSize;
    @JsonProperty("LinkLabelPad")
    private Integer LinkLabelPad;

    public LinkProperties(Integer LinkRadius, String LinkFillColor, Integer LinkWidth, String displayLinkAxis, String LinkAxisColor, double LinkAxisWidth, Integer LinkAxisPad, String displayLinkLabel, String LinkLabelColor, Integer LinkLabelSize, Integer LinkLabelPad) {
        this.LinkRadius = LinkRadius;
        this.LinkFillColor = LinkFillColor;
        this.LinkWidth = LinkWidth;
        this.displayLinkAxis = displayLinkAxis;
        this.LinkAxisColor = LinkAxisColor;
        this.LinkAxisWidth = LinkAxisWidth;
        this.LinkAxisPad = LinkAxisPad;
        this.displayLinkLabel = displayLinkLabel;
        this.LinkLabelColor = LinkLabelColor;
        this.LinkLabelSize = LinkLabelSize;
        this.LinkLabelPad = LinkLabelPad;
    }
    
    

    public Integer getLinkRadius() {
        return LinkRadius;
    }

    public void setLinkRadius(Integer LinkRadius) {
        this.LinkRadius = LinkRadius;
    }

    public String getLinkFillColor() {
        return LinkFillColor;
    }

    public void setLinkFillColor(String LinkFillColor) {
        this.LinkFillColor = LinkFillColor;
    }

    public Integer getLinkWidth() {
        return LinkWidth;
    }

    public void setLinkWidth(Integer LinkWidth) {
        this.LinkWidth = LinkWidth;
    }

    public String getDisplayLinkAxis() {
        return displayLinkAxis;
    }

    public void setDisplayLinkAxis(String displayLinkAxis) {
        this.displayLinkAxis = displayLinkAxis;
    }

    public String getLinkAxisColor() {
        return LinkAxisColor;
    }

    public void setLinkAxisColor(String LinkAxisColor) {
        this.LinkAxisColor = LinkAxisColor;
    }

    public double getLinkAxisWidth() {
        return LinkAxisWidth;
    }

    public void setLinkAxisWidth(double LinkAxisWidth) {
        this.LinkAxisWidth = LinkAxisWidth;
    }

    public Integer getLinkAxisPad() {
        return LinkAxisPad;
    }

    public void setLinkAxisPad(Integer LinkAxisPad) {
        this.LinkAxisPad = LinkAxisPad;
    }

    public String getDisplayLinkLabel() {
        return displayLinkLabel;
    }

    public void setDisplayLinkLabel(String displayLinkLabel) {
        this.displayLinkLabel = displayLinkLabel;
    }

    public String getLinkLabelColor() {
        return LinkLabelColor;
    }

    public void setLinkLabelColor(String LinkLabelColor) {
        this.LinkLabelColor = LinkLabelColor;
    }

    public Integer getLinkLabelSize() {
        return LinkLabelSize;
    }

    public void setLinkLabelSize(Integer LinkLabelSize) {
        this.LinkLabelSize = LinkLabelSize;
    }

    public Integer getLinkLabelPad() {
        return LinkLabelPad;
    }

    public void setLinkLabelPad(Integer LinkLabelPad) {
        this.LinkLabelPad = LinkLabelPad;
    }
    
    
    
    
    
}
