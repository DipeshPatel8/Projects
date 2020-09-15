/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

/**
 * Get a copy of the object.
 * - A cleaner implementation of cloneable.
 * @param <T>
 */
public interface Copyable<T extends Copyable<T>> {
    /**
     * Get a copy (clone) of the object.
     * @return The copy.
     */
    T copy();
}
