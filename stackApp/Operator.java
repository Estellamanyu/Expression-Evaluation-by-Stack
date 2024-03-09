package stackApp;

/*
 * The implementation of the class "Operator"
 * */
public class Operator {
	private int priority = -1; //操作符的优先级
	private char opt; //操作符
	
	/*Constructor*/
	public Operator(char c) {
		switch(c){
		/* '+''-' 优先级一样*/
		case '+':
		case '-':
			this.priority = 1;
			break;
		/* '*''/' 优先级一样*/
		case '*':
		case '/':
		case '%':
			this.priority = 2;
			break;
		case '^':
			this.priority = 3;
			break;
		/* '(' 栈外优先级最高*/
		case '(':
		case '（':
			this.priority = 4;
			break;
		/*')' 设为-1*/	
		case ')':
		case '）':
			this.priority = -1;
		}
		this.opt = c;
			
	}
	/*Set the priority*/
	void setPriority(int i) {
		this.priority = i;
	}
	/*Obtain the priority*/
	int getPriority() {
		return this.priority;
	}
	/*Obtain the operator in the form of character*/
	char getOpt() {
		return this.opt;
	}
	
}
