package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {

        int listing = 0;
        readTasks(listing);

        String[] optionsMenu = new String[]{"add", "remove", "list", "exit"}; //Makes Options Menu array

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
                listing = 1; readTasks(listing);
            } else if (input.equals("add")) {
                addTask();
            } else if (input.equals("remove")) {
                removeTask();
            } else {
                System.out.println("Unknown command, please select a valid option.");
            }
        }
    }

    public static void readTasks(int listing) {

        int tasksNumber = 0;

        File file = new File("tasks.csv");
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                tasksNumber++;              //finds number of lines (tasks) in file
                scan.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem z plikiem.");
        }

        String[][] tasks = new String[tasksNumber][];   //makes array for tasks based on no. of tasks

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                for (int i = 0; i < tasksNumber; i++) {
                    String taskLine = scan.nextLine();
                    String[] row = taskLine.split(", ");
                    tasks[i] = row;                     //loads tasks to array
                    if (listing == 1) { System.out.println(i + 1 + " : " + Arrays.toString(tasks[i])); }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem z plikiem.");
        }
    }

    public static void addTask() {

        int listing = 0;
        readTasks(listing);

        Scanner scan = new Scanner(System.in);              //user inputs new Task data
        System.out.println("Enter task description: ");
        String taskDescription = scan.nextLine();
        System.out.println("Enter due date: ");
        String taskDate = scan.nextLine();
        String taskImp = "";
        while (true) {
            System.out.println("Is this task important? (true / false) ");
            taskImp = scan.nextLine();
            if (taskImp.equals("true") || taskImp.equals("false")) {
                break;
            } else {
                System.out.println("Input only true or false");
            }
        }

        File file = new File("tasks.csv");      //new Task is written to csv file
        try (FileWriter writer = new FileWriter(file, true)) {
            System.out.println(taskDescription + "  " + taskImp);
            writer.append(taskDescription).append(", ").append(taskDate).append(", ").append(taskImp).append("\n"); //adds input as one row (one new task)
        } catch (IOException ex) {
                System.out.println("problem z plikiem");
            }
    }

    public static void removeTask() {
    }

    public static void listTasks() {

    }
}

