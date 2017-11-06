package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.LoginRequest;
import com.piedpiper.gib.protocol.LoginResponse;
import com.piedpiper.gib.service.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginHandler implements Handler<LoginRequest, LoginResponse>{

    private final GithubService githubService;

    @Autowired
    public LoginHandler(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public LoginResponse handle(LoginRequest request) {
        log.debug("Got login request from user {}", request.getUsername());
        String token = githubService.login(request.getUsername(), request.getPassword());

        log.info("Login user with username {}", request.getUsername());
        return new LoginResponse(token);
    }
}
