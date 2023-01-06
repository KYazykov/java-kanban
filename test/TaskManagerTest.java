import manager.InMemoryHistoryManager;
import manager.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import static task.Status.*;
import static task.TaskType.*;

abstract class TaskManagerTest<T extends TaskManager> {

    InMemoryHistoryManager historyManager;
    T manager;

    final ZoneId zone = ZoneId.of("Europe/Moscow");


    ZonedDateTime originalDate =
            ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0), ZoneId.of("Europe/Moscow"));
    Duration originalTime = Duration.ofMinutes(0);

    @Test
    void shouldEmptyListOfEpicSubtasks() {

        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        final int epicId = epic.getId();

        Assertions.assertTrue(manager.findEpicSubtasks(epicId).isEmpty(),
                "Возвращается не пустой список");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAllEpicSubtasksStatusNEW() {
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
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        final Status status = epic.getStatus();
        Assertions.assertEquals(status, NEW, "Главная задача не имеет статус NEW");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAllEpicSubtasksStatusDONE() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", DONE, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", DONE, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 15, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        final Status status = epic.getStatus();
        Assertions.assertEquals(status, DONE, "Главная задача не имеет статус DONE");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAllEpicSubtasksStatusIN_PROGRESS() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", IN_PROGRESS, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", IN_PROGRESS, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 15, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        final Status status = epic.getStatus();
        Assertions.assertEquals(status, IN_PROGRESS, "Главная задача не имеет статус IN_PROGRESS");
        manager.deleteAllEpics();
    }

    @Test
    void shouldEpicSubtasksStatusNEW_DONE() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", DONE, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        final Status status = epic.getStatus();
        Assertions.assertEquals(status, IN_PROGRESS, "Главная задача не имеет статус IN_PROGRESS");
        manager.deleteAllEpics();
    }

    @Test
    void shouldHasSubtaskEpic() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);

        Assertions.assertEquals(epic, manager.getEpic(subtask1.getEpicID()), "Сабтаск не имеет эпика");
        manager.deleteAllEpics();
    }

    @Test
    void shouldGetAll0Tasks() {
        Assertions.assertTrue(manager.getTasks().isEmpty(), "Список тасков не пустой");
    }

    @Test
    void shouldGetAll2Tasks() {
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        manager.addTask(task2);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);


        Assertions.assertEquals(tasks, manager.getTasks(), "Список тасков не пустой");
        manager.deleteAllTasks();
    }

    @Test
    void shouldGetAll0Epics() {
        Assertions.assertTrue(manager.getEpics().isEmpty(), "Список эпиков не пустой");
    }

    @Test
    void shouldGetAll2Epics() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        manager.addEpic(epic2);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(epic);
        tasks.add(epic2);


        Assertions.assertEquals(tasks, manager.getEpics(), "Список эпиков не пустой");
        manager.deleteAllEpics();
    }

    @Test
    void shouldGetAll0Subtasks() {
        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список сабтасков не пустой");
    }

    @Test
    void shouldGetAll3Subtasks() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1971, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 1
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 1, 12, 0), zone)
                , Duration.ofMinutes(100));
        manager.addEpic(epic);
        manager.addEpic(epic2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(subtask1);
        tasks.add(subtask2);
        tasks.add(subtask3);


        Assertions.assertEquals(tasks, manager.getSubtasks(), "Список сабтасков неправильный");
        manager.deleteAllEpics();
    }

    @Test
    void shouldDeleteAll0Tasks() {
        manager.deleteAllTasks();
        Assertions.assertTrue(manager.getTasks().isEmpty(), "Список тасков после удаления не пустой");
    }

    @Test
    void shouldDeleteAll2Tasks() {
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        manager.addTask(task2);

        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);

        manager.deleteAllTasks();

        Assertions.assertTrue(manager.getTasks().isEmpty(), "Список тасков после удаления не пустой");

    }

    @Test
    void shouldDeleteAll0Epics() {
        manager.deleteAllEpics();
        Assertions.assertTrue(manager.getEpics().isEmpty(), "Список эпиков после удаления не пустой");
    }

    @Test
    void shouldDeleteAll2Epics() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 15, 10, 0), zone)
                , Duration.ofMinutes(8000));
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 12, 0), zone)
                , Duration.ofMinutes(100));
        manager.addEpic(epic);
        manager.addEpic(epic2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        manager.deleteAllEpics();

        Assertions.assertTrue(manager.getEpics().isEmpty(), "Список эпиков после удаления не пустой");
        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список сабтасков после удаления не пустой");
    }

    @Test
    void shouldDeleteAll0Subtasks() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        manager.deleteAllSubtasks();
        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список сабтасков после удаления не пустой");
    }

    @Test
    void shouldDeleteAll2Subtasks() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 15, 10, 0), zone)
                , Duration.ofMinutes(8000));
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 12, 0), zone)
                , Duration.ofMinutes(100));
        manager.addEpic(epic);
        manager.addEpic(epic2);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);

        manager.deleteAllSubtasks();


        Assertions.assertFalse(manager.getEpics().isEmpty(), "Список эпиков после удаления пустой");
        Assertions.assertTrue(manager.getSubtasks().isEmpty(), "Список сабтасков после удаления не пустой");
        manager.deleteAllEpics();
    }

    @Test
    void shouldGetTaskWrongId() {
        Assertions.assertNull(manager.getTask(0), "Несуществующий таск не возвращает null");

    }

    @Test
    void shouldGetTask() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        Assertions.assertEquals(task2, manager.getTask(0), "Возвращает неправильный таск");
        manager.deleteAllTasks();
    }

    @Test
    void shouldGetEpicWrongId() {
        Assertions.assertNull(manager.getEpic(0), "Несуществующий эпик не возвращает null");

    }

    @Test
    void shouldGetEpic() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        Assertions.assertEquals(epic, manager.getEpic(0), "Возвращает неправильный эпик");
        manager.deleteAllEpics();
    }

    @Test
    void shouldGetSubtaskWrongId() {
        Assertions.assertNull(manager.getSubtask(0), "Несуществующий сабтаск не возвращает null");

    }

    @Test
    void shouldGetSubtask() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        Assertions.assertEquals(subtask1, manager.getSubtask(1), "Возвращает неправильный сабтаск");
        manager.deleteAllEpics();
    }

    @Test
    void shouldChangeTask() {
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        Task task2 = new Task(0, "Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        manager.changeTask(task2);

        Assertions.assertEquals(task2, manager.getTask(0), "Неправильное изменение таска");
        manager.deleteAllTasks();
    }

    @Test
    void shouldChangeTaskWithoutElementary() {
        Task task2 = new Task(0, "Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.changeTask(task2);

        Assertions.assertNull(manager.getTask(0), "Изменение таска при отсутствии первоначального");
        manager.deleteAllTasks();
    }

    @Test
    void shouldChangeEpic() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Epic epic2 = new Epic(0, "Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        manager.changeEpic(epic2);

        Assertions.assertEquals(epic2, manager.getEpic(0), "Неправильное изменение эпика");
        manager.deleteAllEpics();
    }

    @Test
    void shouldChangeEpicWithoutElementary() {
        Epic epic2 = new Epic(0, "Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.changeEpic(epic2);

        Assertions.assertNull(manager.getEpic(0), "Изменение эпика при отсутствии первоначального");
        manager.deleteAllEpics();
    }

    @Test
    void shouldChangeSubtask() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));

        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask(1, "Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.changeSubtask(subtask2);

        Assertions.assertEquals(subtask2, manager.getSubtask(1), "Неправильное изменение сабтаска");
        manager.deleteAllEpics();
    }

    @Test
    void shouldChangeSubtaskWithoutElementary() {
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC
                , ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));

        manager.addEpic(epic2);
        manager.changeEpic(epic2);

        Assertions.assertNull(manager.getSubtask(1), "Изменение сабтаска при отсутствии первоначального");
        manager.deleteAllEpics();
    }

    @Test
    void shouldThrowExceptionChangeSubtaskForAnotherEpic() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask(1, "Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 1
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);

        final NullPointerException exception = Assertions.assertThrows(


                NullPointerException.class,


                new Executable() {
                    @Override
                    public void execute() {
                        manager.changeSubtask(subtask2);
                    }
                });
        manager.deleteAllEpics();
    }

    @Test
    void shouldDeleteTaskWrongId() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Task task = new Task(0, "Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addEpic(epic);
        manager.addTask(task);
        manager.deleteTask(0);
        Assertions.assertEquals(epic, manager.getEpic(0), "Удаляет эпик при удалении таска");
        Assertions.assertEquals(task, manager.getTask(1), "Удаляет таск при неправильном параметре");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldDeleteTask() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        manager.deleteTask(0);
        Assertions.assertNull(manager.getTask(0), "Возвращает таск несмотря на его удаление");
        manager.deleteAllTasks();
    }

    @Test
    void shouldDeleteEpic() {
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
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.deleteEpic(0);

        Assertions.assertNull(manager.getEpic(0), "Возвращает эпик несмотря на его удаление");
        Assertions.assertNull(manager.getSubtask(1), "Возвращает сабтаск несмотря на удаление эпика");
        Assertions.assertNull(manager.getSubtask(2), "Возвращает сабтаск несмотря на удаление эпика");
        manager.deleteAllEpics();
    }

    @Test
    void shouldDeleteSubtask() {
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
        manager.deleteSubtask(1);

        Assertions.assertEquals(epic, manager.getEpic(0), "Неправильный эпик при удалении сабтаска");
        Assertions.assertNull(manager.getSubtask(1), "Возвращает не null при удалении сабтаска");
        Assertions.assertEquals(subtask2, manager.getSubtask(2), "Возвращает сабтаск несмотря на удаление эпика");
        manager.deleteAllEpics();
    }

    @Test
    void shouldFindEpicSubtasks() {
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

        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        Assertions.assertEquals(subtasks, manager.findEpicSubtasks(0),
                "Сабтаски не совпадают с найденными");
        manager.deleteAllEpics();
    }

    @Test
    void shouldFindEpicSubtasks0() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));

        manager.addEpic(epic);

        ArrayList<Subtask> subtasks = new ArrayList<>();

        Assertions.assertEquals(subtasks, manager.findEpicSubtasks(0),
                "Список сабтасков не пустой");
        manager.deleteAllEpics();
    }

    @Test
    void shouldFindEpicSubtasksWrongId() {

        Assertions.assertNull(manager.findEpicSubtasks(0),
                "Сабтаски несуществующего эпика не null");
    }

    @Test
    void shouldAddTaskWrongId() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addTask(epic);

        Assertions.assertNull(manager.getTask(0), "Таск не пустой при добавлении задачи другого типа");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddTaskAnotherType() {
        Task task2 = new Task(0, "Убраться", "Помыть полы", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);

        Assertions.assertNull(manager.getTask(0), "Таск не пустой при добавлении задачи другого типа");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddTask() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);

        Assertions.assertEquals(task2, manager.getTask(0), "Добавленный таск не равен изначальному");
        manager.deleteAllTasks();
    }

    @Test
    void shouldAddSubtaskAnotherType() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));

        manager.addEpic(epic);

        Assertions.assertNull(manager.getSubtask(0), "Достается сабтаск при его отсутствии");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddSubtaskWithoutEpic() {
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        manager.addSubtask(subtask1);

        Assertions.assertNull(manager.getSubtask(0), "Сабтаск добавился без эпика");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddSubtask() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);

        Assertions.assertEquals(subtask1, manager.getSubtask(1), "Сабтаск не возвращается после добавления");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddEpicAnotherType() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);

        Assertions.assertNull(manager.getEpic(0), "Эпик не пустой при добавлении задачи другого типа");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddEpic() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);

        Assertions.assertEquals(epic, manager.getEpic(0), "Добавленный эпик не равен изначальному");
        manager.deleteAllEpics();
    }

    @Test
    void shouldAddTaskHistory() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        historyManager.addTaskHistory(task2);
        Assertions.assertEquals(task2, historyManager.getHistory().get(0), "История пуста");
        manager.deleteAllTasks();
    }

    @Test
    void shouldAddTaskHistoryDuplicate() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        historyManager.addTaskHistory(task2);
        historyManager.addTaskHistory(task2);
        Assertions.assertEquals(task2, historyManager.getHistory().get(0), "История дублируется");
        manager.deleteAllTasks();
    }

    @Test
    void shouldGetTaskHistoryEmpty() {
        Assertions.assertTrue(historyManager.getHistory().isEmpty(), "История не пуста");

    }

    @Test
    void shouldGetTaskHistory() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        historyManager.addTaskHistory(task2);
        historyManager.addTaskHistory(epic);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task2);
        tasks.add(epic);
        Assertions.assertEquals(tasks, historyManager.getHistory(), "История возвращается неправильно");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldRemoveTaskHistory() {
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        historyManager.addTaskHistory(task2);
        historyManager.removeHistory(0);
        Assertions.assertTrue(historyManager.getHistory().isEmpty(), "История не пуста");
        manager.deleteAllTasks();
    }

    @Test
    void shouldRemoveTaskHistoryFirst() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        historyManager.addTaskHistory(task2);
        historyManager.addTaskHistory(epic);
        historyManager.addTaskHistory(task);
        historyManager.removeHistory(1);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(epic);
        tasks.add(task);
        Assertions.assertEquals(tasks, historyManager.getHistory(), "Удаляется неправильный элемент истории");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldRemoveTaskHistoryLast() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        historyManager.addTaskHistory(task2);
        historyManager.addTaskHistory(epic);
        historyManager.addTaskHistory(task);
        historyManager.removeHistory(2);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task2);
        tasks.add(epic);
        Assertions.assertEquals(tasks, historyManager.getHistory(), "Удаляется неправильный элемент истории");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    void shouldRemoveTaskHistoryMiddle() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        manager.addEpic(epic);
        Task task2 = new Task("Убраться", "Помыть полы", NEW, TASK, ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 28, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task2);
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK, ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));
        manager.addTask(task);
        historyManager.addTaskHistory(task2);
        historyManager.addTaskHistory(epic);
        historyManager.addTaskHistory(task);
        historyManager.removeHistory(0);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task2);
        tasks.add(task);
        Assertions.assertEquals(tasks, historyManager.getHistory(), "Удаляется неправильный элемент истории");
        manager.deleteAllTasks();
        manager.deleteAllEpics();
    }

    @Test
    public void shouldGetEndTime() {
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
        Assertions.assertEquals(epic.getStartTime(), subtask2.getStartTime()
                , " Время эпика не совпадает с временем начального сабтаска");
        Assertions.assertEquals(manager.getEndTime(subtask1), subtask1.getStartTime().plus(subtask1.getDuration())
                , "Время окончания задачи не совпадает с реальным");
        manager.deleteAllEpics();
    }

    @Test
    public void shouldGetEndTimeWrongId() {
        Epic epic = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC, ZonedDateTime.of(
                LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 16, 20, 0), zone)
                , Duration.ofMinutes(4000));
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 5, 10, 0), zone)
                , Duration.ofMinutes(8000));
        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        Assertions.assertNull(manager.getEndTime(epic), "У эпика не нулевое время");
        manager.deleteAllEpics();
        Assertions.assertNull(manager.getEndTime(epic), "Ошибка подсчета при отсутствии эпика");
    }

    @Test
    public void shouldGetPrioritizedTasksIfEmptyList() {
        Assertions.assertTrue(manager.getPrioritizedTasks().isEmpty(), "Список приоритетов не пустой");
    }

    @Test
    public void shouldGetPrioritizedTasks() {
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

        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK
                , ZonedDateTime.of(
                LocalDateTime.of(2023, 1, 10, 12, 0)
                , zone), Duration.ofMinutes(120));

        manager.addEpic(epic);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addEpic(epic2);
        manager.addSubtask(subtask3);
        manager.addTask(task);

        ArrayList<Task> taskPriority = new ArrayList<>();
        taskPriority.add(epic2);
        taskPriority.add(subtask3);
        taskPriority.add(epic);
        taskPriority.add(subtask1);
        taskPriority.add(task);

        Assertions.assertEquals(manager.getPrioritizedTasks(), taskPriority, "Список приоритетов не совпадает");
        manager.deleteAllEpics();
        manager.deleteAllTasks();
    }
}

