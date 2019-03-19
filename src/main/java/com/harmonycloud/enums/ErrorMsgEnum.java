package com.harmonycloud.enums;


public enum ErrorMsgEnum {

    SERVICE_ERROR("Internal service error"),
    PARAMETER_ERROR("Parameter error"),
    LOCKD("The clinical note has been updated by another user"),
    CLINICAL_SAVE_ERROR("Save clinical note error"),
    CLINICAL_UPDATE_ERROR("Update clinical note error"),
    DIAGNOSIS_SAVE_ERROR("Save diagnosis  error"),
    DIAGNOSIS_UPDATE_ERROR("Update diagnosis error"),
    ORDER_SAVE_ERROR("Save clinical note error"),
    ORDER_UPDATE_ERROR("Update clinical note error");

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
