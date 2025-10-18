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
public class Node<T> {
    
    // Atributo privado para el dato que almacenará el nodo
    private T dato; 
    
    // Atributo privado para la referencia al siguiente nodo
    private Node<T> siguiente; 

    // Constructor
    public Node(T dato) {
        this.dato = dato;
        this.siguiente = null; // Inicialmente no apunta a nada
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

    // Permite acceder a la referencia del siguiente nodo
    public Node<T> getSiguiente() {
        return siguiente;
    }

    // Permite enlazar el nodo a otro (crucial para SLL)
    public void setSiguiente(Node<T> siguiente) {
        this.siguiente = siguiente;
    }
}