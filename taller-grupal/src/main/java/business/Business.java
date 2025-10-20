package business;

import domain.Ticket;
import enums.Status;

public class Business {
    private TicketsQueue queue;
    private ChangesStack changesHistorial;
    
    public void addToQueue(Ticket ticket){
        queue.enqueue(ticket);
    }
    
    public Ticket  processTicked(){
        Ticket actualTicket= queue.dequeue();
        changesHistorial=null;
        registerChange(actualTicket);
        actualTicket.setStatus(Status.EN_ATENCION);
        return actualTicket;
    }
    
    public void registerChange(Ticket ticket){
        changesHistorial.pushUndo(ticket);
    }
    
    public Ticket undoChanges(Ticket actualTicket){
        changesHistorial.pushRedo(actualTicket);
        return changesHistorial.popUndo();
    }
    
    public Ticket redoChanges(Ticket actualTicket){
        changesHistorial.pushUndo(actualTicket);
        return changesHistorial.popRedo();
    }
    
    
}
