package manager;

import task.Task;

import java.util.*;

/**
 * Класс реализует методы интерфейса по истории просмотра задачи
 *
 */
public class InMemoryHistoryManager implements HistoryManager {

    private final Map <Integer, Node> nodes = new HashMap<>();

    Node first;
    Node last;



    /**
     * Метод привязывает ноду к последнему указателю
     *
     */
    private void linkLast(Task task) {
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
        final List<Task> history = new ArrayList<>();

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

    /**
     * Класс создает связной список для методов по просмотру истории
     *
     */
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
