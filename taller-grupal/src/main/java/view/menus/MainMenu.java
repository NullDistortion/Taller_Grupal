package view.menus;

import java.util.Scanner;
import business.Business;
import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;
import view.Menu;

public class MainMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Atender siguiente ticket");
        System.out.println("2. Mostrar tickets en cola");
        System.out.println("3. Imprimir historial (pendiente)");
        System.out.println("4. Crear ticket manualmente");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    @Override
    public Ticket handleInput(Business bs, Ticket currentTicket) {
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine();

        switch (option) {
            case "1":
                Ticket next = bs.processTicket();
                if (next != null) {
                    System.out.println("Atendiendo: " + next);
                    new TicketsMenu().handleInput(bs, next);
                } else {
                    System.out.println("No hay tickets disponibles.");
                }
                break;

            case "2":
                bs.printTickets();
                break;

            case "3":
                //TODO FileManger csv
                break;

            case "4": //TODO esta logica va en bussines
                System.out.println("\n=== CREAR NUEVO TICKET ===");
                System.out.print("Nombre: ");
                String name = sc.nextLine();
                System.out.print("Apellido: ");
                String lastName = sc.nextLine();

                if (!bs.validateInput(name, lastName)) {
                    return currentTicket;
                }

                System.out.print("¿Es prioridad? (s/n): ");
                boolean priority = sc.nextLine().equalsIgnoreCase("s");


                System.out.println("\nSeleccione el tipo de trámite:");
                System.out.println("1. MATRICULA");
                System.out.println("2. HOMOLOGACION");
                System.out.println("3. CONTANCIA_CERTIFICADOS");
                System.out.print("Opción: ");
                String typeOption = sc.nextLine();

                Type type;
                switch (typeOption) {
                    case "1":
                        type = Type.MATRICULA;
                        break;
                    case "2":
                        type = Type.HOMOLOGACION;
                        break;
                    case "3":
                        type = Type.CONTANCIA_CERTIFICADOS;
                        break;
                    default:
                        System.out.println("Opción inválida, se usará MATRICULA por defecto.");//TODO debo pensar en eso mejor
                        type = Type.MATRICULA;
                        break;
                }
                // TODO logica mal encapsulada, luego lo cambio ahora nesesitaba probar

                Person person = new Person(name, lastName);
                Ticket newTicket = new Ticket(person, type, Status.EN_COLA, priority);
                bs.addToQueue(newTicket);

                System.out.println("Ticket agregado correctamente: " + newTicket);
                break;

            case "5":
                System.out.println("Saliendo del programa...");
                System.exit(0);
                break;

            default:
                System.out.println("Opción inválida.");
        }

        return currentTicket;
    }
}
