package stackApp;

import java.util.*;

/*输入输出主程序*/
public class Demo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String infix = new String();
		String post = new String();
		
		System.out.println("Please input the infix expression:");
		infix = scanner.nextLine();
		scanner.close();

		post = Medium2Post.m2p(infix); //转为后缀表达式，同时检验表达式是否正确
		if (post != null) { //表达式无误
			System.out.println("The result(reserved to three replaces) calculated directly in infix expression is: " + Calculation.infixccl(infix));
			System.out.println("*******************************************\nThe corresponding post expression is: " + post);
			System.out.println("The result(reserved to three replaces) calculated indirectly in post expression is: " + Calculation.postccl(post));
		}
	}
}