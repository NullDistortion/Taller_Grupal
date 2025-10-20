package view;

import controller.Controller;
import domain.Person;
import domain.Ticket;
import java.util.Scanner;

public class Main {
    public String name;


    public static void main(String[] args) {
        boolean bandera = true;

        System.out.println("Bienveido al sistema");
        Scanner sc = new Scanner(System.in);


        do {
            printMenu();
            Controller ct = new Controller();
            String option = null;
            option = sc.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Hacer registro de ticket");
                    System.out.println("Ingrese su nombre");
                    String name = sc.nextLine();
                    ct.registerTicket();

                    System.out.println("Ingrese su apellido");
                    String lastname = sc.nextLine();

                    System.out.println("Ingrese el numero de su carnet identificativo");
                    String identityCard = sc.nextLine();

                    System.out.println("Ingrese su numero telefonico");
                    String telephone = sc.nextLine();

                    System.out.println("Ingrese el tipo de transaccion que va a realizar");
                    System.out.println("======= Tipos de transaccion ======");
                    System.out.println("1. MATRICULA");
                    System.out.println("2. HOMOLOGACION");
                    System.out.println("3. CONTANCIA_CERTIFICADOS");

                    String option2 = sc.nextLine();
                    switch(option2){

                        case "1":
                            System.out.println("Ha seleccionado matricula");
                            break;
                        case "2":
                            System.out.println("Ha seleccionado homologacion");
                            break;
                        case "3":
                            System.out.println("Ha seleccionado contancia de certificados");
                            break;
                    }
                    Person p = new Person(name, lastname, identityCard, telephone);
                    Ticket t = new Ticket(p, null, null);

                    System.out.println("Tiket ingresado de manera correcta");

                    break;
                case "2":
                    System.out.println("Consultar ticket");
                    ct.consultTicket();

                    break;

                case "3":
                    System.out.println("Editar ticket");
                    System.out.println("Ingrese la nueva descripcion");

                    ct.editTicket();

                    //TODO
                    String description = sc.nextLine();

                    break;
                case "4":
                    System.out.println("Cambiar estado de ticket");
                    System.out.println("Ingrese el nuevo estado");

                    System.out.println("======= Estados ======");
                    System.out.println("1. EN_COLA");
                    System.out.println("2. EN_ATENCION");
                    System.out.println("3. EN_PROCESO");
                    System.out.println("4. PENDIENTE_DOCS");
                    System.out.println("5. COMPLETADO");


                    String option3 = sc.nextLine();

                    switch (option3){
                        case "1":
                            System.out.println("Su tiket esta en cola");
                            break;
                        case "2":
                            System.out.println("Su tiket esta en atencion");
                            break;
                        case "3":
                            System.out.println("Su tiket esta en proceso");
                            break;
                        case "4":
                            System.out.println("Su tiket esta pendiente de documentacion");
                            break;
                        case "5":
                            System.out.println("Su tiket esta completado");
                            break;
                    }
                    ct.changeStatus();


                    break;
                case "5":
                    System.out.println("Salir del programa");
                    bandera = false;
                    break;
                default:
                    System.out.println("Opcion no valida");

            }
        } while (bandera);

        System.out.println("Ha salido del sistema");
        sc.close();


    }

    public static void printMenu() {
        System.out.println("===================================");
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("1. hacer el registro de un ticket");
        System.out.println("2. consultar ticket");
        System.out.println("3. editar tiket");
        System.out.println("4. cambiar estado de ticket");
        //imprimir historial
        System.out.println("5. salir del programa");
        System.out.println("===================================");
        System.out.println("Ingrese una opcion: ");

    }
}
