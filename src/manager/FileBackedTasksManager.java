package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static task.Status.DONE;
import static task.Status.NEW;
import static task.TaskType.*;

/**
 * Класс служит для работы с менеджером задач в файле
 */
public class FileBackedTasksManager extends InMemoryTaskManager {

    /**
     * Основной метод для использования всего доступного функционала
     *
     * @param args
     * @throws ManagerSaveException
     */

    private File file;
    static final ZoneId zone = ZoneId.of("Europe/Moscow");


    public static void main(String[] args) throws ManagerSaveException {

        File pathToFile = Path.of("tasks.txt").toFile();

        try {
            if (!Files.exists(Paths.get("tasks.txt"))) {
                Files.createFile(Paths.get("tasks.txt"));
            }
        } catch (IOException e) {
            System.out.println("Ошибка при создании файла");
            e.printStackTrace();
        }

        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.loadFromFile(pathToFile);

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


        System.out.println(fileBackedTasksManager.getEpics());
        fileBackedTasksManager.addEpic(epic);
        System.out.println(epic.getId());

        fileBackedTasksManager.addSubtask(subtask1);
        System.out.println(subtask1);

        fileBackedTasksManager.addSubtask(subtask2);


        fileBackedTasksManager.addEpic(epic2);
        fileBackedTasksManager.addSubtask(subtask3);
        fileBackedTasksManager.addTask(task);

        Subtask subtask4 = new Subtask(4, "Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 3
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 16, 0), zone)
                , Duration.ofMinutes(100));

        fileBackedTasksManager.changeSubtask(subtask4);

        Task task2 = new Task(5, "Посмотреть футбол", "Включить телевизор", DONE, TASK
                , ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 10, 13, 0)
                , zone), Duration.ofMinutes(120));


        fileBackedTasksManager.changeTask(task2);


        System.out.println(fileBackedTasksManager.getEndTime(epic));
        System.out.println(fileBackedTasksManager.getEndTime(epic2));
        System.out.println(fileBackedTasksManager.getEndTime(subtask1));
        System.out.println(fileBackedTasksManager.getEndTime(subtask2));
        System.out.println(fileBackedTasksManager.getEndTime(subtask3));
        System.out.println(fileBackedTasksManager.getEndTime(task));


        fileBackedTasksManager.getEpic(0);
        fileBackedTasksManager.getTask(5);
        fileBackedTasksManager.getSubtask(1);
        System.out.println(fileBackedTasksManager.getHistory());
        System.out.print(fileBackedTasksManager.getPrioritizedTasks());


    }

    public FileBackedTasksManager(File pathToFile) {
    }


    /**
     * Метод для восстанавлиния данных менеджера из файла при запуске программы
     *
     * @param file
     * @return
     * @throws ManagerSaveException
     */
    public static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {

        File pathToFile = Path.of("tasks.txt").toFile();

        CSVTaskFormatter csvTaskFormatter = new CSVTaskFormatter();

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(pathToFile);
        fileBackedTasksManager.setFile(file);
        if (file.length() == 0) {
            return fileBackedTasksManager;
        }


        try {
            String fileText = Files.readString(file.toPath());

            int generatorId = 0;

            String[] split = fileText.split("\\*");
            String splitHead = split[1].trim();
            String[] splitString = splitHead.split(";");


            List<Integer> historyParse = new ArrayList<>();
            for (String splits : splitString) {
                String[] historyString = splits.trim().split(",");
                String history = String.join(" ", historyString).replaceAll("\\s+", "");
                if (history.matches("[-+]?\\d+")) {
                    for (char ch : history.toCharArray()) {
                        historyParse.add((int) ch - 48);
                    }
                    break;
                }
                Task task = csvTaskFormatter.fromString(splits);
                if (generatorId > task.getId()) {
                    generatorId = task.getId();
                }
                TaskType type = task.getType();

                switch (type) {
                    case TASK:
                        tasks.put(task.getId(), task);
                        break;
                    case SUBTASK:
                        subtasks.put(task.getId(), (Subtask) task);
                        break;
                    case EPIC:
                        epics.put(task.getId(), (Epic) task);
                        break;
                }
                generatorId++;
            }

            for (Subtask subtask1 : subtasks.values()) {
                int epicId = subtask1.getEpicID();
                Epic epic = epics.get(epicId);
                epic.addSubtaskID(subtask1.getId());

            }
            for (Integer id : historyParse) {
                if (new InMemoryTaskManager().getTask(id) != null) {
                    Task task = new InMemoryTaskManager().getTask(id);
                    historyManager.addTaskHistory(task);
                } else {
                    continue;
                }
                if (new InMemoryTaskManager().getSubtask(id) != null) {
                    Task task = new InMemoryTaskManager().getSubtask(id);
                    historyManager.addTaskHistory(task);
                } else {
                    continue;
                }
                if (new InMemoryTaskManager().getEpic(id) != null) {
                    Task task = new InMemoryTaskManager().getEpic(id);
                    historyManager.addTaskHistory(task);
                }
            }


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return fileBackedTasksManager;
    }


    /**
     * метод сохраняет все задачи, подзадачи, эпики и историю просмотра любых задач в файле.
     */
    public static void save() throws ManagerSaveException {
        CSVTaskFormatter csvTaskFormatter = new CSVTaskFormatter();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            writer.write(csvTaskFormatter.getHeader());
            writer.newLine();

            for (Task tasks1 : tasks.values()) {

                writer.write(csvTaskFormatter.toString(tasks1));
                writer.newLine();


            }
            for (Subtask subtasks1 : subtasks.values()) {

                writer.write(csvTaskFormatter.toString(subtasks1));
                writer.newLine();


            }
            for (Epic epics1 : epics.values()) {

                writer.write(csvTaskFormatter.toString(epics1));
                writer.newLine();


            }
            writer.newLine();

            String value = CSVTaskFormatter.historyToString(historyManager);
            List<Integer> history = CSVTaskFormatter.historyFromString(value);

            String listString = Arrays.toString(history.toArray()).replace("[", "").replace("]", "");
            writer.write(listString);


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void changeTask(Task task) {

        super.changeTask(task);
        save();
    }

    @Override
    public void changeSubtask(Subtask subtask) {
        super.changeSubtask(subtask);
        save();
    }

    @Override
    public void changeEpic(Epic epic) {
        super.changeEpic(epic);
        save();
    }

    @Override
    public void deleteTask(Task task) {
        super.deleteTask(task);
        save();
    }

    @Override
    public void deleteEpic(Epic epic) {
        super.deleteEpic(epic);
        save();
    }

    @Override
    public void deleteSubtask(Subtask subtask) {
        super.deleteSubtask(subtask);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> a = super.getHistory();
        save();
        return a;
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Epic getEpic(int id) {
        return super.getEpic(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        return super.getSubtask(id);
    }

    @Override
    public ArrayList<Task> getTasks() {

        return super.getTasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {

        return super.getEpics();
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {

        return super.getSubtasks();
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public ZonedDateTime getEndTime(Task task) {
        return super.getEndTime(task);
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }
}


