package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

import static task.Status.*;
import static task.TaskType.*;

/**
 * Класс служит для работы с менеджером задач в файле
 */
public class FileBackedTasksManager extends InMemoryTaskManager {
    private static File file;

    public static CSVTaskFormatter csvTaskFormatter = new CSVTaskFormatter();

    static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    /**
     * Основной метод для использования всего доступного функционала
     *
     * @param args
     * @throws ManagerSaveException
     */
    public static void main(String[] args) throws ManagerSaveException {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        Epic epic1 = new Epic("Переезд", "Переезд из одного офиса в другой", NEW, EPIC);

        Subtask subtask1 = new Subtask("Собрать вещи", "Упаковать стулья", NEW, SUBTASK, 0);
        Subtask subtask2 = new Subtask("Накопить деньги", "Собрать десять тысяч", NEW, SUBTASK, 0);
        Epic epic2 = new Epic("Сходить в магазин", "Купить продукты", NEW, EPIC);
        Subtask subtask3 = new Subtask("Купить овощи", "Купить картошку и редис", NEW, SUBTASK, 3);
        Task task = new Task("Посмотреть футбол", "Включить телевизор", DONE, TASK);

        fileBackedTasksManager.addEpic(epic1);
        fileBackedTasksManager.addSubtask(subtask1);
        fileBackedTasksManager.addSubtask(subtask2);
        fileBackedTasksManager.addEpic(epic2);
        fileBackedTasksManager.addSubtask(subtask3);
        fileBackedTasksManager.addTask(task);

        System.out.println("2");
        System.out.println(fileBackedTasksManager.getSubtasks());
        System.out.println(fileBackedTasksManager.getEpics());


        System.out.println("4");


        System.out.println(fileBackedTasksManager.getTask(5));

        System.out.println(fileBackedTasksManager.getSubtask(1));
        System.out.println(fileBackedTasksManager.getEpic(0));
        System.out.println(fileBackedTasksManager.getSubtask(2));
        System.out.println(fileBackedTasksManager.getSubtask(1));


        System.out.println("История");

        System.out.println(fileBackedTasksManager.getHistory());

        FileBackedTasksManager newFileBackedTasksManager = new FileBackedTasksManager(file);
        newFileBackedTasksManager.loadFromFile(file);

    }

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    /**
     * Метод для восстанавлиния данных менеджера из файла при запуске программы
     *
     * @param file
     * @return
     * @throws ManagerSaveException
     */
    static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException {


        try {
            String fileText = Files.readString(Path.of("tasks.txt"));

            int generatorId = 0;

            String[] split = fileText.split(":");
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
                        inMemoryTaskManager.tasks.put(task.getId(), task);
                        break;
                    case SUBTASK:
                        inMemoryTaskManager.subtasks.put(task.getId(), (Subtask) task);
                        break;
                    case EPIC:
                        inMemoryTaskManager.epics.put(task.getId(), (Epic) task);
                        break;
                }
                generatorId++;
            }

            for (Subtask subtask1 : inMemoryTaskManager.subtasks.values()) {
                int epicId = subtask1.getEpicID();
                Epic epic = inMemoryTaskManager.epics.get(epicId);
                epic.addSubtaskID(subtask1.getId());

            }
            for (Integer id : historyParse) {
                if (inMemoryTaskManager.getTask(id) != null) {
                    Task task = inMemoryTaskManager.getTask(id);
                    inMemoryHistoryManager.addTaskHistory(task);
                } else {
                    continue;
                }
                if (inMemoryTaskManager.getSubtask(id) != null) {
                    Task task = inMemoryTaskManager.getSubtask(id);
                    inMemoryHistoryManager.addTaskHistory(task);
                } else {
                    continue;
                }
                if (inMemoryTaskManager.getEpic(id) != null) {
                    Task task = inMemoryTaskManager.getEpic(id);
                    inMemoryHistoryManager.addTaskHistory(task);
                }
            }


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }

        return new FileBackedTasksManager(file);
    }


    /**
     * метод сохраняет все задачи, подзадачи, эпики и историю просмотра любых задач в файле.
     */
    public static void save() throws ManagerSaveException {

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

            String value = csvTaskFormatter.historyToString(historyManager);
            List<Integer> history = csvTaskFormatter.historyFromString(value);

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
    public void deleteAllSubtasks(Epic epic) {
        super.deleteAllSubtasks(epic);
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
    public void deleteEpicSubtasks(Subtask subtask) {
        super.deleteEpicSubtasks(subtask);
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
        super.getHistory();
        save();
        return super.getHistory();

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
}


