package task;


import java.time.Duration;
import java.time.ZonedDateTime;

public class Subtask extends Task {

    private int epicIDSubtask;

    public Subtask(String name, String description, Status status, TaskType type, int epicIDSubtask,
                   ZonedDateTime startTime, Duration duration) {
        super(name, description, status, type, startTime, duration);
        this.epicIDSubtask = epicIDSubtask;
    }

    public Subtask(int id, String name, String description, Status status, TaskType type, int epicIDSubtask,
                   ZonedDateTime startTime, Duration duration) {
        super(id, name, description, status, type, startTime, duration);
        this.epicIDSubtask = epicIDSubtask;
    }


    public Integer getEpicID() {
        return epicIDSubtask;
    }

    public void setEpicID(int epicID) {

        this.epicIDSubtask = epicIDSubtask;
    }
}
