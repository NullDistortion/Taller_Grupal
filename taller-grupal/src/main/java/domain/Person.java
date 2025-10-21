package domain;

import theNODE.Node;

import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;

    private Node<Comments> comment;

    public Person(Person other) {
        // Copia los valores primitivos/inmutables
        this.name = other.name;
        this.lastName = other.lastName;
        this.identityCard = other.identityCard;
        this.telephone = other.telephone;

        // Deep copy de la lista enlazada 'comment'
        this.comment = null;
        if (other.comment != null) {
            Node<Comments> currentOther = other.comment;
            Node<Comments> tailThis = null;

            while (currentOther != null) {
                // Clona el objeto Comments
                Comments clonedComment = new Comments(
                        currentOther.getData().getDescription(),
                        currentOther.getData().getDate()
                );

                Node<Comments> newNode = new Node<>(clonedComment);

                if (this.comment == null) {
                    // Es el primer nodo (head)
                    this.comment = newNode;
                    tailThis = newNode;
                } else {
                    // Añade al final
                    tailThis.setNext(newNode);
                    tailThis = newNode;
                }
                currentOther = currentOther.getNext();
            }
        }
    }

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
        // 1. Construye la información base de la persona
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(lastName);
        sb.append(" (").append(identityCard).append(")");

        // 2. Recorre la lista de comentarios y la añade
        if (comment == null) {
            sb.append(" [Sin Comentarios]");
        } else {
            sb.append(" [Comentarios: ");
            Node<Comments> current = comment;
            while (current != null) {
                // Usa el .toString() de la clase Comments
                sb.append(current.getData().toString()); 
                
                if (current.getNext() != null) {
                    sb.append(" | "); // Un separador
                }
                current = current.getNext();
            }
            sb.append("]"); // Cierre de la lista de comentarios
        }

        return sb.toString();
    }
}
