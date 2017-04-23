/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

/**
 *
 * @author vmuser
 */
public class BsaInput {
    
    private String projectName;
    private String sequence;
    private String parent1;
    private String parent2;
    private String pool1;
    private String pool2;
    private String annotation;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getParent1() {
        return parent1;
    }

    public void setParent1(String parent1) {
        this.parent1 = parent1;
    }

    public String getParent2() {
        return parent2;
    }

    public void setParent2(String parent2) {
        this.parent2 = parent2;
    }

    public String getPool1() {
        return pool1;
    }

    public void setPool1(String pool1) {
        this.pool1 = pool1;
    }

    public String getPool2() {
        return pool2;
    }

    public void setPool2(String pool2) {
        this.pool2 = pool2;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }
    
    private String removeSingleExtension(String str) {
        if(str != null){
            str = str.trim();
            return str.substring(0, str.lastIndexOf('.'));
        }
        return null;
    }
    
    public void removeExtensions() {
        //projectName = removeSingleExtension(projectName);
        sequence = removeSingleExtension(sequence);
//        parent1 = removeSingleExtension(parent1);
//        parent2 = removeSingleExtension(parent2);
//        pool1 = removeSingleExtension(pool1);
//        pool2 = removeSingleExtension(pool2);
        annotation = removeSingleExtension(annotation);
    }
    
}
