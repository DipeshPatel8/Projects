/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization;

/**
 * Represents a serialization error.
 */
public class SerializationException extends Exception {
    public SerializationException() {
        super();
    }

    public SerializationException(String message) {
        super(message);
    }
}
