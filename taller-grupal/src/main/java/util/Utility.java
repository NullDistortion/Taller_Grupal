/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import domain.Person;
import domain.Ticket;
import enums.Status;
import enums.Type;
import java.util.Random;

/**
 *
 * @author MikelMZ : Miguel Armas
 */
public class Utility {

    public static String[] nombres = {"Juan", "Miguel", "Manuel", "Andres", "Ximena", "Ana", "Cristina", "Rosa"};
    public static String[] apellidos = {"Morales", "Pineda", "Mora", "Gonzales", "Guzaman", "Cordova", "Maldonado", "Torres"};

    public static Ticket generateTicket() {
        Person person = generatePerson();
        Type[] type = Type.values();
        int randomIndex = generarRandomEnRango(0, type.length - 1);
        return new Ticket(person, type[generarRandomEnRango(0, randomIndex)], Status.EN_COLA);
    }

    public static Person generatePerson() {
        String ci = generarIC();
        String tel = generarTel();
        String nombre = nombres[generarRandomEnRango(0, nombres.length - 1)];
        String apellido = apellidos[generarRandomEnRango(0, apellidos.length - 1)];
        return new Person(nombre, apellido, ci, tel);
    }

    public static String generarIC() {
        long min = 100000000L;
        long max = 2499999999L;

        // Genera el número dentro del rango Long.
        Long numero = generarRandomEnRango(min, max);
        String numStr = numero.toString();

        if (numero < 1000000000L) {
            numStr = "0" + numStr;
        }
        return numStr;
    }

    public static String generarTel() {
        int min = 0;
        int max = 99999999; // 8 nueves (8 dígitos)

        // Genera un número entre 0 y 99,999,999 (puede ser de 1 a 8 dígitos)
        int numero = generarRandomEnRango(min, max);

        String numAleatorio = String.format("%08d", numero);
        String numStr = "09" + numAleatorio;
        return numStr;
    }

    public static int generarRandomEnRango(int min, int max) {
        Random random = new Random();

        //    El rango (bound) es (max - min) + 1 para incluir el valor 'max'.
        int rango = (max - min) + 1;

        // Genera un número de 0 (incluido) a 'rango' (excluido) y le suma el mínimo.
        int numeroAleatorio = random.nextInt(rango) + min;
        return numeroAleatorio;

    }

    public static long generarRandomEnRango(long min, long max) {
        Random random = new Random();

        //    El rango (bound) es (max - min) + 1 para incluir el valor 'max'.
        long rango = (max - min) + 1;

        // Genera un número de 0 (incluido) a 'rango' (excluido) y le suma el mínimo.
        long numeroAleatorio = random.nextLong(rango) + min;
        return numeroAleatorio;

    }
}
