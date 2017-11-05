package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.GetIssuesRequest;
import com.piedpiper.gib.protocol.GetIssuesResponse;
import com.piedpiper.gib.protocol.dao.IssueDao;
import com.piedpiper.gib.service.GithubService;
import com.piedpiper.gib.service.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GetIssuesHandler implements Handler<GetIssuesRequest, GetIssuesResponse>{

    private final GithubService githubService;
    private final Mapper mapper;

    @Autowired
    public GetIssuesHandler(GithubService githubService, Mapper mapper) {
        this.githubService = githubService;
        this.mapper = mapper;
    }

    @Override
    public GetIssuesResponse handle(GetIssuesRequest request) {
        List<IssueDao> issues = githubService
                .getIssues(request.getUser(), request.getRepository(),
                        request.getState(), request.getPage(), request.getSize(), request.getToken())
                .stream()
                .map(mapper::mapIssue)
                .collect(Collectors.toList());


        int issuesCount = githubService.getIssuesCount(request.getUser(), request.getRepository(),
                request.getState(), request.getToken());

        log.info("Returned issues of repository with name '{}'.", request.getRepository());
        return new GetIssuesResponse(issues, issuesCount);
    }
}
