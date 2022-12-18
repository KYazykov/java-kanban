package task;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;


public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();


    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }


    public Epic(int id, String name, String description, Status status, TaskType type,
                ZonedDateTime startTime, Duration duration) {
        super(id, name, description, status, type, startTime, duration);
        subtaskIds = new ArrayList<>();
    }

    public Epic(String name, String description, Status status, TaskType type,
                ZonedDateTime startTime, Duration duration) {
        super(name, description, status, type, startTime, duration);

    }

    public Epic(int id, String name, String description, Status status, TaskType type, ArrayList<Integer> subtaskIds,
                ZonedDateTime startTime, Duration duration) {
        super(id, name, description, status, type, startTime, duration);
        this.subtaskIds = subtaskIds;
    }

    public Epic(String name, String description, Status status, TaskType type, ArrayList<Integer> subtaskIds,
                ZonedDateTime startTime, Duration duration) {
        super(name, description, status, type, startTime, duration);
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskID(int subtaskId) {

        subtaskIds.add(subtaskId);

    }

    public void deleteSubtaskID(int subtaskId) {

        subtaskIds.remove(Integer.valueOf(subtaskId));

    }
    @Override
    public Integer getEpicID() {
        return null;
    }
}
