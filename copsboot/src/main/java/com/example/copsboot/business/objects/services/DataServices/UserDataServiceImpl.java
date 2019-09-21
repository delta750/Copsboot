package com.example.copsboot.business.objects.services.DataServices;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserDataServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createOfficer(String email, String password) {
        User user = User.createOfficer(repository.getId(), email, passwordEncoder.
                encode(password));

        return repository.save(user);
    }
}
