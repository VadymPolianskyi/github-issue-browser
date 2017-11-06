package com.piedpiper.gib.service.util;

import com.piedpiper.gib.protocol.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueComment;
import org.kohsuke.github.GHLabel;
import org.kohsuke.github.GHRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Mapper {

    private ModelMapper mapper = new ModelMapper();

    public IssueDao mapIssue(GHIssue issue) {
        IssueDao issueDao = mapper.map(issue, IssueDao.class);
        issueDao.setOwner(mapRepository(issue.getRepository()));
        log.debug("Mapped issue dao with number {}", issue.getNumber());
        return issueDao;
    }

    public LabelDao mapLabel(GHLabel label) {
        return mapper.map(label, LabelDao.class);
    }

    public RepositoryDao mapRepository(GHRepository repository) {
        RepositoryDao repositoryDao = mapper.map(repository, RepositoryDao.class);
        repositoryDao.setUserName(repository.getOwnerName());
        log.debug("Mapped repository dao with name {}", repository.getName());
        return repositoryDao;
    }

    public IssueDetailDao mapIssueDetails(GHIssue issue) {
        IssueDetailDao issueDetailsDao = mapper.map(issue, IssueDetailDao.class);
        issueDetailsDao.setOwner(mapRepository(issue.getRepository()));
        log.debug("Mapped issue details dao of issue with number", issue.getNumber());
        return issueDetailsDao;
    }

    public CommentDao mapComment(GHIssueComment comment) {
        CommentDao commentDao = mapper.map(comment, CommentDao.class);
        log.debug("Mapped comment dao with id {}", comment.getId());
        return commentDao;
    }

}
