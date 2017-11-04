package com.piedpiper.gib.protocol.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class UserDao {

    private String login;
    private String avatar_url;
    private String gravatar_id;

    // other fields (that only show up in full data)
    private String location;
    private String blog;
    private String email;
    private String name;
    private String company;

    private String html_url;
    private int followers;
    private int following;
    private int public_repos;
    private int public_gists;
}
