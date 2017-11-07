package com.piedpiper.gib.handler;


import com.piedpiper.gib.protocol.Response;
import com.piedpiper.gib.protocol.TokenRequest;
import com.piedpiper.gib.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 10:00
 */
@Component
public class LogoutHandler implements Handler<TokenRequest, Response> {

    private final GithubService githubService;

    @Autowired
    public LogoutHandler(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public Response handle(TokenRequest request) {
        githubService.logout(request.getToken());
        return new Response();
    }
}
