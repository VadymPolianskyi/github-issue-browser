package com.piedpiper.gib.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.piedpiper.gib.protocol.dao.IssueDetailDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data
class IssueDetailsResponse extends Response{

    @JsonProperty("issue_detail")
    private IssueDetailDao issueDetail;
}
