package com.piedpiper.gib.protocol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Vadym Polyanski
 * Date: 06.11.17
 * Time: 9:51
 */
@ResponseStatus(reason = "Can't get or create oauth token for user with this credentials", code = HttpStatus.BAD_REQUEST)
public class UserLoginException extends RuntimeException{

    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
