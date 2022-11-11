import task.manager.InMemoryTaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import static task.manager.Status.NEW;
import static task.manager.Status.IN_PROGRESS;
import static task.manager.Status.DONE;

public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager manager = new InMemoryTaskManager();

        Epic epic1 = new Epic("Переезд", "Переезд из одного офиса в другой", NEW);

        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, 0);
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать 10 000", NEW, 0);
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW);
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, 3);
        Task task = new Task("Посмотреть футбол", "Включить телевизор", NEW);

        manager.addEpic(epic1);
        System.out.println(epic1.getId());

        manager.addSubtask(subtask1);
        System.out.println(subtask1);

        manager.addSubtask(subtask2);
        manager.addEpic(epic2);
        manager.addSubtask(subtask3);
        manager.addTask(task);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        Subtask subtaskChange1 = new Subtask(1, "Собрать вещи", "Упаковать стулья", DONE, 0);
        Subtask subtaskChange2 = new Subtask(2, "Накопить деньги", "Собрать 10 000", IN_PROGRESS, 0);

        manager.changeSubtask(subtaskChange2);
        manager.changeSubtask(subtaskChange1);

        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());


        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println("1");


        System.out.println("2");
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());

        System.out.println("3");
        System.out.println(manager.getTasks());

        System.out.println("4");

        System.out.println(manager.findEpicSubtasks(0));
        System.out.println(manager.getTask(5));

        System.out.println(manager.getSubtask(1));
        System.out.println(manager.getEpic(0));
        System.out.println(manager.getSubtask(2));
        System.out.println(manager.getSubtask(1));

        manager.deleteEpic(epic1);
        System.out.println("История");

        System.out.println(manager.getHistory());

    }
}
