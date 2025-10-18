package business;

import domain.Person;
import enums.Status;
import enums.Type;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {

    private String name;
    private String lastName;
    private String identityCard;
    private String telephone;

    private Status status;
    private Type type;
    private String comment = "Hola";

    public void send_history () {
        File path = new File("src\\main\\java\\history");
        File history = new File(path, "history.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(history))) {

            Person person = new Person(name,lastName,identityCard,telephone,status,type);
            bw.write(person.toString());
            bw.write(comment);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String setCommnet (){
        return comment;
    }

    public static void main (String[] args) {
        FileManager fm = new FileManager();
        fm.send_history();
    }

}
