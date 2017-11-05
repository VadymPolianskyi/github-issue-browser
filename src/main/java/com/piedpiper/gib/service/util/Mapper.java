package com.piedpiper.gib.service.util;

import com.piedpiper.gib.protocol.dao.IssueDao;
import com.piedpiper.gib.protocol.dao.LabelDao;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHLabel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    private ModelMapper mapper = new ModelMapper();

    public IssueDao mapIssue(GHIssue issue) {
        return mapper.map(issue, IssueDao.class);
    }

    public LabelDao mapLabel(GHLabel label) {
        return mapper.map(label, LabelDao.class);
    }
}
