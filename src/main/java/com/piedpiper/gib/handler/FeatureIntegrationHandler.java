package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.FeatureIntegrationResponse;
import com.piedpiper.gib.protocol.RepositoryRequest;
import com.piedpiper.gib.protocol.exception.DayCalculationException;
import com.piedpiper.gib.service.GithubService;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.PagedSearchIterable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class FeatureIntegrationHandler implements Handler<RepositoryRequest, FeatureIntegrationResponse> {

    private final GithubService githubService;

    @Autowired
    public FeatureIntegrationHandler(GithubService githubService) {
        this.githubService = githubService;
    }

    @Override
    public FeatureIntegrationResponse handle(RepositoryRequest request) {
        log.debug("Got request for calculating of feature integration in repository {} of user {}.",
                request.getRepository(), request.getUser());

        PagedSearchIterable<GHIssue> response = githubService.getAllClosedIssues(request.getUser(), request.getRepository(), request.getToken());

        List<Integer> allDays = new ArrayList<>();

        response.forEach(issue -> {
            int days = calculateDays(issue);
            allDays.add(days);
        });

        long sum = 0L;
        for (Integer allDate : allDays) { sum += allDate; }



        int max = allDays.stream().mapToInt(i -> i).max().getAsInt();
        int min = allDays.stream().mapToInt(i -> i).min().getAsInt();
        int count = response.getTotalCount();
        int avg = Math.toIntExact(sum / count);

        log.info("Returned data about feature integration in repository {} of user {}. AVG: {}, " +
                        "MAX: {}, MIN: {}, COUNT: {}.", request.getRepository(), request.getUser(),
                avg, max, min, count);
        return new FeatureIntegrationResponse(avg, count, max, min);
    }

    private Integer calculateDays(GHIssue issue) {
        try {
            Date closedAt = issue.getClosedAt();
            Date openAt = issue.getCreatedAt();

            long diff = closedAt.getTime() - openAt.getTime();
            long days = (diff/3600000)/24;

            return Math.toIntExact(days);
        } catch (IOException e) {
            log.error("DayCalculationException: Exception calculated issue with number {}.", issue.getNumber());
            throw new DayCalculationException("Exception calculated issue with number " + issue.getNumber(),e.getCause());
        }
    }
}
