package com.example.orm.jpa;

import com.example.util.ArtifactForFramework;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@EqualsAndHashCode
@ToString

/**
 * Abstract super class for entities. We are assuming that early primary key
 * generation will be used.
 *
 * @param <T> the type of {@link EntityId} that will be used for this entity
 */

public abstract class AbstractEntity<T extends EntityId> implements Entity<T> {

    @Getter
    @EmbeddedId
    private T id;

    @ArtifactForFramework
    protected AbstractEntity() {

    }

    public AbstractEntity(T id) {
        this.id = Objects.requireNonNull(id);
    }
}