package domain;

import java.time.LocalDate;

public class Comments {

    private String description;
    private LocalDate date;
    private int id;

    public Comments(String description, LocalDate date) throws IllegalArgumentException {

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede ser nula o vacia.");
        }
        this.description = description;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[" + date.toString() + ": " + description + "]";
    }
}