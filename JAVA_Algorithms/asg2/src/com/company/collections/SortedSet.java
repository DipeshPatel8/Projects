package com.company.collections;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * SortedSet Interface implementation
 * -constructor for SortedSet
 * -Override methods/Operations
 * @author Ian Clement(starter) & Dipesh Patel(completed)
 */
public class SortedSet<T extends Comparable<T>> implements Set<T> {

    private static final int DEFAULT_CAPACITY = 100;

    private T[] elements;
    private int size;
    private int tCursor; //Traversal cursor
    private boolean tModify; //traversal modification checker

    public SortedSet() {
        this(DEFAULT_CAPACITY);
    }

    public SortedSet(int capacity) {
        this.tModify = false; //check if modification made during traversal
        this.size = 0;
        this.tCursor = 0;//set cursor at beginning
        this.elements = (T[]) new Comparable[capacity];

    }

    @Override
    public boolean contains(T elem) {
        //binary search returns index of the value, negative number if not found
        return Arrays.binarySearch(elements, 0, size, elem) >= 0; //return true if value is positive
    }

    @Override
    public boolean containsAll(Set<T> rhs) {
        rhs.reset();//start the traversal API
        while (rhs.hasNext()){//go through each element of set
            T tmp = rhs.next();//gets next element
            if(Arrays.binarySearch(elements,0, size, tmp) < 0)//value negative if not found
                return false;
        }
        return true;
    }

    @Override
    public boolean add(T elem) {
        tModify = true;//modification made, keep it noted
        if(contains(elem)) //check if element already in set
            return false;

        if(isFull()) //check if set is not full
            throw  new FullSetException();

        if(isEmpty()){ //set is empty
            elements[0] = elem;//add first element to set
            size++;//increment size
            return true;
        }

        int tmp=0;//index of the new element to add
        for (int i = size-1; i >= 0 ; i--) {//start at end of array and go down for every value bigger than elem
            tmp = i+1;//set index of new elem
            if (elements[i].compareTo(elem) > 0)//if element in array is bigger than elem
                elements[tmp] = elements[i]; //move element up by 1
            else {// element in array is smaller than elem
                break;
            }
            if(i==0)//if first position in array(meaning last loop)
                tmp = 0;
        } 
        elements[tmp] = elem;//add new element to set
        size++;//increment size
        return true;
    }

    @Override
    public boolean remove(T elem) {
        tModify = true;//modification made, keep it noted
        for (int i = 0; i < size; i++) {//go through each element in array
            if(elements[i].compareTo(elem) == 0){//if element is found in set
                for (int j = i; j < size; j++) {//move all element down 1 to that index of elem
                    elements[j] = elements[j+1];//elem to be removed will be replace by elem+1
                }
                size--;//reduce size
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;//size of set
    }

    @Override
    public boolean isEmpty() {
        if(size == 0)
            return true;
        else
            return false;
    }

    /**
     * Retrieve the smallest element in the list
     * @precondition sorted set is not empty
     * @postcondition find the smallest element in the set and not change to set
     * @return the smallest T element
     */
    public T min() {
        if(size == 0)//if no element in set --> no min value
            throw new EmptySetException();
        return elements[0]; //return first value of array because set is sorted
    }

    /**
     * Retrieve the largest element in the list
     * @precondition sorted set is not empty
     * @postcondition find the largest element in the set and not change to set
     * @return the largest T element
     */
    public T max() {
        if(size == 0) //if no element in set --> no max value
            throw new EmptySetException();
        return elements[size-1]; //return last value in set because array is sorted
    }

    /**
     * Creates a subset containing the elements between an lower and upper bound.
     * @param first Lower bound of the subset (inclusive)
     * @param last upper bound of the subset (exclusive)
     * @precondtition lower bound must be less or equals to upper bound.
     * @postcondition produces a subset of elements between the bounds from the main set
     * @return A SortedSet type subset
     */
    public SortedSet<T> subset(T first, T last) {
        if(first.compareTo(last) == 1)//if range is not valid
            throw new IllegalArgumentException();

        //find index of both bounds
        int startIndex = Arrays.binarySearch(elements, 0 ,size, first);
        int endIndex = Arrays.binarySearch(elements, 0 ,size, last);

        //if bounds are not in set, find the index where it is suppose to be
        //Arrays.binarySearch returns the (-insertion point - 1) if value not in array
        if(startIndex<0)
            startIndex = (startIndex*-1)-1;//get the index of start
        if(endIndex<0)
            endIndex = (endIndex*-1)-1;//get the index of end

        SortedSet<T> set = new SortedSet<>();//create a set for subset

        for (int i = startIndex; i < endIndex; i++) {//go through the element array from start index to end index
            set.add(elements[i]); //add each element to the subset
        }
        return set;
    }

    @Override
    public boolean isFull() {
        if(size == elements.length)//if size is array length
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        String tmp = "{";//create tmp string the build
        for (int i = 0; i < size; i++){//go through each element in set
            tmp+=elements[i];//concatenate each element to string
            if(i != size-1)//if not last element in set --> add a comma
                tmp+=", ";
        }
        tmp+="}";//close the bracket
        return tmp;//return the string
    }

    @Override
    public void reset() {
        tCursor = 0;//reset the traversal cursor
        tModify = false;//traversal reset, no modification made
    }

    @Override
    public T next() {
        if(!hasNext() || tModify) //if there are no more elements and set modified
            throw new TraversalException();
        return elements[tCursor++];//send next element
    }

    @Override
    public boolean hasNext() {
        if(tCursor >= size)//if cursor is at end or past the size --> no more next values
            return false;
        return true;//elements left
    }
}
