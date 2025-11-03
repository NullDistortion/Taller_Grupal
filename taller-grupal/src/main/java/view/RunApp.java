package view;

import business.Business;
import domain.Ticket;
import view.menus.MainMenu;

public class RunApp {

    public static void main(String[] args) {
        Business bs = new Business();
        Ticket currentTicket = null;
        Menu mainMenu = new MainMenu();

        while (true) {
            mainMenu.showMenu();
            currentTicket = mainMenu.handleInput(bs, currentTicket);
        }
    }
}
