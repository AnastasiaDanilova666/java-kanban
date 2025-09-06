package manager;

import task.Task;
import task.Epic;
import task.Status;
import task.Subtask;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        HistoryManager historyManager = manager.getHistoryManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getTaskId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getTaskId());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);


        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2.getTaskId());
        manager.addSubtask(subtask3);


        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasksByEpicId(epic1.getTaskId()));
        System.out.println(manager.getAllSubtasksByEpicId(epic2.getTaskId()));


        subtask1.setStatus(Status.DONE);
        manager.updateSubtaskById(subtask1.getTaskId(), subtask1);

        subtask2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtaskById(subtask2.getTaskId(), subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtaskById(subtask3.getTaskId(), subtask3);

        task1.setStatus(Status.DONE);
        manager.updateTaskById(task1.getTaskId(), task1);

        task2.setStatus(Status.IN_PROGRESS);
        manager.updateTaskById(task2.getTaskId(), task2);

        System.out.println("task.Task 1: " + manager.getTaskById(task1.getTaskId()));
        System.out.println("task.Task 2: " + manager.getTaskById(task2.getTaskId()));

        System.out.println("task.Epic 1: " + manager.getEpicById(epic1.getTaskId()));
        System.out.println("task.Epic 2: " + manager.getEpicById(epic2.getTaskId()));


        manager.removeEpicById(epic2.getTaskId());

        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());


        System.out.println("История:");
        for (Task task : historyManager.getHistory()) {
            System.out.println(task);
        }

    }
}