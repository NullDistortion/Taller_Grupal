package domain;

import business.CommentsList;

public class Person {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;

    private CommentsList comments;

    //Constructor de copia
    public Person(Person other) {
        if (other == null) {
            throw new IllegalArgumentException("No se puede copiar un comentario que no existe");
        }
        this.name = other.name;
        this.lastName = other.lastName;
        this.identityCard = other.identityCard;
        this.telephone = other.telephone;
        this.comments = new CommentsList(other.comments);
    }

    public Person(String name, String lastName, String identityCard, String telephone) {
        this.name = name;
        this.lastName = lastName;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.comments = new CommentsList();
    }

    //Refactorizacion delegada a CommentList
    public void addComment(String description) throws IllegalArgumentException {
        this.comments.add(description);
    }

    //Refactorizacion delegada a CommentList
    public boolean deleteComment(String description) {
        return this.comments.deleteComment(description);
    }

    //Refactorizacion delegada a CommentList
    public boolean updateComment(String oldDescription, String newDescription) {
        return this.comments.updateComment(oldDescription, newDescription);
    }

    //Refactorizacion delegada a CommentList
    public void printComments() {
        if (comments == null) {
            System.out.println("No hay comentarios para " + this.name);
            return;
        }
        System.out.println("Comentarios para: " + this.name + " " + this.lastName);
        System.out.println("  -> " + this.comments.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(lastName);
        sb.append(" (").append(identityCard).append(")");

        sb.append(" ").append(this.comments.toString());

        return sb.toString();
    }
}
