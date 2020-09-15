package ca.qc.johnabbott.cs406;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {

    public static void main(String[] args) {

        Scanner lexiconFile = null;
        try {
            lexiconFile = new Scanner(new FileReader(Alphabets.LEXICON_ALPHABET));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Lexicon lexicon = new Trie(Alphabets.ALPHABET.length);
        while (lexiconFile.hasNext())
            lexicon.add(lexiconFile.next());

        Scanner cin = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String word = cin.next();
            System.out.format("%s %s a lexicon word.\n", word, lexicon.contains(word) ? "is" : "isn't");
        }
    }
}
