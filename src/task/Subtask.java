package task;


public class Subtask extends Task {

    private int epicID;

    public Subtask(int id, String name, String description, Status status) {

        super(id, name, description, status);
    }

    public Subtask(String name, String description, Status status) {
        super(name, description, status);

    }

    public Subtask(String name, String description, Status status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Status status, int epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public Integer getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }
}
