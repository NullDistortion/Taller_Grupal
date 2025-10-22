package domain;

import java.time.LocalDate;

public class Comments {

    private String description;
    private LocalDate date;

    public Comments(String description, LocalDate date) throws IllegalArgumentException {

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía.");
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

    @Override
    public String toString() {
        return "[" + date.toString() + ": " + description + "]";
    }
}
