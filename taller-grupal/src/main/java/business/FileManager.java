package business;

import domain.PrioritizedTicketQueue;
import domain.Queue;
import domain.Ticket;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Gestiona la exportacion de tickets a archivos CSV.
 * Guarda tanto tickets de prioridad como comunes.
 */
public class FileManager {

    private static final String HISTORY_DIRECTORY = "src/main/java/history";
    private static final String HISTORY_FILE = "tickets.csv";


    public FileManager() {
        File path = new File(HISTORY_DIRECTORY);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * Exporta todo el contenido actual de la cola a un archivo CSV.
     * @param queue Cola de tickets (puede ser PrioritizedTicketQueue)
     */
    public void exportToCSV(Queue<Ticket> queue) {
        if (queue == null || queue.isEmpty()) {
            System.out.println("No hay tickets para exportar.");
            return;
        }

        File csvFile = new File(HISTORY_DIRECTORY, HISTORY_FILE);
        boolean append = csvFile.exists();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(csvFile, true), StandardCharsets.UTF_8))) {

            if (!append) {
                bw.write("Nombre;Apellido;Tipo;Estado;Prioridad\n");
            }

            if (queue instanceof PrioritizedTicketQueue pq) {
                exportSubQueues(bw, pq);
            } else {
                exportQueue(bw, queue);
            }

            System.out.println("Historial exportado correctamente en " + csvFile.getPath());

        } catch (IOException e) {
            System.out.println("Error al generar CSV: " + e.getMessage());
        }
    }

    /**
     * Exporta una cola normal (LinkedListQueue) a CSV.
     */
    private void exportQueue(BufferedWriter bw, Queue<Ticket> queue) throws IOException {
        Queue<Ticket> copy = copyQueue(queue);
        while (!copy.isEmpty()) {
            Ticket t = copy.dequeue();
            bw.write(String.format("%s;%s;%s;%s;%s\n",
                    t.getPerson().getName(),
                    t.getPerson().getLastName(),
                    t.getType(),
                    t.getStatus(),
                    t.isPriority() ? "Si" : "No"));
        }
    }

    /**
     * Exporta ambas colas (prioridad + comun) de la cola priorizada.
     */
    private void exportSubQueues(BufferedWriter bw, PrioritizedTicketQueue pq) throws IOException {
        bw.write("# Tickets con Prioridad\n");
        exportQueue(bw, pq.getPriorityQueue());
        bw.write("\n# Tickets Comunes\n");
        exportQueue(bw, pq.getCommonQueue());
    }

    /**
     * Crea una copia temporal de la cola para recorrerla sin modificar la original.
     */
    private Queue<Ticket> copyQueue(Queue<Ticket> original) {
        Queue<Ticket> temp = new domain.LinkedListQueue<>();
        Queue<Ticket> copy = new domain.LinkedListQueue<>();
        while (!original.isEmpty()) {
            Ticket t = original.dequeue();
            temp.enqueue(t);
            copy.enqueue(t);
        }
        // restaurar original
        while (!temp.isEmpty()) {
            original.enqueue(temp.dequeue());
        }
        return copy;
    }
}