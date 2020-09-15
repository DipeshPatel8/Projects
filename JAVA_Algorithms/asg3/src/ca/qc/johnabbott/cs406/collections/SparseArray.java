/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections;

import ca.qc.johnabbott.cs406.terrain.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of a 2D "sparse" array: an array where many locations either don't have data
 * items and/or are unused.
 *
 * When locations are accessed for the first time, a new element is created that is a copy of the
 * default element setup with the array is created.
 *
 * The element datatype must implement AsChar interface so that it can be viewed as a grid in toString().
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class SparseArray<T extends Copyable<T> & AsChar> {

    private final T defaultValue;

    // fields
    private Map<Location, T> elements;

    /**
     * Create a sparse array
     * @param defaultElement The starting value for each cell.
     */
    public SparseArray(T defaultElement) {
        this.defaultValue = defaultElement;
        elements = new HashMap<>();
    }

    // checks to see if a node isn't already at `position`, if not,
    // create one.
    private void makeNodeIfNecessary(Location location) {
        if(!elements.containsKey(location))
            elements.put(location, defaultValue.copy());
    }

    /**
     * Retrieve the element at a specific location.
     * If this is the first time the cell is accessed, a copy of the default elements is created at location.
     * @param location
     * @return the element at the position.
     */
    public T get(Location location) {
        makeNodeIfNecessary(location);
        return elements.get(location);
    }


    /**
     * Set the element at a specific location.
     * If this is the first time the cell is accessed, a copy of the default elements is created at location.
     * @param location
     * @param element
     */
    public void set(Location location, T element) {
        makeNodeIfNecessary(location);
        elements.put(location, element);
    }


    @Override
    public String toString() {

        int minPosX = Integer.MAX_VALUE;
        int maxPosX = Integer.MIN_VALUE;
        int minPosY = Integer.MAX_VALUE;
        int maxPosY = Integer.MIN_VALUE;

        for(Location location : elements.keySet()) {
            minPosX = Math.min(minPosX, location.getX());
            maxPosX = Math.max(maxPosX, location.getX());
            minPosY = Math.min(minPosY, location.getY());
            maxPosY = Math.max(maxPosY, location.getY());
        }

        int width = maxPosX - minPosX + 1;
        StringBuilder builder = new StringBuilder();
        for(int i = minPosY; i <= maxPosY; i++) {
            for(int j = minPosX; j <= maxPosX; j++) {

                Location pos = new Location(j, i);
                if(elements.containsKey(pos))
                    builder.append(elements.get(pos).toChar());
                else
                    builder.append(' ');

            }
           builder.append('\n');
        }
        return builder.toString();
    }
}
