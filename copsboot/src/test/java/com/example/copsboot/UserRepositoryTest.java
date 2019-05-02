package com.example.copsboot;

import com.example.copsboot.business.objects.domain.User;
import com.example.copsboot.business.objects.domain.Users;
import com.example.copsboot.business.objects.enums.UserRole;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

@RunWith(SpringRunner.class)
// You need to annotate the JUnit test class with @RunWith(SpringRunner.class) to enable the testing support of Spring Boot.
@DataJpaTest
// @DataJpaTest instructs the testing support to start only the part of the application responsible for everything related to JPA.
public class UserRepositoryTest {

    // Inject the UserRepository so you can use it in the unit test
    @Autowired
    private UserRepository repository;

    //	@TestConfiguration
//	static class TestConfig {
//		@Bean
//		public UserRepository <UUID> generator(){
//			return (UserRepository<UUID>) new InMemoryUniqueIdGenerator();
//		}
//	}
    @Test
    public void testStoreUser() {
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.OFFICER);
        User user = repository.save(new User(repository.nextId(), "alex.foley@beverly-hills.com",
                "my-secret-pwd",
                roles));

        Assert.assertNotNull(user);
        Assert.assertEquals(1L, repository.count());
    }

    @Test
    public void testFindByEmail() {
        User user = Users.newRandomOfficer();

        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase(user.getEmail());

        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void testFindByEmailIgnoringCase() {
        User user = Users.newRandomOfficer();
        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase(user.getEmail()
                .toUpperCase
                        (Locale.US));

        Assert.assertTrue(result.isPresent());

    }

    @Test
    public void testFindByEmail_unknownEmail() {
        User user = Users.newRandomOfficer();
        repository.save(user);
        Optional<User> result = repository.findByEmailIgnoreCase("will.not@find.me");

        Assert.assertFalse(result.isPresent());
    }

}
