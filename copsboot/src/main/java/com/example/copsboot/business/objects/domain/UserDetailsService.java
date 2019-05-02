package com.example.copsboot.business.objects.domain;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {

    // Spring Security will pass in a username and we need to provide a UserDetails implementation if there is
    // such a user in our application. If not, we throw a UsernameNotFoundException.
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
