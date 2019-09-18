package com.example.copsboot;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.enums.UserRole;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import com.example.orm.jpa.InMemoryUniqueIdGenerator;
import com.example.orm.jpa.UniqueIdGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

// You need to annotate the JUnit test class with @RunWith(SpringRunner.class) to enable the testing support of Spring Boot.
@RunWith(SpringRunner.class)
// @DataJpaTest instructs the testing support to start only the part of the application responsible for everything related to JPA.
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UniqueIdGenerator<UUID> generator() {
            return new InMemoryUniqueIdGenerator();
        }
    }

    @Test
    public void testStoreUser() {
        User user = repository.save(new User(repository.nextId(), "alex.foley@beverly-hills.com",
                "my-secret-pwd",
                Collections.singletonList(UserRole.OFFICER)));

        Assert.assertNotNull(user);
        Assert.assertEquals(1L, repository.count());
    }

    @Test
    public void testFindByEmail() {
        User user = UsersFactoryTest.newRandomOfficer();

        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase(user.getEmail());

        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testFindByEmailIgnoringCase() {
        User user = UsersFactoryTest.newRandomOfficer();
        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase(user.getEmail()
                .toUpperCase
                        (Locale.US));

        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testFindByEmail_unknownEmail() {
        User user = UsersFactoryTest.newRandomOfficer();
        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase("will.not@find.me");

        Assert.assertFalse(result.isPresent());
    }
}