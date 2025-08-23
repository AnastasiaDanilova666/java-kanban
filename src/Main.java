public class Main{
    public static void main(String[] args){
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", Status.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getIdNumber());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW, epic1.getIdNumber());
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);


        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", Status.NEW);
        manager.addEpic(epic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW, epic2.getIdNumber());
        manager.addSubtask(subtask3);


        System.out.println(manager.getAllTasks());
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasksByEpicId(epic1.getIdNumber()));
        System.out.println(manager.getAllSubtasksByEpicId(epic2.getIdNumber()));


        subtask1.setStatus(Status.DONE);
        manager.updateSubtaskById(subtask1.getIdNumber(), subtask1);

        subtask2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtaskById(subtask2.getIdNumber(), subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtaskById(subtask3.getIdNumber(), subtask3);

        task1.setStatus(Status.DONE);
        manager.updateTaskById(task1.getIdNumber(), task1);

        task2.setStatus(Status.IN_PROGRESS);
        manager.updateTaskById(task2.getIdNumber(), task2);

        System.out.println("Task 1: " + manager.getTaskById(task1.getIdNumber()));
        System.out.println("Task 2: " + manager.getTaskById(task2.getIdNumber()));

        System.out.println("Epic 1: " + manager.getEpicById(epic1.getIdNumber()));
        System.out.println("Epic 2: " + manager.getEpicById(epic2.getIdNumber()));


        System.out.println(manager.removeEpicById(epic2.getIdNumber()));

        System.out.println(manager.getAllTasks());

        System.out.println(manager.getAllEpics());
    }
}