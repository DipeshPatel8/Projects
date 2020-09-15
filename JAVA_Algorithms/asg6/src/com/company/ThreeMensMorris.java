package com.company;

import java.util.Arrays;

public class ThreeMensMorris implements Game, Copyable<ThreeMensMorris>, Cloneable {

    //Constant
    private final int RANK_SIZE = 3; // size of the board

    //Fields
    private Token turn; // keep tracks of which player's turn
    private Token [][] board; // the game board

    //Constructor
    public ThreeMensMorris() {
        board = new Token[RANK_SIZE][RANK_SIZE]; // set empty board
        for (int i = 0; i < board.length; i++) { //set each spot to no token
            for (int j = 0; j < RANK_SIZE; j++) {
                board[i][j] = Token.NONE;
            }
        }
        turn = Token.WHITE; // white player starts
    }

    public ThreeMensMorris(Token[][] preset){
        board = preset; // create a given board
    }

    //Methods
    public boolean play(int file, int rank) {
        //check if values are valid
        if(file <= 0 || file > 3)
            throw new IndexOutOfBoundsException();
        if(rank <= 0 || rank > 3)
            throw new IndexOutOfBoundsException();

        int indexRow;
        int indexCol;
        boolean tokenSet;

        tokenSet = false;

        //logic unifies player placement and array layout
        indexCol = file - 1;
        indexRow = RANK_SIZE - rank;

        // place token if place not taken
        if(board[indexRow][indexCol] == Token.NONE){
            board[indexRow][indexCol] = turn;
            turn = turn.opposite();
            tokenSet = true;
        }

        return tokenSet;
    }

    public Token winner() {
        //general variables
        int center = 1;
        int lhs = 0;
        int rhs = 2;

        //check horizontal winning conditions
        for (int i = 0; i < board.length; i++) {
            if(board[i][lhs] == board[i][center] && board[i][rhs] == board[i][center])
                return board[i][center];
        }

        //check vertical winning condition
        for (int i = 0; i < board.length; i++) {
            if(board[lhs][i] == board[center][i] && board[rhs][i] == board[center][i])
                return board[center][i];

        }

        //check diagonal winning condition
        if(board[lhs][lhs] == board[center][center] && board[rhs][rhs] == board[center][center])
            return board[center][center];
        if(board[lhs][rhs] == board[center][center] && board[rhs][lhs] == board[center][center])
            return board[center][center];

        //no winning condition found
        return null;
    }

    public boolean equals(ThreeMensMorris rhs) {
        //check if every token match
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < RANK_SIZE; j++) {
                if(rhs.board[i][j] != this.board[i][j])
                    return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return Arrays.hashCode(board);
    }

    public String toString() {
        String tmp = "[";

        //start at bottom left corner and move up, add each token to string
        for (int i = RANK_SIZE -1; i >= 0; i--) {
            for (int j = 0; j < RANK_SIZE; j++) {
                tmp = tmp+board[i][j].toString();
            }
        }
        tmp=tmp+"] turn="+turn.toString()+" winner=";

        if (winner() == Token.BLACK)
            tmp = tmp+"BLACK";
        else if(winner() == Token.WHITE)
            tmp = tmp+"WHITE";
        else
            tmp = tmp+"NONE";
        return tmp;
    }

    public ThreeMensMorris copy() {
        //create new game and copy info
        ThreeMensMorris duplicate = new ThreeMensMorris();
        //copy every token onto new board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < RANK_SIZE; j++) {
                duplicate.board[i][j] = this.board[i][j];
            }
        }
        duplicate.turn = this.turn;
        return duplicate;
    }
}
