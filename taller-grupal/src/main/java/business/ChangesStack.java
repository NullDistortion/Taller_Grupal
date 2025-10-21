package business;

import domain.Ticket;
import theNODE.DoubleNode;
import theNODE.Node;

public class ChangesStack {

    private DoubleNode<Ticket> undo;
    private DoubleNode<Ticket> tail;
    private Node<Ticket> redo;
    private final int MAX_LIMIT = 6;
    private int currentSize;

    public ChangesStack() {
        this.undo = null;
        this.redo = null;
        this.tail = null;
        this.currentSize = 0;
    }

    //Inserta un elemento arriba de la pila UNDO para los cambios que se rehacen
    public void pushUndo(Ticket value) {
        DoubleNode newNode = new DoubleNode(value);

        if (this.undo == null) {
            this.undo = newNode;
            this.tail = newNode;
        } else {

            newNode.setNext(this.undo);
            this.undo.setPrev(newNode);
            this.undo = newNode;

        }
        this.currentSize++;
        if (currentSize >= MAX_LIMIT) {
            removeTail();
        }
    }

    // Elimina y devuelve el elemento de la cima de la pila UNDO.
    public Ticket popUndo() {
        if (isEmptyUndo()) {
            return null;
        }

        DoubleNode<Ticket> auxNode = this.undo;
        this.undo = auxNode.getNext();

        if (this.undo != null) {
            this.undo.setPrev(null);
        } else {
            this.tail = null;
        }

        this.currentSize--;
        return auxNode.getData();
    }

    //Inserta un elemento arriba de la pila REDO para los cambios que se rehacen
    public void pushRedo(Ticket value) {
        Node newNode = new Node(value);

        if (this.redo == null) {
            this.redo = newNode;
        } else {

            newNode.setNext(this.redo);
            this.redo = newNode;

        }
    }

    public Ticket popRedo() {
        if (isEmptyRedo()) {
            return null;
        }

        Node<Ticket> auxNode = this.redo;
        this.redo = auxNode.getNext();

        return auxNode.getData();
    }

    public void removeTail() {

        if (this.tail == null) {
            return;
        }

        DoubleNode<Ticket> newTail = this.tail.getPrev();

        if (newTail != null) {
            newTail.setNext(null);
            this.tail.setPrev(null);
            this.tail = newTail;
        } else {
            this.undo = null;
            this.tail = null;
        }

        this.currentSize--;
    }

    public void clearRedo() {
        this.redo = null;
    }

    public boolean isEmptyUndo() {
        return this.undo == null;
    }

    public boolean isEmptyRedo() {
        return this.redo == null;
    }

    public int size() {
        return currentSize;
    }
}
