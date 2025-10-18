/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package theNODE;

/**
 *
 * @author MikelMZ : Miguel Armas
 */
/**
 * Clase Nodo genérico para estructuras de datos (SLL, Pila, Cola).
 * Permite almacenar cualquier tipo de dato T.
 */
public class DoubleNode<T> {
    
    // Atributo privado para el dato que almacenará el nodo
    private T dato; 
    
    // Atributo privado para la referencia al siguiente nodo
    private DoubleNode<T> prev; 
    private DoubleNode<T> next; 

    // Constructor
    public DoubleNode(T dato) {
        this.dato = dato;
        this.next = null; 
        this.prev=null;
    }

    // --- Getters y Setters ---

    // Permite acceder al dato almacenado
    public T getDato() {
        return dato;
    }

    // Permite cambiar el dato almacenado (opcional, dependiendo de la inmutabilidad)
    public void setDato(T dato) {
        this.dato = dato;
    }

    public DoubleNode<T> getPrev() {
        return prev;
    }

    public void setPrev(DoubleNode<T> prev) {
        this.prev = prev;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }
    
}