package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.entities.UserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


/*
 * The authorization server is responsible for the authorization of the client application and the users
 *
 */

@Configuration
// The @EnableAuthorizationServer annotation ensures that you have an authorization server started
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    /*
     * UserDetailsService is the contact point between the application and Spring Security. You will have
     * to create a class that implements this interface, which will use the previously created
     * UserRepository to know what users you have in your application.
     */
    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    // The TokenStore is what Spring will use to store the generated access tokens
    private final TokenStore tokenStore;

    private final SecurityConfiguration securityConfiguration;

    public AuthorizationServerConfiguration(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder, TokenStore tokenStore, SecurityConfiguration securityConfiguration) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.securityConfiguration = securityConfiguration;
    }

    /* You obviously should not store user passwords in plain text. This example uses an implementation
     * of PasswordEncoder to encrypt passwords when storing them in the database. PasswordEncoder hashes
     * the password so that there is no way to recover the original password, even when the complete
     * database leaks out.
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws
            Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
         * only have one mobile app for now, you don’t need anything more elaborate. You may define this in
         * the database if you want. You would then use clients.jdbc(dataSource).
         */
        clients.inMemory()
                .withClient(securityConfiguration.getMobileAppClientId()) // hard-coded the client ID for authentication of the client application to copsboot-mobile-client
                .authorizedGrantTypes("password", "refresh_token") // This has to be "password" to allow password flow using the defined client application. Also add "refresh_token" so that the client application can use the refresh token to get a new access token
                .scopes("mobile_app") // The scopes allow you to define what "part" of the application is allowed by the received token. Let’s not do anything based on that value for now, so its exact value does not really matter
                .resourceIds(OAuth2ServerConfiguration.RESOURCE_ID)
                .secret(passwordEncoder.encode(securityConfiguration.getMobileAppClientSecret())); // This defines the client secret to authenticate the client application.
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints){
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }
}