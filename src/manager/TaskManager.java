package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public ArrayList<Task> getTasks();
    public ArrayList<Epic> getEpics();
    public ArrayList<Subtask> getSubtasks();
    public void deleteAllTasks ();
    public void deleteAllEpics ();
    public void deleteAllSubtasks (Epic epic);
    public Task getTask (int id);
    public Epic  getEpic (int id);
    public Subtask getSubtask (int id);
    public void changeTask(Task task);
    public void changeSubtask(Subtask subtask);
    public void changeEpic(Epic epic);
    public void deleteTask (Task task);
    public void deleteEpic (Epic epic);
    public void deleteEpicSubtasks (Subtask subtask);
    public void deleteSubtask (Subtask subtask);
    public ArrayList<Subtask> findEpicSubtasks(int epicId);
    public void addTask(Task task);
    public void addSubtask(Subtask subtask);
    public void addEpic(Epic epic);

    List<Task> getHistory();



}
