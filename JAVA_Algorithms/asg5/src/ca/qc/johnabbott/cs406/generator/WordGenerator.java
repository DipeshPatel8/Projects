package ca.qc.johnabbott.cs406.generator;

import java.util.Random;

/**
 * Generate a random word from a set of words.
 */
public class WordGenerator implements Generator<String> {

    private String[] words;

    public WordGenerator(String[] words) {
        this.words = words;
    }

    @Override
    public String generate(Random random) {
        return words[random.nextInt(words.length)];
    }
}
