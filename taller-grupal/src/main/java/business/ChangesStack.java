/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package business;

import domain.Ticket;
import theNODE.DoubleNode;

/**
 *
 * @author MikelMZ : Miguel Armas
 */
public class ChangesStack {
    private DoubleNode<Ticket> undo;
    private DoubleNode<Ticket> redo;
    private int undoLimit;
    
    //TODO
}
