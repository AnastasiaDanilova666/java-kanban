package task;

import java.util.ArrayList;
import java.util.List;


public class Epic extends Task {

    private final List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String definition) {
        super(name, definition, Status.NEW);
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);

    }

    @Override
    public String toString() {
        return "task.Epic{" +
                "id=" + getTaskId() +
                ", name='" + getName() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks.size() +
                '}';
    }
}

