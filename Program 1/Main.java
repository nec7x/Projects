/*Nehemya Gugsa
CSC112 Fall 2021
Programming Assignment 1
September 29, 2021 
This program reads a file, divides the file's contents, and outputs them in alphabetized groups. 
*/


import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        
        FileInputStream fileByteStream = null;

        Scanner inputFile = null;
        
        fileByteStream = new FileInputStream("input.txt");  // directory here
        
        inputFile = new Scanner(fileByteStream);

        int ARSIZE = 0;

        // count total values in input file
        while (inputFile.hasNext()) {
            inputFile.next();
            ARSIZE++;
        }

        System.out.println();

        ARSIZE = ARSIZE / 9;    // each student has nine values associated with them (first, middle last names; ID; 5 grades)

        inputFile.close();

        // create new file input stream with same input file directory as the first file input stream has been read-through
        FileInputStream file2ByteStream = null;
        Scanner in2File = null;
        
        file2ByteStream = new FileInputStream("input.txt");
        in2File = new Scanner(file2ByteStream);

        String[][] nameArr = new String[ARSIZE][3];
        String[] studentID = new String[ARSIZE];
        int[][] grades = new int[5][ARSIZE];

        for (int i = 0; i < ARSIZE; ++i) {
            for (int j = 0; j < 3; ++j) {
                nameArr[i][j] = in2File.next();             // 3-column name array: first name in one column, middle in another, and surname in another
            }

            in2File.nextLine();

            studentID[i] = in2File.nextLine();

            for (int j = 0; j < 5; ++j) {
                if (in2File.hasNextInt()) {
                    grades[j][i]  = in2File.nextInt();
                }
                else {
                    in2File.nextLine();
                }
            } 

            in2File.nextLine();
        }



        in2File.close();

        double[] averageGrade = average(grades, ARSIZE);

        operations(nameArr, ARSIZE, averageGrade, studentID);
    }

    // this method calculates the average grade for each student
    public static double[] average(int[][] grades, int ARSIZE) {

        double[] averageGrade = new double[ARSIZE];

        for (int i = 0; i < ARSIZE; ++i) {
            int val = 0;
            for (int j = 0; j < 5; ++j) {
                val = val + grades[j][i];
            } averageGrade[i] = (double)(val) / 5;
        }

        return averageGrade;

    }

    // this method conducts a series of operations, such as alphabetizing the students in the input file and rearranging their grades and IDs accordingly
    // it then divides them into groups and prints out these students by group, as well as the group's average grade and a randomly selected group leader
    public static void operations(String[][] names, int ARSIZE, double[] averageGrade, String[] ID) throws IOException {

        Random rand = new Random();
        
        String[] abName = new String[ARSIZE];
        String[] tempString = new String[3];
        double tempGrade = 0.0;
        String tempID = null;

        for (int i = 0; i < ARSIZE; i++) {
            for (int j = 1; j < (ARSIZE - i); j++) {
                if (Integer.valueOf(names[j-1][2].charAt(0)) > Integer.valueOf(names[j][2].charAt(0))) {     // bubblesorting by surname's first character integer value
                    tempString = names[j-1];
                    names[j-1] = names[j];
                    names[j] = tempString;

                    tempID = ID[j-1];
                    ID[j-1] = ID[j];
                    ID[j] = tempID;

                    tempGrade = averageGrade[j-1];
                    averageGrade[j-1] = averageGrade[j];
                    averageGrade[j] = tempGrade;

                }
            }
        }

        int numGroups = (ARSIZE / 4);

        if ((ARSIZE % 4) > 1) {
            numGroups = numGroups + 1;
        }

        int randomNum = rand.nextInt(4);

        double group1Avg = 0;
        String[][] group1Names = new String[4][3];

        double group2Avg = 0;
        String[][] group2Names = new String[4][3];

        double group3Avg = 0;
        String[][] group3Names = new String[4][3];

        double group4Avg = 0;
        String[][] group4Names = new String[4][3];

        double group5Avg = 0;
        String[][] group5Names = new String[4][3];

        PrintStream out = new PrintStream(new FileOutputStream("output.txt"));
        System.setOut(out);

        for (int i = 0; i < ARSIZE; ++i) {
            System.out.println(names[i][2] + ", " + names[i][0] + " " + names[i][1]);
            System.out.println(ID[i]);
            System.out.println(averageGrade[i]);
        }

        System.out.println();

        int i = 0;
        while (i < ARSIZE) {

            // dividing into groups of 4

            System.out.println("Group 1:");
            while (i < 4) {
                int j = 0;
                group1Names[j] = names[i];
                group1Avg = group1Avg + averageGrade[i];
                System.out.print(group1Names[j][2] + ", " + group1Names[j][0] + " " + group1Names[j][1]);
                System.out.println();
                if (i == ARSIZE-1) {        // this if-statement conducts operations for the last student, printing the averages for the group in case the loop doesn't finish and exiting
                    randomNum = rand.nextInt(ARSIZE % 4);
                    group1Avg = group1Avg / (ARSIZE % 4);
                    System.out.println("Group leader: " + names[randomNum][2] + ", " + names[randomNum][0] + " " + names[randomNum][1]);
                    System.out.println("Group average: " + group1Avg);
                    System.out.println("");
                    System.exit(0);
                }
                ++i;
                ++j;
            }
            
            // the next four lines are the contents of the if-statement that occurs in the while loop; this covers the cases where the loop does finish and prints group values before moving on to next group
            group1Avg = group1Avg / 4;

            System.out.println("Group leader: " + names[randomNum][2] + ", " + names[randomNum][0] + " " + names[randomNum][1]);
            System.out.println("Group average: " + group1Avg);
            System.out.println("");

            System.out.println("Group 2:");
            while (i < 8) {
                int j = 0;
                group2Names[j] = names[i];
                group2Avg = group2Avg + averageGrade[i];
                System.out.print(group2Names[j][2] + ", " + group2Names[j][0] + " " + group2Names[j][1]);
                System.out.println();
                if (i == ARSIZE-1) {
                    randomNum = rand.nextInt(ARSIZE % 4);
                    group2Avg = group2Avg / (ARSIZE % 4);
                    System.out.println("Group leader: " + names[randomNum+4][2] + ", " + names[randomNum+4][0] + " " + names[randomNum+4][1]);
                    System.out.println("Group average: " + group2Avg);
                    System.out.println("");
                    System.exit(0);
                }
                ++i;
                ++j;
            }

            group2Avg = group2Avg / 4;

            System.out.println("Group leader: " + names[randomNum+4][2] + ", " + names[randomNum+4][0] + " " + names[randomNum+4][1]);
            System.out.println("Group average: " + group2Avg);
            System.out.println("");

            System.out.println("Group 3:");
            while (i < 12) {
                int j = 0;
                group3Names[j] = names[i];
                group3Avg = group3Avg + averageGrade[i];
                System.out.print(group3Names[j][2] + ", " + group3Names[j][0] + " " + group3Names[j][1]);
                System.out.println();
                if (i == ARSIZE-1) {
                    randomNum = rand.nextInt(ARSIZE % 4);
                    group3Avg = group3Avg / (ARSIZE % 4);
                    System.out.println("Group leader: " + names[randomNum+8][2] + ", " + names[randomNum+8][0] + " " + names[randomNum+8][1]);
                    System.out.println("Group average: " + group3Avg);
                    System.out.println("");
                    System.exit(0);
                }
                ++i;
                ++j;
            }

            group3Avg = group3Avg / 4;

            System.out.println("Group leader: " + names[randomNum+8][2] + ", " + names[randomNum+8][0] + " " + names[randomNum+8][1]);
            System.out.println("Group average: " + group3Avg);
            System.out.println("");

            System.out.println("Group 4:");
            while (i < 16) {
                int j = 0;
                group4Names[j] = names[i];
                group4Avg = group4Avg + averageGrade[i];
                System.out.print(group4Names[j][2] + ", " + group4Names[j][0] + " " + group4Names[j][1]);
                System.out.println();
                if (i == ARSIZE-1) {
                    randomNum = rand.nextInt(ARSIZE % 4);
                    group4Avg = group4Avg / (ARSIZE % 4);
                    System.out.println("Group leader: " + names[randomNum+12][2] + ", " + names[randomNum+12][0] + " " + names[randomNum+16][1]);
                    System.out.println("Group average: " + group4Avg);
                    System.out.println("");
                    System.exit(0);
                }
                ++i;
                ++j;
            }

            group4Avg = group4Avg / 4;

            System.out.println("Group leader: " + names[randomNum+12][2] + ", " + names[randomNum+12][0] + " " + names[randomNum+12][1]);
            System.out.println("Group average: " + group4Avg);
            System.out.println("");

            System.out.println("Group 5:");
            while (i < 20) {
                int j = 0;
                group4Names[j] = names[i];
                group5Avg = group5Avg + averageGrade[i];
                System.out.print(group5Names[j][2] + ", " + group5Names[j][0] + " " + group5Names[j][1]);
                System.out.println();
                if (i == ARSIZE-1) {
                    randomNum = rand.nextInt(ARSIZE % 4);
                    group5Avg = group5Avg / (ARSIZE % 4);
                    System.out.println("Group leader: " + names[randomNum+16][2] + ", " + names[randomNum+16][0] + " " + names[randomNum+16][1]);
                    System.out.println("Group average: " + group5Avg);
                    System.out.println("");
                    System.exit(0);
                }
                ++i;
                ++j;
            }

            group5Avg = group5Avg / 4;

            System.out.println("Group leader: " + names[randomNum+16][2] + ", " + names[randomNum+16][0] + " " + names[randomNum+16][1]);
            System.out.println("Group average: " + group5Avg);
            System.out.println("");

            
        }
    }
}