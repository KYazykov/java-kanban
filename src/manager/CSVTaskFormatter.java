package manager;

import task.*;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализует методы по форматированию задач и истории в строки и обратно
 */
public class CSVTaskFormatter {

    /**
     * Метод преобразует задачу в строку, в зависимости от типа таска
     *
     * @param task принимает задачу
     * @return taskString возвращает сроку
     */
    public String toString(Task task) {

        String taskString = task.getId() + ", " + task.getName() + ", " + task.getDescription() + ", " + task.getStatus() + ", " +
                task.getType().toString();
        if (task.getEpicID() != null) {
            String[] split = taskString.split(";");
            taskString = split[0];
            taskString = taskString + ", " + task.getEpicID().toString();
        }
        taskString = taskString + ", " + task.getStartTime() + "," + task.getDuration() + ";";
        return taskString;
    }


    /**
     * Метод преобразует задачу из строки
     *
     * @param value принимает строку
     * @return возвращает задачу
     */
    public Task fromString(String value) {
        String[] tasks = value.split(",");
        Task task = new Task();
        task.setId(Integer.parseInt(tasks[0].trim()));
        task.setName(tasks[1].trim());
        task.setDescription(tasks[2].trim());
        task.setStatus(Status.valueOf(tasks[3].trim()));
        task.setType(TaskType.valueOf(((tasks[4].trim()))));
        if (!task.getType().equals(TaskType.SUBTASK)) {
            task.setStartTime(ZonedDateTime.parse(tasks[5].trim()));
            task.setDuration(Duration.parse(tasks[6].trim()));
        }
        int a = task.getId();
        String b = task.getName();
        String c = task.getDescription();
        Status status = task.getStatus();
        TaskType taskType = task.getType();
        ZonedDateTime dataTime = task.getStartTime();
        Duration duration = task.getDuration();

        if (task.getType().equals(TaskType.SUBTASK)) {
            task.setEpicID(Integer.parseInt(tasks[5].trim()));
            task.setStartTime(ZonedDateTime.parse(tasks[6].trim()));
            task.setDuration(Duration.parse(tasks[7].trim()));

            Subtask subtask = new Subtask(a, b, c, status, taskType, Integer.parseInt(tasks[5].trim())
                    , ZonedDateTime.parse(tasks[6].trim()), Duration.parse(tasks[7].trim()));
            return subtask;
        } else if (task.getType().equals(TaskType.EPIC)) {
            Epic epic = new Epic(a, b, c, status, taskType, dataTime, duration);
            return epic;
        }
        return task;
    }

    /**
     * Метод преобразует историю просмотра задач в строку
     *
     * @param manager
     * @return сроку истории просмотра задач
     */
    public static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder(100);
        List<Task> history = new ArrayList<>(manager.getHistory());

        for (Task task : history) {
            sb.append(task.getId() + " ");
        }
        return sb.toString();
    }

    /**
     * Метод из строки истории находит и создает список id просмотренных задач
     *
     * @param value
     * @return возвращает список id задач
     */
    static List<Integer> historyFromString(String value) {
        List<Integer> historyZero = new ArrayList<>();

        if (value.isBlank()) {
            return historyZero;
        }

        List<String> history = new ArrayList<>(Arrays.asList(value.split(" ")));


        return history.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Метод создает шапку файла
     *
     * @return возвращает строку
     */
    public String getHeader() {
        return "id, name,  description, status, type, epic, startTime, duration*";
    }

}
