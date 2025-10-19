package theNODE;

/**
 * Clase Nodo genérico para estructuras de datos (SLL, Pila, Cola).
 * Permite almacenar cualquier tipo de data T.
 */
public class Node<T> {

    // Atributo privado para el data que almacenará el nodo
    private T data;

    // Atributo privado para la referencia al next nodo
    private Node<T> next;

    // Constructor
    public Node(T data) {
        this.data = data;
        this.next = null; // Inicialmente no apunta a nada
    }

    // Permite acceder al data almacenado
    public T getData() {
        return data;
    }

    // Permite cambiar el data almacenado (opcional, dependiendo de la inmutabilidad)
    public void setData(T data) {
        this.data = data;
    }

    // Permite acceder a la referencia del next nodo
    public Node<T> getNext() {
        return next;
    }

    // Permite enlazar el nodo a otro (crucial para SLL)
    public void setNext(Node<T> next) {
        this.next = next;
    }
}