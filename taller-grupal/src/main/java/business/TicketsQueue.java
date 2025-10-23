package business;

import domain.Ticket;
import theNODE.Node;

public class TicketsQueue {

    private Node<Ticket> head;
    private Node<Ticket> tail;
    private int length;

    // Metodo para insertar un ticket al final de la cola
    public void enqueue(Ticket node) {
        if (node == null) {
            System.out.println("No se puede agregar un ticket nulo");
            return;
        }
        Node<Ticket> newNode = new Node<>(node);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        length++;
    }

    // Metodo para eliminar el primer elemento de la cola
    public Ticket dequeue() {
        if (isEmpty()) {
            System.out.println("No hay tickets en la cola");
            return null;
        }
        Ticket aux = head.getData();
        head = head.getNext();
        if (head == null) tail = null;
        length--;
        return aux;
    }

    // Metodo que devuelve verdadero si la cola esta vacia
    public boolean isEmpty() {
        return length == 0;
    }

    // Metodo que devuelve el primer elemento de la cola sin eliminarlo
    public String peek() {
        if (isEmpty()) {
            System.out.println("La cola esta vacia");
            return null;
        }
        return head.getData().toString();
    }

    // Metodo que devuelve el tamaño de la cola
    public int size() {
        return length;
    }

    // Metodo toString que muestra todos los tickets en la cola
    @Override
    public String toString() {
        if (isEmpty()) {
            return "La cola esta vacia.";
        }
        StringBuilder sb = new StringBuilder("[");
        Node<Ticket> actual = head;
        while (actual != null) {
            sb.append(actual.getData().toString());
            if (actual.getNext() != null) sb.append(" <- ");
            actual = actual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
