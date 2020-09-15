package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.collections.list.List;
import ca.qc.johnabbott.cs406.collections.map.HashMap;
import ca.qc.johnabbott.cs406.collections.map.Map;
import ca.qc.johnabbott.cs406.collections.map.NaiveMap;
import ca.qc.johnabbott.cs406.generator.Generator;
import ca.qc.johnabbott.cs406.generator.SentenceGenerator;
import ca.qc.johnabbott.cs406.generator.WordGenerator;
import ca.qc.johnabbott.cs406.profiler.Profiler;
import ca.qc.johnabbott.cs406.profiler.Report;

import java.util.Random;

public class ProfileMaps {

    public static final Generator<String> STRING_GENERATOR;
    public static final Random RANDOM;
    public static final int SAMPLE_SIZE = 10000;

    static {
        RANDOM = new Random();
        STRING_GENERATOR = new SentenceGenerator(new WordGenerator("foo bar baz qux quux quuz corge grault garply waldo fred plugh xyzzy thud".split(" ")), 10);
    }

    public static void main(String[] args) {

        // Map
        Profiler.getInstance().startSection("NaiveMap - Initialize");
        mapInitialize(new NaiveMap<>());
        Profiler.getInstance().endSection();

        Profiler.getInstance().startSection("HashMap - Initialize");
        mapInitialize(new HashMap<>());
        Profiler.getInstance().endSection();

        Map<String, Integer> nmap = new NaiveMap<>();
        for(int i=0; i<SAMPLE_SIZE; i++)
            nmap.put(STRING_GENERATOR.generate(RANDOM), i);
        List<String> keys = nmap.keys();

        Profiler.getInstance().startSection("NaiveMap - Get");
        mapGet(nmap, keys);
        Profiler.getInstance().endSection();

        Map<String, Integer> hmap = new HashMap<>();
        for(int i=0; i<SAMPLE_SIZE; i++)
            hmap.put(STRING_GENERATOR.generate(RANDOM), i);
        keys = hmap.keys();

        Profiler.getInstance().startSection("HashMap - Get");
        mapGet(hmap, keys);
        Profiler.getInstance().endSection();


        // print report to STDOUT

        Report.printAllSections(Profiler.getInstance().produceSections());


    }


    private static void mapInitialize(Map<String, Integer> map) {
        for(int i=0; i<SAMPLE_SIZE; i++)
            map.put(STRING_GENERATOR.generate(RANDOM), i);
    }

    private static void mapGet(Map<String, Integer> map, List<String> keys) {
        Profiler.getInstance().startRegion("key-found");
        for(int i=0; i<SAMPLE_SIZE/2; i++) {
            String key = keys.get(RANDOM.nextInt(keys.size()));
            if(map.containsKey(key))
                map.get(key);
        }
        Profiler.getInstance().endRegion();

        Profiler.getInstance().startRegion("key-not-found");
        for(int i=0; i<SAMPLE_SIZE/2; i++) {
            String key = STRING_GENERATOR.generate(RANDOM);
            if(map.containsKey(key))
                map.get(key);
        }
        Profiler.getInstance().endRegion();
    }

}
