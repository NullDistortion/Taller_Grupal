package view;

import business.Business;
import business.FileManager;
import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;

import java.util.Scanner;
import util.Utility;

public class Main {

    @SuppressWarnings("empty-statement")
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
                case "0":
                    System.out.println("Cuantos tickets desea generar");
                    int n = 0;
                    boolean flagInt = true;
                    do {
                        try {
                            System.out.print("Por favor, ingresa un número entero: ");
                            n = sc.nextInt();
                            flagInt = false;

                        } catch (java.util.InputMismatchException e) {

                            System.out.println("Error: Eso no es un número entero. Inténtalo de nuevo.");
                            sc.nextLine();

                        }
                    } while (flagInt);

                    sc.nextLine();

                    for (int i = 0; i < n; i++) {
                        bs.addToQueue(Utility.generateTicket());
                    }
                    System.out.println("Se han ingresado x" + n + " tickets");

                    break;

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
                            case "0": //Mostrar ticket Actual
                                System.out.println("Ticket Actual");

                                if (t != null) {
                                    System.out.println(t.toString());
                                } else {
                                    System.out.println("No hay ticket activo (posiblemente revertido a nulo).");
                                }

                                break;
                            case "1": //Opciones de Comentarios
                                printMenuComments();
                                switch (sc.nextLine()) {

                                    case "1": { // Añadir comentario
                                        System.out.println("Anadiendo comentario");
                                        System.out.println("Ingrese su comentario:");
                                        String comment = sc.nextLine();

                                        try {
                                            bs.registerChange(t);
                                            // 2. Ejecutar la acción (que ahora valida y puede fallar)
                                            t.getPerson().addComment(comment);
                                            System.out.println("Comentario añadido.");

                                        } catch (IllegalArgumentException e) {
                                            System.out.println("Error al añadir: " + e.getMessage());
                                            bs.discardLastUndo();
                                        }
                                        break;
                                    }

                                    case "2": { // Modificar comentario
                                        System.out.println("Modificando comentario");
                                        System.out.println("Ingrese el texto EXACTO del comentario a modificar:");
                                        String oldDesc = sc.nextLine();
                                        System.out.println("Ingrese el NUEVO texto del comentario:");
                                        String newDesc = sc.nextLine();

                                        try {

                                            bs.registerChange(t);

                                            boolean updated = t.getPerson().updateComment(oldDesc, newDesc);

                                            if (updated) {
                                                System.out.println("Comentario actualizado.");
                                            } else {
                                                System.out.println("Error: No se encontró el comentario o la nueva descripción estaba vacía.");
                                                bs.discardLastUndo();
                                            }
                                        } catch (IllegalArgumentException e) {
                                            System.out.println("Error al modificar: " + e.getMessage());
                                            bs.discardLastUndo();
                                        }
                                        break;
                                    }

                                    case "3": { // Eliminar comentario
                                        System.out.println("Eliminando comentario");
                                        System.out.println("Ingrese el texto EXACTO del comentario a eliminar:");
                                        String delDesc = sc.nextLine();

                                        bs.registerChange(t);

                                        boolean deleted = t.getPerson().deleteComment(delDesc);

                                        if (deleted) {
                                            System.out.println("Comentario eliminado.");
                                        } else {
                                            System.out.println("Error: No se encontró el comentario.");
                                            bs.discardLastUndo();
                                        }
                                        break;
                                    }

                                    case "4": { // Volver
                                        System.out.println("Volviendo al menú anterior...");
                                        break;
                                    }

                                    default:
                                        System.out.println("Opción no válida.");
                                        break;
                                }
                                break;
                            case "2":
                                System.out.println("Cambiando estado del ticket");
                                printMenuStatus();
                                switch (sc.nextLine()) {
                                    case "1":

                                        bs.registerChange(t);
                                        t.setStatus(Status.EN_COLA);
                                        break;
                                    case "2":

                                        bs.registerChange(t);
                                        t.setStatus(Status.EN_ATENCION);
                                        break;
                                    case "3":

                                        bs.registerChange(t);
                                        t.setStatus(Status.EN_PROCESO);
                                        break;
                                    case "4":

                                        bs.registerChange(t);
                                        t.setStatus(Status.PENDIENTE_DOCS);
                                        break;
                                    case "5":

                                        bs.registerChange(t);
                                        t.setStatus(Status.COMPLETADO);
                                        break;
                                    case "6":
                                        System.out.println("Regresando al menu anterior");
                                        break;
                                    default:
                                        System.out.println("Opcion no valida");
                                }
                                break;

                            case "3":
                                if (t != null) {

                                    Ticket estadoAnterior = bs.undoChanges(t);

                                    if (estadoAnterior != null) {
                                        t = estadoAnterior;
                                        System.out.println("Cambio revertido.");
                                    } else {
                                        System.out.println("No hay más cambios que revertir.");
                                    }
                                } else {
                                    System.out.println("No hay CAMBIOS POR EFECTUAR.");
                                }
                                break;

                            case "4": // Rehacer cambios
                                if (t != null) {

                                    Ticket estadoSiguiente = bs.redoChanges(t);

                                    if (estadoSiguiente != null) {
                                        t = estadoSiguiente;
                                        System.out.println("Cambio rehecho.");
                                    } else {
                                        System.out.println("No hay más cambios que rehacer.");
                                    }
                                } else {
                                    System.out.println("No hay ticket activo.");
                                }
                                break;

                            case "5":
                                secondFlagTicket = false;
                                if (t != null) {
                                    fm.logTicketState(t);

                                } else {
                                    System.out.println("No hay ticket para guardar");
                                }

                                break;

                            default:
                                System.out.println("Opcion no valida");
                        }
                    } while (secondFlagTicket);
                    break;

                case "2":
                    System.out.println("Consultando tickets disponibles");
                    bs.printTickets();
                    break;

                case "3":
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
                    t = newTicket;
                    break;

                case "4":

                    System.out.println(fm.generateReport());

                    break;

                case "5":
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
        System.out.println("0. Llenar tickets");
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
        System.out.println("0. Mostrar Ticket");
        System.out.println("1. Opciones de Comentarios");
        System.out.println("2. Cambiar estado");
        System.out.println("3. Revertir cambios");
        System.out.println("4. Rehacer cambios");
        System.out.println("5. Finalizar Atencion de Ticket actual");
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

    public static void printMenuComments() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("====================================");
        System.out.println("1. Añadir Comentario");
        System.out.println("2. Modificar Comentario");
        System.out.println("3. Eliminar Comentario");
        System.out.println("4. Regresar");
        System.out.println("==================================");
        System.out.println("Ingrese una opcion: ");
    }
}
