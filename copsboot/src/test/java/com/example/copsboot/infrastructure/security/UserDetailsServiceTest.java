package com.example.copsboot.infrastructure.security;

import com.example.copsboot.UsersFactoryTest;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsServiceTest {

    @Test
    public void existingUsernameUserIsReturned_OK() {

        UserRepository repository = mock(UserRepository.class);

        UserDetailsService service = new UserDetailsService(repository);

        // If a user with email address UsersFactoryTest.OFFICER_EMAIL is requested to the repository, return the appropriate User object
        when(repository.findByEmailIgnoreCase(UsersFactoryTest.OFFICER_EMAIL)).thenReturn(Optional.of(UsersFactoryTest.officer()));

        UserDetails userDetails = service.loadUserByUsername(UsersFactoryTest.OFFICER_EMAIL);
        Assert.assertNotNull(userDetails);

        Assert.assertEquals(UsersFactoryTest.OFFICER_EMAIL, userDetails.getUsername());

        Assert.assertEquals("[ROLE_OFFICER]", userDetails.getAuthorities().toString());


//        assertThat(userDetails).isInstanceOfSatisfying(UserDetails.class,
//                applicationUserDetails -> {
//                    assertThat
//                            (applicationUserDetails.getUserId())
//                            .isEqualTo(UsersFactoryTest
//                                    .officer().getId());
//                });
    }

        @Test(expected = UsernameNotFoundException.class)
        public void notExistingUsernameExceptionThrown_OK() {

            UserRepository repository = mock(UserRepository.class);

            UserDetailsService service = new UserDetailsService(repository);

            when(repository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.empty());
            service.loadUserByUsername("i@donotexist.com");
        }
    }