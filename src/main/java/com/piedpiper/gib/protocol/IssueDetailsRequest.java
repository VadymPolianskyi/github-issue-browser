package com.piedpiper.gib.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class IssueDetailsRequest extends TokenRequest{
    @JsonProperty("issue_number")
    private int issueNumber;
    private String user;
    private String repository;
}
