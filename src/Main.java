import Server.InstantAdapter;
import Server.KVServer;
import Server.KVTaskClient;
import Server.ZonedDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import manager.HistoryManager;
import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Subtask;
import task.Task;

import java.io.IOException;
import java.time.*;

import static task.Status.DONE;
import static task.Status.NEW;
import static task.TaskType.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        final ZoneId zone = ZoneId.of("Europe/Moscow");

        InMemoryTaskManager manager = new InMemoryTaskManager();

        KVServer server;
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter());
            gsonBuilder.registerTypeAdapter(Instant.class, new InstantAdapter());
            gsonBuilder.serializeNulls();
            Gson gson = gsonBuilder.create();

            server = new KVServer();
            server.start();
            HistoryManager historyManager = Managers.getDefaultHistory();
            TaskManager httpTaskManager = Managers.getDefault(historyManager);

            Epic epic = new Epic("Переезд",
                    "Переезд из одного офиса в другой",
                    NEW,
                    EPIC,
                    ZonedDateTime.of(
                    LocalDateTime.of(1970, 1, 1, 0, 0), zone),
                    Duration.ofMinutes(0));

            Subtask subtask1 = new Subtask("Собрать вещи",
                    "Упаковать стулья",
                    NEW,
                    SUBTASK,
                    0,
                    ZonedDateTime.of(
                    LocalDateTime.of(2022, 12, 16, 20, 0), zone),
                    Duration.ofMinutes(4000));
            Subtask subtask2 = new Subtask("Накопить деньги",
                    "Собрать десять тысяч",
                    NEW,
                    SUBTASK,
                    0,
                    ZonedDateTime.of(
                    LocalDateTime.of(2022, 12, 15, 10, 0), zone),
                    Duration.ofMinutes(8000));

            Epic epic2 = new Epic("Сходить в магазин", "Купить продукты",
                    NEW,
                    EPIC,
                    ZonedDateTime.of(
                    LocalDateTime.of(1970, 1, 1, 0, 0), zone), Duration.ofMinutes(0));
            Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис",
                    NEW,
                    SUBTASK,
                    1,
                    ZonedDateTime.of(
                    LocalDateTime.of(2022, 12, 10, 12, 0), zone),
                    Duration.ofMinutes(100));

            Task task = new Task("Посмотреть футбол", "Включить телевизор",
                    DONE,
                    TASK,
                    ZonedDateTime.of(
                    LocalDateTime.of(2023, 1, 10, 12, 0)
                    , zone), Duration.ofMinutes(120));


            httpTaskManager.addEpic(epic);
            httpTaskManager.addEpic(epic2);
            httpTaskManager.addSubtask(subtask1);
            httpTaskManager.addSubtask(subtask2);
            httpTaskManager.addSubtask(subtask3);
            httpTaskManager.addTask(task);


            httpTaskManager.getEpic(1);
            httpTaskManager.getSubtask(3);

            System.out.println("Печать всех задач");
            System.out.println(gson.toJson(httpTaskManager.getTasks()));
            System.out.println("Печать всех эпиков");
            System.out.println(gson.toJson(httpTaskManager.getEpics()));
            System.out.println("Печать всех подзадач");
            System.out.println(gson.toJson(httpTaskManager.getSubtasks()));

            KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:8080");
            System.out.println(kvTaskClient.load("tasks"));
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
