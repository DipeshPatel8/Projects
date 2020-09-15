/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.collections.Stack;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A "search" for the goal, using the Depth-First Search algorithm
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca) & Dipesh Patel 1835217
 */

public class DFSearch implements Search {

    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;
    private Stack<Location> route;

    // for tracking the "traversable" solution.
    private Location solution;
    private boolean foundSolution;

    // the terrain we're searching in.
    private Terrain terrain;

    /**
     * Create a Depths-First Search
     */
    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;
        foundSolution = true;

        // track locations we've been to using our terrain "memory"
        Cell defaultCell = new Cell();
        defaultCell.setColor(Color.WHITE);
        memory = new SparseArray<>(defaultCell);

        // make a stack to keep track of every location for backtracking
        route = new Stack<>(terrain.getHeight()*terrain.getWidth());

        // track the current search location, starting at the terrain start location.
        Location current = terrain.getStart();


        //set up all direction
        Direction[] directions = Direction.getClockwise();

        // loop until current location is goal.
        while(!current.equals(terrain.getGoal()) && foundSolution) {

            // verify the location in every direction, clockwise
            for (int i = 0; i < directions.length; i++) { // 0:UP 1:RIGHT 2:DOWN 3:LEFT

                Location next = current.get(directions[i]); //new location to be verified

                //check if new location is valid on terrain
                if(!terrain.inTerrain(next) || terrain.isWall(next) || memory.get(next).getColor() == Color.BLACK){

                    // if new location invalid and its the left side, backtrack to previous location
                    if(i == directions.length-1){

                        //make sure stack isn't to avoid errors
                        if(!route.isEmpty())
                            current = route.pop();
                        else{
                            foundSolution = false;
                            break;
                        }
                    }
                    else
                        // if new location isn't on terrain, check another direction
                        continue;
                }
                else { // new location is valid on terrain

                    // record the step we've taken to memory to recreate the solution in the later traversal.
                    memory.get(current).setTo(directions[i]);

                    // step
                    current = next;

                    //save in Stack for backtrack
                    route.push(current);

                    // record that we've been here
                    memory.get(current).setColor(Color.BLACK);

                    System.out.println(memory);
                    break;
                }
            }
        }
    }

    @Override
    public void reset() {
        // start the traversal of our path at the terrain's start.
        solution = terrain.getStart();
    }

    @Override
    public Direction next() {
        // recall the direction at this location, move to the corresponding location and return it.
        Direction direction = memory.get(solution).getTo();
        solution = solution.get(direction);
        return direction;
    }

    @Override
    public boolean hasNext() {
        //we're only done when we get to the terrain goal.
        return !solution.equals(terrain.getGoal());
    }
}
