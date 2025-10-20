package business;

import domain.Comments;
import domain.Person;
import domain.Ticket;
import theNODE.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

//Clase para creacion del historial en archivos .txt
//wasads
public class FileManager {

    private int id = 1;
    File path = new File("src/main/java/history");
    File history = new File(path, "history.txt");

    public void create_history(Ticket ticket) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(history))){
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
                Comments c = current_person.getData();

                bw.write("     " + id_coment + ") [" + c.getDate() + "] "
                        + "  " + c.getDescription());
                bw.newLine();

                current_person = current_person.getNext();
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

}
