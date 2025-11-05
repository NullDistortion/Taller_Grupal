package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import enums.Status;
import view.Menu;

public class TicketsMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== MENU DE TICKET ===");
        System.out.println("1. Gestor de comentarios");
        System.out.println("2. Cambiar estado");
        System.out.println("3. Deshacer cambio");
        System.out.println("4. Rehacer cambio");
        System.out.println("5. Finalizar Atencion de Ticket");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        if (!bs.validateExistence()) {
            return currentTicket;
        }

        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            bs.printCurrentTicket();
            bs.printCommentsOfCurrentTicket();
            showMenu();
            String option = sc.nextLine().trim();

            switch (option) {

                case "1":
                    currentTicket = new CommentMenu().handleInput(bs, currentTicket);
                    break;
                case "2":
                    currentTicket = new StatusMenu().handleInput(bs, currentTicket);
                    if (currentTicket.getStatus() == Status.PENDIENTE_DOCS) {
                        bs.returnToQueueIfPendingDocuments();
                        currentTicket = null;
                        continuar = false;
                        break;
                    }
                    if (currentTicket.getStatus() == Status.COMPLETADO) {
                        bs.finalizeCurrentTicket();
                        currentTicket = null;
                        continuar = false;
                    }
                    break;
                case "3":
                    System.out.println("\n\n");
                    currentTicket = bs.undoChanges();
                    break;
                case "4":
                    System.out.println("\n\n");
                    currentTicket = bs.redoChanges();
                    break;
                case "5":
                    System.out.println("\n\n");
                    bs.finalizeCurrentTicket();
                    System.out.println("\n\n");
                    currentTicket = null;
                case "0":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        return currentTicket;
    }
}
