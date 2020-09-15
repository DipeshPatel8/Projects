/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import jdk.management.resource.internal.inst.FileOutputStreamRMHooks;

import java.io.IOException;

/**
 * A simple IP address class
 * - uses a byte[] to store octets
 * @author Ian Clement
 */
public class IPAddress implements Comparable<IPAddress>, Serializable {

    public static final byte SERIAL_ID = 0x10;

    private byte[] octet;

    /**
     * Create a blank IP address.
     */
    public IPAddress() {
        octet = new byte[4];
    }

    /**
     * Create IP from octet bytes
     * @param o1 octet 1
     * @param o2 octet 2
     * @param o3 octet 3
     * @param o4 octet 4
     */
    public IPAddress(byte o1, byte o2, byte o3, byte o4) {
        octet = new byte[4];
        octet[0] = o1;
        octet[1] = o2;
        octet[2] = o3;
        octet[3] = o4;
    }

    /**
     * Create IP from dot-decimal string
     * @param addressStr dot-decimal string
     */
    public IPAddress(String addressStr) {

        String[] octetStr = addressStr.split("\\.");

        // IP address must be 4 dotted octets
        if(octetStr.length != 4)
            throw new RuntimeException("Bad IP address: " + addressStr);

        octet = new byte[4];
        octet[0] = (byte) Integer.parseInt(octetStr[0]);
        octet[1] = (byte) Integer.parseInt(octetStr[1]);
        octet[2] = (byte) Integer.parseInt(octetStr[2]);
        octet[3] = (byte) Integer.parseInt(octetStr[3]);
    }

    /**
     * Determine if the IP address is in a network.
     * @param network The address "prefix" of the network.
     * @param prefixLength The length corresponding to the prefix in the "network" address.
     * @return true is the address is in the network.
     */
    public boolean isInNetwork(IPAddress network, int prefixLength) {
        if(prefixLength < 1 || prefixLength > 32)
            throw new IllegalArgumentException("Invalid prefix, must be in range [1,32].");
        if(prefixLength % 8 != 0)
            throw new IllegalArgumentException("Currently only multiples of 8 supported.");

        int octetsToCheck = prefixLength / 8;
        for(int i=0; i < octetsToCheck; i++) {
            if (this.octet[i] != network.octet[i])
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", octet[0] & 0xFF,  octet[1] & 0xFF, octet[2] & 0xFF, octet[3] & 0xFF);
    }

    /**
     * Determine if two IP addresses are the same.
     * @param rhs
     * @return
     */
    // Note: To use the actual Java .equals(..) @Override, the parameter would have to be of type Object
    public boolean equals(IPAddress rhs) {
        for(int i = 0; i < 4; i++) {
            if (octet[i] != rhs.octet[i])
                return false;
        }
        return true;
    }

    @Override
    public int compareTo(IPAddress rhs) {
        for(int i=0; i<4; i++) {
            int r = (octet[i] & 0xFF) - (rhs.octet[i] & 0xFF);
            if(r != 0)
                return r;
        }
        return 0;
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(octet);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        serializer.read(octet);
    }
}
