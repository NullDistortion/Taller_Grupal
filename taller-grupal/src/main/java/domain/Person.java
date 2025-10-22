package domain;

import business.CommentsList;
import theNODE.Node;

import java.time.LocalDate;

public class Person {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;

   private CommentsList comments;

    public Person(Person other) {
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
        return this.comments.deleteByDescription(description);
    }

   //Refactorizacion delegada a CommentList
    public boolean updateComment(String oldDescription, String newDescription) {
        return this.comments.updateByDescription(oldDescription, newDescription);
    }
    
    //Refactorizacion delegada a CommentList
    public void printComments() {
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
