package task;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Task {


    private TaskType type;
    private int id;
    private String name;
    private String description;
    private Status status;
    private int epicID;


    private ZonedDateTime startTime;
    private Duration duration;

    public Task(int id, String name, String description, Status status,
                ZonedDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(int id, String name, String description, Status status, TaskType type,
                ZonedDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public Task(int id, String name, String description, Status status, TaskType type, int epicID,
                ZonedDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.epicID = epicID;
    }

    public Task(String name, String description, Status status,
                ZonedDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, Status status, TaskType type,
                ZonedDateTime startTime, Duration duration) {
        this.startTime = startTime;
        this.duration = duration;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public Task() {

    }


    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public Status getStatus() {

        return status;
    }

    public Status setStatus(Status status) {

        this.status = status;
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, description, status);
    }

    @Override
    public String toString() {
        return "Task.Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getEpicID() {
        return null;
    }

    public void setEpicID(int epicID) {

        this.epicID = epicID;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
