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
 * Data structure which is sent back to web application to draw BSA circos. 
 * Data are already processed and in the good shape to be understood by the biocircos library.
 * @author vmuser
 */
public class BsaOutPut {
    
    private List<Object[]> genomes;
//    private IndGff arc;
    private Link link;

    public List<Object[]> getGenomes() {
        return genomes;
    }

    public void setGenomes(List<Object[]> genomes) {
        this.genomes = genomes;
    }

//    public IndGff getArc() {
//        return arc;
//    }
//
//    public void setArc(IndGff arc) {
//        this.arc = arc;
//    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
    
    
    
}
