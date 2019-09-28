package com.example.copsboot.restservices;

import com.example.copsboot.SpringProfiles;
import com.example.copsboot.UsersFactoryTest;
import com.example.copsboot.business.objects.entities.UserDetailsService;
import com.example.copsboot.business.objects.services.DataServices.UserDataService;
import com.example.copsboot.infrastructure.security.StubUserDetailsService;
import com.example.copsboot.infrastructure.security.config.OAuth2ServerConfiguration;
import com.example.copsboot.infrastructure.security.config.SecurityConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.boot.json.JacksonJsonParser;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // Spring Runner to enable test slicing.
@WebMvcTest(UserRestService.class) // with a reference to the controller you want to test
@ActiveProfiles(SpringProfiles.TEST) //Activate the test profile.
public class UserRestServiceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDataService userDataService; // This is the mocked instance of the UserDataService since the controller needs to get it autowired.

    /* An inner class is added for the testing framework to use as the Spring context for the test.
     * Any static inner class that is annotated with @TestConfiguration will be
     * picked up when the test runs and the beans defined there will be put in the Spring context together
     * with the automatically created beans from the @WebMvcTest annotation
     */
    @TestConfiguration
    @Import(OAuth2ServerConfiguration.class) // Import your actual security configuration from the application
    static class TestConfig {
        @Bean
        public UserDetailsService userDetailsService() {
            return new StubUserDetailsService();
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        // Use the default SecurityConfiguration bean. As you are running with the test profile, this will be
        //filled with the values from application-test.properties
        @Bean
        public SecurityConfiguration securityConfiguration() {
            return new SecurityConfiguration();
        }
    }

    @Test
    public void givenNotAuthenticatedWhenAskingMyDetails_NOK() throws Exception {
        mvc.perform(get("/api/users/currentUser")) // the get() is a static import from MockMvcRequestBuilders
                .andExpect(status().isUnauthorized()); // status() is statically imported from MockMvcResultMatchers
    }

    @Test
    public void givenAuthenticatedAsOfficerWhenAskingMyDetailsDetailsReturned_OK() throws Exception {

        String accessToken = obtainAccessToken(mvc, UsersFactoryTest.OFFICER_EMAIL, UsersFactoryTest
                .OFFICER_PASSWORD);
        when(userDataService.getUser(UsersFactoryTest.officer().getId())).thenReturn(Optional.of(UsersFactoryTest.officer()));

        mvc.perform(get("/api/users/me")
                .header(HEADER_AUTHORIZATION, bearer(accessToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(UsersFactoryTest.OFFICER_EMAIL))
                .andExpect(jsonPath("roles").isArray())
                .andExpect(jsonPath("roles[0]").value("OFFICER"))
        ;
    }

    @Test
    public void testCreateOfficer() throws Exception {
        String email = "wim.deblauwe@example.com";
        String password = "my-super-secret-pwd";

        CreateOfficerParameters parameters = new CreateOfficerParameters();
        parameters.setEmail(email);
        parameters.setPassword(password);

        when(userDataService.createOfficer(email, password)).thenReturn(UsersFactoryTest.newOfficer(email, password));

        mvc.perform(post("/api/users/createOfficer")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(parameters))) // Use the Jackson objectMapper to turn the CreateOfficerParameters object into JSON and use it as the body of the request.
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("email").value(email))
                .andExpect(jsonPath("roles").isArray())
                .andExpect(jsonPath("roles[0]").value("OFFICER"));

        verify(userDataService).createOfficer(email, password);
    }
}