package business;

import domain.Comments;
import theNODE.Node;

public class CommentsList {

    private Node<Comments> head;

    public CommentsList() {
        this.head = null;
    }

    public CommentsList(Node<Comments> head) {
        this.head = head;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<Comments> current = head;
        while (current != null) {
            sb.append(current.getData().toString()); 
            if (current.getNext() != null) {
                sb.append(" -> ");
            }
            current = current.getNext();
        }
        sb.append("]");
        return sb.toString();
    }
}
