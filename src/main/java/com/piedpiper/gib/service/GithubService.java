package com.piedpiper.gib.service;

import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GithubService {

    public List<GHIssue> getIssues(String user, String repositoryName, GHIssueState state, int page, int size, String token) {
        PagedIterator<GHIssue> iterator = getRepository(repositoryName, user, token).listIssues(state)._iterator(size);
        int counter = 0;
        while (page < counter || !iterator.hasNext()) {
            if (page == counter) return  iterator.nextPage();
            iterator.nextPage();
            counter++;
        }
        throw new RuntimeException(); //todo: create custom IssuesNotFoundException
    }

    private GHRepository getRepository(String name, String user, String token) {
        try {
            GitHub github = GitHub.connectUsingOAuth(token);
            return github.getUser(user).getRepository(name);
        } catch (IOException e) {
            throw new RuntimeException();//todo:create custom RepositoryNotFoundException
        }
    }
}
