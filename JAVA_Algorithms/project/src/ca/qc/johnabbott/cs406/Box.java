/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

/**
 * Box class
 *
 * - Stores a value in a box. Example of serialization for generic classes.
 *
 * Format:
 *
 * 1.  Serial representation of the value.
 *
 */
public class Box<T extends Serializable> implements Serializable {

    public static final byte SERIAL_ID = 0x07;

    private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return "Box{" + value.toString() + "}";
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(value);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        value = (T) serializer.readSerializable();
    }

}
