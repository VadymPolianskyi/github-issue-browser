package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.FeatureIntegrationHandler;
import com.piedpiper.gib.protocol.RepositoryRequest;
import com.piedpiper.gib.protocol.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 17:04
 */
@RestController
@RequestMapping("/api")
public class UtilController {

    private final FeatureIntegrationHandler featureIntegrationHandler;

    @Autowired
    public UtilController(FeatureIntegrationHandler featureIntegrationHandler) {
        this.featureIntegrationHandler = featureIntegrationHandler;
    }

    @PostMapping("/util/fi")
    public Response getIssueDetail(@RequestBody RepositoryRequest request) {
        return featureIntegrationHandler.handle(request);
    }
}
