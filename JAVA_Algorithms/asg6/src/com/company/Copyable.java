/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package com.company;

/**
 * Get a copy of the object.
 * - A simpler implementation of Java's Cloneable interface.
 * @param <T>
 */
public interface Copyable<T extends Copyable<T>> {
    /**
     * Get a copy (clone) of the object.
     * @return The copy.
     */
    T copy();
}
