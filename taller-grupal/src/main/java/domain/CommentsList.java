package domain;

import java.time.LocalDate;
import theNODE.Node;

public class CommentsList {

    private Node<Comments> head;
    private int nextId = 1;
    private int length;

    public CommentsList() {
        this.head = null;
        this.length = 0;
    }

    //Constructor de Copia (Deep Copy).
    public CommentsList(CommentsList other) {
        this.head = null;
        this.nextId = other.nextId;
        this.length = 0;

        if (other.head != null) {
            Node<Comments> currentOther = other.head;
            Node<Comments> tailThis = null;

            while (currentOther != null) {
                Comments clonedComment = new Comments(
                        currentOther.getData().getDescription(),
                        currentOther.getData().getDate()
                );

                clonedComment.setId(currentOther.getData().getId());
                Node<Comments> newNode = new Node<>(clonedComment);

                if (this.head == null) {
                    this.head = newNode;
                    tailThis = newNode;
                } else {
                    tailThis.setNext(newNode);
                    tailThis = newNode;
                }
                currentOther = currentOther.getNext();
                this.length++;
            }
        }
    }

    public void addComment(String description) throws IllegalArgumentException {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("No se puede añadir un comentario nulo o vacío.");
        }

        Comments newComment = new Comments(description, LocalDate.now());
        newComment.setId(nextId);
        nextId++;
        Node<Comments> newNode = new Node<>(newComment);

        if (head == null) {
            head = newNode;
        } else {
            Node<Comments> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        this.length++;
    }

    // --- METODOS (BASADOS EN POSICION) ---
    /**
     * Borra un comentario basado en su posición visible (1, 2, 3...).
     */
    public boolean deleteCommentByPosition(int position) {
        int idToDelete = getIdAtPosition(position); // Traduce Posición -> ID
        if (idToDelete == -1) {
            return false; // No se encontró esa posición
        }
        // Borra por ID
        return deleteComment(idToDelete);
    }
    
    /**
     * Actualiza un comentario basado en su posición visible (1, 2, 3...).
     */
    public boolean updateCommentByPosition(int position, String newDescription) {
        int idToUpdate = getIdAtPosition(position); // Traduce Posición -> ID
        if (idToUpdate == -1) {
            return false; // No se encontró esa posición
        }
        // Actualiza por ID
        return updateComment(idToUpdate, newDescription);
    }
    
    /**
     * Busca el ID del comentario que está en la posición N de la lista.
     * @param position (posision del comentario que se desea eliminar)
     * @return el ID del comentario, o -1 si la posición no es válida.
     */
    private int getIdAtPosition(int position) {
        if (position < 1 || position > length || isEmpty()) {
            return -1; // Posición inválida
        }
        Node<Comments> current = head;
        int currentPosition = 1;
        
        while (current != null) {
            if (currentPosition == position) {
                return current.getData().getId();
            }
            current = current.getNext();
            currentPosition++;
        }
        return -1;
    }

    //ES USADO INTERNAMENTE POR deleteByPosition
    private boolean deleteComment(int id) throws IllegalArgumentException {
        if (head == null) {
            return false;
        }
        if (head.getData().getId() == id) {
            head = head.getNext();
            this.length--;
            return true;
        }

        Node<Comments> previous = head;
        Node<Comments> current = head.getNext();
        while (current != null) {
            if (current.getData().getId() == id) {
                previous.setNext(current.getNext());
                this.length--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false; // No se encontró
    }

    //ES USADO INTERNAMENTE POR updateByPosition
    public boolean updateComment(int id, String newDescription) {
        if (newDescription == null || newDescription.trim().isEmpty()) {
            System.out.println("Descripción vacía, no se puede actualizar");
            return false;
        }

        Node<Comments> current = head;
        while (current != null) {
            if (current.getData().getId() == id) { // Busca por ID
                current.getData().setDescription(newDescription.trim());
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public int size() {
        return length;
    }

   @Override
    public String toString() {
        if (isEmpty()) {
            return "[Sin Comentarios]";
        }
        StringBuilder sb = new StringBuilder("[Comentarios:\n");
        Node<Comments> current = head;
        int position = 1; // El contador visible para el usuario

        while (current != null) {
            sb.append("   ")
              .append(position) // <-- Imprime la POSICIÓN (1.)
              .append(". ")
              .append(current.getData().toString()); // <-- Imprime el Comentario (Sin el ID porque no esta en el toString de comment)
            
            if (current.getNext() != null) {
                sb.append("\n"); // Salto de línea para el siguiente
            }
            current = current.getNext();
            position++;
        }
        sb.append("\n]");
        return sb.toString();
    }
}
