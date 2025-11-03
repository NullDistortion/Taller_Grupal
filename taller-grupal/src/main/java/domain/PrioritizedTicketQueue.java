package domain;

public class PrioritizedTicketQueue implements Queue<Ticket> {

    // Usa dos colas comunes internamente
    private Queue<Ticket> priorityQueue = new LinkedListQueue<>();
    private Queue<Ticket> commonQueue = new LinkedListQueue<>();

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
        return "Prioridad: \n" + priorityQueue.toString() + "\n" +
                "\nComún: \n" + commonQueue.toString();
    }
}