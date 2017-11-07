package com.piedpiper.gib.controller;

import com.piedpiper.gib.handler.LoginHandler;
import com.piedpiper.gib.protocol.LoginRequest;
import com.piedpiper.gib.protocol.LoginResponse;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Vadym Polyanski
 * Date: 07.11.17
 * Time: 19:48
 */
@WebMvcTest(LoginController.class)
public class LoginControllerTest  extends DefaultControllerTest {
    @MockBean
    private LoginHandler loginHandler;

    @Test
    public void getAllLabelsTest() throws Exception {
        LoginRequest request = new LoginRequest("afgs2", "pass");
        LoginResponse response = new LoginResponse("your token");

        when(loginHandler.handle(any())).thenReturn(response);

        String requestJson = mapper.writeValueAsString(request);
        String responseJson = mapper.writeValueAsString(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseJson))
                .andDo(document("login",
                        preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").description("Username from GitHub"),
                                fieldWithPath("password").description("Password from GitHub")
                        ),
                        responseFields(
                                fieldWithPath("token").description("OAuth token that user got after login.")
                        )));

    }


}
