package task.manager;

import task.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Класс реализует методы интерфейса по истории просмотра задачи
 *
 */
public class InMemoryHistoryManager implements HistoryManager {

    Map <Integer, Node> nodes = new HashMap<>();

    Node first;
    Node last;

    private final LinkedList<Task> history = new LinkedList<>();

    /**
     * Метод привязывает ноду к последнему указателю
     *
     */
    public void linkLast(Task task) {
        final Node newNode = new Node(task, last, null);
        if (first == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        last = newNode;
    }

    @Override
    public List<Task> getHistory() {

        Node curNode = first;
        while (curNode != null){
            history.add(curNode.value);
            curNode = curNode.next;
        }

        return history;
    }

    @Override
    public void addTaskHistory(Task task) {
        if (task == null) {
            return;
        }
        removeHistory(task.getId());
        linkLast(task);
        nodes.put(task.getId(), last);

    }
    @Override
    public void removeHistory(int id) {
        Node node = nodes.get(id);
        if (node == null) {
            return;
        }
        if (node != first) {
            node.prev.next = node.next;
            if (node.next == null) {
            last = node.prev;
            }
            if (node.next != null){
                node.next.prev = node.prev;
            }
        }
        if (node == first) {
            first = node.next;
            if (first == null) {
            last = null;
            }
            if (first != null) {
                first.prev = null;
            }
        }
    }

   static class Node {
        private Task value;
        private Node prev;
        private Node next;
        public Node (Task value, Node prev, Node next) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

       public Task getValue() {
           return value;
       }

       public void setValue(Task value) {
           this.value = value;
       }

       public Node getPrev() {
           return prev;
       }

       public void setPrev(Node prev) {
           this.prev = prev;
       }

       public Node getNext() {
           return next;
       }

       public void setNext(Node next) {
           this.next = next;
       }
   }
}
