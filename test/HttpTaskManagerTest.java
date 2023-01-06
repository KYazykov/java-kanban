import Server.HttpTaskManager;
import Server.KVServer;
import Server.KVTaskClient;
import manager.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;

import java.io.IOException;
import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static task.Status.DONE;
import static task.Status.NEW;
import static task.TaskType.*;
import static task.TaskType.SUBTASK;

public class HttpTaskManagerTest <T extends TaskManagerTest<HttpTaskManager>> {
    private KVServer server;
    private TaskManager manager;
    private HistoryManager historyManager;
    private static final ZoneId zone = ZoneId.of("Europe/Moscow");


    @BeforeEach
    public void createManager() {
        try {
            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            manager = Managers.getDefault(historyManager);
        } catch (IOException | InterruptedException e) {
            System.out.println("Ошибка при создании менеджера");
        }
    }

    @AfterEach
    public void stopServer() {
        server.stop();
    }

    @Test
    public void shouldLoadTasks() throws IOException, InterruptedException {

        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        manager.addTask(task2);
        manager.getTask(task.getId());
        manager.getTask(task2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getTasks(), list);

    }

    @Test
    public void shouldLoadEpics() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        manager.addEpic(epic2);
        manager.getEpic(epic.getId());
        manager.getEpic(epic2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getEpics(), list);

    }

    @Test
    public void shouldLoadSubtasks() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());
        List<Task> list = manager.getHistory();
        assertEquals(manager.getSubtasks(), list);

    }

}