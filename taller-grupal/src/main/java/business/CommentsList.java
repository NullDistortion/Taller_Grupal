package business;

import domain.Comments;
import java.time.LocalDate;
import theNODE.Node;

public class CommentsList {

    private Node<Comments> head;

    public CommentsList() {
        this.head = null;
    }

    //Constructor de Copia (Deep Copy).
    public CommentsList(CommentsList other) {
        this.head = null;
        if (other.head != null) {
            Node<Comments> currentOther = other.head;
            Node<Comments> tailThis = null;

            while (currentOther != null) {
                Comments clonedComment = new Comments(
                        currentOther.getData().getDescription(),
                        currentOther.getData().getDate()
                );
                Node<Comments> newNode = new Node<>(clonedComment);

                if (this.head == null) {
                    this.head = newNode;
                    tailThis = newNode;
                } else {
                    tailThis.setNext(newNode);
                    tailThis = newNode;
                }
                currentOther = currentOther.getNext();
            }
        }
    }

    public void add(String description) throws IllegalArgumentException {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("No se puede añadir un comentario que no existe");
            return;
        }

// La validación del constructor de Comments lanza la excepción
        Comments newComment = new Comments(description, LocalDate.now());
        Node<Comments> newNode = new Node<>(newComment);

        if (head == null) {
            head = newNode;
        } else {
            Node<Comments> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    public boolean deleteComment(String description) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Descripcion vacia, no eliminar comentario");
            return false;
        }
        if (head == null) {
            return false;
        }
        if (head.getData().getDescription().equals(description)) {
            head = head.getNext();
            return true;
        }
        Node<Comments> previous = head;
        Node<Comments> current = head.getNext();
        while (current != null) {
            if (current.getData().getDescription().equalsIgnoreCase(description)) {
                previous.setNext(current.getNext());
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false; // No se encontró
    }

    public boolean updateComment(String oldDescription, String newDescription) {
        if (oldDescription == null || newDescription == null
                || oldDescription.trim().isEmpty() || newDescription.trim().isEmpty()) {
            System.out.println("Descripciones vacia, no se puede actualizar");
            return false;
        }
        Node<Comments> current = head;
        while (current != null) {
            if (current.getData().getDescription().equalsIgnoreCase(oldDescription)) {
                current.getData().setDescription(newDescription.trim());
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[Sin Comentarios]";
        }
        StringBuilder sb = new StringBuilder("[Comentarios: ");
        Node<Comments> current = head;
        while (current != null) {
            sb.append(current.getData().toString());
            if (current.getNext() != null) {
                sb.append(" | ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
