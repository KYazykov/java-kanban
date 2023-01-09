package Server;

import com.google.gson.Gson;
import manager.FileBackedTasksManager;
import manager.HistoryManager;

import java.io.IOException;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient client;
    private final Gson gson = new Gson();


    public HttpTaskManager(HistoryManager historyManager, String path) throws IOException, InterruptedException {
        super(historyManager);
        client = new KVTaskClient(path);
    }

    @Override
    public void save() {
        client.put("tasks",  gson.toJson(tasks.values()));
        client.put("subtasks", gson.toJson(subtasks.values()));
        client.put("epics", gson.toJson(epics.values()));
        client.put("history", gson.toJson(getHistory()));
    }
}
