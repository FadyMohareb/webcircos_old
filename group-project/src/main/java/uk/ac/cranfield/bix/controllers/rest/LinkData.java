/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

/**
 *
 * @author solene
 */
public class LinkData implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("g1chr")
    private String g1chr;
    @JsonProperty("g1start")
    private Integer g1start;
    @JsonProperty("g1end")
    private Integer g1end;
    @JsonProperty("g1name")
    private String g1name;
    @JsonProperty("g2chr")
    private String g2chr;
    @JsonProperty("g2start")
    private Integer g2start;
    @JsonProperty("g2end")
    private Integer g2end;
    @JsonProperty("g2name")
    private String g2name;

    public LinkData(String name, String g1chr, Integer g1start, Integer g1end, String g1name, String g2chr, Integer g2start, Integer g2end, String g2name) {
        this.name = name;
        this.g1chr = g1chr;
        this.g1start = g1start;
        this.g1end = g1end;
        this.g1name = g1name;
        this.g2chr = g2chr;
        this.g2start = g2start;
        this.g2end = g2end;
        this.g2name = g2name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getG1chr() {
        return g1chr;
    }

    public void setG1chr(String g1chr) {
        this.g1chr = g1chr;
    }

    public Integer getG1start() {
        return g1start;
    }

    public void setG1start(Integer g1start) {
        this.g1start = g1start;
    }

    public Integer getG1end() {
        return g1end;
    }

    public void setG1end(Integer g1end) {
        this.g1end = g1end;
    }

    public String getG1name() {
        return g1name;
    }

    public void setG1name(String g1name) {
        this.g1name = g1name;
    }

    public String getG2chr() {
        return g2chr;
    }

    public void setG2chr(String g2chr) {
        this.g2chr = g2chr;
    }

    public Integer getG2start() {
        return g2start;
    }

    public void setG2start(Integer g2start) {
        this.g2start = g2start;
    }

    public Integer getG2end() {
        return g2end;
    }

    public void setG2end(Integer g2end) {
        this.g2end = g2end;
    }

    public String getG2name() {
        return g2name;
    }

    public void setG2name(String g2name) {
        this.g2name = g2name;
    }

}
