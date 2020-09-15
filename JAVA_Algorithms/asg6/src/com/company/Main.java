package com.company;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

public class Main {

    static int whiteRank;
    static int whiteFile;
    static int blackRank;
    static int blackFile;

    public static void main(String[] args) {
        //Example 1 testing
        System.out.println("Example 1");

        Game game = new ThreeMensMorris();

        game.play(1, 1);
        assertEquals(Game.Token.NONE, game.winner());
        game.play(2, 2);
        game.play(1, 3);
        game.play(1, 2);
        game.play(3, 1);
        game.play(3, 2);
        System.out.println(game.toString());
        assertEquals(Game.Token.BLACK, game.winner());

        //copy method testing
        ThreeMensMorris board1 = new ThreeMensMorris();
        ThreeMensMorris board2 = board1.copy();
        assertTrue(board1.equals(board2));
        assertFalse(board1 == board2);

        System.out.println("\n");

        System.out.println("All boards possible");
        ThreeMensMorris initial = new ThreeMensMorris();

        //set the first 4 tokens as initial start board
        initial.play(1,2);
        initial.play(3,1);
        initial.play(2,2);
        initial.play(3,2);

        List<ThreeMensMorris> allBoards = new ArrayList<>();
        allBoards = generate(initial);//get list of all board possibilities

        //print all possibilities
        for (ThreeMensMorris currentBoard: allBoards) {
            System.out.println(currentBoard.toString());
        }
    }

    public static List<ThreeMensMorris> generate(ThreeMensMorris initial){
        List<ThreeMensMorris> configList = new ArrayList<>();
        configList.add(initial);//add initial board to list

        whiteFile=1;
        whiteRank=1;
        blackFile=2;
        blackRank=1;

        configList = generateHelper(initial, initial, configList);//use helper to generate list
        configList.remove(0);

        List<ThreeMensMorris> uniqueBoardsList = new ArrayList<>();//create unique list to get rid of duplicate boards

        //check if board already in unique list, if not then add it
        for (ThreeMensMorris currentBoard: configList) {
            if(!uniqueBoardsList.contains(currentBoard))
                uniqueBoardsList.add(currentBoard);
        }

        return uniqueBoardsList;
    }

    private static List<ThreeMensMorris> generateHelper(ThreeMensMorris initial, ThreeMensMorris current, List<ThreeMensMorris> acc){
        if(whiteRank <= 3){//if all white rank check then all position were check meaning end generating list

            current = initial.copy();//reset board to initial setup

            if(current.play(whiteFile, whiteRank) && current.play(blackFile, blackRank))//if tokens were placed then add to list
                acc.add(current);

            //if all file for current rank check then increase rank and reset file (Black)
            if (blackFile == 3){
                blackFile = 1;
                blackRank++;
            }
            else //if not then go next file
                blackFile++;

            if(blackRank > 3){//if all ranks check for black then move white to next file
                blackRank = 1;
                whiteFile++;
            }

            // if all file checked for current rank then go next rank and reset file (White)
            if(whiteFile > 3){
                whiteFile = 1;
                whiteRank++;
            }
            acc = generateHelper(initial, current, acc); //recursive call
        }
        return acc;//return the list
    }
}
