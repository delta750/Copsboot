package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // @Service tells Spring’s component scanning to create a singleton instance of this class
public class UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s could not be found", username)));

        // Wrap your User object in the UserDetails object and return it for Spring Security to further handle it.
        return new UserDetails(user);
    }
}