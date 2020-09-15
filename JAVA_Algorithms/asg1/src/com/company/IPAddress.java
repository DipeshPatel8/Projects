/*
 * Copyright (c) 2020 Ian Clement.  All rights reserved.
 */

package com.company;

/**
 * A simple IP address class
 * - uses a integer to store address
 * @author Ian Clement (ian.clement@johnbbott.qc.ca)
 */
public class IPAddress implements Comparable<IPAddress> {

    // without unsigned ints we need to use a long to store the 32bit
    private long address;

    /**
     * Creates and IPAddress from a String in the dot-decimal notation
     * - throws exception if dot-decimals are invalid
     * @param addressStr IP address in dot-decimal notation
     */
    public IPAddress(String addressStr) {

        String[] octets = addressStr.split("\\.");

        // IP address must be 4 dotted octets
        if(octets.length != 4)
            throw new RuntimeException("Bad IP address: " + addressStr);

        // we will build an integer `address` by concatenating the bitstrings
        address = 0L;
        for(int i = 0; i < 4; i++) {

            // parse and validate this octet
            long current = Integer.parseInt(octets[i]);
            if(current < 0 || current >= 255)
                throw new RuntimeException("Bad IP address: " + addressStr);

            // add the current value to the bottom 8 bits of `address`
            address |= current;
            if(i < 3)  // shift address 8 bits to the left if there are more octets to process
                address <<= 8;
        }
    }

    @Override
    public String toString() {

        // create a String more efficiently using a "builder"
        StringBuilder sb = new StringBuilder();

        // extract the individual integers from the integer `address` by
        for(int i=3; i>=0; i--) {

            // copy the address shifted down to the current decimal
            long current = address >> (i * 8);
            current &= 255L;  // mask only the lowest byte

            // addpend the current decimal and a dot (if necessary)
            sb.append(String.valueOf(current));
            if(i != 0)
                sb.append('.');
        }

        return sb.toString();
    }

    @Override
    public int compareTo(IPAddress rhs) {
        long diff = this.address - rhs.address;

        // we can't return `diff`, because of the range of `int` datatype.
        if (diff > 0)
            return 1;
        else if(diff < 0)
            return -1;
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPAddress ipAddress = (IPAddress) o;

        return address == ipAddress.address;

    }



}
