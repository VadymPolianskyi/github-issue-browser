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

    private String repositoryName;
    private String user;
    private int page;
    private int size;
    @JsonIgnore
    private GHIssueState state;
}
