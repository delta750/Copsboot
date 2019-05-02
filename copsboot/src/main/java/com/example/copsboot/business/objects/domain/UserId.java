package com.example.copsboot.business.objects.domain;

import com.example.orm.jpa.AbstractEntityId;
import com.sun.tracing.dtrace.ArgsAttributes;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor // Hibernate needs the protected no-args constructor to work.
public class UserId extends AbstractEntityId <UUID> {

    public UserId(UUID id){ // This is the constructor that the application code should use.
        super(id);
    }

    @Override
    public String asString() {
        return null;
    }
}
