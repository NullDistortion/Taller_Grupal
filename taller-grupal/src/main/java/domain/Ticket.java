package domain;

import enums.Status;
import enums.Type;

public class Ticket {
    private Person person;
    private Type type;
    private Status status;
    private boolean priority;
    //TODO agregar ID par aubicacion en csv

    public Ticket(Person person, Type type, Status status, boolean priority) {
        this.person = person;
        this.type = type;
        this.status = status;
        this.priority = priority;
    }

    public Ticket(Ticket other) {
        this.person = new Person(other.person);
        this.type = other.type;
        this.status = other.status;
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
                "\nPrioridad: " + (priority ? "Sí" : "No");
    }
    
}
