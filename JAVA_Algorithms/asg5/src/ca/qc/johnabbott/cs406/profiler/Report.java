package ca.qc.johnabbott.cs406.profiler;

import java.util.*;


public class Report {

    public static final String ROW_FORMAT = "| %-30s | %9d | %10.2f\u00B5s | %6.2f%% %-30s |\n";

    private Report() {
    }

    /**
     * Print sections.
     * @param sections A list of sections to print.
     */
    public static void printAllSections(List<Section> sections) {
        for(Section section : sections)
            printSection(section);
    }

    private static void printHeader(Section section) {
        System.out.println("* " + section.getSectionLabel());
        System.out.println();
        System.out.println(
                "|--------------------------------+-----------+--------------+----------------------------------------|\n" +
                "| Region                         | Run Count | Total Time   | Percent of Section                     |\n" +
                "|--------------------------------+-----------+--------------+----------------------------------------|"
        );
    }

    private static void printFooter() {
        System.out.println(
                "|--------------------------------+-----------+--------------+----------------------------------------|"
        );
    }

    private static void printRow(String label, Region region) {
        double elapsedTimeInMicroSeconds = (double) region.getElapsedTime() / 1000.0;

        // construct a nice ASCII progress bar!
        StringBuilder bar = new StringBuilder();
        int pct = (int) (30.0 * region.getPercentOfSection());
        for (int i = 0; i < pct; i++)
            bar.append('\u25A0');

        System.out.format(ROW_FORMAT, label, region.getRunCount(), elapsedTimeInMicroSeconds, region.getPercentOfSection() * 100, bar);
    }

    private static void printSection(Section section) {

        printHeader(section);

        Map<String, Region> regions = section.getRegions();

        // get all region labels from the map, but make sure TOTAL appears last.
        List<String> labels = new ArrayList<>();
        for (String label : regions.keySet()) {
            if (!label.equals(Section.TOTAL))
                labels.add(label);
        }

        // for each region, print it's statistics to console
        for (String label : labels)
            printRow(label, regions.get(label));

        printFooter();
        printRow("TOTAL", regions.get("TOTAL"));
        printFooter();
        System.out.println();

    }




}
