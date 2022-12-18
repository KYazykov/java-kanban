import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

public class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void beforeEachForInMemoryTaskManagerTest() {
        manager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }


}

