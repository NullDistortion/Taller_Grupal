package domain;

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

    public CommentsList getComments() {
        return this.comments;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(lastName);
        sb.append(" (").append(identityCard).append(")");

        sb.append(" ").append(this.comments.toString());

        return sb.toString();
    }
}
