package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.IssueDetailsRequest;
import com.piedpiper.gib.protocol.IssueDetailsResponse;
import com.piedpiper.gib.protocol.dao.CommentDao;
import com.piedpiper.gib.protocol.dao.IssueDetailDao;
import com.piedpiper.gib.service.GithubService;
import com.piedpiper.gib.service.StringUtil;
import com.piedpiper.gib.service.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class IssueDetailsHandler implements Handler<IssueDetailsRequest, IssueDetailsResponse> {

    private final StringUtil stringUtil;
    private final GithubService githubService;
    private final Mapper mapper;

    @Autowired
    public IssueDetailsHandler(StringUtil stringUtil, GithubService githubService, Mapper mapper) {
        this.stringUtil = stringUtil;
        this.githubService = githubService;
        this.mapper = mapper;
    }

    @Override
    public IssueDetailsResponse handle(IssueDetailsRequest request) {
        GHIssue issue = githubService.getIssueById(request.getIssueNumber(), request.getUser(),
                request.getRepository(), request.getToken());

        IssueDetailDao issueDetailDao = mapper.mapIssueDetails(issue);


        List<CommentDao> commentDaos = getComments(issue);
        issueDetailDao.setCommentsList(commentDaos);

        //searching for relevant prs
        List<String> relevantIssues = new ArrayList<>();
        List<String> relevantPRs = new ArrayList<>();

        commentDaos.forEach(comment -> {
            relevantIssues.addAll(stringUtil.extractIssuesUrls(comment.getBody()));
            relevantPRs.addAll(stringUtil.extractPrUrls(comment.getBody()));
        });

        issueDetailDao.setRelevantPRs(relevantPRs);
        issueDetailDao.setRelevantIssues(relevantIssues);

        log.info("Got information about issue with title '{}'", issue.getTitle());
        return new IssueDetailsResponse(issueDetailDao);
    }

    private List<CommentDao> getComments(GHIssue issue) {
        try {
            return issue.getComments().stream()
                    .map(comment -> {
                        CommentDao commentDao = mapper.mapComment(comment);
                        try { commentDao.setAuthor(comment.getUser().getName()); } catch (IOException ignored) {}
                        return commentDao;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);//todo: create custom CommentGettingException
        }
    }
}
