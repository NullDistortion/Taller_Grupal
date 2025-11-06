package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Queue;
import domain.Ticket;
import java.util.List;
import utility.Utility;
import view.Menu;

public class TopKMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== REPORTE TOP-K ===");
        System.out.println("\nSeleccione el tipo de reporte Top-k:");
        System.out.println("1. Ordenar por ID (Ascendente)");
        System.out.println("2. Ordenar por Numero de Comentarios (Descendente)");
        System.out.println("0. Regresar");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {

        Queue<Ticket> finalizedTickets = bs.finalizedTicketsFromJSON();
        if (finalizedTickets.isEmpty()) {
            System.out.println("No hay tickets finalizados para generar reportes.");
            return currentTicket;
        }
        Scanner sc = new Scanner(System.in);
        Integer k = Utility.requestValidInteger(sc, "Ingrese el valor 'K' para el Top-K: ");
        while (true) {
            if (k == null) { // Manejar cancelación
                return currentTicket;
            }
            List<Ticket> results;
            showMenu();
            String option = Utility.requestNonEmptyString(sc, "Seleccione una opcion: ");

            try {

                switch (option) {
                    case "1":
                        results = bs.getTopKById(finalizedTickets, k);
                        bs.printIdReport(results, k);
                        break;

                    case "2":
                        results = bs.getTopKByComments(finalizedTickets, k);
                        bs.printCommentReport(results, k);
                        break;

                    case "0":
                        System.out.println("Volviendo al menu.");
                        return currentTicket;

                    default:
                        System.out.println("Opcion invalida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        }
    }
}
