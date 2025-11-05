package domain;

import enums.Status;
import enums.Type;

public class Ticket {
    private int id;
    private Person person;
    private Type type;
    private Status status;
    private boolean priority;

    public Ticket(int id,Person person, Type type, Status status, boolean priority) {
        this.id=id;
        this.person = person;
        this.type = type;
        this.status = status;
        this.priority = priority;
    }

    public Ticket(Ticket other) {
        this.id=other.id;
        this.person = new Person(other.person);
        this.type = other.type;
        this.status = other.status;
        this.priority=other.priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "\nPersona: " + person.getName() + " " + person.getLastName() +
                "\nTipo: " + type +
                "\nEstado: " + status +
                "\nPrioridad: " + (priority ? "Si" : "No");
    }
}