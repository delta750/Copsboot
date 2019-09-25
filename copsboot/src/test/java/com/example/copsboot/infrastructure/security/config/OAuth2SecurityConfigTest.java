package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.DevelopmentDbInitializer;
import com.example.copsboot.SpringProfiles;
import com.example.copsboot.UsersFactoryTest;
import com.example.copsboot.business.objects.services.DataServices.UserDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @SpringBootTest indicates that the complete application should be started with a mock servlet environment.
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(SpringProfiles.TEST)

// The mock servlet environment can be automatically configured as you don’t require any special
//configuration for this test.
@AutoConfigureMockMvc
public class OAuth2SecurityConfigTest {

    @Autowired
    private MockMvc mvc; // Autowire the MockMvc instance so you can test your endpoint

    @Autowired
    private UserDataService userDataService;

    @Test
    public void testGetAccessTokenAsOfficer() throws Exception {

        userDataService.createOfficer(UsersFactoryTest.OFFICER_EMAIL, UsersFactoryTest.OFFICER_PASSWORD);

        String clientId = "test-client-id";
        String clientSecret = "test-client-secret";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", UsersFactoryTest.OFFICER_EMAIL);
        params.add("password", UsersFactoryTest.OFFICER_PASSWORD);

        mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(clientId, clientSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(print())
                .andExpect((ResultMatcher) jsonPath("access_token").isString())
                .andExpect((ResultMatcher) jsonPath("token_type").value("bearer"))
                .andExpect((ResultMatcher) jsonPath("refresh_token").isString())
                .andExpect((ResultMatcher) jsonPath("expires_in").isNumber())
                .andExpect((ResultMatcher) jsonPath("scope").value("mobile_app"))
        ;

    }
}