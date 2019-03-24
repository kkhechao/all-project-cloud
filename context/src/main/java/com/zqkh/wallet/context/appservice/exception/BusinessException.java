package com.zqkh.wallet.context.appservice.exception;

import com.jovezhao.nest.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * @author kok
 *
 */
public class BusinessException extends CustomException {

    public BusinessException(String msg, Object... args){
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, args);
    }
}
