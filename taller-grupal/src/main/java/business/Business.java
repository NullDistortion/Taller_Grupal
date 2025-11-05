package business;

import domain.*;
import enums.Status;
import enums.Type;
import java.util.Scanner;
import utility.Utility;

public class Business {

    private final Scanner sc = new Scanner(System.in);
    private UndoRedoManager changesHistorial;
    private Queue<Ticket> ticketQueue;
    private FileManager fileManager;
    private Queue<Ticket> attendedTickets = new LinkedListQueue<>();
    private int nextTicketId;
    private Ticket currentTicket;

    public Business() {
        this.ticketQueue = new PrioritizedTicketQueue();
        this.changesHistorial = new UndoRedoManager();
        this.fileManager = new FileManager();
        this.nextTicketId = loadLastId();
    }

    public void addToQueue(Ticket ticket) {
        ticketQueue.enqueue(ticket);
    }

    public Ticket processTicket() {
        if (currentTicket != null) {
            System.out.println("Ya hay un ticket en atención. Finalícelo antes de continuar.");
            return null;
        }

        Ticket ticket = ticketQueue.dequeue();
        this.currentTicket = ticket;
        if (!validateExistence()) {
            return null;
        }

        changesHistorial = new UndoRedoManager();
        registerChange();
        ticket.setStatus(Status.EN_ATENCION);
        currentTicket = ticket;

        return currentTicket;
    }

    public void finalizeCurrentTicket() {
        if (currentTicket == null) {
            System.out.println("No hay ticket en atención.");
            return;
        }

        if (currentTicket.getStatus() == Status.COMPLETADO) {
            System.out.println("Este ticket ya fue finalizado.");
            return;
        }

        currentTicket.setStatus(Status.COMPLETADO);
        addAttendedTicket(currentTicket);
        currentTicket = null;
    }

    public void registerChange() {
        changesHistorial.pushUndo(new Ticket(currentTicket));
        changesHistorial.clearRedo();
    }

    public Ticket undoChanges() {
        if (changesHistorial.isEmptyUndo()) {
            System.out.println("No hay nada que deshacer.");
            return this.currentTicket;
        }

        // Guarda el estado actual (que se va a deshacer) en la pila Redo
        changesHistorial.pushRedo(new Ticket(this.currentTicket));

        // Actualiza el estado interno de la clase
        this.currentTicket = changesHistorial.popUndo();

        // Devuelve el ticket actualizado (el estado deshecho)
        return this.currentTicket;
    }

    public Ticket redoChanges() {
        if (changesHistorial.isEmptyRedo()) {
            System.out.println("No hay nada que rehacer.");
            // Devuelve el ticket actual si no hay nada que rehacer
            return this.currentTicket;
        }

        // Guarda el estado actual (el estado deshecho) en la pila Undo
        changesHistorial.pushUndo(new Ticket(this.currentTicket));

        // Actualiza el estado interno de la clase
        this.currentTicket = changesHistorial.popRedo();

        // Devuelve el ticket actualizado (el estado rehecho)
        return this.currentTicket;
    }

    public void discardLastUndo() {
        changesHistorial.popUndo();
    }

    public void printTickets() {
        System.out.println(ticketQueue.toString());
    }

    public void addCommentToCurrentTicket(String commentDescription) throws IllegalArgumentException {
        if (currentTicket == null) {
            throw new IllegalArgumentException("No hay ningun ticket en atencion.");
        }

        this.registerChange();
        try {
            Person person = currentTicket.getPerson();
            CommentsList list = person.getComments();
            list.addComment(commentDescription);
            System.out.println("Comentario anadido.");
        } catch (IllegalArgumentException e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void deleteCommentFromCurrentTicket(int position) {
        if (currentTicket == null) {
            System.out.println("No hay ningun ticket en atencion.");
            return;
        }
        this.registerChange();

        try {
            Person person = currentTicket.getPerson();
            CommentsList list = person.getComments();
            boolean success = list.deleteCommentByPosition(position);
            if (!success) {
                this.discardLastUndo();
                System.out.println("No se encontro un comentario en la posicion " + position);
            }
            System.out.println("Comentario borrado exitosamente.");
        } catch (Exception e) {
            this.discardLastUndo();
            throw e;
        }
    }

    public void updateCommentOnCurrentTicket(int position, String newDesc) {
        if (currentTicket == null) {
            System.out.println("No hay ningun ticket en atencion.");
            return;
        }

        this.registerChange();
        try {
            Person person = currentTicket.getPerson();
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

    public void printCommentsOfCurrentTicket() {
        if (currentTicket != null) {
            Person person = currentTicket.getPerson();
            CommentsList comments = person.getComments();
            System.out.println("Comentarios para: " + person.getName() + " " + person.getLastName());
            System.out.println("  -> " + comments.toString());
        } else {
            System.out.println("No hay ningun ticket en atencion.");
        }
    }

    public boolean validateExistence() {
        if (currentTicket == null) {
            System.out.println("No hay ticket.");
            return false;
        }
        return true;
    }

    public void createTicket() {

        System.out.println("\n=== CREAR NUEVO TICKET ===");
        System.out.println("En cualquier paso, ingrese 'q' para cancelar y volver al menú.");

        // 1. Pedir datos de la persona
        // Asumo que Utility.requestPersonData ya devuelve null si se cancela
        Person person = Utility.requestPersonData(sc);
        if (person == null) {
            System.out.println("Creación de ticket cancelada.");
            return; // Salir del método
        }

        Boolean priority = Utility.validatePrio(sc);
        if (priority == null) {
            System.out.println("Creación de ticket cancelada.");
            return; // Salir del método
        }

        Type type = Utility.selectType(sc);
        if (type == null) {
            System.out.println("Creación de ticket cancelada.");
            return; // Salir del método
        }

        // Si llegamos aquí, toda la información fue recolectada:
        Ticket newTicket = new Ticket(generateNextTicketId(), person, type, Status.EN_COLA, priority); // Java auto-unboxes 'priority' aquí

        saveLastId();
        nextTicketId++; // Asegúrate que esta lógica coincide con generateNextTicketId()
        addToQueue(newTicket);

        System.out.println("\nTicket agregado correctamente:\n" + newTicket);

    }

    public void handleDeleteComment(String input) {
        if (!validateExistence()) {
            return;
        }

        if (currentTicket.getPerson().getComments().isEmpty()) {
            System.out.println("No hay comentarios para eliminar.");
            return;
        }

        try {
            int posDel = Utility.requestValidInteger(sc, input);
            deleteCommentFromCurrentTicket(posDel);
        } catch (NumberFormatException e) {
            System.out.println("Entrada no valida. Debe ingresar un numero.");
        }
    }

    public void handleUpdateComment(Ticket ticket, Scanner sc) {
        if (!validateExistence()) {
            return;
        }

        if (ticket.getPerson().getComments().isEmpty()) {
            System.out.println("No hay comentarios para actualizar.");
            return;
        }

        printCommentsOfCurrentTicket();

        int pos = Utility.requestValidInteger(sc, "Número de comentario a actualizar: ");
        String newDesc = Utility.requestNonEmptyString(sc, "Nueva descripción: ");
        updateCommentOnCurrentTicket(pos, newDesc);
    }

    public void addAttendedTicket(Ticket ticket) {
        if (ticket != null && ticket.getStatus() == Status.COMPLETADO) {
            attendedTickets.enqueue(new Ticket(ticket));
            System.out.println("Ticket agregado al historial de atendidos.");
        }
    }

    public void returnToQueueIfPendingDocuments() {
        if (currentTicket != null && currentTicket.getStatus() == Status.PENDIENTE_DOCS) {
            addToQueue(currentTicket);
            currentTicket = null;
            System.out.println("Ticket devuelto a la cola por estado PENDIENTE_DOCUMENTOS.");
        }
    }

    private int loadLastId() {
        return fileManager.loadLastId();
    }

    private void saveLastId() {
        fileManager.saveLastId(nextTicketId);
    }

    public void savePendingQueue() {
        if (ticketQueue.isEmpty()) {
            System.out.println("No hay tickets pendientes para exportar.");
            return;
        }
        fileManager.exportPendingTickets(ticketQueue);
    }

    private int generateNextTicketId() {
        int id = nextTicketId;
        saveLastId();
        nextTicketId++;
        return id;
    }

    public void loadPendingTickets() {
        Queue<Ticket> imported = fileManager.importPendingTickets();
        while (!imported.isEmpty()) {
            ticketQueue.enqueue(imported.dequeue());
        }
    }

    public void showFinalizedHistory() {
        fileManager.printFinalizedTickets(attendedTickets);
    }

    public void printCurrentTicket() {
        System.out.println(currentTicket.toString());
    }

    public void exportFinalizedTicketsToJSON() {
        if (attendedTickets == null || attendedTickets.isEmpty()) {
            System.out.println("No hay tickets finalizados para exportar.");
            return;
        }
        fileManager.exportFinalizedTicketsToJSON(attendedTickets);
    }

    public void importFinalizedTicketsFromJSON() {
        Queue<Ticket> imported = fileManager.importFinalizedTicketsFromJSON();
        if (imported.isEmpty()) {
            System.out.println("No se encontraron tickets finalizados en el archivo JSON.");
            return;
        }

        while (!imported.isEmpty()) {
            Ticket t = imported.dequeue();
            attendedTickets.enqueue(t);
        }

        System.out.println("Tickets finalizados cargados en memoria.");
    }

    public void printFinalizedTickets() {
        if (attendedTickets == null || attendedTickets.isEmpty()) {
            System.out.println("No hay tickets finalizados para mostrar.");
            return;
        }
        fileManager.printFinalizedTickets(attendedTickets);
    }

}
