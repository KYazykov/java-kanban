package manager;

import task.Epic;
import task.Subtask;
import task.Task;
import task.TaskType;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;


public class FileBackedTasksManager extends InMemoryTaskManager {
    private File file;

    public static CSVTaskFormatter csvTaskFormatter = new CSVTaskFormatter();
    public HistoryManager manager = new HistoryManager() {
        @Override
        public void addTaskHistory(Task task) {

        }

        @Override
        public void removeHistory(int id) {

        }

        @Override
        public List<Task> getHistory() {
            return null;
        }
    };

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    static FileBackedTasksManager loadFromFile(File file) throws ManagerSaveException, IOException {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        try {
            String f = Files.readString(Path.of("tasks.txt"));

            int generatorId = 0;

            String[] split = f.split(";");
            for (String splits: split) {
                Task task = csvTaskFormatter.fromString(splits);
                if (generatorId > task.getId()) {
                    generatorId = task.getId();
                }
            }


        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }


    }


    /**
     * метод сохраняет все задачи, подзадачи, эпики и историю просмотра любых задач.
     */
    public void save() throws ManagerSaveException, IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            writer.write(csvTaskFormatter.getHeader());
            writer.newLine();

            for (Task tasks1: tasks.values()) {

                writer.write("TASK, " + csvTaskFormatter.toString(tasks1));
                writer.newLine();


            }
            for (Subtask subtasks1: subtasks.values()) {

                writer.write("SUBTASK, " + csvTaskFormatter.toString(subtasks1));
                writer.newLine();


            }
            for (Epic epics1: epics.values()) {

                writer.write("EPIC, " + csvTaskFormatter.toString(epics1));
                writer.newLine();


            }
            String value = csvTaskFormatter.historyToString(manager);
            List <Integer> history = csvTaskFormatter.historyFromString(value);
            writer.write(history.toString());


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
        TaskType.valueOf("TASK");
        super.addTask(task);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        TaskType.valueOf("SUBTASK");
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        TaskType.valueOf("EPIC");
        super.addEpic(epic);
        save();
    }
    @Override
    public List<Task> getHistory() {
        super.getHistory();
        save();
        return super.getHistory();

    }
}


