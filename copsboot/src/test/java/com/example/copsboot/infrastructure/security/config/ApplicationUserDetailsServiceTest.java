package com.example.copsboot.infrastructure.security.config;

import com.example.copsboot.business.objects.entities.Users;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApplicationUserDetailsServiceTest {

    @Test
    public void givenExistingUsername_whenLoadingUser_userIsReturned() {

        UserRepository repository = mock(UserRepository.class);

        ApplicationUserDetailsService service = new ApplicationUserDetailsService(repository);

        when(repository.findByEmailIgnoreCase(Users.OFFICER_EMAIL)).thenReturn(Optional.of(Users.officer())); // If a user with email address Users.OFFICER_EMAIL is requested to the repository, return the appropriate User object

        UserDetails userDetails = service.loadUserByUsername(Users.OFFICER_EMAIL);
        Assert.assertNotNull(userDetails);
        Assert.assertEquals(Users.OFFICER_EMAIL, userDetails.getUsername());
//        Assert.assertEquals("ROLE_OFFICER", userDetails.getAuthorities());

//        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority).contains("ROLE_OFFICER");
//////        assertThat(userDetails).isInstanceOfSatisfying(ApplicationUserDetails.class,
//////                applicationUserDetails -> {
//////                    assertThat
//////                            (applicationUserDetails.getUserId())
//////                            .isEqualTo(Users
//////                                    .officer().getId());
//////                });
    }

        @Test(expected = UsernameNotFoundException.class)
        public void givenNotExistingUsername_whenLoadingUser_exceptionThrown() {

            UserRepository repository = mock(UserRepository.class);

            ApplicationUserDetailsService service = new ApplicationUserDetailsService(repository);

            when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
            service.loadUserByUsername("i@donotexist.com");
        }


    }