package view.menus;

import java.util.Scanner;
import business.Business;
import business.FileManager;
import domain.Ticket;
import view.Menu;

public class ComentMenu implements Menu {

    @Override
    public void showMenu() {
        System.out.println("Escoga la accion que desee realizar");
        System.out.println("===================================");
        System.out.println("1. Añadir Comentario");
        System.out.println("2. Modificar Comentario");
        System.out.println("3. Eliminar Comentario");
        System.out.println("4. Regresar");
        System.out.println("===================================");
        System.out.print("Ingrese una opcion: ");
    }

    @Override
    public Ticket handleInput(Scanner sc, Business bs, FileManager fm, Ticket t) {
        boolean back = false;

        while (!back) {
            showMenu();
            String opt = sc.nextLine();

            switch (opt) {
                case "1": { // Añadir comentario
                    System.out.println("Anadiendo comentario");
                    System.out.println("Ingrese su comentario:");
                    String comment = sc.nextLine();
                    try {
                        bs.registerChange(t);
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
                        if (oldDesc.isEmpty()) System.out.println("Error: La descripción no puede estar vacía.");
                    } while (oldDesc.isEmpty());

                    String newDesc;
                    do {
                        System.out.println("Ingrese el NUEVO texto del comentario:");
                        newDesc = sc.nextLine().trim();
                        if (newDesc.isEmpty()) System.out.println("Error: La descripción no puede estar vacía.");
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

                case "4":
                    System.out.println("Volviendo al menu anterior...");
                    back = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        return t;
    }
}
