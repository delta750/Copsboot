package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.domain.User;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // @Service tells Spring’s component scanning to create a singleton instance of this class
public class ApplicationUserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApplicationUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s could not be found", username)));
        return new ApplicationUserDetails(user); // Wrap your User object in the ApplicationUserDetails object and return it for Spring Security to further handle it.

    }
}