package business;

import domain.*;
import enums.Status;
import enums.Type;

import java.util.Scanner;

public class Business {

    private UndoRedoManager changesHistorial;
    private Queue<Ticket> ticketQueue;
    private FileManager fileManager;
    private Queue<Ticket> attendedTickets = new LinkedListQueue<>();


    public Business() {
        this.ticketQueue = new PrioritizedTicketQueue();
        this.changesHistorial = new UndoRedoManager();
        this.fileManager = new FileManager();
    }

    public void addToQueue(Ticket ticket) {
        ticketQueue.enqueue(ticket);
    }

    public Ticket processTicket() {
        Ticket currentTicket = ticketQueue.dequeue();

        if (!validateExistence(currentTicket)) {
            return null;
        }

        changesHistorial = new UndoRedoManager();
        registerChange(new Ticket(currentTicket));
        currentTicket.setStatus(Status.EN_ATENCION);

        return currentTicket;
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
            throw new IllegalArgumentException("No hay ningun ticket en atencion.");
        }

        this.registerChange(ticket);
        try {
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            list.addComment(commentDescription);
            System.out.println("Comentario anadido.");
        } catch (IllegalArgumentException e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void deleteCommentFromCurrentTicket(Ticket ticket, int position) {
        if (ticket == null) {
            System.out.println("No hay ningun ticket en atencion.");
            return;
        }
        this.registerChange(ticket);

        try {
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            boolean success = list.deleteCommentByPosition(position);
            if (!success) {
                this.discardLastUndo();
                System.out.println("No se encontro un comentario en la posicion " + position);
            }
        } catch (Exception e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void updateCommentOnCurrentTicket(int position, String newDesc, Ticket ticket) {
        if (ticket == null) {
            System.out.println("No hay ningun ticket en atencion.");
            return;
        }

        this.registerChange(ticket);
        try {
            Person person = ticket.getPerson();
            CommentsList list = person.getComments();
            boolean success = list.updateCommentByPosition(position, newDesc);
            if (!success) {
                this.discardLastUndo();
                System.out.println("No se encontro un comentario en la posicion " + position);
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
            System.out.println("No hay ningun ticket en atencion.");
        }
    }

    public boolean validateInput(String name, String lastname) {
        if (name.trim().isEmpty() || lastname.trim().isEmpty()) {
            System.out.println("No se pudo crear el ticket, datos no validos.");
            return false;
        }

        if (!name.matches("[a-zA-Z]+") || !lastname.matches("[a-zA-Z]+")) {
            System.out.println("El nombre y apellido solo deben contener letras.");
            return false;
        }
        return true;
    }

    public boolean validateExistence(Ticket currentTicket) {
        if (currentTicket == null) {
            System.out.println("No hay ticket.");
            return false;
        }
        return true;
    }

    public void createTicket() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n=== CREAR NUEVO TICKET ===");

        Person person = requestPersonData(sc);
        if (person == null) return;

        boolean priority = validatePrio(sc);
        Type type = selectType(sc);

        Ticket newTicket = new Ticket(person, type, Status.EN_COLA, priority);
        addToQueue(newTicket);

        System.out.println("\nTicket agregado correctamente:\n" + newTicket);
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
            System.out.print("Es prioridad? (s/n): ");
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
                    System.out.println("Ingrese un parametro valido (s/n).");
                    break;
            }
        }

        return priority;
    }

    private Type selectType(Scanner sc) {
        Type type = null;

        while (type == null) {
            System.out.println("\nSeleccione el tipo de tramite:");
            System.out.println("1. MATRICULA");
            System.out.println("2. HOMOLOGACION");
            System.out.println("3. CONTANCIA_CERTIFICADOS");
            System.out.print("Opcion: ");
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
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        }
        return type;
    }

    public void printTicketHistory() {
        if (attendedTickets == null || attendedTickets.isEmpty()) {
            System.out.println("No hay tickets atendidos para exportar.");
            return;
        }

        fileManager.exportToCSV(attendedTickets);
    }

    public void handleDeleteComment(Ticket ticket, String input) {
        if (!validateExistence(ticket)) return;

        if (ticket.getPerson().getComments().isEmpty()) {
            System.out.println("No hay comentarios para eliminar.");
            return;
        }

        try {
            int posDel = Integer.parseInt(input);
            deleteCommentFromCurrentTicket(ticket, posDel);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Debe ingresar un numero.");
        }
    }

    public void handleUpdateComment(Ticket ticket, Scanner sc) {
        if (!validateExistence(ticket)) return;

        if (ticket.getPerson().getComments().isEmpty()) {
            System.out.println("No hay comentarios para actualizar.");
            return;
        }

        printCommentsOfCurrentTicket(ticket);
        System.out.print("Numero de comentario a actualizar: ");
        try {
            int pos = Integer.parseInt(sc.nextLine());
            System.out.print("Nueva descripcion: ");
            String newDesc = sc.nextLine();
            updateCommentOnCurrentTicket(pos, newDesc, ticket);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Debe ingresar un numero.");
        }
    }

    public void addAttendedTicket(Ticket ticket) {
        if (ticket != null && ticket.getStatus() == Status.COMPLETADO) {
            attendedTickets.enqueue(new Ticket(ticket));
            System.out.println("Ticket agregado al historial de atendidos.");
        }
    }

}
