package com.example.copsboot.business.objects.interfaces;

import com.example.copsboot.business.objects.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// By creating this interface, you have a repository at run time that allows you to save, edit, delete, and
// find User entities
@Repository
public interface UserRepository extends CrudRepository <User, UUID>, UserRepositoryCustom{
    Optional<User> findByEmailIgnoreCase(String email);
}
