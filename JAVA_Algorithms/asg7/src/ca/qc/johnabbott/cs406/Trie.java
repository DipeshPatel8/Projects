package ca.qc.johnabbott.cs406;

import com.sun.xml.internal.bind.v2.runtime.SwaRefAdapter;

import java.util.HashMap;

/**
 * Created by Dipesh Patel - 1835217
 *    2020-05-18
 */
public class Trie implements Lexicon {

    enum leafColor {Red, Black};

    //Node class
    class Node{

        private HashMap<Character, Node> children; //set of nodes within a node
        private leafColor color; // Red = end of word, Black = default

        public Node(){
            children = new HashMap<Character, Node>();// create first hashmap
            color = leafColor.Black; // set default color
        }
    }

    private Node root;//main beginning node

    public Trie(int alphabetLength) {
        root = new Node(); // first node of trie
    }

    @Override
    public void add(String word) {
        addHelper(word, root, 0); // start at letter 0 with root as initial Node
    }

    private void addHelper(String word, Node current, int letterIndex){
        char tmp = word.charAt(letterIndex); //get letter
        Node tmpCurrent = new Node(); // Node down the tree

        if(current.children.containsKey(tmp))//if node already exist for the letter
            tmpCurrent = current.children.get(tmp); // set new Node to existing Node
        else
            current.children.put(tmp, tmpCurrent); // add new Node to children

        current = tmpCurrent; // set new current

        if(letterIndex == word.length()-1) // if last letter
            tmpCurrent.color = leafColor.Red; //set color red for marking
        else
            addHelper(word, current, letterIndex+1); // go next letter
    }


    @Override
    public boolean contains(String word) {
        return containsHelper(word, root, 0); //start at letter 0 with root as initial Node
    }

    private boolean containsHelper(String word, Node current, int letterIndex) {
        char tmp = word.charAt(letterIndex); // get letter
        boolean found;

        if (!current.children.containsKey(tmp)) // if hashmap doesn't have char as key
            found = false;
        else{
            if(letterIndex+1 == word.length())//if last letter in word
                return true;
            current = current.children.get(tmp); //set new current
            found = containsHelper(word, current, letterIndex+1); // go search for next letter
        }
        return found;
    }
}
