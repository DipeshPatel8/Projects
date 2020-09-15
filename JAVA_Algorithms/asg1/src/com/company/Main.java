package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    //File CONSTANTS
    public static final String FILE1 = "data/test/in1.txt";
    public static final String FILE2 = "data/test/in2.txt";
    public static final String OUT = "data/test/out.txt";

    public static void main(String[] args) throws IOException {
        merge(FILE1, FILE2, OUT);
    }

    public static void merge(String in1, String in2, String out) throws IOException {
        //IO variables & variables
        FileReader reader1 = new FileReader(in1); //file 1 reader
        FileReader reader2 = new FileReader(in2); //file 2 reader
        Scanner scanner1 = new Scanner(reader1); //file 1 scanner
        Scanner scanner2 = new Scanner(reader2); //file 2 scanner
        FileWriter writer = new FileWriter(out);
        PrintWriter printWriter = new PrintWriter(writer); //general printer for all logs

        int file1Lines = 0; //line counter for file 1
        int file2Lines = 0; //line counter for file 2

        //get first line of each file
        try{
            Log firstLine = new Log(scanner2.nextLine());
            file1Lines++;
            Log secondLine = new Log(scanner2.nextLine());
            file2Lines++;

            while (scanner1.hasNextLine() && scanner2.hasNextLine()) { //loop until 1 file is empty
                if(firstLine.compareTo(secondLine)==1){ //file 2 log < file 1 log
                    printWriter.println(secondLine.toString()); // print log in new file
                    secondLine = new Log(scanner2.nextLine()); //get next log
                    file2Lines++; //increment counter
                }
                else if(firstLine.compareTo(secondLine)==-1){ //file 1 log < file 2 log
                    printWriter.println(firstLine.toString()); // print log in new file
                    firstLine = new Log(scanner1.nextLine()); //get next log
                    file1Lines++; //increment counter
                }
                else{
                    //both file log are equal
                    printWriter.println(firstLine.toString()); //print both logs
                    printWriter.println(secondLine.toString());
                    firstLine = new Log(scanner1.nextLine()); //get next logs in both files
                    secondLine = new Log((scanner2.nextLine()));
                    file1Lines++; //increment both counters
                    file2Lines++;
                }
            }

            // final if statement for final line of one of the files
            if(firstLine.compareTo(secondLine)==1){ //file 2 log < file 1 log
                printWriter.println(secondLine.toString()); //print log
                file1Lines++; //increment counter
            }
            else if(firstLine.compareTo(secondLine)==-1){ //file 1 log < file 1 log
                printWriter.println(firstLine.toString()); //print log
                file2Lines++; //increment counter
            }
            else {
                //in case final log of one of the files is equal to the other log
                printWriter.println(firstLine.toString()); //print both logs
                printWriter.println(secondLine.toString());
                file1Lines++; //increment both counters
                file2Lines++;
                if (scanner1.hasNextLine()) //get next log of appropriate file
                    firstLine = new Log(scanner1.nextLine());
                else
                    secondLine = new Log(scanner2.nextLine());
            }

            if(scanner1.hasNextLine()){ //if file 1 is not empty
                file1Lines += printRemainingLine(in1, out);
            }

            if(scanner2.hasNextLine()){ //if file 2 is not empty
                file2Lines += printRemainingLine(in2, out);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error: {0} (File:  Line: ", e);
        }

        scanner1.close(); //close IO variables
        scanner2.close();
        printWriter.close();

    }

    public static int printRemainingLine(String file, String out) throws IOException {
       //IO variables
        FileReader reader = new FileReader(file);
        Scanner scanner = new Scanner(reader);
        FileWriter writer = new FileWriter(out);
        PrintWriter print = new PrintWriter(writer);

        //other variables
        Log line = new Log(scanner.nextLine());
        int lineCounter = 0;

        while(scanner.hasNextLine()) { //print all logs
            print.println(line.toString());
            line = new Log(scanner.nextLine());
            lineCounter++;
        }

        print.println(line.toString()); //print final log
        scanner.close();
        print.close();
        return lineCounter;
    }
}