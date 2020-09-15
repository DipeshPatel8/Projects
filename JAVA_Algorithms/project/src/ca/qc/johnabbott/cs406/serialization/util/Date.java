package ca.qc.johnabbott.cs406.serialization.util;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;

public class Date implements Serializable {

    public static final byte SERIAL_ID = 0x08;

    private java.util.Date value;

    public Date(java.util.Date value) {
        this.value = value;
    }

    public Date(){}

    public java.util.Date get() {
        return value;
    }

    public void set(java.util.Date value) {
        this.value = value;
    }

    @Override
    public java.lang.String toString() {
        return "Date{" +
                "value=" + value +
                '}';
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(value.getTime());//convert to long then serialize to simplify
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        value = new java.util.Date(serializer.readLong()); //deserialize long value
    }
}
