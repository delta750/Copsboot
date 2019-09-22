package com.example.copsboot.business.objects.entities;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends org.springframework.security.core.userdetails.UserDetailsService {

    // Spring Security will pass in a username and we need to provide a UserDetails implementation if there is
    // such a user in our application. If not, we throw a UsernameNotFoundException.
    UserDetails loadUserByUsername(String username);
}
