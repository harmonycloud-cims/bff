package com.harmonycloud.result;

import io.swagger.annotations.ApiModelProperty;

public class CimsResponseWrapper<E> {

    @ApiModelProperty(required = true)
    private boolean success;

    @ApiModelProperty(notes = "only return when success=false")
    private String errorMessage;

    @ApiModelProperty(notes = "only return when success=true")
    private E data;

    public CimsResponseWrapper() {
        super();
    }

    public CimsResponseWrapper(boolean success, String errorMessage, E data) {
        this.success = success;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
