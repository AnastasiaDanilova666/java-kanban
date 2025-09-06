import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private HistoryManager historyManager;
    private TaskManager manager;
    private Task task;
    private Epic epic;
    private Subtask subtask;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();

        task = new Task("Task A", "Description A", Status.NEW);
        manager.addTask(task);

        epic = new Epic("Epic 1", "Epic Description");
        manager.addEpic(epic);

        subtask = new Subtask("Subtask 1", "Subtask Description", Status.NEW, epic.getTaskId());
        manager.addSubtask(subtask);
    }

    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task2 = new Task("Task B", "Description B", Status.IN_PROGRESS);

        task.setTaskId(1);
        task2.setTaskId(1);

        assertEquals(task, task2, "Задачи с одинаковыми ID должны быть равны");
    }

    @Test
    void shouldNotAddEpicAsItsSubtask() {
        TaskManager freshManager = Managers.getDefault();

        Epic epic = new Epic("Epic", "Epic description");
        freshManager.addEpic(epic);


        Subtask invalidSubtask = new Subtask("Invalid Subtask", "Should fail", Status.NEW, epic.getTaskId());
        invalidSubtask.setTaskId(epic.getTaskId());

        freshManager.addSubtask(invalidSubtask);

        List<Subtask> subtasks = freshManager.getAllSubtasksByEpicId(epic.getTaskId());
        assertTrue(subtasks.isEmpty(), "Epic не должен содержать сам себя как подзадачу");
    }


    @Test
    void shouldNotAddSubtaskAsItsEpic() {
        TaskManager freshManager = Managers.getDefault();

        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        freshManager.addEpic(epic);

        Subtask invalidSubtask = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic.getTaskId());
        invalidSubtask.setTaskId(epic.getTaskId());

        freshManager.addSubtask(invalidSubtask);

        List<Subtask> subtasks = freshManager.getAllSubtasksByEpicId(epic.getTaskId());
        assertTrue(subtasks.isEmpty(), "Subtask не может иметь тот же ID, что и Epic");
    }

    @Test
    void shouldReturnInitializedTaskManager() {
        assertNotNull(manager, "TaskManager должен быть инициализирован");

        Task retrievedTask = manager.getTaskById(task.getTaskId());

        assertNotNull(retrievedTask, "TaskManager должен уметь сохранять и возвращать задачи");
        assertEquals(task, retrievedTask, "Сохранённая и полученная задачи должны совпадать");
    }

    @Test
    void shouldReturnInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager должен быть инициализирован");
    }

    @Test
    void shouldAddTaskAndFindById() {
        Task foundTask = manager.getTaskById(task.getTaskId());
        Epic foundEpic = manager.getEpicById(epic.getTaskId());
        Subtask foundSubtask = manager.getSubtaskById(subtask.getTaskId());

        assertNotNull(foundTask, "Должен найти сохранённую задачу");
        assertNotNull(foundEpic, "Должен найти сохранённый эпик");
        assertNotNull(foundSubtask, "Должен найти сохранённую подзадачу");
    }

    @Test
    void shouldNotConflictBetweenManualAndGeneratedIds() {

        Task manualTask = new Task("Manual ID Task", "Task with manual id", Status.NEW);
        manualTask.setTaskId(100);

        Task autoIdTask = new Task("Auto ID Task", "Task with auto-generated id", Status.NEW);

        manager.addTask(manualTask);
        manager.addTask(autoIdTask);

        assertNotEquals(manualTask.getTaskId(), autoIdTask.getTaskId(), "номера не должны совпадать");

        Task sameManualTask = manager.getTaskById(manualTask.getTaskId());
        Task sameAutoIdTask = manager.getTaskById(autoIdTask.getTaskId());

        assertEquals(manualTask, sameManualTask, "должны совпадать");
        assertEquals(autoIdTask, sameAutoIdTask, "должны совпадать");

    }

    @Test
    void taskShoudRemainUntouchedAfterAdding() {
        int originalId = task.getTaskId();
        String originalName = task.getName();
        String originalDescription = task.getDescription();
        Status originalStatus = task.getStatus();

        Task taskFromManager = manager.getTaskById(task.getTaskId());

        assertEquals(originalName, taskFromManager.getName(), "Имя задачи не должно измениться");
        assertEquals(originalDescription, taskFromManager.getDescription(), "Описание задачи не должно измениться");
        assertEquals(originalStatus, taskFromManager.getStatus(), "Статус задачи не должен измениться");
        assertEquals(originalId, taskFromManager.getTaskId(), "ID задачи не должен измениться");
    }

    @Test
    void historyShouldSavePreviousVersionOfTask() {

        Task taskFromManager = manager.getTaskById(task.getTaskId());

        historyManager.add(taskFromManager);

        assertTrue(historyManager.getHistory().contains(taskFromManager), "История должна содержать задачу");

        taskFromManager.setName("Changed name");
        taskFromManager.setDescription("Changed description");
        taskFromManager.setStatus(Status.IN_PROGRESS);

        manager.updateTaskById(taskFromManager.getTaskId(), taskFromManager);

        historyManager.add(taskFromManager);

        Task taskFromHistory = historyManager.getHistory().get(0);
        assertEquals(taskFromManager, taskFromHistory);

    }

}
