package task;


public class Subtask extends Task {

    private int epicID;

    public Subtask(int id, String name, String description, Status status, TaskType type) {

        super(id, name, description, status, type);
    }

    public Subtask(String name, String description, Status status, TaskType type) {
        super(name, description, status, type);

    }

    public Subtask(String name, String description, Status status, TaskType type, int epicID) {
        super(name, description, status, type);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Status status, TaskType type, int epicID) {
        super(id, name, description, status, type);
        this.epicID = epicID;
    }


    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {

        this.epicID = epicID;
    }
}
