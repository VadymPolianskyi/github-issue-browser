package com.piedpiper.gib.protocol.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class IssueDao implements Serializable {

    RepositoryDao owner;

    private String id;
    private String state;
    private int number;
    private String closed_at;
    private int comments;
    private String body;
    private List<LabelDao> labels;
    private UserDao user;
    private String title, html_url;
    private UserDao closed_by;
    private boolean locked;
}
