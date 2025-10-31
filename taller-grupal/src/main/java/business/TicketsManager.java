
import domain.CommonQueue;
import domain.PriorityQueue;
import domain.Queue;
import domain.Ticket;

public class TicketsManager {
    private Queue<Ticket> commonQueue = new CommonQueue<>();
    private Queue<Ticket> priorityQueue = new PriorityQueue();

    public void addTicket(Ticket ticket) {
        if (ticket.isPriority()) {
            priorityQueue.enqueue(ticket);
        } else {
            commonQueue.enqueue(ticket);
        }
    }

    public Ticket nextTicket() {
        if (!priorityQueue.isEmpty()) {
            return priorityQueue.dequeue();
        } else {
            return commonQueue.dequeue();
        }
    }

    public void printQueues() {
        System.out.println("Prioridad: " + priorityQueue);
        System.out.println("Común: " + commonQueue);
    }
}
