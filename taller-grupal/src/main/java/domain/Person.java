package domain;

import theNODE.Node;

import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;

    private Node<Comments> comment;

    public Person(String name, String lastName, String identityCard, String telephone) {
        this.name = name;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.comment = null;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public Node<Comments> getComment() {
        return comment;
    }

    public void addComment(String description) {
        Comments newComment = new Comments(description, LocalDate.now());
        Node<Comments> newNode = new Node<>(newComment);

        if (comment == null) {
            comment = newNode;
        } else {
            Node<Comments> current = comment;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

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

    public boolean deleteComment(String description) {
        if (comment == null) {
            return false;
        }

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

        return false; // No se encontró
    }

    public boolean updateComment(String oldDescription, String newDescription) {
        if (newDescription == null || newDescription.isEmpty()) {
            return false;
        }

        Node<Comments> current = comment;
        while (current != null) {
            if (current.getData().getDescription().equals(oldDescription)) {
                current.getData().setDescription(newDescription);
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public String toString() {
        // Ejemplo: "Juan Pérez (11223344)"
        return name + " " + lastName + " (" + identityCard + ")";
    }
}
