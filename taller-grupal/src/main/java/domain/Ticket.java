package domain;

import enums.Status;
import enums.Type;

public class Ticket {
    private Person person;
    private Type type;
    private Status status;

    public Ticket(Person person, Type type, Status status) {
        this.person = person;
        this.type = type;
        this.status = status;
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
    @Override
    public String toString() {
        // Ejemplo: "[Persona: Juan Pérez (11223344) | Tipo: MATRICULA | Estado: EN_ATENCION]"
        return "[Persona: " + person.toString() + 
               " | Tipo: " + type + 
               " | Estado: " + status + "]";
    }
}
