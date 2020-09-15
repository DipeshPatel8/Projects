/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization;

/**
 * Interface used to structure instance creation used in deserialization.
 * @param <T>
 */
public interface Creator<T> {

    /**
     * Create a new instance of the object.
     * @return
     */
    T create();
}
