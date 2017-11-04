package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.GetIssuesHandler;
import com.piedpiper.gib.protocol.GetIssuesRequest;
import com.piedpiper.gib.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.kohsuke.github.GHIssueState.ALL;
import static org.kohsuke.github.GHIssueState.CLOSED;
import static org.kohsuke.github.GHIssueState.OPEN;

@RestController
@RequestMapping("/api")
public class IssueController {

    private final GetIssuesHandler getIssuesHandler;

    @Autowired
    public IssueController(GetIssuesHandler getIssuesHandler) {
        this.getIssuesHandler = getIssuesHandler;
    }

    @PostMapping("/issues/{user}/{repoName}")
    public Response getAllIssues(@PathVariable String repoName, @PathVariable String user,
                                 @RequestParam("size") int size, @RequestParam("page") int page) {
        return getIssuesHandler.handle(new GetIssuesRequest(repoName,user, page, size, ALL));
    }

    @PostMapping("/issues/open/{user}/{repoName}")
    public Response getOpenIssues(@PathVariable String repoName, @PathVariable String user,
                                  @RequestParam("size") int size, @RequestParam("page") int page) {
        return getIssuesHandler.handle(new GetIssuesRequest(repoName,user, page, size, OPEN));
    }

    @PostMapping("/issues/closed/{user}/{repoName}")
    public Response getClosedIssues(@PathVariable String repoName, @PathVariable String user,
                                    @RequestParam("size") int size, @RequestParam("page") int page) {
        return getIssuesHandler.handle(new GetIssuesRequest(repoName,user, page, size, CLOSED));
    }
}
