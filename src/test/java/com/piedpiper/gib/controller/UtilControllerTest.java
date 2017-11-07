package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.FeatureIntegrationHandler;
import com.piedpiper.gib.protocol.FeatureIntegrationResponse;
import com.piedpiper.gib.protocol.LoginRequest;
import com.piedpiper.gib.protocol.LoginResponse;
import com.piedpiper.gib.protocol.RepositoryRequest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 19:52
 */
@WebMvcTest(UtilController.class)
public class UtilControllerTest  extends DefaultControllerTest {
    @MockBean
    private FeatureIntegrationHandler featureIntegrationHandler;

    @Test
    public void getAllLabelsTest() throws Exception {
        RepositoryRequest request = new RepositoryRequest("facebook", "react");
        request.setToken("my token");
        FeatureIntegrationResponse response = new FeatureIntegrationResponse(4,4905, 276, 0);

        when(featureIntegrationHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/util/fi")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("fi",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("user").description("user github"),
                                fieldWithPath("repository").description("repository github"),
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        ),
                        responseFields(
                                fieldWithPath("avg").description("avg"),
                                fieldWithPath("count").description("count of issues"),
                                fieldWithPath("max").description("max value from periods"),
                                fieldWithPath("min").description("min value from periods")
                        )));

    }
}
