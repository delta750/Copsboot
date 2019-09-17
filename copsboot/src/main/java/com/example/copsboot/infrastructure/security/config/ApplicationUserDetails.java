package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.entities.UserId;
import com.example.copsboot.business.objects.enums.UserRole;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ApplicationUserDetails extends org.springframework.security.core.userdetails.User {

    private static final String ROLE_PREFIX = "ROLE_";
    private final UserId userId;


    public ApplicationUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), createAuthorities(user.getRoles()));
        this.userId = user.getId();
    }


    public UserId getUserId() {
        return userId;
    }

    private static Collection<SimpleGrantedAuthority> createAuthorities(List<UserRole> roles) {

        return roles.stream().map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.name()))
                .collect(Collectors.toSet());
    }
}
