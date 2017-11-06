package com.piedpiper.gib.protocol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Vadym Polyanski
 * Date: 06.11.17
 * Time: 9:51
 */
@ResponseStatus(reason = "Can't find repository.", code = HttpStatus.BAD_REQUEST)
public class RepositoryNotFoundException extends RuntimeException{

    public RepositoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
