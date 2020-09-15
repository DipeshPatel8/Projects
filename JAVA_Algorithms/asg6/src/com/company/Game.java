/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package com.company;

/**
 * The structure of a basic 2-player board game.
 */
public interface Game {

    /**
     * Represents a Token
     */
    enum Token {
        NONE(' '), WHITE('\u25CB'), BLACK('\u25CF');

        private final char c;

        Token(char c) {
            this.c = c;
        }

        public char get() { return c; }

        public Token opposite() {
            switch(this) {
                case WHITE:
                    return BLACK;
                case BLACK:
                    return WHITE;
                default:
                    return NONE;
            }
        }

        @Override
        public String toString() { return String.valueOf(c); }
    }


    /**
     * Play the current piece at the specified position
     * @param file The file of
     * @param rank
     * @return True if the move was performed, false otherwise.
     */
    boolean play(int file, int rank);

    /**
     * Determine the winner of the game.
     * @return The winner of the game, or NONE if there is no winner yet.
     */
    Token winner();
}
