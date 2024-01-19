package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {

        String[] optionsMenu = new String[] {"add", "remove", "list", "exit"}; //Makes Options Menu array

        Scanner scan = new Scanner(System.in);
        System.out.println("Please select an option: ");

        while (true) {                                          //unending loop, exits with break;

            for (int i = 0; i < optionsMenu.length; i++) {      //displays Options Menu from array
                System.out.println(optionsMenu[i]);
            }

            String input = scan.nextLine();

            if (input.equals("exit")) {
                break;
            } else if (input.equals("list")) {
                readTasks();
            } else {
                System.out.println("Unknown command, please select a valid option.");
            }
        }
    }

    public static void readTasks() {

        int tasksNumber = 0;

        File file = new File("tasks.csv");
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                tasksNumber++;              //finds number of lines (tasks) in file
                scan.nextLine();
            }
        }   catch (FileNotFoundException ex) {
                System.out.println("Problem z plikiem.");
            }

        String[][] tasks = new String[tasksNumber][];   //makes array for tasks based on no. of tasks

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                for (int i = 0; i < tasksNumber; i++) {
                    String taskLine = scan.nextLine();
                    String[] row = taskLine.split(", ");
                    tasks[i] = row;                     //loads tasks to array
                    System.out.println(i+1 + " : " + Arrays.toString(tasks[i]));
                }
            }
        }   catch (FileNotFoundException ex) {
            System.out.println("Problem z plikiem.");
        }
    }
}


