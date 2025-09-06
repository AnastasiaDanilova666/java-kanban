package manager;

import task.Status;
import task.Subtask;
import task.Task;
import task.Epic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private static int nextId = 1;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }



    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
        System.out.println("все задачи удалены");
    }

    @Override
    public void addTask(Task task) {
        int id = nextId++;
        task.setTaskId(id);
        tasks.put(id, task);
        System.out.println("задача добавлена");

    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
            return null;
        }
        historyManager.add(task);
        return task;
    }

    @Override
    public void updateTaskById(int id, Task updatedTask) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
            return;
        }

        updatedTask.setTaskId(id); // Обновляем id у нового объекта, чтобы он совпадал
        tasks.put(id, updatedTask);

        System.out.println("Задача с ID " + id + " успешно обновлена.");
    }

    @Override
    public void deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача с ID " + id + " не найдена.");
        }
        tasks.remove(id);
        System.out.println("Задача с ID " + id + " удалена.");
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        epics.clear();
        System.out.println("Все эпики удалены.");
    }

    @Override
    public void addEpic(Epic epic) {
        int id = nextId++;
        epic.setTaskId(id);
        epics.put(id, epic);
        System.out.println("Эпик добавлен с ID: " + id);
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
            return null;
        }
        historyManager.add(epic);
        return epic;
    }

    @Override
    public void updateEpicById(int id, Epic updatedEpic) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
        epics.put(id, updatedEpic);
        System.out.println("Эпик с ID " + id + " успешно обновлён.");
    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик с ID " + id + " удалён.");
        } else {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
    }

    @Override
    public List<Subtask> getAllSubtasksByEpicId(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
            return new ArrayList<>();
        }
        return epic.getSubtasks();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (subtask.getTaskId() != 0 && subtask.getTaskId() == epicId) {
            System.out.println("Ошибка: Подзадача не может ссылаться на саму себя как на эпик.");
            return;
        }
        Epic epic = epics.get(epicId);
        if (epic == null) {
            System.out.println("Ошибка: эпик с ID " + epicId + " не найден.");
            return;
        }
        int id = nextId++;
        subtask.setTaskId(id);
        subtasks.put(id, subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(epicId);
        System.out.println("субтаск успешно добавлен");
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
            return null;
        }
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public void updateSubtaskById(int id, Subtask newSubtask) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
            return;
        }

        subtasks.put(id, newSubtask);

        updateEpicStatus(newSubtask.getEpicId());

        System.out.println("субтаск успешно обновлен");
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
            return;
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

    @Override
    public String deleteAllSubtasksByEpic(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return "Эпик с ID " + id + " не найден!";
        }

        List<Subtask> epicSubtasks = epic.getSubtasks();
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getTaskId());
        }

        epic.deleteAllSubtasks();
        updateEpicStatus(id);

        return "Все подзадачи эпика удалены.";
    }

    @Override
    public void updateEpicStatus(int id) {
        Epic epic = epics.get(id);

        if (epic == null) {
            System.out.println("Эпика не существует");
            return;
        }
        List<Subtask> subtasksList = epic.getSubtasks();

        if (subtasksList.isEmpty()) {
            epic.setStatus(Status.NEW);
            System.out.println("Статус Эпика - NEW");
        }
        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtasksList) {
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
            System.out.println("Статус эпика - NEW");
        } else if (allDone) {
            epic.setStatus(Status.DONE);
            System.out.println("Статус эпика - DONE");
        } else {
            epic.setStatus(Status.IN_PROGRESS);
            System.out.println("Статус Эпика - IN_PROGRESS");
        }
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAlLSubtasks() {
        for (Epic epic : epics.values()) {
            epic.deleteAllSubtasks();
            updateEpicStatus(epic.getTaskId());
        }
        subtasks.clear();
        System.out.println("Все субтаски удалены.");
    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }


}
