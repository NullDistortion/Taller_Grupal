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
        // 1. Pedir Nombre
        String name = requestValidString(sc, "Nombre (o 'q' para cancelar): ");
        if (name.equalsIgnoreCase("q")) {
            return null; // Cancelación propagada
        }

        // 2. Pedir Apellido
        String lastName = requestValidString(sc, "Apellido (o 'q' para cancelar): ");
        if (lastName.equalsIgnoreCase("q")) {
            return null; // Cancelación propagada
        }        
        return new Person(name, lastName);
    }

    public static Boolean validatePrio(Scanner sc) { // ¡Cambiado a Boolean!
        while (true) {
            System.out.print("Es prioridad? (s/n) (o 'q' para cancelar): ");
            String answer = sc.nextLine().trim().toLowerCase();

            switch (answer) {
                case "s":
                    return true; // Éxito
                case "n":
                    return false; // Éxito
                case "q":
                    return null; // Cancelación
                default:
                    System.out.println("Ingrese un parametro valido (s/n/q).");
                    break;
            }
        }
    }

    public static Type selectType(Scanner sc) {
        while (true) {
            System.out.println("\nSeleccione el tipo de tramite:");
            System.out.println("1. MATRICULA");
            System.out.println("2. HOMOLOGACION");
            System.out.println("3. CONTANCIA_CERTIFICADOS");
            System.out.print("Opcion (o 'q' para cancelar): ");
            String option = sc.nextLine().trim();

            switch (option) {
                case "1":
                    return Type.MATRICULA;
                case "2":
                    return Type.HOMOLOGACION;
                case "3":
                    return Type.CONTANCIA_CERTIFICADOS;
                case "q":
                case "Q":
                    return null; // Cancelación
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        }
    }

   public static String requestValidString(Scanner sc, String promptMessage) {
        String input;
        while (true) {
            System.out.print(promptMessage);
            input = sc.nextLine().trim();

       
            if (input.isEmpty()) {
                System.out.println("El texto no puede estar vacío.");
            } else if (input.contains(" ")) {
                System.out.println("No se permiten espacios internos.");
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Solo se permiten letras.");
            } else {
              
                return input;
            }
            
            System.out.println("Intente de nuevo, o ingrese 'q' para cancelar.");
        }
    }

    public static Integer requestValidInteger(Scanner sc, String prompt) { // ¡Cambiado a Integer!
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number <= 0) {
                    System.out.println("Debe ingresar un numero mayor que cero.");
                } else {
                    return number; // Éxito
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no valida. Debe ingresar un numero entero.");
            }
        }
    }

    public static String requestNonEmptyString(Scanner sc, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("La descripcion no puede estar vacia.");
            } else {
                return input; // Éxito
            }
        }
    }

    public static boolean validateInput(String name, String lastname) {
        if (!isValidSingleWord(name) || !isValidSingleWord(lastname)) {
            System.out.println("Nombre y apellido deben contener solo letras, sin espacios ni estar vacios.");
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
