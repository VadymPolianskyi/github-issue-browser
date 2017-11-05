package com.piedpiper.gib.protocol.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class CommentDao implements Serializable {
    private String body;
    private String gravatar_id;
    private String author;
    private IssueDao issueDao;
}
