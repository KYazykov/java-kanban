package Server;

import com.google.gson.Gson;
import manager.FileBackedTasksManager;
import manager.HistoryManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import manager.InMemoryTaskManager;
import task.Task;

public class HttpTaskManager extends FileBackedTasksManager {

    private final KVTaskClient client;
    private final Gson gson = new Gson();
    private final InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    public HttpTaskManager(HistoryManager historyManager, String path) throws IOException, InterruptedException {
        super(historyManager);
        client = new KVTaskClient(path);
    }

    @Override
    public void save() {
        client.put("tasks",  gson.toJson(tasks.values()));
        client.put("subtasks", gson.toJson(subtasks.values()));
        client.put("epics", gson.toJson(epics.values()));
        client.put("history", gson.toJson(inMemoryTaskManager.getHistory()));
    }
}
