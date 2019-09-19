package com.example.copsboot;

import com.example.copsboot.business.objects.services.DataServices.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// Mark the class with @Component so the component scanning picks it up
@Component
@Profile("dev")
public class DevelopmentDbInitializer implements ApplicationRunner {

    private final UserDataService userService;
    @Autowired
    public DevelopmentDbInitializer(UserDataService userService) {
        this.userService = userService;
    }
    @Override
    public void run(ApplicationArguments args){
        createTestUsers();
    }

    private void createTestUsers() {
        userService.createOfficer( "officer@example.com", "password");
    }
}
