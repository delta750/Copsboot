package com.example.copsboot.infrastructure.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component // creates an instance to autowire later on.
@ConfigurationProperties(prefix = "copsboot-security")
public class SecurityConfiguration {

    private String mobileAppClientId;
    private String mobileAppClientSecret;
}
