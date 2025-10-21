package business;

import domain.Comments;
import theNODE.Node;

public class CommentsList {

    private Node<Comments> head;

    public CommentsList() {
        this.head = null;
    }

    public CommentsList(Node<Comments> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void addFirst(Comments comment) {
        if (comment == null) {
            return;
        }

        Node<Comments> newNode = new Node<>(comment);
        newNode.setNext(this.head);
        this.head = newNode;
    }


    public Comments findByDescription(String description) {
        Node<Comments> current = this.head;
        while (current != null) {
            // Compara la descripción del comentario actual con la buscada
            if (current.getData().getDescription().equals(description)) {
                return current.getData();
            }
            current = current.getNext(); 
        }
        return null; // No se encontró
    }

    public boolean deleteByDescription(String description) {
        if (isEmpty()) {
            return false; // No hay nada que borrar
        }

        if (head.getData().getDescription().equals(description)) {
            head = head.getNext();
            return true;
        }

        Node<Comments> previous = head;
        Node<Comments> current = head.getNext();

        while (current != null) {
            if (current.getData().getDescription().equals(description)) {
                previous.setNext(current.getNext());
                return true; 
            }
     
            previous = current;
            current = current.getNext();
        }

        return false; // No se encontró el elemento
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<Comments> current = head;
        while (current != null) {
            sb.append(current.getData().toString()); 
            if (current.getNext() != null) {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
