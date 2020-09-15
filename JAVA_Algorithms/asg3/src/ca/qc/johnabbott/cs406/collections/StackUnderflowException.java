/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

/**
 * Exception thrown when a stack underflows.
 * @author Ian Clement
 */
public class StackUnderflowException extends RuntimeException {

    public StackUnderflowException() {
        super();
    }

    public StackUnderflowException(String message) {
        super(message);
    }
}
