package com.piedpiper.gib.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.kohsuke.github.GHIssueState;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public @Data class GetIssuesRequest extends TokenRequest {

    @JsonIgnore
    private String repositoryName;
    @JsonIgnore
    private String user;
    @JsonIgnore
    private int page;
    @JsonIgnore
    private int size;
    @JsonIgnore
    private GHIssueState state;
}
