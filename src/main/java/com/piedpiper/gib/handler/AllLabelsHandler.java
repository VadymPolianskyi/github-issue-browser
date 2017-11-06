package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.AllLabelsResponse;
import com.piedpiper.gib.protocol.TokenRequest;
import com.piedpiper.gib.protocol.dao.LabelDao;
import com.piedpiper.gib.service.GithubService;
import com.piedpiper.gib.service.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AllLabelsHandler implements Handler<TokenRequest, AllLabelsResponse> {

    private final GithubService githubService;
    private final Mapper mapper;

    @Autowired
    public AllLabelsHandler(GithubService githubService, Mapper mapper) {
        this.githubService = githubService;
        this.mapper = mapper;
    }

    @Override
    public AllLabelsResponse handle(TokenRequest request) {
        log.debug("Got request for getting all labels.");

        List<LabelDao> labels = githubService.labels(request.getToken())
                .stream()
                .map(mapper::mapLabel)
                .collect(Collectors.toList());

        log.info("Returned list with all labels. Count: {}", labels.size());
        return new AllLabelsResponse(labels);
    }
}
