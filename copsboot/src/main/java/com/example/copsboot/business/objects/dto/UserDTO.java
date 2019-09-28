package com.example.copsboot.business.objects.dto;

import com.example.copsboot.business.objects.entities.User;
import com.example.copsboot.business.objects.entities.UserId;
import com.example.copsboot.business.objects.enums.UserRole;
import lombok.Value;

import java.util.List;

/*
 * UserDTO object for serialization to JSON
 */

// This annotation adds
//getters for each field and a single constructor for taking each field. It will also generate a proper
//equals() and hashCode implementation.
@Value
public class UserDTO {

    private final UserId id;
    private final String email;
    private final List<UserRole> roles;

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getId(),
                user.getEmail(),
                user.getRoles());
    }
}
