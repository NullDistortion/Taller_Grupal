package business;

import domain.Person;
import domain.PrioritizedTicketQueue;
import domain.Queue;
import domain.Ticket;
import enums.Status;
import enums.Type;

import java.io.*;

/**
 * Gestiona la exportacion de tickets a archivos CSV. Guarda tanto tickets de
 * prioridad como comunes.
 */
public class FileManager {

    private static final String HISTORY_DIRECTORY = "src/main/java/history";
    private static final String PENDING_FILE = "tickets_pendientes.csv";
    private static final String HISTORY_FILE = "tickets.csv";
    private static final String LAST_ID_FILE = "last_id.txt";

    public FileManager() {
        File path = new File(HISTORY_DIRECTORY);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    /**
     * Exporta todo el contenido actual de la cola de atencion a un archivo CSV.
     *
     * @param queue Cola de tickets (puede ser PrioritizedTicketQueue)
     */
    public void exportPendingTickets(Queue<Ticket> queue) {
        if (queue == null || queue.isEmpty()) {
            System.out.println("No hay tickets pendientes para exportar.");
            return;
        }

        File csvFile = new File(HISTORY_DIRECTORY, PENDING_FILE);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false))) {
            bw.write("ID;Nombre;Apellido;Tipo;Estado;Prioridad\n");

            Queue<Ticket> copy = copyQueue(queue);
            while (!copy.isEmpty()) {
                Ticket t = copy.dequeue();
                if (t.getStatus() != Status.COMPLETADO) {
                    bw.write(String.format("%d;%s;%s;%s;%s;%s\n",
                            t.getId(),
                            t.getPerson().getName(),
                            t.getPerson().getLastName(),
                            t.getType(),
                            t.getStatus(),
                            t.isPriority() ? "Si" : "No"));
                }
            }

            System.out.println("Tickets pendientes exportados en: " + csvFile.getPath());

        } catch (IOException e) {
            System.out.println("Error al exportar tickets pendientes: " + e.getMessage());
        }
    }

    /**
     * Exporta todo el contenido actual de la cola de atencion a un archivo CSV.
     *
     * @param queue Cola de tickets (puede ser PrioritizedTicketQueue)
     */
   public void exportFinalizedTicketsWithComments(Queue<Ticket> queue) {
    if (queue == null || queue.isEmpty()) {
        System.out.println("No hay tickets finalizados para exportar.");
        return;
    }

    File csvFile = new File(HISTORY_DIRECTORY, HISTORY_FILE);
    boolean append = csvFile.exists();

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, true))) {
        if (!append) {
            bw.write("ID;Nombre;Apellido;Tipo;Estado;Prioridad\n");
        }

        Queue<Ticket> copy = copyQueue(queue);
        while (!copy.isEmpty()) {
            Ticket t = copy.dequeue();
            if (t.getStatus() == Status.COMPLETADO) {
                bw.write(String.format("%d;%s;%s;%s;%s;%s\n",
                        t.getId(),
                        t.getPerson().getName(),
                        t.getPerson().getLastName(),
                        t.getType(),
                        t.getStatus(),
                        t.isPriority() ? "Si" : "No"));

                bw.write("Comentarios:\n");
                bw.write(t.getPerson().getComments().toString());
                bw.write("\n--------------------------------------\n");
            }
        }

        System.out.println("Tickets finalizados exportados en: " + csvFile.getPath());

    } catch (IOException e) {
        System.out.println("Error al exportar tickets finalizados: " + e.getMessage());
    }
}


    /**
     * Exporta una cola normal (LinkedListQueue) a CSV.
     */
    private void exportQueue(BufferedWriter bw, Queue<Ticket> queue) throws IOException {
        Queue<Ticket> copy = copyQueue(queue);
        while (!copy.isEmpty()) {
            Ticket t = copy.dequeue();
            bw.write(String.format("%d;%s;%s;%s;%s;%s\n",
                    t.getId(),
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
     * Crea una copia temporal de la cola para recorrerla sin modificar la
     * original.
     */
    private Queue<Ticket> copyQueue(Queue<Ticket> original) {
        Queue<Ticket> temp = new domain.LinkedListQueue<>();
        Queue<Ticket> copy = new domain.LinkedListQueue<>();
        while (!original.isEmpty()) {
            Ticket t = original.dequeue();
            temp.enqueue(t);
            copy.enqueue(t);
        }
        while (!temp.isEmpty()) {
            original.enqueue(temp.dequeue());
        }
        return copy;
    }

    public void saveLastId(int id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(HISTORY_DIRECTORY, LAST_ID_FILE), false))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println("Error al guardar el ID: " + e.getMessage());
        }
    }

    public int loadLastId() {
        File file = new File(HISTORY_DIRECTORY, LAST_ID_FILE);
        if (!file.exists()) {
            return 1; // Si no existe, empieza desde 1
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return Integer.parseInt(line.trim());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al leer el ID: " + e.getMessage());
            return 1; // Valor por defecto si hay error
        }
    }

    public Queue<Ticket> importPendingTickets() {
        Queue<Ticket> importedQueue = new domain.LinkedListQueue<>();
        File csvFile = new File(HISTORY_DIRECTORY, PENDING_FILE);

        if (!csvFile.exists()) {
            System.out.println("No hay archivo de tickets pendientes para importar.");
            return importedQueue;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // Saltar encabezado
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");
                if (parts.length < 6) {
                    continue;
                }

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String lastName = parts[2].trim();
                Type type = Type.valueOf(parts[3].trim());
                Status status = Status.valueOf(parts[4].trim());
                boolean priority = parts[5].trim().equalsIgnoreCase("Si");

                Person person = new Person(name, lastName);
                Ticket ticket = new Ticket(id, person, type, status, priority);
                importedQueue.enqueue(ticket);
            }

            System.out.println("Tickets pendientes importados desde: " + csvFile.getPath());

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al importar tickets pendientes: " + e.getMessage());
        }

        return importedQueue;
    }

    public void printFinalizedTickets(Queue<Ticket> queue) {
        if (queue == null || queue.isEmpty()) {
            System.out.println("No hay tickets finalizados para mostrar.");
            return;
        }

        Queue<Ticket> copy = copyQueue(queue);
        System.out.println("\n=== HISTORIAL DE TICKETS FINALIZADOS ===");

        while (!copy.isEmpty()) {
            Ticket t = copy.dequeue();
            if (t.getStatus() == Status.COMPLETADO) {
                System.out.println("ID: " + t.getId());
                System.out.println("Nombre: " + t.getPerson().getName() + " " + t.getPerson().getLastName());
                System.out.println("Tipo: " + t.getType());
                System.out.println("Estado: " + t.getStatus());
                System.out.println("Prioridad: " + (t.isPriority() ? "Sí" : "No"));
                System.out.println("Comentarios: " + t.getPerson().getComments().toString());
                System.out.println("--------------------------------------");
            }
        }
    }

}
