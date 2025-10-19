/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package business;

import domain.Ticket;
import theNODE.Node;



/**
 *
 * @author
 */
public class TicketsQueue {

    Node<Ticket> queue;

    private Node<Ticket> head;
    private Node<Ticket> tail;
    private int length;

    // Agregar un elemento al final
    public void enqueue(Ticket node) {
        Node<Ticket> newnode = new Node<>(node);
        if (isEmpty()) {
            head = tail = newnode;
        } else {
            tail.setNext(newnode);
            tail = newnode;
        }
        length++;
    }

    // Eliminar y devolver el elemento del frente
    public Ticket dequeue() {
        if (isEmpty()) {
            return null;
        }
        Ticket aux = head.getData();
        head=head.getNext();
        if (head == null) {
            tail = null; // cola vacía, -> ambos null
        }
        length--;
        return aux;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    // Ver el elemento del frente sin eliminarlo
    public String peek() {
        return isEmpty() ? null : head.getData().toString();
    }

    public int size() {
        return length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<Ticket> actual = head;
        while (actual != null) {
            sb.append(actual.toString());
            if (actual.getNext().toString() != null) sb.append(" <- ");
            actual = actual.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

}
