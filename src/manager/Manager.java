package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int generatorId = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    /**
     *Метод вовзвращает список всех задач
     *
     * @return ArrayList возвращает список задач
     */

    public ArrayList<Task> getTasks() {

        return new ArrayList<>(tasks.values());
    }
    /**
     *Метод вовзвращает список всех эпиков
     *
     * @return ArrayList возвращает список эпиков
     */
    public ArrayList<Epic> getEpics() {

        return new ArrayList<>(epics.values());
    }
    /**
     *Метод вовзвращает список всех подзадач
     *
     * @return ArrayList возвращает список подзадач
     */
    public ArrayList<Subtask> getSubtasks() {

        return new ArrayList<>(subtasks.values());
    }
    /**
     *Метод удаляет все задачи
     */
    public void deleteAllTasks () {
        tasks.clear();
    }
    /**
     *Метод удаляет все эпики
     */
    public void deleteAllEpics () {
        epics.clear();
        subtasks.clear();
    }
    /**
     *Метод удаляет все подзадачи
     */
    public void deleteAllSubtasks (Epic epic) {

        subtasks.clear();
        updateEpicStatus(epic);
    }
    /**
     *Метод находит выбранную задачу
     *
     * @param task принимает значение задачи
     * @return ArrayList возвращает найденую задачу
     */
    public Task findTask (Task task) {
            return tasks.get(task.getId());

    }
    /**
     *Метод находит выбранный эпик
     *
     * @param epic принимает значение эпика
     * @return ArrayList возвращает найденый эпик
     */
    public Epic  findEpic (Epic epic) {

            return epics.get(epic.getId());
    }
    /**
     *Метод находит выбранную подзадачу
     *
     * @param subtask принимает значение подзадачи
     * @return ArrayList возвращает найденую подзадачу
     */
    public Subtask findSubtask (Subtask subtask) {

            return subtasks.get(subtask.getId());
    }
    /**
     *Метод обновляет выбранную задачу
     *
     * @param task принимает значение задачи
     */
    public void changeTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.remove(task.getId());
            tasks.put(task.getId(), task);
        }
    }
    /**
     *Метод обновляет выбранную подзадачу
     *
     * @param subtask принимает значение задачи
     */
    public void changeSubtask(Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);

        if (subtasks.containsKey(subtask.getId())) {
            subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
        }
        updateEpicStatus(epic);
    }
    /**
     *Метод обновляет выбранный эпик
     *
     * @param epic принимает значение эпика
     */
    public void changeEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.remove(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }
    /**
     *Метод удаляет выбранную задачу
     *
     * @param task принимает значение задачи
     */
    public void deleteTask (Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.remove(task.getId());
        }
    }
    /**
     *Метод удаляет выбранный эпик и его подзадачи
     *
     * @param epic принимает значение эпика
     */
    public void deleteEpic (Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();


        if (epics.containsKey(epic.getId())) {
            for (int subtaskId : subtaskIds) {

                Subtask subtask = subtasks.get(subtaskId);
                deleteEpicSubtasks(subtask);
            }
            epics.remove(epic.getId());
        }
    }
    /**
     *Метод удаляет подзадачи при удалении эпика
     *
     * @param subtask принимает значение подзадачи
     */
    public void deleteEpicSubtasks (Subtask subtask) {


        if (subtasks.containsKey(subtask.getId())) {

            subtasks.remove(subtask.getId());

        }
    }
    /**
     *Метод удаляет подзадачу
     *
     * @param subtask принимает значение подзадачи
     */
    public void deleteSubtask (Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        int deleteId = 0;

        if (subtasks.containsKey(subtask.getId())) {

            deleteId = subtaskIds.indexOf(subtask.getId());
            System.out.println(deleteId);
            epic.deleteSubtaskID(deleteId);
            subtasks.remove(subtask.getId());
            updateEpicStatus(epic);
        }
    }
    /**
     *Метод получает список всех подзадач определённого эпика
     *
     * @param epicId принимает значение id эпика
     * @return ArrayList возвращает список подзадач
     */
    public ArrayList<Subtask> findEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        return new ArrayList<>(subtasks.values());
    }
    /**
     *Метод добавляет задачу
     *
     * @param task принимает значение задачи
     */
    public void addTask(Task task) {

        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;

    }
    /**
     *Метод добавляет подзадачу
     *
     * @param subtask принимает значение подзадачи
     */
    public void addSubtask(Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        subtask.setId(generatorId);
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtaskID(subtask.getId());
        updateEpicStatus(epic);
        generatorId++;

    }
    /**
     *Метод добавляет эпик
     *
     * @param epic принимает значение эпика
     */
    public void addEpic(Epic epic) {

        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;

    }
    /**
     *Метод обновляет статус эпика
     *
     * @param epic принимает значение эпика
     */
    private void updateEpicStatus(Epic epic) {

        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus("NEW");
            return;
        }

        String status = null;
        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (status == null) {
                status = subtask.getStatus();
                continue;
            }

            if (status.equals(subtask.getStatus())) {
                continue;
            }

            epic.setStatus("IN PROGRESS");
            return;

        }
        epic.setStatus(status);

    }
}