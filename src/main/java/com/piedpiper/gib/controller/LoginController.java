package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.LoginHandler;
import com.piedpiper.gib.protocol.LoginRequest;
import com.piedpiper.gib.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginHandler loginHandler;

    @Autowired
    public LoginController(LoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest request) {
        return loginHandler.handle(request);
    }

}
