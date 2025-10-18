package domain;

import enums.Status;
import enums.Type;

public class Person {
    private String name;
    private String lastname;
    private String identityCard;
    private String telephone;

    private Status status;
    private Type type;

    public Person(String name, String lastname, String identityCard, String telephone, Status status, Type type) {
        this.name = name;
        this.lastname = lastname;
        this.identityCard = identityCard;
        this.telephone = telephone;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Nombre: %s %s%nCédula: %s%nTeléfono: %s%nEstado: %s%nTipo: %s%n",
                name, lastname, identityCard, telephone, status, type
        );
    }
}
