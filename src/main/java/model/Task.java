package model;

/**
 * Created by Oleksii on 02.10.2016.
 * oleksii.holyk@outlook.com
 */

public class Task {
    private Long taskId;
    private String taskName;
    private Long taskExpirationDate;
    private Priority taskPriority;
    private Status taskStatus;

    public Task() {
    }

    public Task(String taskName, Long taskExpirationDate, Priority taskPriority) {
        this.taskName = taskName;
        this.taskExpirationDate = taskExpirationDate;
        this.taskPriority = taskPriority;
    }

    public Task(Long taskId, String taskName, Long taskExpirationDate, Priority taskPriority, Status taskStatus) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskExpirationDate = taskExpirationDate;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Long getTaskExpirationDate() {
        return taskExpirationDate;
    }

    public void setTaskExpirationDate(Long taskExpirationDate) {
        this.taskExpirationDate = taskExpirationDate;
    }

    public Priority getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Priority taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskExpirationDate=" + taskExpirationDate +
                ", taskPriority=" + taskPriority +
                ", taskStatus=" + taskStatus;
    }
}


