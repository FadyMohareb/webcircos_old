package uk.ac.cranfield.bix.controllers.rest;

public class RestResponse {
    private String errors;
    private String message;

    public RestResponse(String errors, String message) {
        this.errors = errors;
        this.message = message;
    }

    public RestResponse() {
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

