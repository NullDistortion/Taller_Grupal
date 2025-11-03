package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import view.Menu;

public class TicketsMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== MENÚ TICKET ===");
        System.out.println("1. Mostrar comentarios");
        System.out.println("2. Agregar / editar / eliminar comentarios");
        System.out.println("3. Cambiar estado");
        System.out.println("4. Deshacer cambio");
        System.out.println("5. Rehacer cambio");
        System.out.println("6. Finalizar atención");
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        if (currentTicket == null) {
            System.out.println("No hay ticket");
            return null;
        }

        Scanner sc = new Scanner(System.in);
        showMenu();
        String option = sc.nextLine();

        switch (option) {
            case "1":
                bs.printCommentsOfCurrentTicket(currentTicket);
                break;

            case "2":
                currentTicket = new ComentMenu().handleInput(bs, currentTicket);
                break;

            case "3":
                currentTicket = new StatusMenu().handleInput(bs, currentTicket);
                break;

            case "4":
                Ticket undo = bs.undoChanges(currentTicket);
                if (undo != null) currentTicket = undo;
                break;

            case "5":
                Ticket redo = bs.redoChanges(currentTicket);
                if (redo != null) currentTicket = redo;
                break;

            case "6":
                System.out.println("Finalizando atención de: " + currentTicket.getPerson().getName());
                return null;

            default:
                System.out.println("Opción inválida.");
        }

        return currentTicket;
    }
}
