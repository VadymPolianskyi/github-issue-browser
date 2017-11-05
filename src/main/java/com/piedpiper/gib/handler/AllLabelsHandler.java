package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.AllLabelsResponse;
import com.piedpiper.gib.protocol.TokenRequest;
import com.piedpiper.gib.protocol.dao.LabelDao;
import com.piedpiper.gib.service.GithubService;
import com.piedpiper.gib.service.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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
        List<LabelDao> labels = githubService.labels(request.getToken())
                .stream()
                .map(mapper::mapLabel)
                .collect(Collectors.toList());

        return new AllLabelsResponse(labels);
    }
}
