package task;

import manager.Status;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Subtask> subtasks = new ArrayList<>();

    public Epic(String name, String definition, Status status) {
        super(name, definition, status);
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


    public static class Subtask extends Task {
        private int epicId;

        public Subtask(String name, String definition, Status status, int epicId) {
            super(name, definition, status);
            this.epicId = epicId;
        }

        public int getEpicId() {
            return epicId;
        }

        public void setEpicId(int epicId) {
            this.epicId = epicId;
        }

        @Override
        public String toString() {
            return "task.Epic.task.Subtask{" +
                    "id=" + getTaskId() +
                    ", name='" + getName() + '\'' +
                    ", status=" + getStatus() +
                    ", epicId=" + epicId +
                    '}';
        }
    }
}

