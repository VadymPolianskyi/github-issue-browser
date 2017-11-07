package com.piedpiper.gib.protocol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Exception when markdown comment's body.", code = HttpStatus.INTERNAL_SERVER_ERROR)
public class CommentMarkdownException extends RuntimeException{
    public CommentMarkdownException(String message, Throwable cause) {
        super(message, cause);
    }
}
