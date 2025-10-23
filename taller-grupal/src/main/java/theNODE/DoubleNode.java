package theNODE;

/**
 * Clase Nodo genérico para estructuras de datos (DLL, Pila, Cola).
 * Permite almacenar cualquier tipo de data T.
 * Adaptada de la sigueinte fuente: https://www.it.uc3m.es/java/git-gisc-2013-14/units/pilas-colas/guides/2/guide_es_solution.html
 */
public class DoubleNode<T> {

    // Atributo privado para el data que almacenará el nodo
    private T data;

    // Atributo privado para la referencia al nodo anterior y siguiente
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