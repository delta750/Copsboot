package com.example.copsboot.business.objects.interfaces;

import com.example.copsboot.business.objects.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

// By creating this interface, you have a repository at run time that allows you to save, edit, delete, and
// find User entities
public interface UserRepository extends CrudRepository <User, UUID>, UserRepositoryCustom{
    Optional<User> findByEmailIgnoreCase(String email);
}
