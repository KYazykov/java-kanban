package task;

public class Subtask extends Task {

    private int epicID;

    public Subtask(int id, String name, String description, String status) {

        super(id, name, description, status);
    }

    public Subtask(String name, String description, String status) {
        super(name, description, status);

    }

    public Subtask(String name, String description, String status, int epicID) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, String status, int epicID) {
        super(id, name, description, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }
}
