package com.example.copsboot.business.objects.dao;

import com.example.copsboot.business.objects.entities.UserId;
import com.example.copsboot.business.objects.interfaces.UserRepositoryCustom;
import com.example.orm.jpa.UniqueIdGenerator;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    // This might seem like a bit overkill for the UUID case but you certainly need it if you want to use long values
    // You might want to get them from the database or maybe from some distributed component that can hand out unique IDs
    // across connected JVMs
    private final UniqueIdGenerator generator;

    public UserRepositoryImpl(UniqueIdGenerator <UUID> generator) {
        this.generator = generator;
    }

    @Override
    public UserId getId() {
        return new UserId((UUID) generator.getNextUniqueId());
    }
}
