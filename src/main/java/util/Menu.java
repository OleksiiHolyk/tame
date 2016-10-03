package util;

import service.TaskService;
import service.TaskServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Oleksii on 03.10.2016.
 * oleksii.holyk@outlook.com
 */
public class Menu {
    public static void mainMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int operationNumber = 0;
        while (true) {
            System.out.println("\nChoose operation: \n"
                    + "1. Add Task \n"
                    + "2. Print a list of tasks \n"
                    + "3. Exit\n");
            String str1 = br.readLine().toString();
            operationNumber = Integer.parseInt(str1);
            switch (operationNumber) {
                case 1: {
                    System.out.println("Enter task name");
                    String taskName = br.readLine();

                    System.out.println("Enter task time for complete. Format: 02.10.2016 00:00");
                    String taskExpirationDate = br.readLine();

                    System.out.println("Enter task priority");
                    System.out.println("1 - Low, 2 - Medium, 3 - High");
                    String taskPriority = br.readLine();

                    TaskService taskService = new TaskServiceImpl();
                    taskService.createTask(taskName, taskExpirationDate, taskPriority);
                    break;
                }
                case 2: {

                    subMenu();
                    break;
                }
                case 3: {
                    System.out.println("Thank you!");
                    System.exit(0);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public static void subMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nChoose operation: \n"
                + "1. Print a list of tasks \n"
                + "2. Make task comleted\n"
                + "3. Print all completed tasks\n"
                + "4. Back to main menu");
        String str1 = br.readLine().toString();
        byte operationNumber = Byte.valueOf(str1);
        switch (operationNumber) {
            case 1: {
                TaskService taskService = new TaskServiceImpl();
                taskService.readAllTasks();
                smallMenu();
                break;
            }
            case 2: {
                TaskService taskService = new TaskServiceImpl();
                System.out.println("Enter task ID");
                String taskId = br.readLine();
                taskService.makeComplete(taskId);
                subMenu();
                break;
            }
            case 3: {
                TaskService taskService = new TaskServiceImpl();
                taskService.readAllCompletedTasks();
                subMenu();
                break;
            }
            case 4: {
                mainMenu();
            }
        }
    }

    public static void smallMenu() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nChoose operation: \n"
                + "1. You can mark some task (like expired) as complete, just input '1' \n"
                + "2. Back to main menu");
        String str1 = br.readLine().toString();
        byte operationNumber = Byte.valueOf(str1);
        switch (operationNumber) {
            case 1: {
                TaskService taskService = new TaskServiceImpl();
                System.out.println("Enter task ID");
                String taskId = br.readLine();
                taskService.makeComplete(taskId);
                smallMenu();
                break;
            }
            case 2: {
                subMenu();
                break;
            }
        }
    }
}
