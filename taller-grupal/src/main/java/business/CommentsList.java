package business;

import domain.Comments;
import theNODE.Node;

public class CommentsList {
    private Node<Comments> head;

    public CommentsList(){
        this.head=null;
    }
    public CommentsList(Node<Comments> head) {
        this.head = head;
    }

}
