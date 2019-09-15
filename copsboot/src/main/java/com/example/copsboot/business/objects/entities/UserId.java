package com.example.copsboot.business.objects.entities;

import com.example.orm.jpa.AbstractEntityId;
import lombok.NoArgsConstructor;

import java.util.UUID;

// Hibernate needs the protected no-args constructor to work.
@NoArgsConstructor
public class UserId extends AbstractEntityId <UUID> {

    public UserId(UUID id){ // This is the constructor that the application code should use.
        super(id);
    }

    @Override
    public String asString() {
        return null;
    }
}
