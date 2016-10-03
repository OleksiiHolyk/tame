package service;

import dao.TaskRepository;
import dao.TaskRepositoryImpl;
import model.Priority;
import model.Status;
import model.Task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */
public class TaskServiceImpl implements TaskService {

    @Override
    public void createTask(String taskName, String taskExpirationDate, String taskPriority) {
        Task task = new Task();
        try {
            task.setTaskName(taskName);
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = new Date();
            date = dateFormat.parse(taskExpirationDate);
            long time = date.getTime();
            task.setTaskExpirationDate(time);
            Byte tempVariable = Byte.valueOf(taskPriority);
            switch (tempVariable) {
                case 1: {
                    task.setTaskPriority(Priority.PRIORITY_LOW);
                    break;
                }
                case 2: {
                    task.setTaskPriority(Priority.PRIORITY_MEDIUM);
                    break;
                }
                case 3: {
                    task.setTaskPriority(Priority.PRIORITY_HIGH);
                    break;
                }
            }
            task.setTaskStatus(Status.STATUS_ACTIVE);
            TaskRepository taskRepository = new TaskRepositoryImpl();
            taskRepository.insertTask(task, "active");

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readTaskById(String taskIdStr) {
        long taskId = Long.valueOf(taskIdStr);
        TaskRepository taskRepository = new TaskRepositoryImpl();
        Task task = taskRepository.selectTaskById(taskId);
        Date date = new Date(task.getTaskExpirationDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String dateText = simpleDateFormat.format(date);

        System.out.println("id=" + task.getTaskId() + ", " +
                "name=" + task.getTaskName() + ", " +
                "expiration date=" + dateText + ", " +
                task.getTaskPriority() + ", " +
                task.getTaskStatus());
    }

    @Override
    public void makeComplete(String taskIdStr) {
        long taskId = Long.valueOf(taskIdStr);
        TaskRepository taskRepository = new TaskRepositoryImpl();
        taskRepository.makeTaskCompleted(taskId);
    }

    @Override
    public void readAllTasks() {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        List<Task> arrayList = taskRepository.selectAllTasks("active");
        long createdDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

        for (int i = 0; i < arrayList.size(); i++) {
            Date date = new Date(arrayList.get(i).getTaskExpirationDate());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String dateText = simpleDateFormat.format(date);

            if (createdDate>arrayList.get(i).getTaskExpirationDate()){
                arrayList.get(i).setTaskStatus(Status.STATUS_EXPIRED);
            }
            System.out.println("id=" + arrayList.get(i).getTaskId() + ", " +
                    "name=" + arrayList.get(i).getTaskName() + ", " +
                    "expiration date=" + dateText + ", " +
                    arrayList.get(i).getTaskPriority() + ", " +
                    arrayList.get(i).getTaskStatus());
        }
    }

    @Override
    public void readAllCompletedTasks() {
        TaskRepository taskRepository = new TaskRepositoryImpl();
        List<Task> arrayList = taskRepository.selectAllTasks("completed");
        for (int i = 0; i < arrayList.size(); i++) {
            long expirationDate = arrayList.get(i).getTaskExpirationDate();
            Date date = new Date(expirationDate);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String dateText = simpleDateFormat.format(date);

            System.out.println("id=" + arrayList.get(i).getTaskId() + ", " +
                    "name=" + arrayList.get(i).getTaskName() + ", " +
                    "expiration date=" + dateText + ", " +
                    arrayList.get(i).getTaskPriority() + ", " +
                    arrayList.get(i).getTaskStatus());
        }
    }
}
