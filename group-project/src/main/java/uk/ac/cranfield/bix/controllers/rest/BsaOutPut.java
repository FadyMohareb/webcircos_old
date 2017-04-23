/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.IndGff;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.finalObjects.Link;

/**
 *
 * @author vmuser
 */
public class BsaOutPut {
    
    private List<Object[]> genomes;
    private IndGff arc;
    private Link link;

    public List<Object[]> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Object[]> genomes) {
        this.genomes = genomes;
    }

    public IndGff getArc() {
        return arc;
    }

    public void setArc(IndGff arc) {
        this.arc = arc;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
    
    
    
}
