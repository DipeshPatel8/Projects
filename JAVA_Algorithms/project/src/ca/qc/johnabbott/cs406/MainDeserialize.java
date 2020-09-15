package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;
import org.omg.CORBA.DATA_CONVERSION;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Deserialize example.
 */
public class MainDeserialize {
    public static void main(java.lang.String arg[]) throws IOException, SerializationException {


        Serializer serializer = new Serializer(
                new BufferedChannel(
                        new RandomAccessFile("foo.bin", "rw").getChannel()
                        , BufferedChannel.Mode.READ
                ), null);

        //register each serial id
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Box.SERIAL_ID, Box::new);
        serializer.register(IPAddress.SERIAL_ID, IPAddress::new);
        serializer.register(Date.SERIAL_ID, Date::new);
        serializer.register(Grade.SERIAL_ID, Grade::new);
        serializer.register(LinkedList.SERIAL_ID, LinkedList::new);
        serializer.register(HashMap.SERIAL_ID, HashMap::new);

        //deserialize each value
        Integer i = (Integer) serializer.readSerializable();
        String s = (String) serializer.readSerializable();
        Box<String> bs = (Box<String>) serializer.readSerializable();
        IPAddress ip = (IPAddress) serializer.readSerializable();
        Date d = (Date) serializer.readSerializable();
        Grade g = (Grade)serializer.readSerializable();
        LinkedList ll = (LinkedList) serializer.readSerializable();
        HashMap hs = (HashMap) serializer.readSerializable();

        //print out each value
        System.out.println(i);
        System.out.println(s);
        System.out.println(bs);
        System.out.println(ip);
        System.out.println(d);
        System.out.println(g);
        System.out.println(ll);
        System.out.println(hs);

        serializer.close();
    }
}
