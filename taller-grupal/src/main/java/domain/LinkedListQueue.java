
package domain;

import theNODE.Node;

public class LinkedListQueue<T> implements Queue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int length;

    @Override
    public void enqueue(T element) {
        if (element == null) return;
        Node<T> newNode = new Node<>(element);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        length++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) return null;
        T data = head.getData();
        head = head.getNext();
        if (head == null) tail = null;
        length--;
        return data;
    }

    @Override
    public T peek() {
        return isEmpty() ? null : head.getData();
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public String toString() {
        if (isEmpty()) return "No hay tickets en esta cola.\n";

        StringBuilder sb = new StringBuilder();
        Node<T> current = head;
        int index = 1;

        while (current != null) {
            sb.append(index++)
                    .append(") ")
                    .append(current.getData().toString()) // usa el toString() del Ticket
                    .append("\n--------------------------------------\n");
            current = current.getNext();
        }

        return sb.toString();
    }
}