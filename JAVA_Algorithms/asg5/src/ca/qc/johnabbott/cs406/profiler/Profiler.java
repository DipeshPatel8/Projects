package ca.qc.johnabbott.cs406.profiler;

import java.util.*;

/**
 * A simple profiling class.
 */
public class Profiler {

    /*
    Delimits a section or region of the profiling.
    */
    private static class Mark {

        // stores the type of mark
        private enum Type {
            START_REGION, END_REGION, START_SECTION, END_SECTION
        }

        public Type type;
        public long time;
        public String label;

        // Create mark without a label
        public Mark(Type type, long time) {
            this(type, time, null);
        }

        // Create a mark with a label
        public Mark(Type type, long time, String label) {
            this.type = type;
            this.time = time;
            this.label = label;
        }

        @Override
        public String toString() {
            return "Mark{" +
                    "type=" + type +
                    ", time=" + time +
                    ", label='" + label + '\'' +
                    '}';
        }
    }

    // store singleton instance
    private static Profiler INSTANCE;
    static {
        INSTANCE = new Profiler();
    }

    /**
     * Get profiler singleton instance.
     * @return the profiler singleton.
     */
    public static Profiler getInstance() {
        return INSTANCE;
    }

    // store marks in list
    private List<Mark> marks;

    // use to prevent regions when not wanted/needed.
    private boolean paused;
    private boolean inSection;

    // private constructor for singleton
    private Profiler() {
        // linked list, because append is a constant time operation.
        marks = new LinkedList<>();
        paused = false;
        inSection = false;
    }

    /**
     * Starts a new profiling section.
     * @param label The section label.
     */
    public void startSection(String label) {
        if(!paused) {
            marks.add(new Mark(Mark.Type.START_SECTION, System.nanoTime(), label));
            inSection = true;
        }
    }

    /**
     * Ends a section. Must be paired with a corresponding call to `startSection(..)`.
     */
    public void endSection() {
        if(!paused) {
            marks.add(new Mark(Mark.Type.END_SECTION, System.nanoTime()));
            inSection = false;
        }
    }

    /**
     * Starts a new profiling region.
     * @param label The region label.
     */
    public void startRegion(String label) {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.START_REGION, System.nanoTime(), label));
    }

    /**
     * Ends a region. Must be paired with a corresponding call to `startRegion(..)`.
     */
    public void endRegion() {
        if(!paused && inSection)
            marks.add(new Mark(Mark.Type.END_REGION, System.nanoTime()));
    }

    /**
     * Using the currently collected data, generate all the section data for reporting.
     * @return A list of sections.
     */
    public List<Section> produceSections() {
        // TODO: compile results
        List<Section> sections = new ArrayList<>(); // main list of sections
        Stack<Mark> regionsInProcess = new Stack<>(); // stack of marks to pair up opening and closing marks
        Section currentSection = new Section(""); // the section being built
        Map<String, Region> SRMap = new HashMap<>(); // map of all regions made

        for(Mark currentMark : marks){ // for all marks
            switch (currentMark.type){ // check which type of mark it is
                case START_SECTION:

                    currentSection = new Section(currentMark.label); // start new section
                    regionsInProcess.push(currentMark); // add to stack
                    SRMap.put(currentMark.label, new Region(currentSection,1,0,0)); // make region aswell for time
                    break;

                case END_SECTION:

                    Mark startMark = regionsInProcess.pop(); // get pairing mark

                    SRMap.get(startMark.label).addElapsedTime(currentMark.time - startMark.time); // set time of section
                    SRMap.get(startMark.label).setPercentOfSection(1.0); // set percent of section

                    Region total = SRMap.get(startMark.label); // create final region of section

                    SRMap.remove(startMark.label); // remove from has since not actual region (section)

                    for(String currentKey : SRMap.keySet()){
                        Region currentRegion = SRMap.get(currentKey);
                        currentRegion.setPercentOfSection((double)currentRegion.getElapsedTime()/(double)total.getElapsedTime());
                        currentSection.addRegion(currentKey, currentRegion); // add region to section
                    }
                    SRMap.clear();
                    currentSection.addRegion("TOTAL", total); // add final region(total) to section
                    sections.add(currentSection); // add the section to the sections list
                    break;

                case START_REGION:

                    if(SRMap.containsKey(currentMark.label)) // check if region is already existing
                        SRMap.get(currentMark.label).addRun(); // revisit of same region, increase run count
                    else
                        SRMap.put(currentMark.label, new Region(currentSection,1,0,0)); // new region
                    regionsInProcess.push(currentMark); // add region to stack
                    break;

                case END_REGION:

                    Mark mainMark = regionsInProcess.pop(); // get pairing mark/region start
                    SRMap.get(mainMark.label).addElapsedTime(currentMark.time-mainMark.time); // set elapsed time
                    break;
            }
        }
        return sections;
    }


    @Override
    public String toString() {
        // print all marks using formatting.
        StringBuilder builder = new StringBuilder();
        for(Mark mark : marks)
            builder.append(String.format("%d %13s %-30s\n", mark.time, mark.type.toString(), mark.label != null ? mark.label : ""));
        return builder.toString();
    }
}