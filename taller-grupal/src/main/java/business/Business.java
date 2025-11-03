package business;

import domain.*;
import enums.Status;
import enums.Type;

import java.util.Scanner;

public class Business {

    private UndoRedoManager changesHistorial;
    private Queue<Ticket> ticketQueue;

    public Business() {
        this.ticketQueue = new PrioritizedTicketQueue();
        this.changesHistorial = new UndoRedoManager();
    }

    public void addToQueue(Ticket ticket) {
        ticketQueue.enqueue(ticket);
    }

    public Ticket processTicket() {
        Ticket actualTicket = ticketQueue.dequeue();

        if (actualTicket == null) {
            return null;
        }
        changesHistorial = new UndoRedoManager();
        registerChange(new Ticket(actualTicket));
        actualTicket.setStatus(Status.EN_ATENCION);
        return actualTicket;
    }

    public void registerChange(Ticket ticket) {
        changesHistorial.pushUndo(new Ticket(ticket));
        changesHistorial.clearRedo();
    }

    public Ticket undoChanges(Ticket actualTicket) {
        if (changesHistorial.isEmptyUndo()) {
            return null;
        }
        changesHistorial.pushRedo(new Ticket(actualTicket));
        return changesHistorial.popUndo();
    }

    public Ticket redoChanges(Ticket actualTicket) {
        if (changesHistorial.isEmptyRedo()) {
            return null;
        }
        changesHistorial.pushUndo(new Ticket(actualTicket));
        return changesHistorial.popRedo();
    }

    public void discardLastUndo() {
        changesHistorial.popUndo();
    }

    public void printTickets() {
        System.out.println(ticketQueue.toString());
    }

    public void addCommentToCurrentTicket(Ticket ticket, String commentDescription) throws IllegalArgumentException {

        if (ticket == null) {
            throw new IllegalArgumentException("No hay ningún ticket en atención.");
        }

        this.registerChange(ticket);
        try {
            //Business -> pide Ticket -> pide Person -> pide CommentsList -> ejecuta .add()
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            list.addComment(commentDescription);

            System.out.println("Comentario añadido.");

        } catch (IllegalArgumentException e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void deleteCommentFromCurrentTicket(Ticket ticket, int position) {
        if (ticket == null) {
            System.out.println("No hay ningún ticket en atención.");
            return;
        }
        this.registerChange(ticket);

        try {
            //    Business -> pide Ticket -> pide Person -> pide CommentsList -> ejecuta .deleteCommentByPosition()
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            boolean success = list.deleteCommentByPosition(position);
            if (!success) {
                this.discardLastUndo();
                System.out.println("No se encontró un comentario en la posición " + position);
            }
        } catch (Exception e) {
            this.discardLastUndo();
            throw e;
        }
    }

    //Actualiza un comentario del ticket que está en atención.
    public void updateCommentOnCurrentTicket(int position, String newDesc, Ticket ticket) {
        if (ticket == null) {
            System.out.println("No hay ningún ticket en atención.");
            return;
        }

        this.registerChange(ticket);
        try {
            //    Business -> pide Ticket -> pide Person -> pide CommentsList -> ejecuta .updateCommentByPosition()
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            boolean success = list.updateCommentByPosition(position, newDesc);
            if (!success) {
                this.discardLastUndo();
                System.out.println("No se encontró un comentario en la posición " + position);
            }
        } catch (Exception e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void printCommentsOfCurrentTicket(Ticket ticket) {
        if (ticket != null) {
            Person person = ticket.getPerson();
            CommentsList comments = person.getComments();

            System.out.println("Comentarios para: " + person.getName() + " " + person.getLastName());
            System.out.println("  -> " + comments.toString());
        } else {
            System.out.println("No hay ningún ticket en atención.");
        }
    }

    public boolean validateInput(String name, String lastname) {
        if (name.trim().isEmpty() || lastname.trim().isEmpty()) {
            System.out.println("No se pudo crear el ticket, datos no validos");
            return false;
        }

        if (!name.matches("[a-zA-Z]+") || !lastname.matches("[a-zA-Z]+")) {
            System.out.println("El nombre y apellido solo deben contener letras");
            return false;
        }
        return true;
    }

    public boolean valedateExistence(Ticket currentTicket) {
        if (currentTicket == null) {
            System.out.println("No hay ticket");
            return false;
        }
        return true;
    }

    public void createManualTicket() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== CREAR NUEVO TICKET ===");

        Person person = requestPersonData(sc);
        if (person == null) return;

        boolean priority = validatePrio(sc);
        Type type = selectType(sc);

        Ticket newTicket = new Ticket(person, type, Status.EN_COLA, priority);
        addToQueue(newTicket);

        System.out.println("\nTicket agregado correctamente: \n" + newTicket);
    }


    private Person requestPersonData(Scanner sc) {
        System.out.print("Nombre: ");
        String name = sc.nextLine().trim();
        System.out.print("Apellido: ");
        String lastName = sc.nextLine().trim();

        if (!validateInput(name, lastName)) {
            return null;
        }

        return new Person(name, lastName);
    }

    private boolean validatePrio(Scanner sc) {
        boolean priority = false;
        boolean valid = false;

        while (!valid) {
            System.out.print("¿Es prioridad? (s/n): ");
            String answer = sc.nextLine().trim().toLowerCase();

            switch (answer) {
                case "s":
                    priority = true;
                    valid = true;
                    break;
                case "n":
                    valid = true;
                    break;
                default:
                    System.out.println("Ingrese un parámetro válido (s/n).");
                    break;
            }
        }

        return priority;
    }

    private Type selectType(Scanner sc) {
        Type type = null;

        while (type == null) {
            System.out.println("\nSeleccione el tipo de trámite:");
            System.out.println("1. MATRICULA");
            System.out.println("2. HOMOLOGACION");
            System.out.println("3. CONTANCIA_CERTIFICADOS");
            System.out.print("Opción: ");
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    type = Type.MATRICULA;
                    break;
                case "2":
                    type = Type.HOMOLOGACION;
                    break;
                case "3":
                    type = Type.CONTANCIA_CERTIFICADOS;
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        }
        return type;
    }


    public void generateTickets() {
        //TODO metodo vacio
    }

    public void printTicketHistory() {
        //TODO conencta Bussines con FileManger para usar el archivo
    }
}
