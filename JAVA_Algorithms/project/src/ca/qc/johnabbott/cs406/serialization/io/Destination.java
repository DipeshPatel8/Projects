/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.serialization.io;

import java.io.IOException;

/**
 * Destination interface.
 *
 * - Operations needed to be used as a serialization output destination.
 */
public interface Destination {

    /**
     * Write a byte to the output destination.
     * @param b the byte to write.
     * @throws IOException
     */
    void write(byte b) throws IOException;

    /**
     * Write bytes to the output destination.
     * @param bytes the bytes to write.
     * @throws IOException
     */
    void write(byte[] bytes) throws IOException;

    /**
     * Get the byte index from the first byte written to the output.
     * @return the byte index.
     */
    int getPosition();

    void close() throws IOException;
}
