package com.piedpiper.gib.protocol;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetIssuesRequest extends Request {
    private String repoId;
}
