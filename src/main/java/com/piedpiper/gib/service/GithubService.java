package com.piedpiper.gib.service;

import com.piedpiper.gib.protocol.dao.IssueDao;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;

@Service
public class GithubService {

    public GHIssue createIssue(IssueDao issueDao, String token) {
        try {
            GitHub gitHub = getConnection(token);
            GHRepository repository = gitHub.getUser(issueDao.getOwner().getOwnerName()).getRepository(issueDao.getOwner().getName());

            GHIssueBuilder issueBuilder = createIssueBuilder(repository, issueDao.getTitle());

            issueDao.getLabels()
                    .forEach(label -> issueBuilder.label(label.getName()));

            return issueBuilder.assignee(repository.getOwner())
                    .body(issueDao.getBody()).create();
        } catch (IOException e) {
            throw new RuntimeException();//todo: create custom IssueCreationException
        }


    }

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
            return getConnection(token).getUser(user).getRepository(name);
        } catch (IOException e) {
            throw new RuntimeException();//todo:create custom RepositoryNotFoundException
        }
    }

    private GitHub getConnection(String token) {
        try {
            return GitHub.connectUsingOAuth(token);
        } catch (IOException e) {
            throw new RuntimeException();//todo:create custom ConnectionEstablishingException
        }
    }

    private GHIssueBuilder createIssueBuilder(GHRepository repo, String title) {
        try {
            Constructor<GHIssueBuilder> constructor = GHIssueBuilder.class
                    .getDeclaredConstructor(GHRepository.class, String.class);
            constructor.setAccessible(true);
            return constructor.newInstance(repo, title);
        } catch (Exception e) {
            throw new RuntimeException();// todo: create custom IssueBuilderCreationException;
        }


    }
}
