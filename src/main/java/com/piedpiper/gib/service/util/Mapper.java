package com.piedpiper.gib.service.util;

import com.piedpiper.gib.protocol.dao.IssueDao;
import org.kohsuke.github.GHIssue;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    private ModelMapper mapper = new ModelMapper();

    public IssueDao mapIssue(GHIssue issue) {

        return mapper.map(issue, IssueDao.class);
    }
}
