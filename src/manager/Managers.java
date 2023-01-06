package manager;

import Server.HttpTaskManager;
import Server.KVServer;

import java.io.IOException;

public class Managers {


        public static HttpTaskManager getDefault(HistoryManager historyManager) throws IOException, InterruptedException {
            return new HttpTaskManager(historyManager, "http://localhost:" + KVServer.PORT);
        }

        public static HistoryManager getDefaultHistory() {
            return new InMemoryHistoryManager();
        }
    }

