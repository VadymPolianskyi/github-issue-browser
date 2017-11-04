package com.piedpiper.gib.protocol;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class Response implements Serializable{
    private static final long serialVersionUID = 5659533527783351697L;

    private int code = HttpStatus.OK.value();
    private String message = HttpStatus.OK.getReasonPhrase();
}
