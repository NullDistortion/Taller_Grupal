package domain;

import domain.Ticket;


public class PriorityQueue implements Queue<Ticket> {
    
    // Usa dos colas comunes internamente
    private Queue<Ticket> priorityQueue = new CommonQueue<>();
    private Queue<Ticket> commonQueue = new CommonQueue<>();

    @Override
    public void enqueue(Ticket ticket) {
        if (ticket == null) return;
        
        if (ticket.isPriority()) {
            priorityQueue.enqueue(ticket); // Delega a la cola de prioridad
        } else {
            commonQueue.enqueue(ticket); // Delega a la cola normal
        }
    }

    @Override
    public Ticket dequeue() {
        // Siempre saca de la cola de prioridad primero
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.dequeue();
        }
        // Si no hay de prioridad, saca de la cola normal
        return commonQueue.dequeue();
    }

    @Override
    public Ticket peek() {
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.peek();
        }
        return commonQueue.peek();
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty() && commonQueue.isEmpty();
    }

    @Override
    public int size() {
        return priorityQueue.size() + commonQueue.size();
    }
    
    @Override
    public String toString() {
        // Podrías combinar las dos cadenas de texto
        return "Prioridad: " + priorityQueue.toString() + 
               "\nComún: " + commonQueue.toString();
    }
}