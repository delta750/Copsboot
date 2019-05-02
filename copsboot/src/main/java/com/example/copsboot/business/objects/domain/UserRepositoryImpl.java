package com.example.copsboot.business.objects.domain;

import com.example.copsboot.business.objects.interfaces.UserRepositoryCustom;
import com.example.orm.jpa.UniqueIdGenerator;
import java.util.UUID;


public class UserRepositoryImpl implements UserRepositoryCustom {

    // This might seem like a bit overkill for the UUID case but you certainly need it if you want to use long values
    // You might want to get them from the database or maybe from some distributed component that can hand out unique IDs
    // across connected JVMs
    private final UniqueIdGenerator generator;

    public UserRepositoryImpl(UniqueIdGenerator <UUID> generator) {
        this.generator = generator;
    }

    @Override
    public UserId nextId() {
        return new UserId((UUID) generator.getNextUniqueId());
    }
}
