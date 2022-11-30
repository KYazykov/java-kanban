package task;

import java.util.ArrayList;


public class Epic extends Task {

    private ArrayList<Integer> subtaskIds = new ArrayList<>();




    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }


    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
        subtaskIds = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, TaskType type) {
        super(id, name, description, status, type);
        subtaskIds = new ArrayList<>();
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);

    }
    public Epic(String name, String description, Status status, TaskType type) {
        super(name, description, status, type);

    }

    public Epic(int id, String name, String description, Status status, TaskType type, ArrayList<Integer> subtaskIds) {
        super(id, name, description, status, type);
        this.subtaskIds = subtaskIds;
    }

    public Epic(String name, String description, Status status, TaskType type, ArrayList<Integer> subtaskIds) {
        super(name, description, status, type);
        this.subtaskIds = subtaskIds;
    }

    public void addSubtaskID(int subtaskId) {


        subtaskIds.add(subtaskId);

    }

    public void deleteSubtaskID(int subtaskId) {


        subtaskIds.remove(subtaskId);

    }
    @Override
    public Integer getEpicID() {
        return null;
    }
}
