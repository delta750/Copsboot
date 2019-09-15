package com.example.orm.jpa;

import java.io.Serializable;
import com.example.util.ArtifactForFramework;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
@EqualsAndHashCode
@Getter
@ToString
public abstract class AbstractEntityId <T extends Serializable> implements Serializable, EntityId <T> {  // type T must implement Serializable

    private T id;

    // The empty constructor is annotated with @ArtifactForFramework.
    // Create this annotation to indicate that the constructor is solely there for a framework that needs it but
    // is not intended to be used by the application itself.
    @ArtifactForFramework
    protected AbstractEntityId(){
    }

    protected AbstractEntityId(T id){
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public String asString() {
        return id.toString();
    }
}