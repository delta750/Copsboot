package com.example.orm.jpa;

import java.io.Serializable;

/**
 * Interface for primary keys of entities.
 *
 * @param <T> the underlying type of the entity id
 */

public interface EntityId <T> extends Serializable {

    T getId();

    // The asString method returns the ID as a string representation, for use in an URL for example. You
    // are not using toString because that is usually for debugging purposes while you will need to use
    // this as part of your application logic
    String asString();
}
