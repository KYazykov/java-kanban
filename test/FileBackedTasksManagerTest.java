import manager.FileBackedTasksManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static task.Status.DONE;
import static task.Status.NEW;
import static task.TaskType.*;


public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    Path pathToFile = Path.of("tasks.txt");


    @BeforeEach
    public void FileBackedTasksManagerTest() {

        historyManager = new InMemoryHistoryManager();
    }

    @BeforeEach
    public void StartFile() {
        try {
            if (!Files.exists(Paths.get("tasks.txt"))) {
                Files.createFile(Paths.get("tasks.txt"));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }
        manager = new FileBackedTasksManager(pathToFile.toFile());
    }

    @Test
    public void shouldFileBackedTasksManagerEmptyListOfTasksAndHistory() {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(pathToFile.toFile());
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(pathToFile.toFile());
        fileBackedTasksManager.deleteAllTasks();
        fileBackedTasksManager.deleteAllEpics();
        fileBackedTasksManager2.deleteAllEpics();
        fileBackedTasksManager2.deleteAllTasks();


        Assertions.assertTrue(fileBackedTasksManager.getTasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager.getSubtasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager.getEpics().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager2.getTasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager2.getSubtasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager2.getEpics().isEmpty());

        Assertions.assertEquals(fileBackedTasksManager.getHistory(), fileBackedTasksManager2.getHistory(),
                "Истории менеджеров fileBackedTasksManager отличаются");
    }

    @Test
    public void shouldFileBackedTasksManagerEpicWithoutSubtasks() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(pathToFile.toFile());
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(pathToFile.toFile());
        fileBackedTasksManager.deleteAllTasks();
        fileBackedTasksManager.deleteAllEpics();
        fileBackedTasksManager2.deleteAllEpics();
        fileBackedTasksManager2.deleteAllTasks();

        fileBackedTasksManager.addEpic(epic);

        Assertions.assertTrue(fileBackedTasksManager.getTasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager.getSubtasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager.getEpics().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager2.getTasks().isEmpty());
        Assertions.assertTrue(fileBackedTasksManager2.getSubtasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager2.getEpics().isEmpty());

        Assertions.assertEquals(fileBackedTasksManager.getHistory(), fileBackedTasksManager2.getHistory(),
                "Истории менеджеров fileBackedTasksManager отличаются");
    }

    @Test
    public void shouldFileBackedTasksManager() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 15, 10, 0), zone)
                , Duration.ofMinutes(8000));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 12, 0), zone)
                , Duration.ofMinutes(100));
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(pathToFile.toFile());
        FileBackedTasksManager fileBackedTasksManager2 = new FileBackedTasksManager(pathToFile.toFile());
        fileBackedTasksManager.deleteAllTasks();
        fileBackedTasksManager.deleteAllEpics();
        fileBackedTasksManager2.deleteAllEpics();
        fileBackedTasksManager2.deleteAllTasks();

        fileBackedTasksManager.addEpic(epic);
        fileBackedTasksManager.addSubtask(subtask1);
        fileBackedTasksManager.addSubtask(subtask2);
        fileBackedTasksManager.addEpic(epic2);
        fileBackedTasksManager.addSubtask(subtask3);
        fileBackedTasksManager.addTask(task);

        Assertions.assertFalse(fileBackedTasksManager.getTasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager.getSubtasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager.getEpics().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager2.getTasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager2.getSubtasks().isEmpty());
        Assertions.assertFalse(fileBackedTasksManager2.getEpics().isEmpty());

        Assertions.assertEquals(fileBackedTasksManager.getTasks(), fileBackedTasksManager2.getTasks(),
                "Таски менеджеров fileBackedTasksManager отличаются");
        Assertions.assertEquals(fileBackedTasksManager.getSubtasks(), fileBackedTasksManager2.getSubtasks(),
                "Сабтаски менеджеров fileBackedTasksManager отличаются");
        Assertions.assertEquals(fileBackedTasksManager.getEpics(), fileBackedTasksManager2.getEpics(),
                "Эпики менеджеров fileBackedTasksManager отличаются");

        Assertions.assertEquals(fileBackedTasksManager.getHistory(), fileBackedTasksManager2.getHistory(),
                "Истории менеджеров fileBackedTasksManager отличаются");
    }
}

