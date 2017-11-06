package com.piedpiper.gib.service;

import com.piedpiper.gib.protocol.exception.*;
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
            throw new UserLoginException("Can't login user with username '"+username+"'.", e.getCause());
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
            throw new LabelsGettingException("Can't get labels from github service.", e.getCause());
        }
    }

    public GHIssue getIssueById(int issueNumber, String user, String repositoryName, String token) {
        try {
            return getRepository(repositoryName, user, token).getIssue(issueNumber);
        } catch (IOException e) {
            throw new IssueNotFoundException("Issue with number " + issueNumber + " is not found", e.getCause());
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
        throw new IssuesNotFoundException("Issues on page " + page + " with size " + size + "are not found.");
    }

    public int getIssuesCount(String user, String repositoryName, GHIssueState state, String token) {
        List<GHIssue> issues = getRepository(repositoryName, user, token).listIssues(state).asList();
        return issues.size();
    }

    private GHRepository getRepository(String name, String user, String token) {
        try {
            return getConnection(token).getUser(user).getRepository(name);
        } catch (IOException e) {
            throw new RepositoryNotFoundException("Can't find repository with name '" + name + "' of user " + user,e.getCause());
        }
    }

    private GitHub getConnection(String token) {
        try {
            return GitHub.connectUsingOAuth(token);
        } catch (IOException e) {
            throw new ConnectionEstablishingException("Can't connect github account with this token.", e.getCause());
        }
    }
}
