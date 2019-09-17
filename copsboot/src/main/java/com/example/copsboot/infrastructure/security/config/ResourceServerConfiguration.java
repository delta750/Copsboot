package com.example.copsboot.infrastructure.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/*
*
* The resource server is responsible for defining what parts of the application are accessible by the
* different types of authenticated and unauthenticated users.
*
*
*/

@Configuration
@EnableResourceServer //  The @EnableResourceServer annotation ensures that you have a resource server.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_ID = "copsboot";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll() // You define via the fluent API that any OPTIONS request to any sub-path of /api is allowed by everybody. This allows a client to issue a so-called "preflight request". This is something that the Angular framework, for example, does by default
                .and()
                .antMatcher("/api/**").authorizeRequests()
                .anyRequest()
                .authenticated(); // This defines that any request to /api should be authenticated (unless it is an OPTIONS request)
    }


}
