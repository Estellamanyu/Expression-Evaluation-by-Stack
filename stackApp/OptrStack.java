package stackApp;

/*Implementation of the stack*/
public class OptrStack<E> {
	private Node<E> top;  //the top of the stack
	private int size; //the number of nodes in the stack 
	
	/*Constructor*/
	public OptrStack() {
		this.top = null;
		this.size = 0;
	}
	
	/*Empty the whole stack.*/
	public void clear() {
		Node<E> temp = new Node<E>(null); 
		while (this.top != null) {	//the clear operation till the stack is already empty
			temp = top;	//Temporarily store the top node.
			top = top.next;
			temp = null;  //delete node
			
		}
		this.size = 0;
	}
	
	/*Determine whether the stack is empty.*/
	public boolean isEmpty() {
		if (size == 0 && top == null) return true;
		return false;
	}
	
	/*Put 'e' to the stack.*/
	public void push(E e) {
		this.top = new Node<E>(e,this.top);
		size++;
	}
	
	/*Remove the top node out of the stack.*/
	public E pop(){
		E e = null;
		Node<E> temp = new Node<E>(null);
		if (this.isEmpty()) {  //!EmptyStackException!
			System.out.println("The OptrStack is empty!"); //return the error message
		}else {
			e = top.element;
			temp = top.next; //store the new top
			top = null;
			top = temp; //change the top node to the new one
			size--;
		}
		return e; //return 'e'
	}
	
	/*Get the the element of the top node.*/
	public E topValue() {
		//!EmptyStackException!
		if(this.isEmpty()) System.out.println("The optrStack is empty!"); //return the error message
		return this.top.element;
	}
	
	/*Get the number of the nodes in the stack*/
	public int length() {
		return this.size;
	}
	
}
