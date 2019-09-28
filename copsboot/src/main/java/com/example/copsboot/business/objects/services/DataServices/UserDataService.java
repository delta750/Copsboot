package com.example.copsboot.business.objects.services.DataServices;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.entities.UserId;

import java.util.Optional;

public interface UserDataService {
    User createOfficer(String email, String password);

    Optional<User> getUser(UserId userId);
}
