package com.piedpiper.gib.service.util;

import com.piedpiper.gib.protocol.dao.*;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    private ModelMapper mapper = new ModelMapper();

    public IssueDao mapIssue(GHIssue issue) {
        IssueDao issueDao = mapper.map(issue, IssueDao.class);
        issueDao.setOwner(mapRepository(issue.getRepository()));
        return issueDao;
    }

    public LabelDao mapLabel(GHLabel label) {
        return mapper.map(label, LabelDao.class);
    }

    public RepositoryDao mapRepository(GHRepository repository) {
        RepositoryDao repositoryDao = mapper.map(repository, RepositoryDao.class);
        repositoryDao.setUserName(repository.getOwnerName());
        return repositoryDao;
    }

    public IssueDetailDao mapIssueDetails(GHIssue issue) {
        IssueDetailDao issueDetailsDao = mapper.map(issue, IssueDetailDao.class);
        issueDetailsDao.setOwner(mapRepository(issue.getRepository()));
        return issueDetailsDao;
    }

    public CommentDao mapComment(GHIssueComment comment) {
        return mapper.map(comment, CommentDao.class);
    }

}
