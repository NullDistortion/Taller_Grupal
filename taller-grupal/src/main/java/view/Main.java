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
                            System.out.print("Por favor, ingresa un numero entero: ");
                            n = sc.nextInt();
                            if (n > 0) {
                                flagInt = false;
                            } else {
                                System.out.println("Error: El número debe ser mayor que 0.");
                            }

                        } catch (java.util.InputMismatchException e) {

                            System.out.println("Error: Eso no es un numero entero mayor a 0. Inténtalo de nuevo.");
                            sc.nextLine();

                        }
                    } while (flagInt);

                    sc.nextLine();

                    for (int i = 0; i < n; i++) {
                        bs.addToQueue(Utility.generateTicket());
                    }
                    System.out.println("Se han ingresado x" + n + " tickets");

                    break;
// Atender ticket
                case "1":
                    t = bs.processTicked();
                    if (t == null) {
                        System.out.println("No hay tickets en cola.");
                        break;
                    }

                    boolean secondFlagTicket = true;
                    do {
                        printMenuTickets();
                        switch (sc.nextLine()) {
                            //Mostrar ticket Actual
                            case "0": 
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
                                        String oldDesc;
                                        do {
                                            System.out.println("Ingrese el texto EXACTO del comentario a modificar:");
                                            oldDesc = sc.nextLine().trim();
                                            if (oldDesc.isEmpty()) {
                                                System.out.println("Error: La descripción no puede estar vacía.");
                                            }
                                        } while (oldDesc.isEmpty());

                                        String newDesc;
                                        do {
                                            System.out.println("Ingrese el NUEVO texto del comentario:");
                                            newDesc = sc.nextLine().trim();
                                            if (newDesc.isEmpty()) {
                                                System.out.println("Error: La descripción no puede estar vacía.");
                                            }
                                        } while (newDesc.isEmpty());

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
                                Ticket beforeChange = bs.undoChanges(t);
                                if (beforeChange != null) {
                                    t = beforeChange;
                                    System.out.println("Cambio revertido.");
                                } else {
                                    System.out.println("No hay mas cambios que revertir.");
                                }
                                break;

                            case "4": // Rehacer cambios
                                Ticket afterChange = bs.redoChanges(t);
                                if (afterChange != null) {
                                    t = afterChange;
                                    System.out.println("Cambio rehecho.");
                                } else {
                                    System.out.println("No hay más cambios que rehacer.");
                                }
                                break;

                            case "5":
                                secondFlagTicket = false;
                                fm.logTicketState(t);
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

                case "3": {
                    System.out.println("Hacer registro de ticket");

                    String name;
                    do {
                        System.out.println("Ingrese su nombre:");
                        name = sc.nextLine().trim(); // .trim() elimina espacios en blanco
                        if (name.isEmpty()) {
                            System.out.println("Error: El nombre no puede estar vacío. Intente de nuevo.");
                        }
                    } while (name.isEmpty());

                    String lastname;
                    do {
                        System.out.println("Ingrese su apellido:");
                        lastname = sc.nextLine().trim();
                        if (lastname.isEmpty()) {
                            System.out.println("Error: El apellido no puede estar vacío. Intente de nuevo.");
                        }
                    } while (lastname.isEmpty());

                    String identityCard;
                    do {
                        System.out.println("Ingrese el numero de su carnet identificativo:");
                        identityCard = sc.nextLine().trim();
                        if (identityCard.isEmpty()) {
                            System.out.println("Error: El carnet no puede estar vacío. Intente de nuevo.");
                        }
                    } while (identityCard.isEmpty());

                    String telephone;
                    do {
                        System.out.println("Ingrese su numero telefonico:");
                        telephone = sc.nextLine().trim();
                        if (telephone.isEmpty()&& !(bs.validateInput(name, lastname, identityCard, telephone))) {
                            System.out.println("Error: Formato Incorrecto. Intente de nuevo.");
                        }
                    } while (telephone.isEmpty());

                    Person newPerson = new Person(name, lastname, identityCard, telephone);
                    Ticket newTicket = new Ticket(newPerson, null, Status.EN_COLA);

                    boolean tipoValido = false;
                    do {
                        System.out.println("Ingrese el tipo de transaccion que va a realizar");
                        System.out.println("======= Tipos de transaccion ======");
                        System.out.println("1. MATRICULA");
                        System.out.println("2. HOMOLOGACION");
                        System.out.println("3. CONTANCIA_CERTIFICADOS");

                        switch (sc.nextLine()) {
                            case "1":
                                newTicket.setType(Type.MATRICULA);
                                tipoValido = true; // Opción válida, salimos del bucle
                                break;
                            case "2":
                                newTicket.setType(Type.HOMOLOGACION);
                                tipoValido = true; // Opción válida, salimos del bucle
                                break;
                            case "3":
                                newTicket.setType(Type.CONTANCIA_CERTIFICADOS);
                                tipoValido = true; // Opción válida, salimos del bucle
                                break;
                            default:
                                System.out.println("Opcion no valida. Por favor, elija 1, 2 o 3.");
                            // tipoValido sigue 'false', el bucle se repetirá
                        }
                    } while (!tipoValido); // Repetir mientras no se elija una opción válida

                    bs.addToQueue(newTicket);
                    System.out.println("Ticket ingresado de manera correcta");
                    break;
                }
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
