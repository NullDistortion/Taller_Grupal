package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Ticket;
import view.Menu;

public class MainMenu implements Menu {

    private Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu() {
        System.out.println("== MENÚ PRINCIPAL ==");
        System.out.println("1. Atender Siguiente Ticket en cola");
        System.out.println("2. Mostrar tickets en cola");
        System.out.println("3. Crear Nuevo Ticket");
        System.out.println("4. Ver Historial de Tickets Finalizados");
        System.out.println("5. Volver a Ticket Actual");
        System.out.println("6. Guardar y Salir");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        String option = sc.nextLine().trim();

        switch (option) {
            case "1": // ATENDER SIGUIENTE TICKET
                if (currentTicket != null) {
                    System.out.println("¡Error! Ya hay un ticket en atención (ID: " + currentTicket.getId() + ").");
                    System.out.println("Finalice el ticket actual o use la Opción 5 para volver a él.");
                } else {
                    Ticket newTicket = bs.processTicket(); // Saca de la cola
                    if (newTicket == null) {
                        System.out.println("No hay tickets en la cola.");
                    } else {
                        System.out.println("Atendiendo nuevo ticket (ID: " + newTicket.getId() + ").");
                        // Entramos al menú de ticket y actualizamos currentTicket
                        // con lo que sea que devuelva ese menú.
                        currentTicket = new TicketsMenu().handleInput(bs, newTicket);
                    }
                }
                break;
            case "2": // IMPRIME TICKETS EN COLA
                bs.printTickets();
                break;
            case "3": // CREAR NUEVO TICKET
                bs.createTicket();
                break;

            case "4": // VER HISTORIAL
                bs.showFinalizedHistory();// Esto no afecta a currentTicket
                break;

            case "5": // (Opcional) VOLVER A TICKET ACTUAL
                if (currentTicket == null) {
                    System.out.println("No hay ningún ticket en atención.");
                } else {
                    System.out.println("Volviendo al ticket (ID: " + currentTicket.getId() + ").");
                    // Volvemos a entrar al menú de ticket
                    currentTicket = new TicketsMenu().handleInput(bs, currentTicket);
                }
                break;

            case "6": // GUARDAR Y SALIR
                if (currentTicket != null) {
                    System.out.println("Guardando tickets pendientes en cola...");
                    bs.savePendingQueue(); // Tu nueva función de persistencia

                    System.out.println("Guardando historial de tickets finalizados...");
                    bs.exportFinalizedTickets();
                    System.out.println("Saliendo del programa.");

                    System.exit(0);
                } else {
                    System.out.println("Hay un Ticket siendo atendido no puede Salir");
                }
                break;

            default:
                System.out.println("Opción inválida.");
        }

        // Devolvemos el estado de currentTicket (haya cambiado o no) a RunApp
        return currentTicket;
    }
}
