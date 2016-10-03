package dao;

import model.Task;

import java.util.List;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */

public interface TaskRepository {

    void insertTask(Task task, String tableName);

    Task selectTaskById(long taskId);

    List<Task> selectAllTasks(String tableName);

    void updateTask(long id, Task task);

    void makeTaskCompleted (long id);

    void deleteTask (long id, String tableName);
}
