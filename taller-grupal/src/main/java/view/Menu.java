package view;

import java.util.Scanner;
import business.Business;
import business.FileManager;
import domain.Ticket;

public interface Menu {

    void showMenu();

    Ticket handleInput(Scanner sc, Business bs, FileManager fm, Ticket t);
}
