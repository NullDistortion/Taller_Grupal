package view.menus;

import business.Business;
import business.FileManager;
import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;
import util.Utility;
import view.Menu;

import java.util.Scanner;

public class MainMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("0. Llenar tickets");
        System.out.println("1. Atender ticket");
        System.out.println("2. Tickets disponibles");
        System.out.println("3. Generar ticket");
        System.out.println("4. Imprimir historial");
        System.out.println("5. Salir del programa");
        System.out.println("===================================");
        System.out.print("Ingrese una opcion: ");
    }

    @Override
    public Ticket handleInput(Scanner sc, Business bs, FileManager fm, Ticket t) {
        boolean flag = true;

        System.out.println("Bienvenido al sistema");

        while (flag) {
            showMenu();
            String mainOpt = sc.nextLine();

            switch (mainOpt) {
                case "0": {
                    System.out.println("Cuantos tickets desea generar");
                    byte n = 0;
                    boolean flagInt = true;
                    do {
                        try {
                            System.out.print("Por favor, ingresa un numero entero: ");
                            n = sc.nextByte();
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
                    sc.nextLine(); // consumir enter restante

                    for (int i = 0; i < n; i++) {
                        bs.addToQueue(Utility.generateTicket()); //TODO Utility esta vacio papu
                    }
                    System.out.println("Se han ingresado x" + n + " tickets");
                    break;
                }

                case "1": {
                    t = bs.processTicket();
                    if (t == null) {
                        System.out.println("No hay tickets en cola.");
                        break;
                    }
                    TicketsMenu ticketMenu = new TicketsMenu();
                    t = ticketMenu.handleInput(sc, bs, fm, t);
                    break;
                }

                case "2": {
                    System.out.println("Consultando tickets disponibles");
                    bs.printTickets();
                    break;
                }

                case "3": {
                    System.out.println("Hacer registro de ticket");

                    String name;
                    do {
                        System.out.println("Ingrese su nombre:");
                        name = sc.nextLine().trim();
                        if (name.isEmpty())
                            System.out.println("Error: El nombre no puede estar vacío. Intente de nuevo.");
                    } while (name.isEmpty());

                    String lastname;
                    do {
                        System.out.println("Ingrese su apellido:");
                        lastname = sc.nextLine().trim();
                        if (lastname.isEmpty())
                            System.out.println("Error: El apellido no puede estar vacío. Intente de nuevo.");
                    } while (lastname.isEmpty());


                    //TODO Eliminar atributos telephone e identitycard

                    String identityCard;
                    do {
                        System.out.println("Ingrese el numero de su carnet identificativo:");
                        identityCard = sc.nextLine().trim();
                        if (identityCard.isEmpty())
                            System.out.println("Error: El carnet no puede estar vacío. Intente de nuevo.");
                    } while (identityCard.isEmpty());

                    String telephone;
                    do {
                        System.out.println("Ingrese su numero telefonico:");
                        telephone = sc.nextLine().trim();
                        if (telephone.isEmpty() && !(bs.validateInput(name, lastname, identityCard, telephone))) {
                            System.out.println("Error: Formato Incorrecto. Intente de nuevo.");
                        }
                    } while (telephone.isEmpty());

                    Person newPerson = new Person(name, lastname, identityCard, telephone);
                    Ticket newTicket = new Ticket(newPerson, null, Status.EN_COLA, false);

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
                                tipoValido = true;
                                break;
                            case "2":
                                newTicket.setType(Type.HOMOLOGACION);
                                tipoValido = true;
                                break;
                            case "3":
                                newTicket.setType(Type.CONTANCIA_CERTIFICADOS);
                                tipoValido = true;
                                break;
                            default:
                                System.out.println("Opcion no valida. Por favor, elija 1, 2 o 3.");
                        }
                    } while (!tipoValido);

                    bs.addToQueue(newTicket);
                    System.out.println("Ticket ingresado de manera correcta");
                    break;
                }

                case "4": {
                    System.out.println(fm.generateReport());
                    break;
                }

                case "5": {
                    System.out.println("Salir del programa");
                    flag = false;
                    break;
                }

                default:
                    System.out.println("Opcion no valida");
            }
        }
        return null;
    }
}
