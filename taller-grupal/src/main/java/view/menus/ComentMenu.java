package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import view.Menu;

public class ComentMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== COMENTARIOS ===");
        System.out.println("1. Añadir comentario");
        System.out.println("2. Modificar comentario");
        System.out.println("3. Eliminar comentario");
        System.out.println("4. Regresar");
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {

        if (!bs.valedateExistence(currentTicket)) {
            return currentTicket;
        }

        Scanner sc = new Scanner(System.in);
        showMenu();
        String option = sc.nextLine();

        try {
            switch (option) {
                case "1":
                    System.out.print("Comentario: ");
                    String comment = sc.nextLine();
                    bs.addCommentToCurrentTicket(currentTicket, comment);
                    break;

                case "2":
                    System.out.print("Ingrese el ID del comentario: ");
                    int posUpdate = Integer.parseInt(sc.nextLine());

                    System.out.print("Nuevo texto: ");
                    String newText = sc.nextLine();
                    bs.updateCommentOnCurrentTicket(posUpdate, newText, currentTicket);
                    break;

                case "3":
                    System.out.print("Ingrese el ID del comentario: ");
                    int posDel = Integer.parseInt(sc.nextLine());
                    bs.deleteCommentFromCurrentTicket(currentTicket, posDel);
                    break;

                case "4":
                    return currentTicket;

                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return currentTicket;
    }
}
