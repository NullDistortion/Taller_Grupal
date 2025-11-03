package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import enums.Status;
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
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {

        if (!bs.valedateExistence(currentTicket)) {
            return currentTicket;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEstado actual: " + currentTicket.getStatus());
        showMenu();
        String option = sc.nextLine().trim();

        switch (option) {
            case "1":
                currentTicket.setStatus(Status.EN_COLA);
                bs.registerChange(currentTicket);
                System.out.println("Estado cambiado a: EN_COLA");
                break;

            case "2":
                currentTicket.setStatus(Status.EN_ATENCION);
                bs.registerChange(currentTicket);
                System.out.println("Estado cambiado a: EN_ATENCION");
                break;

            case "3":
                currentTicket.setStatus(Status.EN_PROCESO);
                bs.registerChange(currentTicket);
                System.out.println("Estado cambiado a: EN_PROCESO");
                break;

            case "4":
                currentTicket.setStatus(Status.PENDIENTE_DOCS);
                bs.registerChange(currentTicket);
                System.out.println("Estado cambiado a: PENDIENTE_DOCS");
                break;

            case "5":
                currentTicket.setStatus(Status.COMPLETADO);
                bs.registerChange(currentTicket);
                System.out.println("Estado cambiado a: COMPLETADO");
                break;

            case "6":
                System.out.println("Regresando al menú anterior...");
                return currentTicket;

            default:
                System.out.println("Opción inválida. Ingrese un número entre 1 y 6.");
                break;
        }

        return currentTicket;
    }
}
