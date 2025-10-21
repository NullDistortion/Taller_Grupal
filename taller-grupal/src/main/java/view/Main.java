package view;

import business.Business;
import business.FileManager;
import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);

        Business bs = new Business();
        FileManager fm = new FileManager();

        Ticket t = null;

        System.out.println("Bienvenido al sistema");

        do {
            printMenu();
            switch (sc.nextLine()) {

                case "1": // Atender ticket
                    t = bs.processTicked();
                    if (t == null) {
                        System.out.println("No hay tickets en cola.");
                        break;
                    }

                    boolean secondFlagTicket = true;
                    do {
                        printMenuTickets();
                        switch (sc.nextLine()) {

                            case "1": // Agregar comentario
                                System.out.println("Anadiendo comentario");
                                System.out.println("Ingrese su comentario:");
                                String comment = sc.nextLine();
                                t.getPerson().addComment(comment);
                                bs.registerChange(t);
                                break;

                            case "2": // Cambiar estado
                                System.out.println("Cambiando estado del ticket");
                                printMenuStatus();
                                switch (sc.nextLine()) {
                                    case "1":
                                        t.setStatus(Status.EN_COLA);
                                        bs.registerChange(t);
                                        break;
                                    case "2":
                                        t.setStatus(Status.EN_ATENCION);
                                        bs.registerChange(t);
                                        break;
                                    case "3":
                                        t.setStatus(Status.EN_PROCESO);
                                        bs.registerChange(t);
                                        break;
                                    case "4":
                                        t.setStatus(Status.PENDIENTE_DOCS);
                                        bs.registerChange(t);
                                        break;
                                    case "5":
                                        t.setStatus(Status.COMPLETADO);
                                        bs.registerChange(t);
                                        break;
                                    case "6":
                                        System.out.println("Regresando al menu anterior");
                                        break;
                                    default:
                                        System.out.println("Opcion no valida");
                                }
                                break;

                            case "3": // Revertir cambios
                                if (t != null) {
                                    t = bs.undoChanges(t);
                                    System.out.println("Cambio revertido.");
                                } else {
                                    System.out.println("No hay ticket activo.");
                                }
                                break;

                            case "4": // Rehacer cambios
                                if (t != null) {
                                    t = bs.redoChanges(t);
                                    System.out.println("Cambio rehecho.");
                                } else {
                                    System.out.println("No hay ticket activo.");
                                }
                                break;

                            case "5": // Volver al menu principal
                                secondFlagTicket = false;
                                break;

                            default:
                                System.out.println("Opcion no valida");
                        }
                    } while (secondFlagTicket);
                    break;

                case "2": // Tickets disponibles
                    System.out.println("Consultando tickets disponibles");
                    bs.printTickets();
                    break;

                case "3": // Generar ticket
                    System.out.println("Hacer registro de ticket");

                    System.out.println("Ingrese su nombre");
                    String name = sc.nextLine();

                    System.out.println("Ingrese su apellido");
                    String lastname = sc.nextLine();

                    System.out.println("Ingrese el numero de su carnet identificativo");
                    String identityCard = sc.nextLine();

                    System.out.println("Ingrese su numero telefonico");
                    String telephone = sc.nextLine();

                    Person newPerson = new Person(name, lastname, identityCard, telephone);
                    Ticket newTicket = new Ticket(newPerson, null, Status.EN_COLA);

                    System.out.println("Ingrese el tipo de transaccion que va a realizar");
                    System.out.println("======= Tipos de transaccion ======");
                    System.out.println("1. MATRICULA");
                    System.out.println("2. HOMOLOGACION");
                    System.out.println("3. CONTANCIA_CERTIFICADOS");

                    switch (sc.nextLine()) {
                        case "1":
                            newTicket.setType(Type.MATRICULA);
                            break;
                        case "2":
                            newTicket.setType(Type.HOMOLOGACION);
                            break;
                        case "3":
                            newTicket.setType(Type.CONTANCIA_CERTIFICADOS);
                            break;
                        default:
                            System.out.println("Opcion no valida");
                    }

                    bs.addToQueue(newTicket);
                    System.out.println("Ticket ingresado de manera correcta");
                    break;

                case "4": // Imprimir historial
                    if (t != null) {
                        fm.create_history(t);
                    } else {
                        System.out.println("No hay ticket activo para generar historial.");
                    }
                    break;

                case "5": // Salir del programa
                    System.out.println("Salir del programa");
                    flag = false;
                    break;

                default:
                    System.out.println("Opcion no valida");
            }

        } while (flag);

        System.out.println("Ha salido del sistema");
        sc.close();
    }

    public static void printMenu() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("1. Atender ticket");
        System.out.println("2. Tickets disponibles");
        System.out.println("3. Generar ticket");
        System.out.println("4. Imprimir historial");
        System.out.println("5. Salir del programa");
        System.out.println("===================================");
        System.out.println("Ingrese una opcion: ");
    }

    public static void printMenuTickets() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("1. Agregar comentario");
        System.out.println("2. Cambiar estado");
        System.out.println("3. Revertir cambios");
        System.out.println("4. Rehacer cambios");
        System.out.println("5. Volver");
        System.out.println("===================================");
        System.out.println("Ingrese una opcion: ");
    }

    public static void printMenuStatus() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("====================================");
        System.out.println("1. Cambiar estado a En Cola");
        System.out.println("2. Cambiar estado a En Atencion");
        System.out.println("3. Cambiar estado a En Proceso");
        System.out.println("4. Cambiar estado a Documento Pendiente");
        System.out.println("5. Cambiar estado a Completado");
        System.out.println("6. Regresar");
        System.out.println("==================================");
        System.out.println("Ingrese una opcion: ");
    }
}
