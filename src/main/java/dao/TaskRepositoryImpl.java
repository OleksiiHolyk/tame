package dao;

import model.Priority;
import model.Status;
import model.Task;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */

public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public void insertTask(Task task, String tableName) {
        DbConnection dbConnection = new DbConnection();
        try {
            PreparedStatement preparedStatement = dbConnection.setConnection()
                    .prepareStatement("insert into " + tableName + " (taskName, taskExpirationDate, taskPriority, taskStatus) values(?,?,?,?)");
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setTimestamp(2, new Timestamp(task.getTaskExpirationDate()));
            preparedStatement.setString(3, String.valueOf(task.getTaskPriority()));
            preparedStatement.setString(4, String.valueOf(task.getTaskStatus()));
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    task.setTaskId(generatedKeys.getLong(1));
//                    System.out.println("Task '" + task.getTaskName() + "' is added successfully");
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task selectTaskById(long taskId) {
        DbConnection dbConnection = new DbConnection();
        Task task = new Task();

        try {
            PreparedStatement preparedStatement = dbConnection.setConnection().prepareStatement("select * from active where taskId=?");
            preparedStatement.setLong(1, taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task.setTaskId(resultSet.getLong(1));
                task.setTaskName(resultSet.getString(2));
                task.setTaskExpirationDate(resultSet.getTimestamp(3).getTime());
                Priority priority = Priority.valueOf(resultSet.getString(4));
                task.setTaskPriority(priority);
                Status status = Status.valueOf(resultSet.getString(5));
                task.setTaskStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public List<Task> selectAllTasks(String tableName) {
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        DbConnection dbConnection = new DbConnection();
        try {
            Statement st = dbConnection.setConnection().createStatement();
            ResultSet resultSet = st.executeQuery("select * from " + tableName);

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getLong(1));
                task.setTaskName(resultSet.getString(2));
                task.setTaskExpirationDate(resultSet.getTimestamp(3).getTime());
                Priority priority = Priority.valueOf(resultSet.getString(4));
                task.setTaskPriority(priority);
                Status status = Status.valueOf(resultSet.getString(5));
                task.setTaskStatus(status);
                taskArrayList.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskArrayList;
    }

    @Override
    public void updateTask(long id, Task task) {
        DbConnection dbConnection = new DbConnection();
        try {
            PreparedStatement preparedStatement = dbConnection.setConnection().prepareStatement(
                    "update active set taskName=?, taskExpirationDate=?, taskPriority=?,taskStatus=? where taskId=?"
            );
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setTimestamp(2, new Timestamp(task.getTaskExpirationDate()));
            preparedStatement.setString(3, String.valueOf(task.getTaskPriority()));
            preparedStatement.setString(4, String.valueOf(task.getTaskStatus()));
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void makeTaskCompleted(long id) {
        Task task = selectTaskById(id);
        Task completedTask = new Task();
        completedTask.setTaskName(task.getTaskName());
        completedTask.setTaskExpirationDate(task.getTaskExpirationDate());
        completedTask.setTaskPriority(task.getTaskPriority());
        completedTask.setTaskStatus(Status.STATUS_COMPLETED);
        updateTask(id, completedTask);
        task = selectTaskById(id);
        insertTask(task, "completed");
        deleteTask(id, "active");
    }

    @Override
    public void deleteTask(long id, String tableName) {
        DbConnection dbConnection = new DbConnection();
        try {
            PreparedStatement preparedStatement = dbConnection.setConnection().prepareStatement("delete from "+ tableName +" where taskId=?");
            preparedStatement.setLong(1, id);
            int i = preparedStatement.executeUpdate();
            if (i != 0) {
                System.out.println("moved from 'active' to 'completed'");
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
