package Server;

import Server.Handlers.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final HttpServer server;

    public HttpTaskServer() throws IOException, InterruptedException {
        Gson gson = new Gson();
        HistoryManager historyManager = Managers.getDefaultHistory();
        TaskManager taskManager = Managers.getDefault(historyManager);
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks/task/", new TaskHandler(taskManager));
        server.createContext("/tasks/epic/", new EpicHandler(taskManager));
        server.createContext("/tasks/subtask/", new SubtaskHandler(taskManager));
        server.createContext("/tasks/subtask/epic/", new EpicSubtasksHandler(taskManager));
        server.createContext("/tasks/history/", new HistoryHandler(historyManager));
        server.createContext("/tasks/", new AllTasksHandler(taskManager));
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(1);
    }

}
