/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.AsChar;
import ca.qc.johnabbott.cs406.collections.Copyable;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Token;

/**
 * Store search information at a particular point in the terrain. Used as values in the sparse array.
 */
class Cell implements Copyable<Cell>, AsChar {

    private Color color;
    private Direction to;
    private Direction from;

    /**
     * Create a white cell.
     */
    public Cell() {
        this(Color.WHITE);
    }

    /**
     * Create a cell
     * @param color The cell color.
     */
    public Cell(Color color) {
        this.setColor(color);
        setTo(Direction.NONE);
        setFrom(Direction.NONE);
    }

    @Override
    public char toChar() {
        switch (color) {
            case BLACK:
                return Token.BLACK_TOKEN.toChar();
            case GREY:
                return Token.GREY_TOKEN.toChar();
            case WHITE:
                return Token.EMPTY.toChar();
            default:
                return Token.EMPTY.toChar();
        }
    }

    @Override
    public Cell copy() {
        Cell clone = new Cell();
        clone.setColor(this.getColor());
        clone.setTo(this.getTo());
        return clone;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Direction getTo() {
        return to;
    }

    public void setTo(Direction to) {
        this.to = to;
    }

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }
}
