package com.harmonycloud.dto;

public class ResponseDto {
    private boolean success;

    private String errorMsg;

    private Object data;

    public ResponseDto() {
    }

    public ResponseDto(boolean success, String errorMsg, Object data) {
        this.success = success;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
