/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

/**
 * Thrown when an non-existent algorithm is specified.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class UnknownAlgorithm extends Exception {
    public UnknownAlgorithm() {
    }
    public UnknownAlgorithm(String message) {
        super(message);
    }
}
