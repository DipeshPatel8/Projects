/*
 * Copyright (c) 2017 Ian Clement.  All rights reserved.
 */

package ca.qc.johnabbott.cs406.collections.list;

import ca.qc.johnabbott.cs406.profiler.Profiler;

/**
 * An implementation of the List interface using an array.
 *
 * @author Ian Clement
 * @version 2
 */
public class ArrayList<T> implements List<T> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final double DEFAULT_FACTOR = 1.5;
    private T[] elements;
    private double factor;
    private int size;
    private boolean autoTrim;
    // stores the index of the array traversal
    private int traversal;


    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_FACTOR);
    }

    public ArrayList(int initialCapacity, double factor) {
        if(factor <= 1.0)
            throw new IllegalArgumentException();

        elements = (T[]) new Object[initialCapacity];
        size = 0;
        this.factor = factor;
    }

    public ArrayList(int initialCapacity) {
        this(initialCapacity, DEFAULT_FACTOR);
    }

    public ArrayList(double factor) {
        this(DEFAULT_INITIAL_CAPACITY, factor);
    }

    /**
     * Expands the elements array:
     *  - allocates a new array with new size
     *  - copies the current data from the previous elements into the new array
     * @param newSize
     */
    private void resize(int newSize) {
        Profiler.getInstance().startRegion("resize(n)");
        if(newSize < size)
            throw new IllegalArgumentException();
        T[] oldElements = elements;
        elements = (T[]) new Object[newSize];
        for(int i = 0; i < this.size; i++)
            elements[i] = oldElements[i];
        Profiler.getInstance().endRegion();
    }

    @Override
    public void add(T element) {
        Profiler.getInstance().startRegion("add(x)");
        if(size >= elements.length)
            resize((int) Math.ceil(elements.length * factor));
        elements[size++] = element;
        Profiler.getInstance().endRegion();
    }

    @Override
    public void add(int position, T element) {
        Profiler.getInstance().startRegion("add(pos,x)");
        if(position < 0 || position > size)
            throw new ListBoundsException();

        // when "appending" call the add(x) method
        if(position == size) {
            add(element);
            Profiler.getInstance().endRegion();
            return;
        }

        //  expand if we are at capacity.
        if(size >= elements.length)
            resize((int) Math.ceil(elements.length * factor));

        // shift the array upwards to make a new position
        for(int i = size; i > position; i--)
            elements[i] = elements[i-1];

        elements[position] = element;
        size++;
        Profiler.getInstance().endRegion();
    }

    @Override
    public T remove(int position) {
        Profiler.getInstance().startRegion("remove(pos)");
        if(position < 0 && position >= size)
            throw new ListBoundsException();

        // hold the element we're removing to return
        T tmp = elements[position];

        // shift the array downwards over the remove positon.
        for(int i=position; i<size-1; i++)
            elements[i] = elements[i+1];

        // nullify the removed reference
        elements[size-1] = null;

        size--;

        if(autoTrim && size * factor < elements.length)
            resize(size);

        Profiler.getInstance().endRegion();
        return tmp;
    }

    @Override
    public void clear() {
        //elements = (T[]) new Object[elements.length];
        for(int i=0; i<size; i++)
            elements[i] = null;
        size = 0;
    }

    @Override
    public T get(int position) {
        Profiler.getInstance().startRegion("get(pos)");
        if(position < 0 && position >= size)
            throw new ListBoundsException();
        Profiler.getInstance().endRegion();
        return elements[position];
    }

    @Override
    public T set(int position, T element) {
        Profiler.getInstance().startRegion("set(pos,x)");
        if(position < 0 && position >= size)
            throw new ListBoundsException();
        T ret = elements[position];
        elements[position] = element;
        Profiler.getInstance().endRegion();
        return ret;
    }

    public void trimToSize() {
        resize(size);
    }

    public void autoTrim() {
        autoTrim = true;
    }



    public void reset() {
        traversal = 0;
    }

    @Override
    public boolean hasNext() {
        return traversal < size;
    }

    @Override
    public T next() {
        return elements[traversal++];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T element) {
        for(int i=0; i<size; i++)
            if(elements[i].equals(element))
                return true;
        return false;
    }

    @Override
    public boolean remove(T element) {
        throw new RuntimeException("Not implemented exception");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0; i<size; i++) {
            sb.append(elements[i]);
            if(i < size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
