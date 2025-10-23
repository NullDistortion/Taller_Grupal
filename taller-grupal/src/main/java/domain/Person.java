package domain;

import theNODE.Node;
import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;
    private Node<Comments> comment;

    // Constructor copia
    public Person(Person other) {
        if (other == null) throw new IllegalArgumentException("No se puede copiar un comentario que no existe");
        this.name = other.name;
        this.lastName = other.lastName;
        this.identityCard = other.identityCard;
        this.telephone = other.telephone;
        this.comment = null;
        if (other.comment != null) {
            Node<Comments> currentOther = other.comment;
            Node<Comments> tailThis = null;
            while (currentOther != null) {
                Comments clonedComment = new Comments(
                        currentOther.getData().getDescription(),
                        currentOther.getData().getDate()
                );
                Node<Comments> newNode = new Node<>(clonedComment);
                if (this.comment == null) {
                    this.comment = newNode;
                    tailThis = newNode;
                } else {
                    tailThis.setNext(newNode);
                    tailThis = newNode;
                }
                currentOther = currentOther.getNext();
            }
        }
    }

    // Constructor
    public Person(String name, String lastName, String identityCard, String telephone) {
        this.name = name;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.comment = null;
    }

    // Metodo para agregar un comentario
    public void addComment(String description) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("No se puede añadir un comentario que no existe");
            return;
        }
        Comments newComment = new Comments(description.trim(), LocalDate.now());
        Node<Comments> newNode = new Node<>(newComment);
        if (comment == null) {
            comment = newNode;
        } else {
            Node<Comments> current = comment;
            while (current.getNext() != null) current = current.getNext();
            current.setNext(newNode);
        }
    }

    // Metodo para imprimir todos los comentarios
    public void printComments() {
        if (comment == null) {
            System.out.println("No hay comentarios para " + this.name);
            return;
        }
        System.out.println("Comentarios para: " + this.toString());
        Node<Comments> current = comment;
        while (current != null) {
            System.out.println("  -> " + current.getData().toString());
            current = current.getNext();
        }
    }

    // Metodo para eliminar un comentario por descripcion
    public boolean deleteComment(String description) {
        if (description == null || description.trim().isEmpty()) {
            System.out.println("Descripcion vacia, no eliminar comentario");
            return false;
        }
        if (comment == null) return false;
        if (comment.getData().getDescription().equals(description)) {
            comment = comment.getNext();
            return true;
        }
        Node<Comments> previous = comment;
        Node<Comments> current = comment.getNext();
        while (current != null) {
            if (current.getData().getDescription().equals(description)) {
                previous.setNext(current.getNext());
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    // Metodo para actualizar un comentario
    public boolean updateComment(String oldDescription, String newDescription) {
        if (oldDescription == null || newDescription == null ||
                oldDescription.trim().isEmpty() || newDescription.trim().isEmpty()) {
            System.out.println("Descripciones vacia, no se puede actualizar");
            return false;
        }
        Node<Comments> current = comment;
        while (current != null) {
            if (current.getData().getDescription().equals(oldDescription)) {
                current.getData().setDescription(newDescription.trim());
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // Metodo toString que devuelve informacion completa de la persona
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(lastName)
                .append(" (").append(identityCard).append(")");
        if (comment == null) {
            sb.append("No hay comentarios para " + this.name);
        } else {
            sb.append(" [Comentarios: ");
            Node<Comments> current = comment;
            while (current != null) {
                sb.append(current.getData().toString());
                if (current.getNext() != null) sb.append(" | ");
                current = current.getNext();
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
