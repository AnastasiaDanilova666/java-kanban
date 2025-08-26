package manager;

import task.Task;
import task.Epic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private static int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Epic.Subtask> subtasks = new HashMap<>();

    public Map<Integer, Task> getAllTasks() {
        return tasks;
    }

    public void removeAllTasks() {
        tasks.clear();
        System.out.println( "все задачи удалены");
    }

    public void addTask(Task task) {
        int id = nextId++;
        task.setTaskId(id);
        tasks.put(id, task);
        System.out.println("задача добавлена");

    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
            return null;
        }
        return task;
    }

    public void updateTaskById(int id, Task updatedTask) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
        }

        tasks.put(id, task);

        System.out.println("Задача с ID " + id + " успешно обновлена.");

    }

    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача с ID " + id + " не найдена.");
        }
        tasks.remove(id);
        System.out.println("Задача с ID " + id + " удалена.");
    }

    public Map<Integer, Epic> getAllEpics() {
        return epics;
    }

    public void removeAllEpics() {
        epics.clear();
        System.out.println("Все эпики удалены.");
    }

    public void addEpic(Epic epic) {
        int id = nextId++;
        epic.setTaskId(id);
        epics.put(id, epic);
        System.out.println("Эпик добавлен с ID: " + id);
    }

    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
        return epic;
    }


    public void updateEpicById(int id, Epic updatedEpic) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println( "Эпик с ID " + id + " не найден.");
        }
        epics.put(id, updatedEpic);
        System.out.println("Эпик с ID " + id + " успешно обновлён.");
    }

    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик с ID " + id + " удалён.");
        } else {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
    }


    public List<Epic.Subtask> getAllSubtasksByEpicId(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
        return epic.getSubtasks();
    }


    public void addSubtask(Epic.Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            System.out.println("Ошибка: эпик с ID " + epicId + " не найден.");
        }
        int id = nextId++;
        subtask.setTaskId(id);
        subtasks.put(id, subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(epicId);
        System.out.println( "субтаск успешно добавлен");
    }

    public Epic.Subtask getSubtaskById(int id) {
        Epic.Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
        }
        return subtask;
    }

    public void updateSubtaskById(int id, Epic.Subtask newSubtask) {
        Epic.Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
        }

        subtasks.put(id, newSubtask);

        updateEpicStatus(newSubtask.getEpicId());

        System.out.println("субтаск успешно обновлен");
    }

    public void deleteSubtaskById(int id) {
        Epic.Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
        }

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.getSubtasks().remove(subtask);
        }
        subtasks.remove(id);
        updateEpicStatus(epicId);

        System.out.println("Субтаск с ID " + id + " удален.");
    }

    public String deleteAllSubtasksByEpic(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return "такого эпика нет!";
        }

        for (Epic.Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getTaskId());
        }

        epic.deleteAllSubtasks();
        updateEpicStatus(id);
        return"Все субтаски удалены";
    }


    public void updateEpicStatus(int id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            System.out.println("Эпика не существует");
        }
        List<Epic.Subtask> subtasksList = epic.getSubtasks();

        if (subtasksList.isEmpty()) {
            epic.setStatus(Status.NEW);
            System.out.println("Статус Эпика - NEW");
        }
        boolean allNew = true;
        boolean allDone = true;

        for (Epic.Subtask subtask : subtasksList) {
            Status status = subtask.getStatus();

            if (status != Status.NEW) {
                allNew = false;
            }
            if (status != Status.DONE) {
                allDone = false;
            }
        }
        if (allNew) {
            epic.setStatus(Status.NEW);
            System.out.println( "Статус эпика - NEW");
        }
         else if (allDone) {
            epic.setStatus(Status.DONE);
            System.out.println("Статус эпика - DONE");
        } else {
            epic.setStatus(Status.IN_PROGRESS);
            System.out.println("Статус Эпика - IN_PROGRESS");
        }
    }
}
