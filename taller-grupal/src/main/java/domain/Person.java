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

}
