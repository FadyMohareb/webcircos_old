/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest;

/**
 * Class containing the data structure which allow web application and server to communicate for genome and chromosome circos draw purpose
 * @author s262012
 */
public class CircosInput {

    private String projectName;
    private String referenceSequence;
    private String genomicCoverage;
    private String snpdensity;
    private String transcriptiomicCoverage;
    private String genesExpresion;
    private String differentialExpression;
    private String annotation;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getReferenceSequence() {
        return referenceSequence;
    }

    public void setReferenceSequence(String referenceSequence) {
        this.referenceSequence = referenceSequence;
    }

    public String getGenomicCoverage() {
        return genomicCoverage;
    }

    public void setGenomicCoverage(String genomicCoverage) {
        this.genomicCoverage = genomicCoverage;
    }

    public String getSnpdensity() {
        return snpdensity;
    }

    public void setSnpdensity(String snpdensity) {
        this.snpdensity = snpdensity;
    }


    public String getTranscriptiomicCoverage() {
        return transcriptiomicCoverage;
    }

    public void setTranscriptiomicCoverage(String transcriptiomicCoverage) {
        this.transcriptiomicCoverage = transcriptiomicCoverage;
    }

    public String getGenesExpresion() {
        return genesExpresion;
    }

    public void setGenesExpresion(String genesExpresion) {
        this.genesExpresion = genesExpresion;
    }

    public String getDifferentialExpression() {
        return differentialExpression;
    }

    public void setDifferentialExpression(String differentialExpression) {
        this.differentialExpression = differentialExpression;
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
        referenceSequence = removeSingleExtension(referenceSequence);
        genomicCoverage = removeSingleExtension(genomicCoverage);
        snpdensity = removeSingleExtension(snpdensity);
        transcriptiomicCoverage = removeSingleExtension(transcriptiomicCoverage);
        genesExpresion = removeSingleExtension(genesExpresion);
        differentialExpression = removeSingleExtension(differentialExpression);
        annotation = removeSingleExtension(annotation);
    }
}
