package pl.coderslab;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;


public class TaskManager {

    public static void main(String[] args) {

        System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "TASKS MANAGER\n=============" + ConsoleColors.RESET);

        String[] optionsMenu = new String[]{"add", "remove", "list", "exit"}; //Makes Options Menu array

        Scanner scan = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            System.out.println("OPTIONS MENU:");
            for (String option : optionsMenu) {     // display Menu from array
                System.out.print(option + "    ");
            }
            System.out.println();

            input = scan.nextLine();     // inputs user command

            switch (input) {
                case "exit":
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Task Manager ENDS" + "\n" + "=================" + ConsoleColors.RESET);
                    break;
                case "list":
                    System.out.println("LIST OF TASKS:");
                    readTasks(1); // list tasks
                    break;
                case "add":
                    System.out.println("ADD A TASK:");
                    addTask();
                    break;
                case "remove":
                    System.out.println("REMOVE A TASK:");
                    removeTask();
                    break;
                default:
                    System.out.println("   !unknown command");
                    break;
            }
        }
    }

    // === LOAD ARRAY (if argument 0) / AND LIST TASKS (if 1) ===
    public static String[][] readTasks(int listing) {     //loads tasks into array from file and return array. If listing = 1: also print tasks

        int tasksNumber = 0;
        File file = new File("tasks.csv");
        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNextLine()) {
                tasksNumber++;              //finds number of lines (tasks) in file
                scan.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File problem!");
        }

        String[][] tasks = new String[tasksNumber][];   //makes array for tasks based on no. of tasks

        try (Scanner scan = new Scanner(file)) {
            int i = 0;
            while (scan.hasNextLine() && i < tasksNumber) {
                String taskLine = scan.nextLine();
                String[] row = taskLine.split(", ");
                tasks[i] = row; // loads tasks to array
                i++;
            }
        }  catch (FileNotFoundException ex) {
            System.out.println("File problem!");
        }

        if (listing == 1) {                          // list tasks
            for (int k = 0; k < tasks.length; k++) {
                System.out.print(k + " : ");
                for (int l = 0; l < tasks[k].length; l++) {
                    System.out.print(tasks[k][l]);
                    if (l < tasks[k].length - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        }

        return tasks;
    }

//  === ADD TASK ===

    public static void addTask() {

        String[][] tasks = Arrays.copyOf(readTasks(0), readTasks(0).length + 1);
        tasks[tasks.length - 1] = new String[readTasks(0)[0].length];
                                // it was needed to initialize the last row! Otherwise would throw NullPointer

        Scanner scan = new Scanner(System.in);              //user inputs new Task data
        System.out.println(ConsoleColors.BLUE + "Enter task description: ");
        tasks[tasks.length - 1][0] = scan.nextLine();
        System.out.println("Enter due date: ");
        tasks[tasks.length - 1][1] = scan.nextLine();

        while (true) {
            System.out.println(ConsoleColors.BLUE + "Is this task important? (true / false) " + ConsoleColors.RESET);
            tasks[tasks.length - 1][2] = scan.nextLine();
            if (tasks[tasks.length - 1][2].equals("true") || tasks[tasks.length - 1][2].equals("false")) {
                break;
            } else {
                System.out.println(ConsoleColors.RED + "\n   !input only true or false\n" + ConsoleColors.RESET);
            }
        }

        writeToFile(tasks);

    }

    // === REMOVE TASK ===

    public static void removeTask() {

        int removeLnNo = 0;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println(ConsoleColors.BLUE + "Select task number to remove or type exit to cancel:" + ConsoleColors.RESET);
            readTasks(1);          //list tasks to select one for removal
            String removeLnStr = scan.nextLine();
            if (removeLnStr.equals("exit")) {
                break;
            }
            if (!NumberUtils.isParsable(removeLnStr)) {
                System.out.println(ConsoleColors.RED + "\n   !enter a task number or 'exit' only\n");
            } else if (Integer.parseInt(removeLnStr) >= readTasks(0).length | Integer.parseInt(removeLnStr) < 0) {
                System.out.println(ConsoleColors.RED + "\n   !enter a number within range\n");
            } else {
                removeLnNo = Integer.parseInt(removeLnStr);
                break;
            }
        }

        String[][] tasks = readTasks(0);    // makes array fed with data from readTasks method
        String[][] tasksRemoved = new String[tasks.length - 1][]; // makes temporary array 1 row less

        int j = 0;
        for (int i = 0; i < tasks.length; i++) {
            if (i == removeLnNo) {
                // ignores a row to be removed
            } else {
                tasksRemoved[j] = tasks[i];
                j++;
            }
        }

        writeToFile(tasksRemoved);

    }

// === WRITE ARRAY TO FILE ===

    public static void writeToFile(String[][] tasks) {

        File file = new File("tasks.csv");
        try (FileWriter writer = new FileWriter(file, false)) {

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    writer.append(tasks[i][j]);
                    if (j < tasks[i].length - 1) {
                        writer.append(", ");
                    }
                }
                writer.append("\n");
            }
        } catch (IOException ex) {
            System.out.println("File problem!");
        }
    }
}