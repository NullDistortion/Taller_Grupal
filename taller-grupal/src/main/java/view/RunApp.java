package view;

import java.util.Scanner;
import business.Business;
import business.FileManager;
import view.menus.MainMenu;

public class RunApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Business bs = new Business();
        FileManager fm = new FileManager();

        MainMenu mainMenu = new MainMenu();
        mainMenu.handleInput(sc, bs, fm, null);

        sc.close();
        System.out.println("Ha salido del sistema");
    }
}
