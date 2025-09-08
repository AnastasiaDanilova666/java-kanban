package manager;


import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public interface TaskManager {


    List<Task> getAllTasks();


    void removeAllTasks();

    void addTask(Task task);

    Task getTaskById(int id);

    void updateTaskById(int id, Task updatedTask);


    void deleteTaskById(int id);

    List<Epic> getAllEpics();

    void removeAllEpics();

    void addEpic(Epic epic);

    Epic getEpicById(int id);


    void updateEpicById(int id, Epic updatedEpic);

    void removeEpicById(int id);


    List<Subtask> getAllSubtasksByEpicId(int id);


    void addSubtask(Subtask subtask);

    Subtask getSubtaskById(int id);

    void updateSubtaskById(int id, Subtask newSubtask);

    void deleteSubtaskById(int id);


    String deleteAllSubtasksByEpic(int id);


    void updateEpicStatus(int id);

    List<Subtask> getAllSubtasks();

    void removeAlLSubtasks();

    HistoryManager getHistory();

}
