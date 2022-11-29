package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static task.Status.NEW;
import static task.Status.IN_PROGRESS;

public class InMemoryTaskManager implements TaskManager {

    private Status status;
    private int generatorId = 0;



    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }




    public Status getStatus() {

        return status;
    }

    public Status setStatus(Status status) {

        this.status = status;
        return status;
    }


    @Override
    public ArrayList<Task> getTasks() {

        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {

        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {

        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks(Epic epic) {

        subtasks.clear();
        updateEpicStatus(epic);
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.addTaskHistory(task);
        return tasks.get(task.getId());

    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.addTaskHistory(epic);
        return epics.get(epic.getId());
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.addTaskHistory(subtask);
        return subtasks.get(subtask.getId());
    }

    @Override
    public void changeTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.remove(task.getId());
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void changeSubtask(Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);

        if (subtasks.containsKey(subtask.getId())) {
            subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
        }
        updateEpicStatus(epic);
    }

    @Override
    public void changeEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.remove(epic.getId());
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public void deleteTask(Task task) {

        tasks.remove(task.getId());
        historyManager.removeHistory(task.getId());
    }

    @Override
    public void deleteEpic(Epic epic) {
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        for (int subtaskId : subtaskIds) {

            Subtask subtask = subtasks.get(subtaskId);
            deleteEpicSubtasks(subtask);
        }
        epics.remove(epic.getId());
        historyManager.removeHistory(epic.getId());
    }

    @Override
    public void deleteEpicSubtasks(Subtask subtask) {

        subtasks.remove(subtask.getId());
        historyManager.removeHistory(subtask.getId());
    }

    @Override
    public void deleteSubtask(Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        int deleteId = 0;

        if (subtasks.containsKey(subtask.getId())) {

            deleteId = subtaskIds.indexOf(subtask.getId());
            epic.deleteSubtaskID(deleteId);
            subtasks.remove(subtask.getId());
            updateEpicStatus(epic);
            historyManager.removeHistory(subtask.getId());
        }
    }

    @Override
    public ArrayList<Subtask> findEpicSubtasks(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void addTask(Task task) {

        task.setId(generatorId);
        tasks.put(task.getId(), task);
        generatorId++;

    }

    @Override
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

    @Override
    public void addEpic(Epic epic) {

        epic.setId(generatorId);
        epics.put(epic.getId(), epic);
        generatorId++;

    }

    /**
     * Метод обновляет статус эпика
     *
     * @param epic принимает значение эпика
     */
    private void updateEpicStatus(Epic epic) {


        ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
        if (subtaskIds.isEmpty()) {
            epic.setStatus(setStatus(NEW));
            return;
        }

        Status status = null;
        for (int subtaskId : subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            if (status == null) {
                status = subtask.getStatus();
                continue;
            }

            if (status.equals(subtask.getStatus())) {
                continue;
            }

            epic.setStatus(setStatus(IN_PROGRESS));
            return;

        }
        epic.setStatus(status);

    }

    /**
     * Метод приводит к показу истории просмотренных задач
     *
     * @return возвращает метод по показу истории просмотров
     */

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


}