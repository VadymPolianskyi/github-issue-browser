package com.piedpiper.gib.protocol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author: Vadym Polyanski
 * Date: 06.11.17
 * Time: 9:51
 */
@ResponseStatus(reason = "Problem with getting comments from github.", code = HttpStatus.BAD_REQUEST)
public class CommentGettingException extends RuntimeException{

    public CommentGettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
