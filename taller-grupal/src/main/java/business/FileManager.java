package business;

import domain.Comments;
import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;
import theNODE.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Clase para creacion del historial en archivos .txt
public class FileManager {

    private static int id = 1;
    File path = new File("src/main/java/history");
    File history = new File(path, "history.txt");

    public void create_history(Ticket ticket) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(history, true))){
            Person person = ticket.getPerson();

            bw.write(id + ". {");
            bw.newLine();
            bw.write("   Name: " + person.getName());
            bw.newLine();
            bw.write("   Last Name: " + person.getLastName());
            bw.newLine();
            bw.write("   ID: " + person.getIdentityCard());
            bw.newLine();
            bw.write("   Telephone: " + person.getTelephone());
            bw.newLine();
            bw.write("   Type: " + ticket.getType());
            bw.newLine();
            bw.write("   Status: " + ticket.getStatus());
            bw.newLine();
            bw.write("   Comments:");
            bw.newLine();

            Node<Comments> current_person = person.getComment();
            int id_coment = 1;

            while (current_person != null) {
                Comments c = current_person.getDato();

                bw.write("     " + id_coment + ") [" + c.getDate() + "] "
                        + "  " + c.getDescription());
                bw.newLine();

                current_person = current_person.getSiguiente();
                id_coment++;
            }

            bw.write("}");
            bw.newLine();
            bw.newLine();

            id++;

            System.out.println("Historial generado correctamente");
        } catch (IOException e) {
            System.out.println("Error al generar historial: " + e.getMessage());
        }
    }

    //prueba
    public static void main(String[] args) {
        Person p = new Person("Miguel", "Armas", "123434521253", "666");
        p.addComment("dando ticket");
        p.addComment("finalizado");

        Ticket t = new Ticket(p, Type.CONTANCIA_CERTIFICADOS, Status.COMPLETADO);

        FileManager fm = new FileManager();
        fm.create_history(t);

    }

}
