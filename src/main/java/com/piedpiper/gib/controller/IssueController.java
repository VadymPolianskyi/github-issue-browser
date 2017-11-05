package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.AllLabelsHandler;
import com.piedpiper.gib.handler.GetIssuesHandler;
import com.piedpiper.gib.protocol.GetIssuesRequest;
import com.piedpiper.gib.protocol.Response;
import com.piedpiper.gib.protocol.TokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.kohsuke.github.GHIssueState.ALL;
import static org.kohsuke.github.GHIssueState.CLOSED;
import static org.kohsuke.github.GHIssueState.OPEN;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final GetIssuesHandler getIssuesHandler;
    private final AllLabelsHandler allLabelsHandler;

    @Autowired
    public IssueController(GetIssuesHandler getIssuesHandler, AllLabelsHandler allLabelsHandler) {
        this.getIssuesHandler = getIssuesHandler;
        this.allLabelsHandler = allLabelsHandler;
    }

    @PostMapping("/issues")
    public Response getAllIssues(@RequestBody GetIssuesRequest request) {
        request.setState(ALL);
        return getIssuesHandler.handle(request);
    }

    @PostMapping("/issues/open")
    public Response getOpenIssues(@RequestBody GetIssuesRequest request) {
        request.setState(OPEN);
        return getIssuesHandler.handle(request);
    }

    @PostMapping("/issues/closed")
    public Response getClosedIssues(@RequestBody GetIssuesRequest request) {
        request.setState(CLOSED);
        return getIssuesHandler.handle(request);
    }

    @PostMapping("/labels")
    public Response getLabels(@RequestBody TokenRequest request) {
        return allLabelsHandler.handle(request);
    }
}
