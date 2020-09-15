/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.Queue;
import ca.qc.johnabbott.cs406.collections.SparseArray;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Location;
import ca.qc.johnabbott.cs406.terrain.Terrain;

/**
 * A "search" for the goal, using the Breadth-First Search algorithm
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca) & Dipesh Patel 1835217
 */

public class BFSearch implements Search {
    // records where we've been and what steps we've taken.
    private SparseArray<Cell> memory;
    private Queue<Location> path;
    private Queue<Direction> pathDirections; // save direction for memory (tracking purpose)

    //private boolean[] locationChangeIndicator; // array to tell program to change center cell
    //private Queue<Location> pathCopy; // used for center cell

    // for tracking the "traversable" solution.
    private Location solution;
    private boolean foundSolution;


    // the terrain we're searching in.
    private Terrain terrain;

    /**
     * Create a Breadth-First Search
     */
    @Override
    public void solve(Terrain terrain) {

        this.terrain = terrain;
        foundSolution = true;

        Integer locationArrayIndex = 0;
        Integer currentLocationIndex = 0;
        boolean changeLocation;


        // track locations we've been to using our terrain "memory"
        Cell defaultCell = new Cell();
        defaultCell.setColor(Color.WHITE);
        memory = new SparseArray<>(defaultCell);

        // make a stack to keep track of every location for backtracking
        path = new Queue<>(terrain.getHeight()*terrain.getWidth());
        pathDirections = new Queue<>(terrain.getHeight()*terrain.getWidth());

        //locationChangeIndicator = new boolean[terrain.getHeight()*terrain.getWidth()];
        //pathCopy = new Queue<>(terrain.getHeight()*terrain.getWidth());


        // track the current search location, starting at the terrain start location.
        Location current = terrain.getStart();
        memory.get(current).setColor(Color.BLACK);

        //Location centerLocation = terrain.getStart();

        //set up all direction
        Direction[] directions = Direction.getClockwise();

        // loop until current location is goal.
        while(!current.equals(terrain.getGoal()) && foundSolution) {

            // verify the location in every direction, clockwise
            for (int i = 0; i < directions.length; i++) { // 0:UP 1:RIGHT 2:DOWN 3:LEFT

                Location next = current.get(directions[i]); //new location to be verified

                //check if new location is valid on terrain
                if(terrain.inTerrain(next) && !terrain.isWall(next) && memory.get(next).getColor() != Color.BLACK && memory.get(next).getColor() != Color.GREY){

                    path.enqueue(next);
                    pathDirections.enqueue(directions[i]);

                    //pathCopy.enqueue(next);
                    //locationChangeIndicator[locationArrayIndex++] = false;

                    // record that we've been here
                    memory.get(next).setColor(Color.GREY);
                }
            }

            //locationChangeIndicator[locationArrayIndex-1] = true;

            if(!path.isEmpty() && !pathDirections.isEmpty()){

                // record the step we've taken to memory to recreate the solution in the later traversal.
                while(!pathDirections.isEmpty())
                    memory.get(current).setTo(pathDirections.dequeue());

                /*
                  basically, add the direction to a certain location in memory, and only change that location
                  when all valid surrounding location have been checked (visited)


                changeLocation = locationChangeIndicator[currentLocationIndex++];
                if(changeLocation)
                    centerLocation = pathCopy.dequeue();
                */

                // step
                current = path.dequeue();

                // record that we've been here
                memory.get(current).setColor(Color.BLACK);

                System.out.println(memory);
            }
            else
                foundSolution = false;
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
