package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * DESCRIPTION HERE
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 * @since 2018-04-29
 */
public class Grade implements Serializable {

    public static final byte SERIAL_ID = 0x15;
    private String name;
    private int result;
    private Date date;

    public Grade(String name, int result, Date date) {
        this.name = name;
        this.result = result;
        this.date = date;
    }

    public Grade() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                ", result=" + result +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return result == grade.result &&
                Objects.equals(name, grade.name) &&
                Objects.equals(date, grade.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, result, date);
    }

    @Override
    public byte getSerialId() {
        return SERIAL_ID;
    }

    @Override
    public void serialize(Serializer serializer) throws IOException {
        serializer.write(result);
        serializer.write(date.getTime());//serialize long value of date
        serializer.write(name.length());
        for(char c:name.toCharArray())//split the string to serialize each char
            serializer.write((byte)c);
    }

    @Override
    public void deserialize(Serializer serializer) throws IOException, SerializationException {
        result = serializer.readInt();
        date = new Date(serializer.readLong());//get date and refactor into valid formmat
        int length = serializer.readInt();//get string length
        char[]tmp = new char[length];
        for (int i = 0; i < length; i++) {
            tmp[i] = (char)serializer.read(); //get all char
        }
        name = new String(tmp);//rebuild this string

    }
}
