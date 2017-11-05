package com.piedpiper.gib.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piedpiper.gib.protocol.dao.IssueDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data class GetIssuesResponse extends Response {

    private List<IssueDao> issues;
    @JsonProperty("issues_count")
    private int issuesCount;
}
