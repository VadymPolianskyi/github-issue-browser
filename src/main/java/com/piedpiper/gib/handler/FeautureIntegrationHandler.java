package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.FeautureIntegrationResponse;
import com.piedpiper.gib.protocol.IssueDetailsRequest;
import com.piedpiper.gib.service.GithubService;
import org.kohsuke.github.GHIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FeautureIntegrationHandler implements Handler<IssueDetailsRequest, FeautureIntegrationResponse> {

    private final GithubService githubService;

    @Autowired
    public FeautureIntegrationHandler(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public FeautureIntegrationResponse handle(IssueDetailsRequest request) {
        List<GHIssue> issues = githubService.getListIssues(request.getUser(), request.getRepository(), request.getToken());

        List<Integer> allDays = new ArrayList<>();

        issues.forEach( issue -> {
            int days = calculateDays(issue);
            allDays.add(days);
        });

        Long sum = 0L;
        for (Integer allDate : allDays) {
            sum += allDate;
        }

        int count = allDays.size();

        int avg = Math.toIntExact(sum / count);

        return new FeautureIntegrationResponse(allDays, avg);
    }

    private Integer calculateDays(GHIssue issue) {

        try {
            Date closedAt = issue.getClosedAt();
            Date openAt = issue.getCreatedAt();

            long diff = closedAt.getTime() - openAt.getTime();
            long days = ((diff/60)/60)/24;

            return Math.toIntExact(days);
        } catch (IOException e) {
            throw new RuntimeException(e);//todo: create custom DayCalculationException
        }
    }
}
