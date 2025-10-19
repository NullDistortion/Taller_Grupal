package view;

import java.util.Scanner;

public class Main {
    public String name;

    public static void main(String[] args) {

        System.out.println("Bienveido al sistema");
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese su nombre");
        String name = sc.nextLine();
        int option;


        do {
            printMenu();
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine();

                ejecutionOption(option, sc);

            } else {
                System.out.println("/n Ingrese un numero valido");
                sc.nextLine();
                option = -1;
            }

        } while (option != 5);

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
        System.out.println("5. salir del programa");
        System.out.println("===================================");
        System.out.println("Ingrese una opcion: ");

    }

    public static void ejecutionOption(int option, Scanner sc) {

        switch (option) {
            case 1:
                System.out.println("Hacer registro de ticket");
                System.out.println("Ingrese su nombre");
                String name = sc.nextLine();

                System.out.println("Ingrese su apellido");
                String lastname = sc.nextLine();

                System.out.println("Ingrese el numero de su carnet identificativo");
                String identityCard = sc.nextLine();

                System.out.println("Ingrese su numero telefonico");
                String telephone = sc.nextLine();

                System.out.println("Tiket ingreasdo de manera correcta");

                break;
            case 2:
                System.out.println("Consultar ticket");

                break;

            case 3:
                System.out.println("Editar ticket");
                System.out.println("Ingrese la nueva descripcion");

                //TODO
                String description = sc.nextLine();

                break;
            case 4:
                System.out.println("Cambiar estado de ticket");
                System.out.println("Ingrese el nuevo estado");


                break;
            case 5:
                System.out.println("Salir del programa");

                break;

        }

    }
}