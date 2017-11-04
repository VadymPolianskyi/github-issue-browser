package com.piedpiper.gib.protocol;

import com.piedpiper.gib.protocol.dao.IssueDao;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

//@NoArgsConstructor
//@AllArgsConstructor
public class GetIssuesResponse extends Response {

    private List<IssueDao> issues;
}
