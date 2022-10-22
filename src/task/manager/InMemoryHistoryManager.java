package task.manager;

import task.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void addTaskHistory(Task task) {
        if (history.size() < 10) {
            history.add(task);

        } else {
            history.removeFirst();
            history.add(task);
        }
    }
}
