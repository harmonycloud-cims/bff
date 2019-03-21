package com.harmonycloud.enums;


public enum ErrorMsgEnum {

    SERVICE_ERROR("Internal service error"),
    PARAMETER_ERROR("Parameter error"),
    LOCKD("The clinical note has been updated by another user"),
    CLINICAL_SAVE_ERROR("Save clinical note error"),
    CLINICAL_UPDATE_ERROR("Update clinical note error"),
    DIAGNOSIS_SAVE_ERROR("Save diagnosis  error"),
    DIAGNOSIS_UPDATE_ERROR("Update diagnosis error"),
    ORDER_SAVE_ERROR("Save Order note error"),
    ORDER_UPDATE_ERROR("Update Order note error"),
    FORMAT_ERROR("Unable to parse the proxy port number"),
    AUTHENTICATION_ERROR("Could not set user authentication in security context");

    private String message;

    ErrorMsgEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
