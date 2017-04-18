/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cranfield.bix.controllers.rest.finalObjects;

import uk.ac.cranfield.bix.controllers.rest.BackProperties;

/**
 *
 * @author s262012
 */
public class BackgroundDisplay {
    
    private String backId;
    private BackProperties properties;

    public BackgroundDisplay(String backId, BackProperties backProperties) {
        this.backId = backId;
        this.properties = backProperties;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public BackProperties getProperties() {
        return properties;
    }

    public void setProperties(BackProperties properties) {
        this.properties = properties;
    }
 
}
