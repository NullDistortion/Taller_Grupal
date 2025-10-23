package business;

import domain.Ticket;
import enums.Status;

public class Business {
    private final TicketsQueue queue;
    private ChangesStack changesHistorial;
    

    public Business() {
        this.queue = new TicketsQueue();
        this.changesHistorial = new ChangesStack();
    }

    public void addToQueue(Ticket ticket){
        queue.enqueue(ticket);
    }

    public Ticket  processTicked(){
        Ticket actualTicket= queue.dequeue();
        if (actualTicket == null) {
            return null;
        }
        changesHistorial=new ChangesStack();
        registerChange(new Ticket(actualTicket));
        actualTicket.setStatus(Status.EN_ATENCION);
        return actualTicket;
    }

    public void registerChange(Ticket ticket){
        changesHistorial.pushUndo(new Ticket(ticket));
        changesHistorial.clearRedo();
    }

    public Ticket undoChanges(Ticket actualTicket){
        if (changesHistorial.isEmptyUndo()) {
            return null; 
        }
        changesHistorial.pushRedo(new Ticket(actualTicket));
        return changesHistorial.popUndo();
    }

    public Ticket redoChanges(Ticket actualTicket){
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
        System.out.println(queue.toString());
    }
    
    public void printCommentsOfCurrentTicket(Ticket ticket) {
        if (ticket != null) {
            // Llama al método de la persona de ese ticket
            ticket.getPerson().printComments(); 
        } else {
            System.out.println("No hay ningún ticket en atención.");
        }
    }

   
     //Añade un comentario al ticket que está en atención.
     
    public void addCommentToCurrentTicket(String description,Ticket ticket) {
        if (ticket != null) {
            ticket.getPerson().addComment(description);
            System.out.println("Comentario añadido.");
        } else {
            System.out.println("No hay ningún ticket en atención.");
        }
    }

    
     // Elimina un comentario del ticket que está en atención.
     
    public boolean deleteCommentFromCurrentTicket(String description,Ticket ticket) {
        if (ticket != null) {
            return ticket.getPerson().deleteComment(description);
        }
        return false;
    }

    
     //Actualiza un comentario del ticket que está en atención.
    
    public boolean updateCommentOnCurrentTicket(String oldDesc, String newDesc,Ticket ticket) {
        if (ticket != null) {
            return ticket.getPerson().updateComment(oldDesc, newDesc);
        }
        return false;
    }

     public boolean validateInput(String name, String lastname, String identityCard, String telephone) {
        if (name.trim().isEmpty() || lastname.trim().isEmpty() || identityCard.trim().isEmpty()
                || telephone.trim().isEmpty()) {
            System.out.println("No se pudo crear el ticket, datos no validos");
            return false;
        }

        if (!name.matches("[a-zA-Z]+") || !lastname.matches("[a-zA-Z]+")) {
            System.out.println("El nombre y apellido solo deben contener letras");
            return false;
        }

        if (!identityCard.matches("\\d+") || !telephone.matches("\\d+")) {
            System.out.println("El ID y el telefono solo deben contener numeros");
            return false;
        }
        return true;
    }
     
}
