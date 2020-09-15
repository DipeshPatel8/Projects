package ca.qc.johnabbott.cs406;

/**
 * Represents a lexicon.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Lexicon {

    /**
     * Add a word to the lexicon.
     * @param word the word to add to the lexicon.
     */
    void add(String word);


    /**
     * Test if a word is in the lexicon.
     * @param word the word to check.
     * @return true if the word is in the lexicon, false otherwise.
     */
    boolean contains(String word);

}
