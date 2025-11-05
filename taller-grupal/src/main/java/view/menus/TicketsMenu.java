package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import view.Menu;

public class TicketsMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== MENU DE TICKET ===");
        System.out.println("1. Agregar comentario");
        System.out.println("2. Ver comentarios");
        System.out.println("3. Eliminar comentario");
        System.out.println("4. Actualizar comentario");
        System.out.println("5. Cambiar estado");
        System.out.println("6. Deshacer cambio");
        System.out.println("7. Rehacer cambio");
        System.out.println("0. Volver al menu principal");
        System.out.print("Seleccione una opcion: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        if (!bs.validateExistence(currentTicket)) return currentTicket;

        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            showMenu();
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    System.out.print("Ingrese comentario: ");
                    bs.addCommentToCurrentTicket(currentTicket, sc.nextLine());
                    break;
                case "2":
                    bs.printCommentsOfCurrentTicket(currentTicket);
                    break;
                case "3":
                    bs.handleDeleteComment(currentTicket, sc.nextLine());
                    break;
                case "4":
                    bs.handleUpdateComment(currentTicket, sc);
                    break;
                case "5":
                    currentTicket = new StatusMenu().handleInput(bs, currentTicket);
                    break;
                case "6":
                    currentTicket = bs.undoChanges(currentTicket);
                    System.out.println("Cambio deshecho.");
                    break;
                case "7":
                    currentTicket = bs.redoChanges(currentTicket);
                    System.out.println("Cambio rehecho.");
                    break;
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
