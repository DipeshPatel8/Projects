/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

/**
 * Exception thrown when a queue overflows.
 *
 * @author Ian Clement
 */
public class QueueOverflowException extends RuntimeException {

    public QueueOverflowException() {
        super();
    }

    public QueueOverflowException(String message) {
        super(message);
    }
}
