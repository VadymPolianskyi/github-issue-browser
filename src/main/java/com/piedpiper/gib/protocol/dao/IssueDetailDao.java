package com.piedpiper.gib.protocol.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data
class IssueDetailDao extends IssueDao {

    private List<String> relevantIssues;
    private List<String> relevantPRs;
    @JsonProperty("comments_list")
    private List<CommentDao> commentsList;
    private List<UserDao> assignees;
}
