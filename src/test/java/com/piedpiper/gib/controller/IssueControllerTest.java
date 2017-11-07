package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.AllLabelsHandler;
import com.piedpiper.gib.handler.GetIssuesHandler;
import com.piedpiper.gib.handler.IssueDetailsHandler;
import com.piedpiper.gib.protocol.*;
import com.piedpiper.gib.protocol.dao.*;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 18:37
 */
@WebMvcTest(IssueController.class)
public class IssueControllerTest  extends DefaultControllerTest {

    @MockBean
    private AllLabelsHandler allLabelsResponse;

    @MockBean
    private GetIssuesHandler getIssuesHandler;

    @MockBean
    private IssueDetailsHandler issueDetailsHandler;


    private List<LabelDao> labels = Arrays.asList(
            new LabelDao("label1URL", "label1Name", "label1Color"),
            new LabelDao("label2URL", "label2Name", "label2Color"),
            new LabelDao("label3URL", "label3Name", "label3Color"),
            new LabelDao("label4URL", "label4Name", "label4Color"),
            new LabelDao("label5URL", "label5Name", "label5Color")
    );



    private UserDao testUser = new UserDao( "login", "avatar", "gravatar", "location", "blog", "e@gmail.com", "nameUser", "Exadel", "urlursl", 3,5,2,6);
    private RepositoryDao testRepo = new RepositoryDao("some description", "homehome", "repo", "full name", "url", "Andre");
    private CommentDao testComment = new CommentDao("Some message","avatar.png", "Jake");


    private List<IssueDao> issues = Arrays.asList(
            new IssueDao(testRepo, "issue1Id1", "CLOSED", 11383, "never:)", 5, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
            new IssueDao(testRepo, "issue1Id2", "CLOSED", 11384, "23.02.2016", 7, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
            new IssueDao(testRepo, "issue1Id3", "CLOSED", 11385, "23.02.2016", 2, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false)
    );

    @Test
    public void getAllLabelsTest() throws Exception {
        TokenRequest request = new TokenRequest("someOAuthToken");
        AllLabelsResponse response = new AllLabelsResponse(labels);

        when(allLabelsResponse.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/labels")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("all-labels",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("labels").description("Url of label"),
                                fieldWithPath("labels[].url").description("Url of label"),
                                fieldWithPath("labels[].name").description("Label's title."),
                                fieldWithPath("labels[].color").description("Label's color")
                        )));

    }

    @Test
    public void getAllIssuesTest() throws Exception {

        List<IssueDao> issues = Arrays.asList(
                new IssueDao(testRepo, "issue1Id1", "OPEN", 11383, "never:)", 5, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id2", "CLOSED", 11384, "23.02.2016", 7, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id3", "OPEN", 11385, "23.02.2016", 2, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false)
        );

        GetIssuesRequest request = new GetIssuesRequest();
        request.setPage(0);
        request.setSize(3);
        request.setRepository("repo");
        request.setToken("sometoken");
        request.setUser("nameUser");

        GetIssuesResponse response = new GetIssuesResponse(issues);

        when(getIssuesHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/issues/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("all-issues",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("data"),
                                fieldWithPath("size").description("data"),
                                fieldWithPath("user").description("data"),
                                fieldWithPath("repository").description("data"),
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("issues").description("data"),
                                fieldWithPath("issues[].owner").description("data"),
                                fieldWithPath("issues[].owner.description").description("data"),
                                fieldWithPath("issues[].owner.homepage").description("data"),
                                fieldWithPath("issues[].owner.name").description("data"),
                                fieldWithPath("issues[].owner.full_name").description("data"),
                                fieldWithPath("issues[].owner.html_url").description("data"),
                                fieldWithPath("issues[].owner.owner").description("data"),
                                fieldWithPath("issues[].id").description("data"),
                                fieldWithPath("issues[].state").description("data"),
                                fieldWithPath("issues[].closed_at").description("data"),
                                fieldWithPath("issues[].comments").description("data"),
                                fieldWithPath("issues[].labels").description("data"),
                                fieldWithPath("issues[].labels[].url").description("Url of label"),
                                fieldWithPath("issues[].labels[].name").description("Label's title."),
                                fieldWithPath("issues[].labels[].color").description("Label's color"),
                                fieldWithPath("issues[].user").description("data"),
                                fieldWithPath("issues[].user.login").description("data"),
                                fieldWithPath("issues[].user.avatar_url").description("data"),
                                fieldWithPath("issues[].user.gravatar_id").description("data"),
                                fieldWithPath("issues[].user.location").description("data"),
                                fieldWithPath("issues[].user.blog").description("data"),
                                fieldWithPath("issues[].user.email").description("data"),
                                fieldWithPath("issues[].user.name").description("data"),
                                fieldWithPath("issues[].user.company").description("data"),
                                fieldWithPath("issues[].user.html_url").description("data"),
                                fieldWithPath("issues[].user.followers").description("data"),
                                fieldWithPath("issues[].user.following").description("data"),
                                fieldWithPath("issues[].user.public_repos").description("data"),
                                fieldWithPath("issues[].user.public_gists").description("data"),
                                fieldWithPath("issues[].title").description("data"),
                                fieldWithPath("issues[].html_url").description("data"),
                                fieldWithPath("issues[].closed_by").description("data"),
                                fieldWithPath("issues[].locked").description("data")
                        )));

    }


    @Test
    public void getOpenIssuesTest() throws Exception {

        List<IssueDao> issues = Arrays.asList(
                new IssueDao(testRepo, "issue1Id1", "OPEN", 11383, "never:)", 5, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id2", "OPEN", 11384, "23.02.2016", 7, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id3", "OPEN", 11385, "23.02.2016", 2, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false)
        );

        GetIssuesRequest request = new GetIssuesRequest();
        request.setPage(0);
        request.setSize(3);
        request.setRepository("repo");
        request.setToken("sometoken");
        request.setUser("nameUser");

        GetIssuesResponse response = new GetIssuesResponse(issues);

        when(getIssuesHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/issues/open")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("open-issues",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("data"),
                                fieldWithPath("size").description("data"),
                                fieldWithPath("user").description("data"),
                                fieldWithPath("repository").description("data"),
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("issues").description("data"),
                                fieldWithPath("issues[].owner").description("data"),
                                fieldWithPath("issues[].owner.description").description("data"),
                                fieldWithPath("issues[].owner.homepage").description("data"),
                                fieldWithPath("issues[].owner.name").description("data"),
                                fieldWithPath("issues[].owner.full_name").description("data"),
                                fieldWithPath("issues[].owner.html_url").description("data"),
                                fieldWithPath("issues[].owner.owner").description("data"),
                                fieldWithPath("issues[].id").description("data"),
                                fieldWithPath("issues[].state").description("data"),
                                fieldWithPath("issues[].closed_at").description("data"),
                                fieldWithPath("issues[].comments").description("data"),
                                fieldWithPath("issues[].labels").description("data"),
                                fieldWithPath("issues[].labels[].url").description("Url of label"),
                                fieldWithPath("issues[].labels[].name").description("Label's title."),
                                fieldWithPath("issues[].labels[].color").description("Label's color"),
                                fieldWithPath("issues[].user").description("data"),
                                fieldWithPath("issues[].user.login").description("data"),
                                fieldWithPath("issues[].user.avatar_url").description("data"),
                                fieldWithPath("issues[].user.gravatar_id").description("data"),
                                fieldWithPath("issues[].user.location").description("data"),
                                fieldWithPath("issues[].user.blog").description("data"),
                                fieldWithPath("issues[].user.email").description("data"),
                                fieldWithPath("issues[].user.name").description("data"),
                                fieldWithPath("issues[].user.company").description("data"),
                                fieldWithPath("issues[].user.html_url").description("data"),
                                fieldWithPath("issues[].user.followers").description("data"),
                                fieldWithPath("issues[].user.following").description("data"),
                                fieldWithPath("issues[].user.public_repos").description("data"),
                                fieldWithPath("issues[].user.public_gists").description("data"),
                                fieldWithPath("issues[].title").description("data"),
                                fieldWithPath("issues[].html_url").description("data"),
                                fieldWithPath("issues[].closed_by").description("data"),
                                fieldWithPath("issues[].locked").description("data")
                        )));

    }


    @Test
    public void getClosedIssuesTest() throws Exception {

        List<IssueDao> issues = Arrays.asList(
                new IssueDao(testRepo, "issue1Id1", "CLOSED", 11383, "never:)", 5, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id2", "CLOSED", 11384, "23.02.2016", 7, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false),
                new IssueDao(testRepo, "issue1Id3", "CLOSED", 11385, "23.02.2016", 2, "Some text", labels, testUser, "some title", "someHTMLURL", testUser, false)
        );

        GetIssuesRequest request = new GetIssuesRequest();
        request.setPage(0);
        request.setSize(3);
        request.setRepository("repo");
        request.setToken("sometoken");
        request.setUser("nameUser");

        GetIssuesResponse response = new GetIssuesResponse(issues);

        when(getIssuesHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/issues/closed")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("closed-issues",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("page").description("data"),
                                fieldWithPath("size").description("data"),
                                fieldWithPath("user").description("data"),
                                fieldWithPath("repository").description("data"),
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("issues").description("data"),
                                fieldWithPath("issues[].owner").description("data"),
                                fieldWithPath("issues[].owner.description").description("data"),
                                fieldWithPath("issues[].owner.homepage").description("data"),
                                fieldWithPath("issues[].owner.name").description("data"),
                                fieldWithPath("issues[].owner.full_name").description("data"),
                                fieldWithPath("issues[].owner.html_url").description("data"),
                                fieldWithPath("issues[].owner.owner").description("data"),
                                fieldWithPath("issues[].id").description("data"),
                                fieldWithPath("issues[].state").description("data"),
                                fieldWithPath("issues[].closed_at").description("data"),
                                fieldWithPath("issues[].comments").description("data"),
                                fieldWithPath("issues[].labels").description("data"),
                                fieldWithPath("issues[].labels[].url").description("Url of label"),
                                fieldWithPath("issues[].labels[].name").description("Label's title."),
                                fieldWithPath("issues[].labels[].color").description("Label's color"),
                                fieldWithPath("issues[].user").description("data"),
                                fieldWithPath("issues[].user.login").description("data"),
                                fieldWithPath("issues[].user.avatar_url").description("data"),
                                fieldWithPath("issues[].user.gravatar_id").description("data"),
                                fieldWithPath("issues[].user.location").description("data"),
                                fieldWithPath("issues[].user.blog").description("data"),
                                fieldWithPath("issues[].user.email").description("data"),
                                fieldWithPath("issues[].user.name").description("data"),
                                fieldWithPath("issues[].user.company").description("data"),
                                fieldWithPath("issues[].user.html_url").description("data"),
                                fieldWithPath("issues[].user.followers").description("data"),
                                fieldWithPath("issues[].user.following").description("data"),
                                fieldWithPath("issues[].user.public_repos").description("data"),
                                fieldWithPath("issues[].user.public_gists").description("data"),
                                fieldWithPath("issues[].title").description("data"),
                                fieldWithPath("issues[].html_url").description("data"),
                                fieldWithPath("issues[].closed_by").description("data"),
                                fieldWithPath("issues[].locked").description("data")
                        )));

    }


    @Test
    public void getIssueDetailsTest() throws Exception {

        IssueDetailDao issueDetailDao = new IssueDetailDao(
                Arrays.asList("first", "secondIssues"),
                Arrays.asList("first", "secondPR"),
                Arrays.asList(testComment, testComment),
                Collections.singletonList(testUser)
        );

        IssueDao issueDao = issues.get(0);

        issueDetailDao.setOwner(issueDao.getOwner());
        issueDetailDao.setId(issueDao.getId());
        issueDetailDao.setState(issueDao.getState());
        issueDetailDao.setNumber(issueDao.getComments());
        issueDetailDao.setClosed_at(issueDao.getClosed_at());
        issueDetailDao.setComments(issueDao.getComments());
        issueDetailDao.setBody(issueDao.getBody());
        issueDetailDao.setLabels(issueDao.getLabels());
        issueDetailDao.setUser(issueDao.getUser());
        issueDetailDao.setTitle(issueDao.getTitle());
        issueDetailDao.setHtml_url(issueDao.getHtml_url());
        issueDetailDao.setClosed_by(issueDao.getClosed_by());
        issueDetailDao.setLocked(true);


        IssueDetailsRequest request = new IssueDetailsRequest();
        request.setRepository("repo");
        request.setToken("sometoken");
        request.setUser("nameUser");
        request.setIssueNumber(11383);

        IssueDetailsResponse response = new IssueDetailsResponse(issueDetailDao);

        when(issueDetailsHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/issues/details")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("issues-details",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("issue_number").description("data"),
                                fieldWithPath("user").description("data"),
                                fieldWithPath("repository").description("data"),
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("issue_detail").description("data"),
                                fieldWithPath("issue_detail.owner").description("data"),
                                fieldWithPath("issue_detail.owner.description").description("data"),
                                fieldWithPath("issue_detail.owner.homepage").description("data"),
                                fieldWithPath("issue_detail.owner.name").description("data"),
                                fieldWithPath("issue_detail.owner.full_name").description("data"),
                                fieldWithPath("issue_detail.owner.html_url").description("data"),
                                fieldWithPath("issue_detail.owner.owner").description("data"),
                                fieldWithPath("issue_detail.id").description("data"),
                                fieldWithPath("issue_detail.state").description("data"),
                                fieldWithPath("issue_detail.closed_at").description("data"),
                                fieldWithPath("issue_detail.comments").description("data"),
                                fieldWithPath("issue_detail.labels").description("data"),
                                fieldWithPath("issue_detail.labels[].url").description("Url of label"),
                                fieldWithPath("issue_detail.labels[].name").description("Label's title."),
                                fieldWithPath("issue_detail.labels[].color").description("Label's color"),
                                fieldWithPath("issue_detail.user").description("data"),
                                fieldWithPath("issue_detail.user.login").description("data"),
                                fieldWithPath("issue_detail.user.avatar_url").description("data"),
                                fieldWithPath("issue_detail.user.gravatar_id").description("data"),
                                fieldWithPath("issue_detail.user.location").description("data"),
                                fieldWithPath("issue_detail.user.blog").description("data"),
                                fieldWithPath("issue_detail.user.email").description("data"),
                                fieldWithPath("issue_detail.user.name").description("data"),
                                fieldWithPath("issue_detail.user.company").description("data"),
                                fieldWithPath("issue_detail.user.html_url").description("data"),
                                fieldWithPath("issue_detail.user.followers").description("data"),
                                fieldWithPath("issue_detail.user.following").description("data"),
                                fieldWithPath("issue_detail.user.public_repos").description("data"),
                                fieldWithPath("issue_detail.user.public_gists").description("data"),
                                fieldWithPath("issue_detail.title").description("data"),
                                fieldWithPath("issue_detail.html_url").description("data"),
                                fieldWithPath("issue_detail.closed_by").description("data"),
                                fieldWithPath("issue_detail.locked").description("data"),
                                fieldWithPath("issue_detail.relevantIssues").description("data"),
                                fieldWithPath("issue_detail.relevantIssues[]").description("data"),
                                fieldWithPath("issue_detail.relevantPRs").description("data"),
                                fieldWithPath("issue_detail.relevantPRs[]").description("data"),
                                fieldWithPath("issue_detail.comments_list").description("data"),
                                fieldWithPath("issue_detail.comments_list[].body").description("data"),
                                fieldWithPath("issue_detail.comments_list[].avatar_url").description("data"),
                                fieldWithPath("issue_detail.comments_list[].author").description("data"),
                                fieldWithPath("issue_detail.assignees").description("data"),
                                fieldWithPath("issue_detail.assignees[].login").description("data"),
                                fieldWithPath("issue_detail.assignees[].avatar_url").description("data"),
                                fieldWithPath("issue_detail.assignees[].gravatar_id").description("data"),
                                fieldWithPath("issue_detail.assignees[].location").description("data"),
                                fieldWithPath("issue_detail.assignees[].email").description("data"),
                                fieldWithPath("issue_detail.assignees[].name").description("data"),
                                fieldWithPath("issue_detail.assignees[].company").description("data"),
                                fieldWithPath("issue_detail.assignees[].html_url").description("data"),
                                fieldWithPath("issue_detail.assignees[].followers").description("data"),
                                fieldWithPath("issue_detail.assignees[].following").description("data"),
                                fieldWithPath("issue_detail.assignees[].public_repos").description("data"),
                                fieldWithPath("issue_detail.assignees[].public_gists").description("data")
                        )));
    }
}