package task;

import manager.Status;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int taskId;
    private Status status;

    public Task(String name, String definition, Status status) {
        this.name = name;
        this.description = definition;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Task otherTask = (Task) obj;
        return ( taskId== otherTask.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId );
    }


    @Override
    public String toString() {
        return "task.Task{" +
                "id=" + taskId +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }

}
