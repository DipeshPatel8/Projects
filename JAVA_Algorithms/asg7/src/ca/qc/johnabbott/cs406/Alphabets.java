package ca.qc.johnabbott.cs406;

/**
 * Created by ian on 2016-04-20.
 */
public class Alphabets {

    // hide the constructor --> utility class
    private Alphabets() {};

    public static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] ABCDE = "abcde".toCharArray();

    public static final String LEXICON_ALPHABET = "lexicon/words-linux-cleaned.txt";
    public static final String LEXICON_ABCDE = "lexicon/words-linux-cleaned-abcde.txt";

    // can get from http://wordlist.aspell.net/
    // warning: may contain offensive words
    public static final String LEXICON_CANADIAN_60 = "words-canadian-60.txt";
    public static final String LEXICON_CANADIAN_80 = "words-canadian-80.txt";

}
