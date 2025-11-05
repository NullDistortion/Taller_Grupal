package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import view.Menu;

public class MainMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Atender siguiente ticket");
        System.out.println("2. Mostrar tickets en cola");
        System.out.println("3. Imprimir historial");
        System.out.println("4. Crear ticket");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();

        switch (option) {
            case "1":
                Ticket next = bs.processTicket();
                if (next != null) {
                    System.out.println("\nAtendiendo: " + next);
                    new TicketsMenu().handleInput(bs, next);
                }
                break;

            case "2":
                System.out.println("\n");
                bs.printTickets();
                break;

            case "3":
                bs.printTicketHistory();
                break;

            case "4":
                bs.createTicket();
                break;

            case "5":
                System.out.println("Saliendo del programa...");
                System.exit(0);
                break;

            default:
                System.out.println("Opcion invalida.");
        }

        return currentTicket;
    }
}