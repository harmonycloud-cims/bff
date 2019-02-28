package com.harmonycloud.result;

import java.io.Serializable;

/**
 * @author qidong
 * @date 2019/2/15
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object errorMessage;
    private Boolean success;
    private Object data;

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Result(CodeMsg errorMessage, Boolean success) {
        this.errorMessage = errorMessage.getMsg();
        this.success = success;
    }

    public Result(Boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }



    public static Result buildError(CodeMsg errorData){
        return new Result(errorData,false);
    }

    public static Result buildSuccess(Object data){
        return new Result(true, data);
    }


}
