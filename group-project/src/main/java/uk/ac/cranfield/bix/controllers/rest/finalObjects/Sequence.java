/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.io.Serializable;

/**
 * This Class is the model for the GENOME module from biocircos library. It allows to store the data in the good shape to be drawn.
 * @author s262012
 */
public class Sequence implements Serializable {

    private String sequenceName;
    private Integer sequenceLength;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Integer getSequenceLength() {
        return sequenceLength;
    }

    public void setSequenceLength(Integer sequenceLength) {
        this.sequenceLength = sequenceLength;
    }

    @Override
    public String toString() {
        return "[" + sequenceName +","+ sequenceLength+"]";
    }

}
