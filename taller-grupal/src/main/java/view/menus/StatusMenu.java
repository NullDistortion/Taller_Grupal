package view.menus;

import java.util.Scanner;
import business.Business;
import business.FileManager;
import domain.Ticket;
import enums.Status;
import view.Menu;

public class StatusMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("1. Cambiar estado a En Cola");
        System.out.println("2. Cambiar estado a En Atencion");
        System.out.println("3. Cambiar estado a En Proceso");
        System.out.println("4. Cambiar estado a Documento Pendiente");
        System.out.println("5. Cambiar estado a Completado");
        System.out.println("6. Regresar");
        System.out.println("===================================");
        System.out.print("Ingrese una opcion: ");
    }

    @Override
    public Ticket handleInput(Scanner sc, Business bs, FileManager fm, Ticket t) {
        showMenu();
        String opt = sc.nextLine();

        switch (opt) {
            case "1":
                bs.registerChange(t);
                t.setStatus(Status.EN_COLA);
                break;
            case "2":
                bs.registerChange(t);
                t.setStatus(Status.EN_ATENCION);
                break;
            case "3":
                bs.registerChange(t);
                t.setStatus(Status.EN_PROCESO);
                break;
            case "4":
                bs.registerChange(t);
                t.setStatus(Status.PENDIENTE_DOCS);
                break;
            case "5":
                bs.registerChange(t);
                t.setStatus(Status.COMPLETADO);
                break;
            case "6":
                System.out.println("Regresando al menu anterior");
                break;
            default:
                System.out.println("Opcion no valida");
        }

        return t;
    }
}
