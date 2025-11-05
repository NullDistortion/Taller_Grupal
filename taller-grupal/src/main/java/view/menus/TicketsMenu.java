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
        System.out.println("1. Ver comentarios");
        System.out.println("2. Gestor de comentarios");
        System.out.println("3. Cambiar estado");
        System.out.println("4. Deshacer cambio");
        System.out.println("5. Rehacer cambio");
        System.out.println("6. Finalizar Atencion de Ticket");
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
            showMenu();
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    
                    bs.printCommentsOfCurrentTicket();
                    break;
                    
                case "2":
                    currentTicket = new CommentMenu().handleInput(bs, currentTicket);
                    break;
                case "3":
                    currentTicket = new StatusMenu().handleInput(bs, currentTicket);
                    if (currentTicket.getStatus() == Status.PENDIENTE_DOCS) {
                        bs.returnToQueueIfPendingDocuments();
                        currentTicket=null;
                        continuar = false;
                        break;
                    }
                     if (currentTicket.getStatus() == Status.COMPLETADO) {
                        bs.finalizeCurrentTicket();
                        currentTicket=null;
                        continuar = false;
                    }
                    break;
                case "4":
                    currentTicket = bs.undoChanges(currentTicket);
                    System.out.println("Cambio deshecho.");
                    break;
                case "5":
                    currentTicket = bs.redoChanges(currentTicket);
                    System.out.println("Cambio rehecho.");
                    break;
                case "6":
                    bs.finalizeCurrentTicket();
                    currentTicket=null;
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
