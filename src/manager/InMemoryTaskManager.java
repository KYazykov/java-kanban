package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static task.Status.IN_PROGRESS;
import static task.Status.NEW;
import static task.TaskType.*;

public class InMemoryTaskManager implements TaskManager {

    private Status status;
    private int generatorId = 0;
    static final ZoneId zone = ZoneId.of("Europe/Moscow");
    HashSet<Task> taskPriority = new HashSet<>();


    static ZonedDateTime originalDate =
            ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0, 0), ZoneId.of("Europe/Moscow"));
    static Duration originalTime = Duration.ofMinutes(0);


    protected static HashMap<Integer, Task> tasks = new HashMap<>();
    protected static HashMap<Integer, Epic> epics = new HashMap<>();
    protected static HashMap<Integer, Subtask> subtasks = new HashMap<>();

    protected static HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
    }
    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
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
    public void deleteAllSubtasks() {

        for (Epic epic : epics.values()) {
            epic.setStatus(setStatus(NEW));
        }
        subtasks.clear();
    }

    @Override
    public Task getTask(int id) {
        if (tasks.get(id) == null) {
            System.out.println("Такой задачи не существует");
            return null;
        }
        Task task = tasks.get(id);
        historyManager.addTaskHistory(task);
        return tasks.get(task.getId());

    }

    @Override
    public Epic getEpic(int id) {
        if (epics.get(id) == null) {
            System.out.println("Такого эпика не существует");
            return null;
        }
        Epic epic = epics.get(id);
        historyManager.addTaskHistory(epic);
        return epics.get(epic.getId());
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.get(id) == null) {
            System.out.println("Такой подзадачи не существует");
            return null;
        }
        Subtask subtask = subtasks.get(id);
        historyManager.addTaskHistory(subtask);
        return subtasks.get(subtask.getId());
    }

    @Override
    public void changeTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            Task originalTask = tasks.get(task.getId());
            tasks.remove(task.getId());
            tasks.put(task.getId(), task);
            if (!task.getDuration().equals(originalTask.getDuration())
                    || !task.getStartTime().equals(originalTask.getStartTime())) {
                taskPriority = getSortedTasks();
                for (Task task1 : taskPriority) {
                    if (task1.getId() != task.getId()) {
                        if (!task1.getStartTime().equals(originalDate)) {
                            if (task.getStartTime().plus(task.getDuration()).isBefore(task1.getStartTime())
                                    || task.getStartTime().isAfter(task1.getStartTime().plus(task1.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                tasks.remove(task.getId());
                                tasks.put(task.getId(), originalTask);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void changeSubtask(Subtask subtask) {

        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);

        if (subtasks.containsKey(subtask.getId())) {
            Subtask originalSubtask = subtasks.get(subtask.getId());
            subtasks.remove(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            if (!subtask.getDuration().equals(originalSubtask.getDuration())
                    || !subtask.getStartTime().equals(originalSubtask.getStartTime())) {
                taskPriority = getSortedTasks();
                for (Task task : taskPriority) {
                    if (task.getId() != subtask.getId()) {
                        if (!task.getStartTime().equals(originalDate)) {
                            if (subtask.getStartTime().plus(subtask.getDuration()).isBefore(task.getStartTime())
                                    || subtask.getStartTime().isAfter(task.getStartTime().plus(task.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                subtasks.remove(subtask.getId());
                                subtasks.put(subtask.getId(), originalSubtask);
                                return;
                            }
                        }
                    }
                }
            }
            getEndTime(epic);
            updateEpicStatus(epic);
        }
    }

    @Override
    public void changeEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic originalEpic = epics.get(epic.getId());
            epics.remove(epic.getId());
            epics.put(epic.getId(), epic);
            if (!epic.getDuration().equals(originalEpic.getDuration())
                    || !epic.getStartTime().equals(originalEpic.getStartTime())) {
                taskPriority = getSortedTasks();
                for (Task task : taskPriority) {
                    if (task.getId() != epic.getId()) {
                        if (!task.getStartTime().equals(originalDate)) {
                            if (epic.getStartTime().plus(epic.getDuration()).isBefore(task.getStartTime())
                                    || epic.getStartTime().isAfter(task.getStartTime().plus(task.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                epics.remove(epic.getId());
                                epics.put(epic.getId(), originalEpic);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void deleteTask(int id) {
        if (tasks.get(id) != null) {
            tasks.remove(id);
            historyManager.removeHistory(id);
        } else {
            System.out.println("Такой задачи не существует");
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (epics.get(id) != null) {
            Epic epic = epics.get(id);
            ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
            for (int subtaskId : subtaskIds) {

                subtasks.remove(subtaskId);
                historyManager.removeHistory(subtaskId);
            }
            epics.remove(id);
            historyManager.removeHistory(id);

        } else {
            System.out.println("Такого эпика не существует");
        }
    }


    @Override
    public void deleteSubtask(int id) {
        if (subtasks.get(id) != null) {
        Subtask subtask = subtasks.get(id);
        int epicId = subtask.getEpicID();
        Epic epic = epics.get(epicId);
        epic.deleteSubtaskID(id);
        subtasks.remove(id);
        updateEpicStatus(epic);
        historyManager.removeHistory(id);

        } else {
            System.out.println("Такой подзадачи не существует");
        }
    }

    @Override
    public ArrayList<Subtask> findEpicSubtasks(int epicId) {
        ArrayList<Subtask> epicSubtasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return null;
        }
        List<Integer> ids = epic.getSubtaskIds();
        for (Integer id : ids) {
            epicSubtasks.add(subtasks.get(id));
        }

        return epicSubtasks;
    }

    @Override
    public void addTask(Task task) {

        if (task.getType().equals(TASK)) {
            task.setId(generatorId);
            tasks.put(task.getId(), task);
            generatorId++;
            if (!tasks.isEmpty() || !epics.isEmpty() || !subtasks.isEmpty()) {
                taskPriority = getSortedTasks();
                for (Task task1 : taskPriority) {
                    if (task1.getId() != task.getId()) {
                        if (!task1.getStartTime().equals(originalDate)) {
                            if (task.getStartTime().plus(task.getDuration()).isBefore(task1.getStartTime())
                                    || task.getStartTime().isAfter(task1.getStartTime().plus(task1.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                deleteTask(task.getId());
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Передан неправильный тип задачи");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask.getType().equals(SUBTASK)) {
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

            if (!tasks.isEmpty() || !epics.isEmpty() || !subtasks.isEmpty()) {
                taskPriority = getSortedTasks();
                for (Task task : taskPriority) {
                    if (task.getId() != subtask.getId()) {
                        if (!task.getStartTime().equals(originalDate)) {
                            if (subtask.getStartTime().plus(subtask.getDuration()).isBefore(task.getStartTime())
                                    || subtask.getStartTime().isAfter(task.getStartTime().plus(task.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                deleteSubtask(subtask.getId());
                                return;
                            }
                        }
                    }
                }
            }
            getEndTime(epic);
        } else {
            System.out.println("Передан неправильный тип задачи");
        }

    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getType().equals(EPIC)) {
            epic.setId(generatorId);
            epics.put(epic.getId(), epic);
            generatorId++;
            if (!tasks.isEmpty() || !epics.isEmpty() || !subtasks.isEmpty()) {
                taskPriority = getSortedTasks();
                for (Task task : taskPriority) {
                    if (task.getId() != epic.getId()) {
                        if (!task.getStartTime().equals(originalDate)) {
                            if (epic.getStartTime().plus(epic.getDuration()).isBefore(task.getStartTime())
                                    || epic.getStartTime().isAfter(task.getStartTime().plus(task.getDuration()))) {
                            } else {
                                System.out.println("Ошибка, произошло пересечение задач");
                                deleteEpic(epic.getId());
                                return;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Передан неправильный тип задачи");
        }
    }

    /**
     * Метод обновляет статус эпика
     *
     * @param epic принимает значение эпика
     */
    private void updateEpicStatus(Epic epic) {
        if (epic.getType().equals(EPIC)) {

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
    }


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public ZonedDateTime getEndTime(Task task) {
        ZonedDateTime endTime;
        if (tasks.containsValue(task) || epics.containsValue(task) || subtasks.containsValue(task)) {
            ZonedDateTime original =
                    ZonedDateTime.of(LocalDateTime.of(2100, 1, 1, 0, 0), zone);
            Duration max = Duration.ofMinutes(0);
            if (task.getType() == EPIC) {
                Epic epic = (Epic) task;
                ArrayList<Integer> subtaskIds = epic.getSubtaskIds();
                if (subtaskIds.isEmpty()) {
                    epic.setStartTime(originalDate);
                    epic.setDuration(originalTime);
                    System.out.println("У эпика отсутствует дата начала и продолжительность, так как нет подзадач");
                    return null;
                }
                for (int subtaskId : subtaskIds) {
                    Subtask subtask = subtasks.get(subtaskId);
                    max = max.plus(subtask.getDuration());
                    if (subtask.getStartTime().isBefore(original)) {
                        original = subtask.getStartTime();
                    }
                }
                epic.setStartTime(original);
                epic.setDuration(max);
                endTime = original.plus(max);
                return endTime;
            }
            endTime = task.getStartTime().plus(task.getDuration());
        } else {
            System.out.println("Такого объекта не существует");
            return null;
        }
        return endTime;
    }

    /**
     * Класс является компаратором для метода сортировки
     */
    static class myTimeComparator implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            return o1.getStartTime().compareTo(o2.getStartTime());
        }
    }

    @Override
    public ArrayList<Task> getPrioritizedTasks() { // TreeSet недоступен, так как в него не добавляются subtasks
        ArrayList<Epic> epicsWithSubtasks = new ArrayList<>();

        ArrayList<Task> sortedPrioritizedTasks = new ArrayList<>(tasks.values());
        for (Integer id : epics.keySet()) {
            if (!findEpicSubtasks(id).isEmpty()) {
                sortedPrioritizedTasks.add(epics.get(id));
            } else {
                epicsWithSubtasks.add(epics.get(id));
            }
        }
        sortedPrioritizedTasks.addAll(subtasks.values());
        sortedPrioritizedTasks.sort(new myTimeComparator());
        if (!epicsWithSubtasks.isEmpty()) {
            sortedPrioritizedTasks.addAll(epicsWithSubtasks);
        }
        return sortedPrioritizedTasks;
    }

    /**
     * Метод для создания списка всех задач, используется в методе добавления и изменения задач
     *
     * @return список всех задач
     */
    public HashSet<Task> getSortedTasks() {
        HashSet<Task> sortedTasks = new HashSet<>();

        sortedTasks.addAll(tasks.values());
        sortedTasks.addAll(epics.values());
        sortedTasks.addAll(subtasks.values());
        return sortedTasks;
    }
}