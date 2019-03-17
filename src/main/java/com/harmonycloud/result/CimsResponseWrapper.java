package com.harmonycloud.result;

import com.harmonycloud.enums.ErrorMsgEnum;
import io.swagger.annotations.ApiModelProperty;

public class CimsResponseWrapper<E> {

    @ApiModelProperty(required = true)
    private boolean success;

    @ApiModelProperty(notes = "only return when success=false")
    private String errorMessage;

    @ApiModelProperty(notes = "only return when success=true")
    private E returnObject;

    public CimsResponseWrapper() {
        super();
    }

    public CimsResponseWrapper(boolean success, String errorMessage, E returnObject) {
        super();
        this.success = success;
        this.errorMessage = errorMessage;
        this.returnObject = returnObject;
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

    public E getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(E returnObject) {
        this.returnObject = returnObject;
    }


}
