package com.example.copsboot.infrastructure.security;

import com.example.copsboot.UsersFactoryTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class StubUserDetailsService implements com.example.copsboot.business.objects.entities.UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        switch (username) {
            case UsersFactoryTest.OFFICER_EMAIL:
                return new UserDetails(UsersFactoryTest.officer());
            case UsersFactoryTest.CAPTAIN_EMAIL:
                return new UserDetails(UsersFactoryTest.captain());
            default:
                throw new UsernameNotFoundException(username);
        }
    }
}
