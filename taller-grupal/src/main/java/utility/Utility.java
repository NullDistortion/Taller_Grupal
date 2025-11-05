/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import domain.Person;
import enums.Type;
import java.util.Scanner;

/**
 *
 * @author MikelMZ : Miguel Armas
 */
public class Utility {

    public static Person requestPersonData(Scanner sc) {
        String name = requestValidString(sc, "Nombre: ");
        String lastName = requestValidString(sc, "Apellido: ");

        if (!validateInput(name, lastName)) {
            return null;
        }
        return new Person(name, lastName);
    }

    public static boolean validatePrio(Scanner sc) {
        boolean priority = false;
        boolean valid = false;

        while (!valid) {
            System.out.print("Es prioridad? (s/n): ");
            String answer = sc.nextLine().trim().toLowerCase();

            switch (answer) {
                case "s":
                    priority = true;
                    valid = true;
                    break;
                case "n":
                    valid = true;
                    break;
                default:
                    System.out.println("Ingrese un parametro valido (s/n).");
                    break;
            }
        }
        return priority;
    }

    public static Type selectType(Scanner sc) {
        Type type = null;

        while (type == null) {
            System.out.println("\nSeleccione el tipo de tramite:");
            System.out.println("1. MATRICULA");
            System.out.println("2. HOMOLOGACION");
            System.out.println("3. CONTANCIA_CERTIFICADOS");
            System.out.print("Opcion: ");
            String option = sc.nextLine().trim();

            switch (option) {
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
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        }
        return type;
    }

    public static String requestValidString(Scanner sc, String promptMessage) {
        String input;
        do {
            System.out.print(promptMessage);
            input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("El texto no puede estar vacío.");
            } else if (input.contains(" ")) {
                System.out.println("No se permiten espacios internos.");
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Solo se permiten letras.");
            }

        } while (input.isEmpty() || input.contains(" ") || !input.matches("[a-zA-Z]+"));

        return input;
    }

    public static int requestValidInteger(Scanner sc, String prompt) {
        int number = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                number = Integer.parseInt(input);
                if (number <= 0) {
                    System.out.println("Debe ingresar un número mayor que cero.");
                } else {
                    valid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ingresar un número entero.");
            }
        }

        return number;
    }

    public static String requestNonEmptyString(Scanner sc, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("La descripción no puede estar vacía.");
            }
        } while (input.isEmpty());

        return input;
    }

    public static boolean validateInput(String name, String lastname) {
        if (!isValidSingleWord(name) || !isValidSingleWord(lastname)) {
            System.out.println("Nombre y apellido deben contener solo letras, sin espacios ni estar vacíos.");
            return false;
        }
        return true;
    }

    public static boolean isNonEmptyTrimmed(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean hasNoInternalSpaces(String input) {
        return input != null && !input.contains(" ");
    }

    public static boolean isValidSingleWord(String input) {
        return isNonEmptyTrimmed(input) && hasNoInternalSpaces(input) && input.matches("[a-zA-Z]+");
    }

}
