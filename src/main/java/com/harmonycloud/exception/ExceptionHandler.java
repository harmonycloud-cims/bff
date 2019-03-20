package com.harmonycloud.exception;

import com.harmonycloud.enums.ErrorMsgEnum;
import com.harmonycloud.result.CimsResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CimsResponseWrapper<Object> handler(Throwable e) {
        logger.error(e.getMessage());
        if (e instanceof BffException ) {
            return new CimsResponseWrapper<>(false, e.getMessage(), null);
        }

        return new CimsResponseWrapper<>(false, ErrorMsgEnum.SERVICE_ERROR.getMessage(), null);
    }


}
