package com.piedpiper.gib.protocol.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public @Data
class RepositoryDao {

    private String description;
    private String homepage;
    private String name;
    private String full_name;
    private String html_url;
    private String ownerName;
}
