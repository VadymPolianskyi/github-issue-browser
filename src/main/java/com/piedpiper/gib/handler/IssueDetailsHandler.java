package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.IssueDetailsRequest;
import com.piedpiper.gib.protocol.IssueDetailsResponse;
import com.piedpiper.gib.protocol.dao.CommentDao;
import com.piedpiper.gib.protocol.dao.IssueDetailDao;
import com.piedpiper.gib.protocol.exception.CommentGettingException;
import com.piedpiper.gib.protocol.exception.CommentMarkdownException;
import com.piedpiper.gib.service.GithubService;
import com.piedpiper.gib.service.util.StringUtil;
import com.piedpiper.gib.service.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.MarkdownMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
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
        log.debug("Got request for getting details of issue with number {} (repo: {}, user: {}).", request.getIssueNumber(), request.getRepository(), request.getUser());

        GHIssue issue = githubService.getIssueById(request.getIssueNumber(), request.getUser(),
                request.getRepository(), request.getToken());

        IssueDetailDao issueDetailDao = mapper.mapIssueDetails(issue);
        issueDetailDao.setBody(markdownBody(issueDetailDao.getBody(), issue.getRepository()));

        //custom injection user's avatar
        try { issueDetailDao.getUser().setAvatar_url(issue.getUser().getAvatarUrl());} catch (IOException e) { e.printStackTrace(); }

        List<CommentDao> commentDaos = getComments(issue);
        issueDetailDao.setCommentsList(commentDaos);

        //searching for relevant prs
        List<String> relevantIssues = new ArrayList<>();
        List<String> relevantPRs = new ArrayList<>();

        commentDaos.forEach(comment -> {
            comment.setBody(markdownBody(comment.getBody(),issue.getRepository()));

            relevantIssues.addAll(stringUtil.extractIssuesUrls(comment.getBody()));
            relevantPRs.addAll(stringUtil.extractPrUrls(comment.getBody()));
        });


        issueDetailDao.setRelevantPRs(relevantPRs);
        issueDetailDao.setRelevantIssues(relevantIssues);

        log.info("Returned information about issue with title '{}'", issue.getTitle());
        return new IssueDetailsResponse(issueDetailDao);
    }

    private List<CommentDao> getComments(GHIssue issue) {
        try {
            return issue.getComments().stream()
                    .map(comment -> {
                        CommentDao commentDao = mapper.mapComment(comment);
                        try { commentDao.setAuthor(comment.getUser().getName());
                        commentDao.setAvatar_url(comment.getUser().getAvatarUrl());} catch (IOException ignored) {}
                        return commentDao;
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("CommentGettingException: Problems with getting of comments of '{}' issue", issue.getNumber());
            throw new CommentGettingException("Problems with getting of comments of " + issue.getNumber() + " issue.", e.getCause());
        }
    }

    private String markdownBody(String body, GHRepository repository) {
        try {
            Reader reader = repository.renderMarkdown(body, MarkdownMode.GFM);

            StringBuilder html = new StringBuilder();
            int data = reader.read();
            while(data != -1){
                char theChar = (char) data;
                html.append(theChar);
                data = reader.read();
            }

            reader.close();
            return html.toString();
        } catch (IOException e) {
            log.error("CommentMarkdownException: Exception when markdown message body:'{}'.", body);
            throw new CommentMarkdownException("Exception when markdown message body: '" + body + "'",e.getCause());
        }
    }
}
