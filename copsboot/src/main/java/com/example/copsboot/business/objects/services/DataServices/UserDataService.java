package com.example.copsboot.business.objects.services.DataServices;

import com.example.copsboot.business.objects.entities.User;

public interface UserDataService {
    User createOfficer(String email, String password);
}
