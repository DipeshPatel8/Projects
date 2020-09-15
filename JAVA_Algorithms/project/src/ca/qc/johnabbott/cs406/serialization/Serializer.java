/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization;

import ca.qc.johnabbott.cs406.serialization.io.Destination;
import ca.qc.johnabbott.cs406.serialization.io.Source;
import ca.qc.johnabbott.cs406.serialization.util.Double;
import ca.qc.johnabbott.cs406.serialization.util.Float;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.Long;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Serializer class.
 *
 * - Serialize and deserialize primitives.
 * - Serialize and deserialize Serializable objects.
 * - Manage the interaction with the input source and output destination.
 * - Includes reference optimization (TODO).
 */
public class Serializer {

    private static final byte ALIAS_MARKER = (byte) 0xff;
    private static final byte ORIGINAL_MARKER = 0x00;
    private static final byte SERIAL_NULL_ID = 0x00;
    private Map<Byte, Creator<Serializable>> creators;

    // optimization, use a static ByteBuffer to avoid extra allocations on each (de)serialize operation.
    private static final ByteBuffer buffer;

    static {
        buffer = ByteBuffer.wrap(new byte[java.lang.Double.BYTES]);
    }

    private Source source;
    private Destination destination;

    private boolean optimizeReferences;
    private Map<Object, java.lang.Integer> refs;
    private Map<java.lang.Integer, Object> refsInv;

    /**
     * Create a Serializer.
     * @param source The source to deserialize from.
     * @param destination The destination to serialize to.
     */
    public Serializer(Source source, Destination destination) {
        this(source, destination, false);
    }

    /**
     * Create a Serializer.
     * @param source The source to deserialize from.
     * @param destination The destination to serialize to.
     * @param optimizeReferences Perform reference optimzation.
     */
    public Serializer(Source source, Destination destination, boolean optimizeReferences) {
        creators = new HashMap<>();
        this.source = source;
        this.destination = destination;
        this.optimizeReferences = optimizeReferences;
        if(optimizeReferences) {
            refs = new IdentityHashMap<>();
            refsInv = new HashMap<>();
         }
    }

    /**
     * Register a serializable.
     * @param serialId The id of the serializable.
     * @param creator The default constructor for the serializable.
     * @throws SerializationException
     */
    public void register(byte serialId, Creator<Serializable> creator) throws SerializationException {
        if(creators.containsKey(serialId))
            throw new SerializationException("Duplicate SERIAL_ID: " + serialId);
        creators.put(serialId, creator);
    }

    /**
     * Write a NULL record to the output destination.
     * @throws IOException
     */
    public void writeNull() throws IOException {
        destination.write(SERIAL_NULL_ID);
    }

    /**
     * Write the serializable object to the destination. Includes a serialization header.
     * @param value The object to serialize.
     * @throws IOException
     */
    public void write(Serializable value) throws IOException {
        destination.write(value.getSerialId());
        value.serialize(this);
    }

    /**
     * Write a byte array to the destination.
     * @param bytes The bytes to write.
     * @throws IOException
     */
    public void write(byte[] bytes) throws IOException {
        destination.write(bytes);
    }

    /**
     * Write a byte to the destination.
     * @param b The byte to write.
     * @throws IOException
     */
    public void write(byte b) throws IOException {
        destination.write(b);
    }

    /**
     * Write an integer to the destination.
     * @param value The integer to write.
     * @throws IOException
     */
    public void write(int value) throws IOException {
        Integer.write(value, this);
    }

    /**
     * Write a long to the destination.
     * @param value The long to write.
     * @throws IOException
     */
    public void write(long value) throws IOException {
        Long.write(value, this);
    }

    /**
     * Write a double to the destination.
     * @param value The double to write.
     * @throws IOException
     */
    public void write(double value) throws IOException {
        Double.write(value, this);
    }

    /**
     * Write a float to the destination.
     * @param value The float to write.
     * @throws IOException
     */
    public void write(float value) throws IOException {
        Float.write(value, this);
    }

    /**
     * Read a serializable object from the source.
     * @return The deserialized object.
     * @throws IOException
     * @throws SerializationException
     */
    public Serializable readSerializable() throws IOException, SerializationException {
        byte serialId = source.read();
        Serializable s = getSerializableById(serialId);
        if(s != null)
            s.deserialize(this);
        return s;
    }

    // TODO hide?
    /**
     * Read a length `n` from the source (as an int),
     * and use this length as the number of bytes to read and return
     * @return the bytes read.
     * @throws IOException
     */
    public byte[] readNext() throws IOException {
        byte[] bytes = new byte[java.lang.Integer.BYTES];
        source.read(bytes, java.lang.Integer.BYTES);
        int n = ByteBuffer.wrap(bytes).getInt();
        bytes = new byte[n];
        source.read(bytes, n);
        return bytes;
    }

    /**
     * Read a byte from the source.
     * @return The byte read.
     * @throws IOException
     */
    public byte read() throws IOException {
        return source.read();
    }

    /**
     * Read a byte array from the source.
     * @param bytes The byte array to store the read bytes.
     * @return The number of bytes read.
     * @throws IOException
     */
    public int read(byte[] bytes) throws IOException {
        return read(bytes, bytes.length);
    }

    // TODO
    /**
     * Read a byte array from the source with a specified length.
     * @param bytes The byte array to store the read bytes.
     * @param length The maximum number of bytes to read.
     * @return The number of bytes read.
     * @throws IOException
     */
    private int read(byte[] bytes, int length) throws IOException {
        return source.read(bytes, length);
    }

    /**
     * Read a null from the source.
     * @throws IOException
     * @throws SerializationException
     */
    public void readNull() throws IOException, SerializationException {
        byte serialId = source.read();
        if(serialId != SERIAL_NULL_ID)
            throw new SerializationException("Trying to read a null, but found a non-null representation in the source.");
    }
    /**
     * Read an integer from the source.
     * @return The integer value read.
     * @throws IOException
     * @throws SerializationException
     */
    public int readInt() throws IOException {
        return Integer.read(this);
    }

    /**
     * Read a long from the source.
     * @return The long value read.
     * @throws IOException
     * @throws SerializationException
     */
    public long readLong() throws IOException {
        return Long.read(this);
    }

    /**
     * Read a double from the source.
     * @return The double value read.
     * @throws IOException
     */
    public double readDouble() throws IOException {
        return Double.read(this);
    }

    /**
     * Read a float from the source.
     * @return The float value read.
     * @throws IOException
     */
    public float readFloat() throws IOException {
        return Float.read(this);
    }

    /**
     * Create an instance of the object given by the provided serial ID.
     * @param id the serial ID.
     * @return the object.
     * @throws SerializationException
     */
    private Serializable getSerializableById(byte id) throws SerializationException {
        if(id == SERIAL_NULL_ID)
            return null;

        if(creators.containsKey(id))
            return creators.get(id).create();
        else
            throw new SerializationException("Unknown serial ID: " + id + ".");
    }

    /**
     * Get the source.
     * @return
     */
    public Source getSource() {
        return source;
    }

    /**
     * Get the destination.
     * @return
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * Close the serializer. Closes the source and destination.
     * @throws IOException
     */
    public void close() throws IOException {
        if(source != null)
            source.close();
        if(destination != null)
            destination.close();
    }

}
