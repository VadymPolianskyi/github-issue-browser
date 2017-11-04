package com.piedpiper.gib.handler;

import com.piedpiper.gib.protocol.GetIssuesRequest;
import com.piedpiper.gib.protocol.GetIssuesResponse;
import org.springframework.stereotype.Component;

@Component
public class GetIssuesHandler implements Handler<GetIssuesRequest, GetIssuesResponse>{
    @Override
    public GetIssuesResponse handle(GetIssuesRequest request) {
        //todo: implement
        return null;
    }
}
