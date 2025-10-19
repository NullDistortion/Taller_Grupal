package theNODE;

/**
 * Clase Nodo genérico para estructuras de datos (SLL, Pila, Cola).
 * Permite almacenar cualquier tipo de data T.
 */
public class DoubleNode<T> {

    // Atributo privado para el data que almacenará el nodo
    private T data;

    // Atributo privado para la referencia al siguiente nodo
    private DoubleNode<T> prev;
    private DoubleNode<T> next;

    // Constructor
    public DoubleNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    // Permite acceder al data almacenado
    public T getData() {
        return data;
    }

    // Permite cambiar el data almacenado (opcional, dependiendo de la inmutabilidad)
    public void setData(T data) {
        this.data = data;
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