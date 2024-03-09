package stackApp;

/*
 * The implementation of the node in stack
 * */
public class Node<E> {
    public E element;   //the element of this node
    public Node<E> next;    //the next node

    /*Constructor1 */
    public Node(E element, Node<E> next){
        this.element = element;
        this.next = next;
    }
    /*Constructor2 without element*/
    public Node(Node<E> next){
        this.next = next;
        this.element = null;
    }
    /*Override*/
    public String toString(){
        return element.toString();
    }
}

