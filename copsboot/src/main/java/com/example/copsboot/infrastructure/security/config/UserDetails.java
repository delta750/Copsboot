package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.entities.UserId;
import com.example.copsboot.business.objects.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetails extends org.springframework.security.core.userdetails.User {

    private static final String ROLE_PREFIX = "ROLE_";
    @Getter
    private final UserId userId;


    public UserDetails(User user) {
        super(user.getEmail(), user.getPassword(), createAuthorities(user.getRoles()));
        this.userId = user.getId();
    }

    private static Collection<SimpleGrantedAuthority> createAuthorities(List<UserRole> roles) {
        return roles.stream().map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole.name()))
                .collect(Collectors.toSet());
    }
}
