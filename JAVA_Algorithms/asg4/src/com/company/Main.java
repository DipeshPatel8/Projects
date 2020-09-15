package com.company;

import java.util.Scanner;

/**
 * Data Structures Asg 4
 * @author Dipesh Patel (dipeshpatel8@hotmail.com)
 * 1835217
 */

public class Main {

    public static void main(String[] args) {

        //Variables
        Integer n; //elements in chain
        Integer m; //clockwise removal
        Integer o; // counter clockwise removal
        Integer removedCount; // keep track of removed elements and array index
        DoubleLink<Integer> head; // head of chain
        DoubleLink<Integer> current; // current link
        DoubleLink<Integer> nextLink; // next link of the current link
        DoubleLink<Integer> prevLink; // previous link of the current link
        Integer[]removedOrder; // array of removed elements in order
        Scanner in = new Scanner(System.in); // get input form user

        //Getting values
        System.out.println("Enter value for n");
        n = Integer.parseInt(in.nextLine());

        while(n<=0){ //chain must contain elements, cannot be empty
            System.out.println("Invalid value! Enter Integer bigger than 0.");
            n = Integer.parseInt(in.nextLine());
        }

        System.out.println("Enter value for m");
        m = Integer.parseInt(in.nextLine());
        System.out.println("Enter value for o");
        o = Integer.parseInt(in.nextLine());

        while( m<0 || o<0 || m+o<=0){ //removal must occur in at least 1 way
            System.out.println("Invalid value! Enter Integer bigger or equal to 0");
            System.out.println("Enter value for m");
            m = Integer.parseInt(in.nextLine());
            System.out.println("Enter value for o");
            o = Integer.parseInt(in.nextLine());
        }

        //Creating double link chain
        current = head = new DoubleLink<>(1); //start the chain from head

        for (int i = 2; i <= n; i++) {
            current.next = nextLink = new DoubleLink<>(i); //add a link at the next position from current
            nextLink.prev = current; // link previous to next is the current
            current = nextLink; // increment current link to add new link
        }
        current.next = head; // circular chain, set next link of last link to the beginning of chain
        head.prev = current; // circular chain, set previous link of head link to last link in chain
        current = head; // reset current for removal process

        //Removing values
        removedOrder = new Integer[n];//array of removed links
        removedCount = 0; //keep count and indexing
        while (removedCount != n) { // loop until all no more link left

            if(m>0){ // in case clockwise removal is not wanted

                //Remove Clockwise
                for (int j = 1; j < m; j++)
                    current = current.next; // get to the appropriate link

                //remove links to current element and set to surrounding elements
                prevLink = current.prev;
                nextLink = current.next;
                prevLink.next = nextLink;
                nextLink.prev = prevLink;

                //add removed element to array
                removedOrder[removedCount++] = current.element;

                //in case no more elements left, end removing process
                if(removedCount == n)
                    break;

                //set the current to upcoming, current doesn't exist anymore
                current = nextLink;
            }

            if(o>0){ // in case counterclockwise removal is not wanted

                //Remove CounterClockwise
                for (int j = 1; j < o; j++)
                    current = current.prev;

                //remove links to current element and set to surrounding elements
                prevLink = current.prev;
                nextLink = current.next;
                prevLink.next = nextLink;
                nextLink.prev = prevLink;

                //add removed item to array
                removedOrder[removedCount++] = current.element;

                //set the current to upcoming, current doesn't exist anymore
                current = nextLink;
            }
        }

        // print configurations and list of element removed in removed order
        System.out.println("n> "+n);
        System.out.println("m> "+m);
        System.out.println("o> "+o);
        for (int i = 0; i < n ; i++) {
            System.out.print(removedOrder[i]+" ");
        }
    }

    private static class DoubleLink<T> {
        public T element;
        public DoubleLink<T> next;
        public DoubleLink<T> prev;
        public DoubleLink() {}
        public DoubleLink(T element) {
            this.element = element;
        }
    }

}
