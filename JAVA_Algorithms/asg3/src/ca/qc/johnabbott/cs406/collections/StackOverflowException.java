/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

/**
 * Exception thrown when a stack overflows.
 * @author Ian Clement
 */
public class StackOverflowException extends RuntimeException {

    public StackOverflowException() {
        super();
    }

    public StackOverflowException(String message) {
        super(message);
    }
}
