package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.LoginHandler;
import com.piedpiper.gib.handler.LogoutHandler;
import com.piedpiper.gib.protocol.LoginRequest;
import com.piedpiper.gib.protocol.Response;
import com.piedpiper.gib.protocol.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginHandler loginHandler;
    private final LogoutHandler logoutHandler;

    @Autowired
    public LoginController(LoginHandler loginHandler, LogoutHandler logoutHandler) {
        this.loginHandler = loginHandler;
        this.logoutHandler = logoutHandler;
    }

    @PostMapping("/login")
    public Response login(@RequestBody LoginRequest request) {
        return loginHandler.handle(request);
    }

    @PostMapping("/logout")
    public Response login(@RequestBody TokenRequest request) {
        return logoutHandler.handle(request);
    }

}
