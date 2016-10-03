package service;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */
public interface TaskService {
    void createTask(String taskName, String taskExpirationDate, String taskPriority);

    void readTaskById(String taskIdStr);

    void makeComplete(String id);

    void readAllTasks();

    void readAllCompletedTasks();
}
