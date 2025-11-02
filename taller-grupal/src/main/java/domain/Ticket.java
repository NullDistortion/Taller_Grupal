package domain;

import enums.Status;
import enums.Type;

public class Ticket {
    private Person person;
    private Type type;
    private Status status;
    private boolean priority; //TODO Setear logica de inicio, valor por defecto ahora false

    public Ticket(Person person, Type type, Status status,boolean priority) {
        this.person = person;
        this.type = type;
        this.status = status;
        this.priority=priority;
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
        // Ejemplo: "[Persona: Juan Pérez (11223344) | Tipo: MATRICULA | Estado: EN_ATENCION]"
        return "[Persona: " + person.toString() + 
               " | Tipo: " + type + 
               " | Estado: " + status + "]";
    }
}
