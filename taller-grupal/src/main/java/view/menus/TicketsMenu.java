package view.menus;

import java.util.Scanner;
import business.Business;
import business.FileManager;
import domain.Ticket;
import view.Menu;
import view.menus.*;

public class TicketsMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("0. Mostrar Ticket");
        System.out.println("1. Opciones de Comentarios");
        System.out.println("2. Cambiar estado");
        System.out.println("3. Revertir cambios");
        System.out.println("4. Rehacer cambios");
        System.out.println("5. Finalizar Atencion de Ticket actual");
        System.out.println("===================================");
        System.out.print("Ingrese una opcion: ");
    }

    @Override
    public Ticket handleInput(Scanner sc, Business bs, FileManager fm, Ticket t) {
        boolean secondFlagTicket = true;

        while (secondFlagTicket) {
            showMenu();
            String opt = sc.nextLine();

            switch (opt) {
                case "0": // Mostrar ticket
                    System.out.println("Ticket Actual");
                    if (t != null) System.out.println(t.toString());
                    else System.out.println("No hay ticket activo.");
                    break;

                case "1": // Comentarios
                    ComentMenu comentMenu = new ComentMenu();
                    t = comentMenu.handleInput(sc, bs, fm, t);
                    break;

                case "2": // Cambiar estado
                    StatusMenu statusMenu = new StatusMenu();
                    t = statusMenu.handleInput(sc, bs, fm, t);
                    break;

                case "3": // Revertir cambio
                    Ticket beforeChange = bs.undoChanges(t);
                    if (beforeChange != null) {
                        t = beforeChange;
                        System.out.println("Cambio revertido.");
                    } else {
                        System.out.println("No hay mas cambios que revertir.");
                    }
                    break;

                case "4": // Rehacer cambio
                    Ticket afterChange = bs.redoChanges(t);
                    if (afterChange != null) {
                        t = afterChange;
                        System.out.println("Cambio rehecho.");
                    } else {
                        System.out.println("No hay más cambios que rehacer.");
                    }
                    break;

                case "5": // Finalizar atencion
                    fm.logTicketState(t);
                    secondFlagTicket = false;
                    break;

                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }

        return t;
    }
}
