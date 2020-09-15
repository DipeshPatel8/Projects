/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization.util;

import ca.qc.johnabbott.cs406.serialization.*;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * A serializable wrapper class for Strings.
 */
public class String implements Serializable, Comparable<String> {

    public static final byte SERIAL_ID = 0x06;

    private java.lang.String value;

    public String() {
        value = null;
    }

    public String(java.lang.String s) {
        value = s;
    }

    public java.lang.String get() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        String rhs = (String) o;

        return value != null ? value.equals(rhs.value) : rhs.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        // allocate enough room for the string format
        byte[] bytes = new byte[java.lang.Integer.BYTES + value.length()];

        // write the length and the characters to the serializer
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.putInt(value.length());
        for(char c : value.toCharArray())
            buffer.put((byte)c);

        // write the bytes to the serializer.
        serializer.write(bytes);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException {
        // read the char array from the serializer.
        byte[] bytes = serializer.readNext();
        // create a string from the bytes.
        value = new java.lang.String(bytes);

    }

    @Override
    public java.lang.String toString() {
        return value;
    }

    @Override
    public int compareTo(String rhs) {
        return this.value.compareTo(rhs.value);
    }
}
