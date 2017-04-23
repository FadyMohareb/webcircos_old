/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import java.util.List;
import uk.ac.cranfield.bix.controllers.rest.LinkData;
import uk.ac.cranfield.bix.controllers.rest.LinkProperties;

/**
 *
 * @author solene
 */
public class Link {
    
    private String linkId;
    private List<LinkData> linkData;
    private LinkProperties properties;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }
    
    public List<LinkData> getLinkData() {
        return linkData;
    }

    public void setLinkData(List<LinkData> linkData) {
        this.linkData = linkData;
    }

    public LinkProperties getProperties() {
        return properties;
    }

    public void setProperties(LinkProperties properties) {
        this.properties = properties;
    }
    
}
