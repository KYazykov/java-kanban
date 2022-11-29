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

    public Epic(String name, String description, Status status) {
        super(name, description, status);

    }

    public Epic(int id, String name, String description, Status status, ArrayList<Integer> subtaskIds) {
        super(id, name, description, status);
        this.subtaskIds = subtaskIds;
    }

    public Epic(String name, String description, Status status, ArrayList<Integer> subtaskIds) {
        super(name, description, status);
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
