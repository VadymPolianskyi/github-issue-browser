package com.piedpiper.gib.protocol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Vadym Polyanski
 * Date: 06.11.17
 * Time: 9:51
 */
@ResponseStatus(reason = "Exception when calculated the period of issue.", code = HttpStatus.INTERNAL_SERVER_ERROR)
public class DayCalculationException extends RuntimeException{

    public DayCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
