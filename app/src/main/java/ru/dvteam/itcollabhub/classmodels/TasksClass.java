package ru.dvteam.itcollabhub.classmodels;

public class TasksClass {
    private String taskName;
    private String taskDescription;
    private String taskId;
    private String taskComplete;
    private int taskWorks;
    private String date;

    public TasksClass(String taskName, String taskDescription, String taskId, String taskComplete, int taskWorks) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskComplete = taskComplete;
        this.taskWorks = taskWorks;
    }

    public TasksClass(String taskName, String taskDescription, String taskId, String taskComplete, int taskWorks, String date) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskComplete = taskComplete;
        this.taskWorks = taskWorks;
        this.date = date;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskComplete() {
        return taskComplete;
    }

    public int getTaskWorks() {
        return taskWorks;
    }

    public String getDate() {
        return date;
    }
}
