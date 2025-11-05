package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import utility.Utility;
import view.Menu;

public class CommentMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== COMENTARIOS ===");
        System.out.println("1. Agregar comentario");
        System.out.println("2. Modificar comentario");
        System.out.println("3. Eliminar comentario");
        System.out.println("4. Regresar");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {

        if (!bs.validateExistence()) {
            return currentTicket;
        }

        Scanner sc = new Scanner(System.in);
        bs.printCommentsOfCurrentTicket();
        showMenu();
        String option = Utility.requestNonEmptyString(sc, "Seleccione una opcion: ");

        try {
            switch (option) {
                case "1":
                    String comment = Utility.requestNonEmptyString(sc, "Comentario: ");
                    bs.addCommentToCurrentTicket(comment);
                    break;

                case "2":
                    int posUpdate = Utility.requestValidInteger(sc, "Ingrese el numero del comentario: ");

                    String newText = Utility.requestNonEmptyString(sc, "Nuevo texto: ");
                    bs.updateCommentOnCurrentTicket(posUpdate, newText);
                    break;

                case "3":
                    int posDel = Utility.requestValidInteger(sc, "Ingrese el numero del comentario a Eliminar: ");
                    bs.deleteCommentFromCurrentTicket(posDel);
                    break;

                case "4":
                    return currentTicket;

                default:
                    System.out.println("Opcion invalida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return currentTicket;
    }
}