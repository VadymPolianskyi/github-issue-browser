package com.piedpiper.gib.service;

import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import static java.util.Arrays.asList;

@Service
public class GithubService {

    public String login(String username, String password) {
        try {
            GitHub gitHub = new GitHubBuilder().withPassword(username, password).build();
            GHAuthorization authorization = gitHub
                    .createOrGetAuth("a7084dcfd8385fcd4966", "3a38643419d9ba3f829b0cdd437f9db6ff0c1b33",
                            asList(GHAuthorization.REPO_STATUS, GHAuthorization.REPO), "Pied Piper login!", null);

            String token = authorization.getToken();

            if (token.equals("")) {
                gitHub.deleteAuth(authorization.getId());
                return login(username, password);
            }
            return token;
        } catch (IOException e) {
            throw new RuntimeException(e.getCause());//todo: create custom UserLoginException
        }

    }

    public List<GHLabel> labels (String token) {
        try {
            return getConnection(token)
                    .getUser("facebook")
                    .getRepository("react")
                    .listLabels()
                    .asList();
        } catch (IOException e) {
            throw new RuntimeException();//todo: create custom LabelsGettingException
        }
    }

    public GHIssue getIssueById(int issueNumber, String user, String repositoryName, String token) {
        try {
            return getRepository(repositoryName, user, token).getIssue(issueNumber);
        } catch (IOException e) {
            throw new RuntimeException(e.getCause()); //todo: create custom IssueNotFoundException
        }
    }

    public List<GHIssue> getIssues(String user, String repositoryName, GHIssueState state, int page, int size, String token) {
        PagedIterator<GHIssue> iterator = getRepository(repositoryName, user, token).listIssues(state)._iterator(size);
        int counter = 0;
        while (page > counter || iterator.hasNext()) {
            if (page == counter) return  iterator.nextPage();
            iterator.nextPage();
            counter++;
        }
        throw new RuntimeException(); //todo: create custom IssuesNotFoundException
    }

    private GHRepository getRepository(String name, String user, String token) {
        try {
            return getConnection(token).getUser(user).getRepository(name);
        } catch (IOException e) {
            throw new RuntimeException(e);//todo:create custom RepositoryNotFoundException
        }
    }

    private GitHub getConnection(String token) {
        try {
            return GitHub.connectUsingOAuth(token);
        } catch (IOException e) {
            throw new RuntimeException(e);//todo:create custom ConnectionEstablishingException
        }
    }
}
