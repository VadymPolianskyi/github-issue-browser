package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.FeatureIntegrationResponse;
import com.piedpiper.gib.protocol.RepositoryRequest;
import com.piedpiper.gib.service.GithubService;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueSearchBuilder;
import org.kohsuke.github.PagedSearchIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FeatureIntegrationHandler implements Handler<RepositoryRequest, FeatureIntegrationResponse> {

    private final GithubService githubService;

    @Autowired
    public FeatureIntegrationHandler(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public FeatureIntegrationResponse handle(RepositoryRequest request) {

        PagedSearchIterable<GHIssue> response = githubService.getAllClosedIssues(request.getUser(), request.getRepository(), request.getToken());

        List<Integer> allDays = new ArrayList<>();

        response.forEach(issue -> {
            int days = calculateDays(issue);
            allDays.add(days);
        });

        long sum = 0L;
        for (Integer allDate : allDays) {
            sum += allDate;
        }

        int count = response.getTotalCount();
        int avg = Math.toIntExact(sum / count);

        return new FeatureIntegrationResponse(allDays, avg, count);
    }

    private Integer calculateDays(GHIssue issue) {

        try {
            Date closedAt = issue.getClosedAt();
            Date openAt = issue.getCreatedAt();

            long diff = closedAt.getTime() - openAt.getTime();
            long days = (diff/3600000)/24;

            return Math.toIntExact(days);
        } catch (IOException e) {
            throw new RuntimeException(e);//todo: create custom DayCalculationException
        }
    }
}
