import manager.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static task.Status.DONE;
import static task.Status.NEW;
import static task.TaskType.*;

public class Main {

    public static void main(String[] args) {

        final ZoneId zone = ZoneId.of("Europe/Moscow");

        InMemoryTaskManager manager = new InMemoryTaskManager();

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
        System.out.println(epic.getId());

        manager.addSubtask(subtask1);
        System.out.println(subtask1);

        manager.addSubtask(subtask2);


        Task task2 = new Task(5, "Посмотреть футбол", "Включить телевизор", DONE, TASK
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 13, 0)
                , zone), Duration.ofMinutes(120));

        manager.addEpic(epic2);
        manager.addSubtask(subtask3);
        manager.addTask(task);

        System.out.println(1);
        manager.changeTask(task2);
        System.out.println(2);

        System.out.println(manager.getEndTime(epic));
        System.out.println(manager.getEndTime(epic2));
        System.out.println(manager.getEndTime(subtask1));
        System.out.println(manager.getEndTime(subtask2));
        System.out.println(manager.getEndTime(subtask3));
        System.out.println(manager.getEndTime(task));
        System.out.println(manager.getPrioritizedTasks());
    }
}
