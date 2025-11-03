package business;

import domain.Ticket;

import java.io.*;

/**
 * Gestiona la escritura y lectura de un archivo de historial de tickets.
 * Cada estado de un ticket se guarda en una sola línea.
 */
public class FileManager {

    // Se definen las rutas como constantes para evitar errores de escritura.
    private static final String HISTORY_DIRECTORY = "src/main/java/history";
    private static final String HISTORY_FILE = "history.txt";

    /**
     * Constructor. Se asegura de que el directorio para el historial exista.
     */
    public FileManager() {
        File path = new File(HISTORY_DIRECTORY);
        if (!path.exists()) {
            path.mkdirs(); // Crea el directorio si no existe.
        }
    }

    /**
     * Guarda el estado actual de un Ticket en una nueva línea en el archivo de historial.
     * Utiliza el método toString() del Ticket.
     *
     * @param ticket El ticket cuyo estado se va a guardar.
     */
    public void logTicketState(Ticket ticket) {
        // Validación para evitar NullPointerException.
        if (ticket == null) {
            System.out.println("Error: No se puede guardar un ticket nulo en el historial.");
            return;
        }

        File historyFile = new File(HISTORY_DIRECTORY, HISTORY_FILE);

        // Usar try-with-resources asegura que el archivo se cierre automáticamente.
        // El 'true' en FileWriter es para modo "append" (añadir al final).
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(historyFile, true))) {

            String line = ticket.toString(); // Obtiene la representación en una sola línea.
            bw.write(line);                  // Escribe la línea.
            bw.newLine();                    // Añade un salto de línea.

            System.out.println("Historial guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    /**
     * Lee todo el archivo de historial y lo devuelve como un String formateado.
     *
     * @return Un String con todo el contenido del historial, listo para imprimir.
     */
    public String generateReport() {
        File historyFile = new File(HISTORY_DIRECTORY, HISTORY_FILE);

        // Si el archivo no existe, no hay informe que generar.
        if (!historyFile.exists()) {
            return "El archivo de historial no existe todavía. No hay nada que reportar.";
        }

        StringBuilder report = new StringBuilder();
        report.append("--- INFORME DE HISTORIAL DE TICKETS ---\n");

        // Usar try-with-resources para leer el archivo.
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                report.append(lineNumber).append(". ").append(line).append("\n");
                lineNumber++;
            }
        } catch (IOException e) {
            return "Error al leer el archivo de historial: " + e.getMessage();
        }

        report.append("--- FIN DEL INFORME ---\n");
        return report.toString();
    }
}