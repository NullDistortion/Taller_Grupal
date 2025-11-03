package view;

import business.Business;
import domain.Ticket;

public interface Menu {
    void showMenu();
    Ticket handleInput(Business bs, Ticket currentTicket);
}
