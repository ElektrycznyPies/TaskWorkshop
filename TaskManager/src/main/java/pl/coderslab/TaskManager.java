package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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

    //private static String [][] tasks;
    public static void main(String[] args) {


        System.out.println("TASKS MANAGER\n=============");

        String[] optionsMenu = new String[]{"add", "remove", "list", "exit"}; //Makes Options Menu array

        Scanner scan = new Scanner(System.in);

        while (true) {                                          //unending loop, exits with break;
            System.out.println("OPTIONS MENU:");
            for (int i = 0; i < optionsMenu.length; i++) {      //displays Options Menu from array
                System.out.print(optionsMenu[i] + "    ");
            }
            System.out.println();
            String input = scan.nextLine();

            if (input.equals("exit")) {
                System.out.println("Task Manager ENDS" + "\n" + "=================");
                break;
            } else if (input.equals("list")) {
                System.out.println("LIST OF TASKS:");
                readTasks(1, -1);       // list Tasks, remove none
            } else if (input.equals("add")) {
                System.out.println("ADD A TASK:");
                addTask();
            } else if (input.equals("remove")) {
                System.out.println("REMOVE A TASK:");
                removeTask();
            } else {
                System.out.println("   !unknown command");
            }
        }
    }

    public static void readTasks(int listing, int removing) {

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

                    if (listing == 1) {                 //if "list" was selected from Menu, displays tasks
                        System.out.println(i + 1 + " : " + Arrays.toString(tasks[i]));
                    }
                }
                if ((removing > -1) && (removing < tasks.length)) {        //if "remove" was selected, deletes specified line
                    String[][] tempArr = new String[tasks.length-1][];
                    for (int i = 0; i < tasks.length; i++) {
                        if (i == removing) {
                                        //do nothing for the row to be deleted
                        } else {
                            tempArr[i] = tasks[i];
                            tasks = Arrays.copyOf(tempArr, tasks.length-1);

                            try (FileWriter writer = new FileWriter(file, false)) {
                                for (int j = 0; j< tasks[i].length; j++) {
                                    writer.append(tasks[i][j]).append(", "); // purges file and fills with new array
                                    if (j == tasks[i].length) { writer.append("\n"); }
                                }
                            } catch (IOException ex) {
                                System.out.println("Problem z plikiem.");
                            }
                        }
                    }
                    break;
                } else {
                    break;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem z plikiem.");
        }
    }

    public static void addTask() {

      /*  int listing = 0;
        readTasks(listing); */

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
                System.out.println("   !input only true or false");
            }
        }

        File file = new File("tasks.csv");      //new Task is written to csv file
        try (FileWriter writer = new FileWriter(file, true)) {
            System.out.println(taskDescription + "  " + taskImp);
            writer.append(taskDescription).append(", ").append(taskDate).append(", ").append(taskImp).append("\n"); //adds input as one row (one new task)
        } catch (IOException ex) {
                System.out.println("Problem z plikiem.");
            }
    }

    public static void removeTask() {

        int removeLnNo = 0;

        Scanner scan = new Scanner(System.in);              //user inputs new Task data
        while (true) {
            System.out.println("Select task number to remove or type exit to cancel:");
            readTasks(1, -1);           //list tasks, remove none
            String removeLnStr = scan.nextLine();
            if (!NumberUtils.isParsable(removeLnStr) || removeLnStr.equals("exit")) {
                System.out.println("   !enter a task number or exit only");
            } else if (removeLnStr.equals("exit")) {
                break;
            } else {
                removeLnNo = Integer.parseInt(removeLnStr) - 1;
                readTasks(0, removeLnNo);
                break;


//                tasks[][] = ArrayUtils.remove(tasks, removeLnNo - 1);

            }
        }
    }
}

