package business;

import domain.Ticket;
import theNODE.DoubleNode;
import theNODE.Node;

public class UndoRedoManager {

    private DoubleNode<Ticket> undo;
    private DoubleNode<Ticket> tail;
    private Node<Ticket> redo;
    private final int MAX_LIMIT = 6;
    private int currentSize;

    public UndoRedoManager() {
        this.undo = null;
        this.redo = null;
        this.tail = null;
        this.currentSize = 0;
    }

    // Metodo para agregar una accion a la pila de UNDO
    public void pushUndo(Ticket value) {
        if (value == null) {
            return;
        }
        if (currentSize >= MAX_LIMIT) {
            removeTail();
        }
        DoubleNode<Ticket> newNode = new DoubleNode<>(value);
        if (undo == null) {
            undo = newNode;
            tail = newNode;
        } else {
            newNode.setNext(undo);
            undo.setPrev(newNode);
            undo = newNode;
        }
        currentSize++;
    }

    // Metodo para eliminar el ultimo elemento agregado a la pila UNDO
    public Ticket popUndo() {
        if (isEmptyUndo()) {
            return null;
        }
        DoubleNode<Ticket> auxNode = undo;
        undo = auxNode.getNext();
        if (undo != null) {
            undo.setPrev(null);
        } else {
            tail = null;
        }
        currentSize--;
        return auxNode.getData();
    }

    // Metodo para agregar una accion a la pila REDO
    public void pushRedo(Ticket value) {
        if (value == null) {
            return;
        }
        Node<Ticket> newNode = new Node<>(value);
        newNode.setNext(redo);
        redo = newNode;
    }

    // Metodo para eliminar el ultimo elemento agregado a la pila REDO
    public Ticket popRedo() {
        if (isEmptyRedo()) {
            return null;
        }
        Node<Ticket> auxNode = redo;
        redo = auxNode.getNext();
        return auxNode.getData();
    }

    // Metodo para eliminar el ultimo elemento (tail) de la pila UNDO cuando excede el limite
    public void removeTail() {
        if (tail == null) {
            return;
        }
        DoubleNode<Ticket> newTail = tail.getPrev();
        if (newTail != null) {
            newTail.setNext(null);
            tail.setPrev(null);
            tail = newTail;
        } else {

            undo = null;
            tail = null;
        }
        currentSize--;
    }

    // Metodo para limpiar la pila REDO
    public void clearRedo() {
        this.redo = null;
    }

    // Metodo para verificar si la pila UNDO esta vacia
    public boolean isEmptyUndo() {
        return this.undo == null;
    }

    // Metodo para verificar si la pila REDO esta vacia
    public boolean isEmptyRedo() {
        return this.redo == null;
    }

    // Metodo para obtener el tamaño actual de la pila UNDO
    public int size() {
        return currentSize;
    }

    public Node<Ticket> getRedo() {
        return redo;
    }

    public void setRedo(Node<Ticket> redo) {
        this.redo = redo;
    }

}
