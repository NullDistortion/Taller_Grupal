package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import enums.Status;
import utility.Utility;
import view.Menu;

public class StatusMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== CAMBIAR ESTADO ===");
        System.out.println("1. EN_COLA");
        System.out.println("2. EN_ATENCION");
        System.out.println("3. EN_PROCESO");
        System.out.println("4. PENDIENTE_DOCS");
        System.out.println("5. COMPLETADO");
        System.out.println("6. Regresar");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {

        if (!bs.validateExistence()) {
            return currentTicket;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEstado actual: " + currentTicket.getStatus());
        showMenu();
        String option = Utility.requestNonEmptyString(sc, "Seleccione una opcion: ");

        switch (option) {
            case "1":
                if (currentTicket.getStatus() != Status.EN_COLA) {
                    currentTicket.setStatus(Status.EN_COLA);
                    bs.registerChange();
                    System.out.println("Estado cambiado a: EN_COLA");
                } else {
                    System.out.println("El Estado seleccionado es Igual al estado actual");
                }
                break;

            case "2":
                 if (currentTicket.getStatus() != Status.EN_ATENCION) {
                currentTicket.setStatus(Status.EN_ATENCION);
                bs.registerChange();
                System.out.println("Estado cambiado a: EN_ATENCION");
                 } else {
                    System.out.println("El Estado seleccionado es Igual al estado actual");
                }
                break;

            case "3":
                 if (currentTicket.getStatus() != Status.EN_PROCESO) {
                currentTicket.setStatus(Status.EN_PROCESO);
                bs.registerChange();
                System.out.println("Estado cambiado a: EN_PROCESO");
                 } else {
                    System.out.println("El Estado seleccionado es Igual al estado actual");
                }
                break;

            case "4":
                 if (currentTicket.getStatus() != Status.PENDIENTE_DOCS) {
                currentTicket.setStatus(Status.PENDIENTE_DOCS);
                bs.registerChange();
                System.out.println("Estado cambiado a: PENDIENTE_DOCS");
                 } else {
                    System.out.println("El Estado seleccionado es Igual al estado actual");
                }
                break;

            case "5":
                 if (currentTicket.getStatus() != Status.COMPLETADO) {
                currentTicket.setStatus(Status.COMPLETADO);
                bs.registerChange();
                bs.addAttendedTicket(currentTicket);
                System.out.println("Estado cambiado a: COMPLETADO");
                } else {
                    System.out.println("El Estado seleccionado es Igual al estado actual");
                }
                break;

            case "6":
                System.out.println("Regresando al menu anterior...");
                return currentTicket;

            default:
                System.out.println("Opcion invalida. Ingrese un numero entre 1 y 6.");
                break;
        }

        return currentTicket;
    }
}
