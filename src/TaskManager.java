import java.util.HashMap;
import java.util.ArrayList;
public class TaskManager {
    private static int nextId = 1;
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public HashMap<Integer, Task> getAllTasks() {
        return tasks;
    }

    public String removeAllTasks() {
        tasks.clear();
        return "все задачи удалены";
    }

    public String addTask(Task task) {
        int id = nextId++;
        task.setIdNumber(id);
        tasks.put(id, task);
        return "задача добавлена";

    }

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            System.out.println("Задача с ID " + id + " не найдена.");
            return null;
        }
        return task;
    }

    public String updateTaskById(int id, Task updatedTask) {
        Task task = tasks.get(id);
        if (task == null) {
            return "Задача с ID " + id + " не найдена.";
        }
        task.setName(updatedTask.getName());
        task.setDefinition(updatedTask.getDefinition());
        task.setStatus(updatedTask.getStatus());
        tasks.put(id, task);

        return "Задача с ID " + id + " успешно обновлена.";

    }

    public String deleteTaskById(int id) {
        if (!tasks.containsKey(id)) {
            return "Задача с ID " + id + " не найдена.";
        }
        tasks.remove(id);
        return "Задача с ID " + id + " удалена.";
    }

    public HashMap<Integer, Epic> getAllEpics() {
        return epics;
    }

    public String removeAllEpics() {
        epics.clear();
        return "Все эпики удалены.";
    }

    public String addEpic(Epic epic) {
        int id = nextId++;
        epic.setIdNumber(id);
        epics.put(id, epic);
        return "Эпик добавлен с ID: " + id;
    }

    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
        return epic;
    }


    public String updateEpicById(int id, Epic updatedEpic) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return "Эпик с ID " + id + " не найден.";
        }
        epics.put(id, updatedEpic);
        return "Эпик с ID " + id + " успешно обновлён.";
    }

    public String removeEpicById(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            return "Эпик с ID " + id + " удалён.";
        } else {
            return "Эпик с ID " + id + " не найден.";
        }
    }


    public ArrayList<Subtask> getAllSubtasksByEpicId(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Эпик с ID " + id + " не найден.");
        }
        return epic.getSubtasks();
    }



    public String addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return "Ошибка: эпик с ID " + epicId + " не найден.";
        }

        int id = nextId++;
        subtask.setIdNumber(id);
        subtasks.put(id, subtask);
        epic.addSubtask(subtask);
        updateEpicStatus(epicId);
        return "субтаск успешно добавлен";
    }

    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            System.out.println("Субтаск с ID " + id + " не найден.");
        }
        return subtask;
    }

    public String updateSubtaskById(int id, Subtask newSubtask) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return "Субтаск с ID " + id + " не найден.";
        }
        newSubtask.setIdNumber(id); // сохранить ID
        newSubtask.setEpicId(subtask.getEpicId()); // сохранить связь с эпиком
        subtasks.put(id, newSubtask);

        updateEpicStatus(newSubtask.getEpicId());

        return "субтаск успешно обновлен";
    }

    public String deleteSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) {
            return "Субтаск с ID " + id + " не найден.";
        }

        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epic.getSubtasks().remove(subtask);
        }
        subtasks.remove(id);
        updateEpicStatus(epicId);

        return "Субтаск с ID " + id + " удален.";
    }

    public String deleteAllSubtasksByEpic(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return "Эпик с ID " + id + " не найден.";
        }

        for (Subtask subtask : epic.getSubtasks()) {
            subtasks.remove(subtask.getIdNumber());
        }

        epic.deleteAllSubtasks();
        updateEpicStatus(id);

        return "Все субтаски удалены";
    }


    public String updateEpicStatus(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            return "Эпика не существует";
        }
        ArrayList<Subtask> subtasksList = epic.getSubtasks();
        if (subtasksList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return "Статус Эпика - NEW";
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
            return "Статус эпика - NEW";
        }
        if (allDone) {
            epic.setStatus(Status.DONE);
            return "Статус эпика - DONE";
        } else {
            epic.setStatus(Status.IN_PROGRESS);
            return "Статус Эпика - IN_PROGRESS";
        }
    }
}
