package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.GetIssuesHandler;
import com.piedpiper.gib.protocol.GetIssuesRequest;
import com.piedpiper.gib.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final GetIssuesHandler getIssuesHandler;

    @Autowired
    public IssueController(GetIssuesHandler getIssuesHandler) {
        this.getIssuesHandler = getIssuesHandler;
    }

    @GetMapping("/issues/{repoId}")
    public Response getIssues(@PathVariable String repoId) {
        return getIssuesHandler.handle(new GetIssuesRequest(repoId));
    }
}
