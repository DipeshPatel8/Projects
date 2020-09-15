package ca.qc.johnabbott.cs406;

import ca.qc.johnabbott.cs406.generator.Generator;
import ca.qc.johnabbott.cs406.generator.SentenceGenerator;
import ca.qc.johnabbott.cs406.generator.WordGenerator;
import ca.qc.johnabbott.cs406.profiler.Profiler;
import ca.qc.johnabbott.cs406.profiler.Region;
import ca.qc.johnabbott.cs406.profiler.Report;
import ca.qc.johnabbott.cs406.profiler.Section;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProfileSamples {

    private static final boolean DEBUG = true;

    public static final Generator<String> STRING_GENERATOR;
    public static final Random RANDOM;
    public static final int SAMPLE_SIZE = 10000;

    static {
        RANDOM = new Random();
        STRING_GENERATOR = new SentenceGenerator(new WordGenerator("foo bar baz qux quux quuz corge grault garply waldo fred plugh xyzzy thud".split(" ")), 10);
    }

    public static void main(String[] args) throws InterruptedException {

        // ================================================================================

        Section section = new Section("Sample Section 0: Manual creation of regions and sections to verify the reporting only.");

        Region region1 = new Region(section, 5, 1234, 0.25);
        Region region2 = new Region(section, 1, 10000, 0.45);
        Region region3 = new Region(section, 10, 123, 0.01);

        section.addRegion("Sample Region 1", region1);
        section.addRegion("Sample Region 2", region2);
        section.addRegion("Sample Region 3", region3);

        Region total = new Region(section, 1, 20000, 1.0);
        section.addRegion("TOTAL", total);

        List<Section> list = new ArrayList<>();
        list.add(section);

        Report.printAllSections(list);

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 1: the regions are sequential.");

        Thread.sleep(1);
        Profiler.getInstance().startRegion("First region.");
        Thread.sleep(2);
        Profiler.getInstance().endRegion();
        Profiler.getInstance().startRegion("Second region.");
        Thread.sleep(5);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().endSection();

        // TODO: remove when done with this example
        //if(DEBUG) printProfiler();

        List<Section> sectionList = Profiler.getInstance().produceSections();
        Report.printAllSections(sectionList);

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 2: entering and exiting a region multiple times.");

        for(int i=0; i<10; i++) {
            Profiler.getInstance().startRegion("The region.");
            Thread.sleep(2);
            Profiler.getInstance().endRegion();
        }

        Profiler.getInstance().endSection();

        // TODO: remove when done with this example
        //if(DEBUG) printProfiler();

        sectionList = Profiler.getInstance().produceSections();
        Report.printAllSections(sectionList);

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 3: inner and outer regions.");

        Thread.sleep(1);

        Profiler.getInstance().startRegion("First region.");
        Thread.sleep(1);

        Profiler.getInstance().startRegion("Second region.");
        Thread.sleep(1);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().startRegion("Third region.");
        Thread.sleep(1);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().endRegion(); // end region 1

        Profiler.getInstance().endSection();

        // TODO: remove when done with this example
        //if(DEBUG) printProfiler();

        sectionList = Profiler.getInstance().produceSections();
        Report.printAllSections(sectionList);

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 4: build a string using concatenation.");

        String string = "";
        for(int i = 0; i < 1000; i++) {

            Profiler.getInstance().startRegion("Generate random strings.");
            String s = STRING_GENERATOR.generate(RANDOM);
            Profiler.getInstance().endRegion();

            Profiler.getInstance().startRegion("String concatenation.");
            string += s;
            Profiler.getInstance().endRegion();
        }

        Profiler.getInstance().startRegion("Console IO.");
        System.out.println(string);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().endSection();

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 5: build a string using StringBuilder");

        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 1000; i++) {

            Profiler.getInstance().startRegion("Generate random strings.");
            String s = STRING_GENERATOR.generate(RANDOM);
            Profiler.getInstance().endRegion();

            Profiler.getInstance().startRegion("Append to StringBuilder.");
            builder.append(s);
            Profiler.getInstance().endRegion();
        }

        Profiler.getInstance().startRegion("Console IO.");
        System.out.println(builder.toString());
        Profiler.getInstance().endRegion();

        Profiler.getInstance().endSection();

        // ================================================================================

        Profiler.getInstance().startSection("Sample Section 6: InsertionSort");

        Profiler.getInstance().startRegion("Generate random string.");
        String[] arr = new String[1000];
        for(int i = 0; i < 1000; i++)
            arr[i] = STRING_GENERATOR.generate(RANDOM);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().startRegion("Sort.");
        insertionSort(arr);
        Profiler.getInstance().endRegion();

        Profiler.getInstance().endSection();

        // ================================================================================

        Report.printAllSections(Profiler.getInstance().produceSections());
    }

    private static void printProfiler() {
        System.out.println("Profiler contains marks:");
        System.out.println(Profiler.getInstance());
        System.out.println();
    }


    //https://www.geeksforgeeks.org/insertion-sort/
    private static <T extends Comparable<T>> void insertionSort(T[] arr) {

        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            T key = arr[i];
            int j = i - 1;

            Profiler.getInstance().startRegion("Shift.");
            while (j >= 0) {
                Profiler.getInstance().startRegion("compare(x,y).");
                int x = arr[j].compareTo(key);
                Profiler.getInstance().endRegion();

                if(x <= 0)
                    break;

                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            Profiler.getInstance().endRegion();
        }
    }

}
