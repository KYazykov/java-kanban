package manager;

import task.Task;

import java.util.List;

/**
 * Интерфейс имеет методы по добавлению задач в историю и показ этой истории
 */
public interface HistoryManager {

    /**
     * Метод добавляет задачу в список истории просмотров
     *
     * @param task принимает значение любой задачи
     */
    void addTaskHistory(Task task);



    /**
     * Метод удаляет задачу из истории просмотров
     */
    void removeHistory(int id);

    /**
     * Метод показывает историю просмотренных задач
     */
    List<Task> getHistory();
}
