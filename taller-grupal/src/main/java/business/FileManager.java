package business;

import domain.Comments;
import domain.LinkedListQueue;
import domain.Person;
import domain.PrioritizedTicketQueue;
import domain.Queue;
import domain.Ticket;
import enums.Status;
import enums.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import theNODE.Node;
import java.io.*;
import java.time.LocalDate;

/**
 * Gestiona la exportacion de tickets a archivos CSV. Guarda tanto tickets de
 * prioridad como comunes.
 */
public class FileManager {

    private static final String HISTORY_DIRECTORY = "src/main/java/history";
    private static final String PENDING_FILE = "tickets_pendientes.csv";
    private static final String HISTORY_FILE = "tickets.csv";
    private static final String LAST_ID_FILE = "last_id.txt";
    private static final String HISTORY_JSON_FILE = "tickets_finalizados.json";

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

    public Queue<Ticket> importFinalizedTicketsFromCSV() {
        Queue<Ticket> imported = new LinkedListQueue<>();
        File csvFile = new File(HISTORY_DIRECTORY, HISTORY_FILE);

        if (!csvFile.exists()) {
            System.out.println("No hay archivo CSV de tickets finalizados.");
            return imported;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean readingComments = false;
            Ticket currentTicket = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("ID;")) {
                    continue; // encabezado
                }
                if (line.startsWith("Comentarios:")) {
                    readingComments = true;
                    continue;
                }

                if (line.startsWith("--------------------------------------")) {
                    if (currentTicket != null) {
                        imported.enqueue(currentTicket);
                        currentTicket = null;
                    }
                    readingComments = false;
                    continue;
                }

                if (!readingComments) {
                    String[] parts = line.split(";");
                    if (parts.length < 6) {
                        continue;
                    }

                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String lastName = parts[2];
                    Type type = Type.valueOf(parts[3]);
                    Status status = Status.valueOf(parts[4]);
                    boolean priority = parts[5].equalsIgnoreCase("Si");

                    Person person = new Person(name, lastName);
                    currentTicket = new Ticket(id, person, type, status, priority);
                } else {
                    // Comentario en formato: 1. [2025-11-05: texto]
                    int start = line.indexOf("[");
                    int end = line.indexOf("]");
                    if (start != -1 && end != -1) {
                        String content = line.substring(start + 1, end);
                        String[] commentParts = content.split(":");
                        if (commentParts.length >= 2) {
                            String date = commentParts[0].trim();
                            String desc = line.substring(line.indexOf(":") + 1).trim();
                            Comments comment = new Comments(desc, LocalDate.parse(date));
                            currentTicket.getPerson().getComments().addComment(comment.getDescription());
                        }
                    }
                }
            }

            System.out.println("Tickets finalizados importados desde CSV.");
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error al importar tickets finalizados: " + e.getMessage());
        }

        return imported;
    }

    public void printFinalizedTickets(Queue<Ticket> queue) {
        if (queue == null || queue.isEmpty()) {
            System.out.println("No hay tickets finalizados para mostrar.");
            return;
        }

        Queue<Ticket> copy = copyQueue(importFinalizedTicketsFromCSV());
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
public void exportFinalizedTicketsToJSON(Queue<Ticket> queue) {
        if (queue == null || queue.isEmpty()) {
            System.out.println("No hay tickets finalizados para exportar a JSON.");
            return;
        }

        File jsonFile = new File(HISTORY_DIRECTORY, HISTORY_JSON_FILE);
        JSONParser parser = new JSONParser();
        JSONArray mainArray;

        // 1. Leer el archivo JSON existente
        if (jsonFile.exists() && jsonFile.length() > 0) {
            try (FileReader reader = new FileReader(jsonFile)) {
                mainArray = (JSONArray) parser.parse(reader);
            } catch (IOException | ParseException e) {
                System.out.println("Error al leer JSON existente, se creará uno nuevo: " + e.getMessage());
                mainArray = new JSONArray();
            }
        } else {
            mainArray = new JSONArray();
        }

        // 2. Copiar la cola para no modificar la original
        Queue<Ticket> copy = copyQueue(queue);

        // 3. Añadir los nuevos tickets al JSONArray
        while (!copy.isEmpty()) {
            Ticket t = copy.dequeue();
            if (t.getStatus() != Status.COMPLETADO) {
                continue; // Solo exportar completados
            }

            // Crear el objeto de ticket principal
            JSONObject ticketJson = new JSONObject();
            ticketJson.put("id", t.getId());
            ticketJson.put("type", t.getType().name());
            ticketJson.put("status", t.getStatus().name());
            ticketJson.put("priority", t.isPriority());

            // Crear el objeto de persona anidado
            JSONObject personJson = new JSONObject();
            personJson.put("name", t.getPerson().getName());
            personJson.put("lastName", t.getPerson().getLastName());

            // Crear el array de comentarios anidado
            JSONArray commentsJson = new JSONArray();
            
            // Asumo que CommentsList tiene un método getHead() que devuelve un Node<Comments>
            // y que la clase Comments tiene getDescription() y getCreationDate()
            Node<Comments> currentCommentNode = t.getPerson().getComments().getHead(); // ¡Debes implementar getHead() en CommentsList!
            while (currentCommentNode != null) {
                Comments comment = currentCommentNode.getData();
                JSONObject commentJson = new JSONObject();
                commentJson.put("date", comment.getDate()
.toString()); // Asumido
                commentJson.put("description", comment.getDescription()); // Asumido
                commentsJson.add(commentJson);
                currentCommentNode = currentCommentNode.getNext();
            }
            
            personJson.put("comments", commentsJson);
            ticketJson.put("person", personJson);

            // Añadir el ticket al array principal
            mainArray.add(ticketJson);
        }

        // 4. Escribir el array actualizado de vuelta al archivo
        try (FileWriter writer = new FileWriter(jsonFile, false)) { // false = sobrescribir
            writer.write(mainArray.toJSONString());
            System.out.println("Tickets finalizados exportados a: " + jsonFile.getPath());
        } catch (IOException e) {
            System.out.println("Error al exportar tickets a JSON: " + e.getMessage());
        }
    }

public Queue<Ticket> importFinalizedTicketsFromJSON() {
        Queue<Ticket> imported = new LinkedListQueue<>();
        File jsonFile = new File(HISTORY_DIRECTORY, HISTORY_JSON_FILE);
        JSONParser parser = new JSONParser();

        if (!jsonFile.exists()) {
            System.out.println("No se encontró el archivo JSON de tickets finalizados.");
            return imported;
        }

        try (FileReader reader = new FileReader(jsonFile)) {
            JSONArray mainArray = (JSONArray) parser.parse(reader);

            for (Object obj : mainArray) {
                JSONObject ticketJson = (JSONObject) obj;

                // Parsear datos del ticket
                // JSON-simple parsea números como Long, por eso (Long) ... .intValue()
                int id = ((Long) ticketJson.get("id")).intValue();
                Type type = Type.valueOf((String) ticketJson.get("type"));
                Status status = Status.valueOf((String) ticketJson.get("status"));
                boolean priority = (Boolean) ticketJson.get("priority");

                // Parsear datos de persona
                JSONObject personJson = (JSONObject) ticketJson.get("person");
                String name = (String) personJson.get("name");
                String lastName = (String) personJson.get("lastName");

                Person person = new Person(name, lastName);

                // Parsear comentarios
                JSONArray commentsJson = (JSONArray) personJson.get("comments");
                for (Object cObj : commentsJson) {
                    JSONObject commentJson = (JSONObject) cObj;
                    String desc = (String) commentJson.get("description");
                    String dateStr = (String) commentJson.get("date");
                    
                    // IMPORTANTE:
                    // Aquí estoy asumiendo que tu método addComment(String desc)
                    // crea un nuevo Comments con la fecha actual.
                    // Al importar, esto significa que la fecha original se pierde.
                    // Esto es consistente con tu importación de CSV (línea 313).
                    // Si quieres preservar la fecha, necesitarías un método como:
                    // addComment(String desc, LocalDate date)
                    person.getComments().addComment(desc);
                }

                // Crear y encolar el ticket
                Ticket ticket = new Ticket(id, person, type, status, priority);
                imported.enqueue(ticket);
            }

            System.out.println("Tickets finalizados importados desde JSON.");

        } catch (IOException | ParseException | ClassCastException | IllegalArgumentException e) {
            System.out.println("Error al importar tickets desde JSON: " + e.getMessage());
        }

        return imported;
    }
}
