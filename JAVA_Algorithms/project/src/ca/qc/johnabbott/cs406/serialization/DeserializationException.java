/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization;

/**
 * Represents a deserialization error.
 */
public class DeserializationException extends Exception {
    public DeserializationException() {
        super();
    }
    public DeserializationException(String message) {
        super(message);
    }
}
