package com.piedpiper.gib.protocol;

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
}
