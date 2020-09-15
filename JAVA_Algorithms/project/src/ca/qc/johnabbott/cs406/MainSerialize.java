package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.list.LinkedList;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.serialization.Creator;
import ca.qc.johnabbott.cs406.serialization.Serializable;
import ca.qc.johnabbott.cs406.serialization.SerializationException;
import ca.qc.johnabbott.cs406.serialization.Serializer;
import ca.qc.johnabbott.cs406.serialization.io.BufferedChannel;
import ca.qc.johnabbott.cs406.serialization.util.Date;
import ca.qc.johnabbott.cs406.serialization.util.Integer;
import ca.qc.johnabbott.cs406.serialization.util.String;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Serialization example.
 */
public class MainSerialize {


    public static void main(java.lang.String arg[]) throws IOException, SerializationException {

        BufferedChannel channel = new BufferedChannel(new RandomAccessFile("foo.bin", "rw").getChannel(), BufferedChannel.Mode.WRITE);

        Serializer serializer = new Serializer(null, channel);

        //register required serial ids
        serializer.register(Integer.SERIAL_ID, Integer::new);
        serializer.register(String.SERIAL_ID, String::new);
        serializer.register(Box.SERIAL_ID, Box::new);
        serializer.register(IPAddress.SERIAL_ID, IPAddress::new);
        serializer.register(Date.SERIAL_ID, Date::new);
        serializer.register(Grade.SERIAL_ID, Grade::new);
        serializer.register(LinkedList.SERIAL_ID, LinkedList::new);
        serializer.register(HashMap.SERIAL_ID, HashMap::new);

        //create necessary values for testing
        Integer i = new Integer(123);
        String s = new String("hello,");
        Box<String> bs = new Box<>(new String("world."));
        IPAddress ip = new IPAddress("12.34.56.7");
        Date d = new Date(new java.util.Date());
        Grade g = new Grade("Bob", 80, new java.util.Date());

        LinkedList ll = new LinkedList();
        ll.add(new Integer(1));
        ll.add(new Integer(2));
        ll.add(new Integer(3));

        HashMap<String, Integer> hs = new HashMap<>();
        hs.put(new String("test"), new Integer(23));
        hs.put(new String("why"), new Integer(34));
        hs.put(new String("ok"), new Integer(56));

        //serialize each value
        serializer.write(i);
        serializer.write(s);
        serializer.write(bs);
        serializer.write(ip);
        serializer.write(d);
        serializer.write(g);
        serializer.write(ll);
        serializer.write(hs);

        channel.close();

    }
}
