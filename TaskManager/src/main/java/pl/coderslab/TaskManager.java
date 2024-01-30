package pl.coderslab;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.*;
import java.util.Arrays;
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
                //listTasks();
                readTasks(1);       // list Tasks, remove none
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

    // === LOAD ARRAY (if argument 0) / LIST TASKS (if 1) ===
    public static String[][] readTasks(int listing) {     //loads tasks into array and return array. If listing = 1: also print tasks

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

                    if (listing == 1) {
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
                        //System.out.println(i + 1 + " : " + Arrays.toString(tasks[i])); // list array of tasks //DOROBIĆ ŁADNE WYŚWIETLANIE
                    }
                }
            }
        }  catch (FileNotFoundException ex) {
            System.out.println("Problem z plikiem.");
        }
    return tasks;
    }

//  === ADD TASK ===

    public static void addTask() {

        String[][] tempTasks = readTasks(0);
        String[][] tasks = Arrays.copyOf(tempTasks, tempTasks.length + 1);
        tasks[tasks.length - 1] = new String[tempTasks[0].length]; // it was needed to initialize the last row! Otherwise would not work...

//        tasks[tasks.length - 1][0] = ""; tasks[tasks.length - 1][1] = ""; tasks[tasks.length - 1][2] = "";

        Scanner scan = new Scanner(System.in);              //user inputs new Task data
        System.out.println("Enter task description: ");
        tasks[tasks.length - 1][0] = scan.nextLine();
        System.out.println("Enter due date: ");
        tasks[tasks.length - 1][1] = scan.nextLine();

        while (true) {
            System.out.println("Is this task important? (true / false) ");
            tasks[tasks.length - 1][2] = scan.nextLine();
            if (tasks[tasks.length - 1][2].equals("true") || tasks[tasks.length - 1][2].equals("false")) {
                break;
            } else {
                System.out.println("   !input only true or false");
            }
        }

        writeToFile(tasks);

    }


    // === REMOVE TASK ===

    public static void removeTask() {

        int removeLnNo = 0;
        Scanner scan = new Scanner(System.in);              //user inputs new Task data
        while (true) {
            System.out.println("Select task number to remove or type exit to cancel:");
            readTasks(1);          //list tasks
            String removeLnStr = scan.nextLine();
            if (!NumberUtils.isParsable(removeLnStr) | removeLnStr.equals("exit")) {
                System.out.println("   !enter a task number or exit only");
            } else if (removeLnStr.equals("exit")) {
                break;
            } else {
                removeLnNo = Integer.parseInt(removeLnStr) - 1;
                readTasks(0);
                break;
            }
        }

        String[][] tasks = readTasks(0); // makes array fed with data from readTasks method
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

/*        for (String[] row : tasksRemoved) {
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println();  // Dodaj nową linię po każdym rzędzie
        }
*/
        writeToFile(tasksRemoved);

// === WRITE ARRAY TO FILE ===

    }
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
            System.out.println("Problem z plikiem.");
        }
    }
}

