package com.piedpiper.gib.protocol;

import com.piedpiper.gib.protocol.dao.IssueDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class CreateIssueRequest extends TokenRequest {
    private IssueDao issue;
}
