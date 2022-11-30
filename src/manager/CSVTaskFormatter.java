package manager;

import task.*;


import java.util.ArrayList;


import java.util.List;
import java.util.regex.*;

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
    public static String toString(Task task) {

        String taskString = task.getId() + ", " + task.getName() + ", " + task.getDescription() + ", " + task.getStatus() + ", " +
                task.getType().toString() + ";";
        if (task.getEpicID() != null) {
            String[] split = taskString.split(";");
            taskString = split[0];
            taskString = taskString + ", " + task.getEpicID().toString() + ";";
        }

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

        int a = task.getId();
        String b = task.getName();
        String c = task.getDescription();
        Status status = task.getStatus();
        TaskType taskType = task.getType();

        if (task.getType().equals(TaskType.SUBTASK)) {
            task.setEpicID(Integer.parseInt(tasks[5].trim()));

            Subtask subtask = new Subtask(a, b, c, status, taskType, Integer.parseInt(tasks[5].trim()));
            return subtask;
        } else if (task.getType().equals(TaskType.EPIC)) {
            Epic epic = new Epic(a, b, c, status, taskType);
            return epic;
        }
        return task;
    }

    /**
     * Метод преобразует историю просмотка задач в строку
     *
     * @param manager
     * @return сроку истории просмотра задач
     */
    public static String historyToString(HistoryManager manager) {
        StringBuilder sb = new StringBuilder(100);
        List<Task> history = new ArrayList<>(manager.getHistory());

        for (Task task : history) {
            sb.append(task);

        }
        String historyString = sb.toString();


        return historyString;
    }

    /**
     * Метод из строки истории находит и создает список id просмотренных задач
     *
     * @param value
     * @return возвращает список id задач
     */
    static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();
        if (value.isBlank()) {
            return history;
        }
        Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher = pat.matcher(value);
        while (matcher.find()) {
            history.add(Integer.parseInt(matcher.group()));
        }
        ;

        return history;
    }

    /**
     * Метод создает шапку файла
     *
     * @return возвращает строку
     */
    public String getHeader() {
        return "id, name,  description, status, type, epic:";
    }

}
