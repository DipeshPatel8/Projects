/*
 * Copyright (c) 2020 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs406.search;

import ca.qc.johnabbott.cs406.collections.Copyable;
import ca.qc.johnabbott.cs406.collections.AsChar;
import ca.qc.johnabbott.cs406.collections.Traversable;
import ca.qc.johnabbott.cs406.terrain.Direction;
import ca.qc.johnabbott.cs406.terrain.Terrain;
import ca.qc.johnabbott.cs406.terrain.Token;

/**
 * <p>Defines the structure of a search algorithm. Includes `Traversable<Direction>` as way of navigating
 * the solution path.</p>
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public interface Search extends Traversable<Direction> {

    /**
     * Search the provided terrain to find a solution path.
     * @param terrain the terrain to search for a solution path.
     */
    void solve(Terrain terrain);
}
