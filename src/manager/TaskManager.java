package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.time.ZonedDateTime;
import java.util.ArrayList;


/**
 * Интерфейс обладает основными методами по работе с задачами
 */
public interface TaskManager {

    /**
     * Метод вовзвращает список всех задач
     *
     * @return ArrayList возвращает список задач
     */
    ArrayList<Task> getTasks();

    /**
     * Метод вовзвращает список всех эпиков
     *
     * @return ArrayList возвращает список эпиков
     */
    ArrayList<Epic> getEpics();

    /**
     * Метод вовзвращает список всех подзадач
     *
     * @return ArrayList возвращает список подзадач
     */
    ArrayList<Subtask> getSubtasks();

    /**
     * Метод удаляет все задачи
     */
    void deleteAllTasks();

    /**
     * Метод удаляет все эпики
     */
    void deleteAllEpics();

    /**
     * Метод удаляет все подзадачи
     */
    void deleteAllSubtasks();

    /**
     * Метод находит выбранную задачу
     *
     * @param id принимает значение задачи
     * @return ArrayList возвращает найденую задачу
     */
    Task getTask(int id);

    /**
     * Метод находит выбранный эпик
     *
     * @param id принимает значение эпика
     * @return ArrayList возвращает найденый эпик
     */
    Epic getEpic(int id);

    /**
     * Метод находит выбранную подзадачу
     *
     * @param id принимает значение подзадачи
     * @return ArrayList возвращает найденую подзадачу
     */
    Subtask getSubtask(int id);

    /**
     * Метод обновляет выбранную задачу
     *
     * @param task принимает значение задачи
     */
    void changeTask(Task task);

    /**
     * Метод обновляет выбранную подзадачу
     *
     * @param subtask принимает значение задачи
     */
    void changeSubtask(Subtask subtask);

    /**
     * Метод обновляет выбранный эпик
     *
     * @param epic принимает значение эпика
     */
    void changeEpic(Epic epic);

    /**
     * Метод удаляет выбранную задачу
     *
     * @param task принимает значение задачи
     */
    void deleteTask(Task task);

    /**
     * Метод удаляет выбранный эпик и его подзадачи
     *
     * @param epic принимает значение эпика
     */
    void deleteEpic(Epic epic);

    /**
     * Метод удаляет подзадачу
     *
     * @param subtask принимает значение подзадачи
     */
    void deleteSubtask(Subtask subtask);

    /**
     * Метод получает список всех подзадач определённого эпика
     *
     * @param epicId принимает значение id эпика
     * @return ArrayList возвращает список подзадач
     */
    ArrayList<Subtask> findEpicSubtasks(int epicId);

    /**
     * Метод добавляет задачу
     *
     * @param task принимает значение задачи
     */
    void addTask(Task task);

    /**
     * Метод добавляет подзадачу
     *
     * @param subtask принимает значение подзадачи
     */
    void addSubtask(Subtask subtask);

    /**
     * Метод добавляет эпик
     *
     * @param epic принимает значение эпика
     */
    void addEpic(Epic epic);

    /**
     * Метод вычисляет время завершения задачи
     */
    ZonedDateTime getEndTime(Task task);

    /**
     * Метод сортирует задачи по приоритету
     */
    ArrayList<Task> getPrioritizedTasks();
}
