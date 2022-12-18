package task;


import java.time.Duration;
import java.time.ZonedDateTime;

public class Subtask extends Task {

    private int epicID;

    public Subtask(String name, String description, Status status, TaskType type, int epicID,
                   ZonedDateTime startTime, Duration duration) {
        super(name, description, status, type, startTime, duration);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Status status, TaskType type, int epicID,
                   ZonedDateTime startTime, Duration duration) {
        super(id, name, description, status, type, startTime, duration);
        this.epicID = epicID;
    }


    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {

        this.epicID = epicID;
    }
}
