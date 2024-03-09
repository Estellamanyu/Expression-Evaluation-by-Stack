package stackApp;

import java.text.*;

public class Calculation {
	
	/*
	 * Calculate a binary operation.（执行运算）
	 * Both postccl(String post) and infixccl(String infix) can invoke this
	 * Args:o(the operator), l(the left operand), r(the right operand).
	 * */
	public static double uniccl(String o,double l,double r) {
		double rsl = -1;
		switch (o) {
		case "+":
			rsl = l+r;
			break;
		case "-":
			rsl = l-r;
			break;
		case "*":
			rsl = l*r;
			break;
		case "/":
			rsl = l/r;
			break;
		case "%":
			rsl = l%r;
			break;
		case "^":
			rsl = l;
			while(r > 1) {
				rsl *= l;
				r--;
			}
		}
		return rsl;
	}
	
	/* Calculate the post expression.
	 * (The result is  reserved to three decimal replaces.)
	 * */
	public static double postccl(String post) { 
		OpndStack opnd = new OpndStack(); //Construct an empty stack to store the operands
		double out = 0; //the return value
		int i = 0; //tracer
		double pre,later = 0;
		if (post == null) {//!EmptyExpressionException!
			System.out.println("The post expression can't be empty!\nPlease try again...");//display error message
			return 0;
		}
		String[] prc = post.split(" ");   //Separates all the operands and operators
		String regex_opnd = "[-]?[0-9]+[.]?[0-9]*"; //Regular expression for the operands
		String regex_opr = "[+-/*%^]{1}";  //Regular expression for the operators
		
		for (;i<prc.length;i++) {
			if (prc[i].matches(regex_opnd)){//操作数入栈
				opnd.push(Double.parseDouble(prc[i]));
			}
			if (prc[i].matches(regex_opr)) { //遇操作符，则pop出两个操作数进行运算
					later = opnd.pop();//先出栈的为操作符后面的数
					pre = opnd.pop();//后出栈的为操作符前面的数
					opnd.push(uniccl(prc[i],pre,later));//调用uniccl进行运算，并将运算的结果push入栈
			}
		}
		out = opnd.pop(); //最后的结果
		
        DecimalFormat df = new DecimalFormat("#.000");//保留三位小数
        out = Double.parseDouble(df.format(out));

		if (opnd.isEmpty())//正确情况栈应该为空
			return out;
		else {//!操作数和二元操作符个数不匹配!
			//display error message
			System.out.println("Calculation Wrong!(The number of operands doesn't matches with the number of operators)\nPlease try again...");
			return 0;
		}
	}
	
	/*Calculate the infix expression directly.
	 * (The result is reserved to three decimal replaces.)
	 * */
	public static double infixccl(String infix) {
		OptrStack<Operator> oprs = new OptrStack<Operator>();//操作符栈
		OpndStack opns = new OpndStack();//操作数栈
		double out,left,right = 0; //计算结果，左操作数，右操作数
		Operator temp;//构造以temp为Operator中opt的Operator，以便数据类型一致
		String num = new String();//暂时存储每个完整的操作数（含小数点、单目负）
		char curr;//中缀表达式中正在判断的字符
		char last = '\u0000';//初始化为null，存储上一个已处理的操作数或者操作符
		int cnt = 0;//每个完整的操作数的字符个数
		String regex_zero = "[0]+[.]?[0]*";//“0”的正则表达式
		
		if (infix == null) {//!EmptyExpressionException!
			System.out.println("The infix expression can't be empty!\nPlease try again...");//display error message
			return 0;
		}
		while (Parentheses.isStdParetheses(infix)) {//！判断括号是否匹配正确!
			for (int i = 0; i < infix.length();i++) {//遍历中缀表达式的每一个字符
				curr = infix.charAt(i);
				if (curr != ' ') { //忽略空格
					if ((curr <= '9' && curr >= '0') || curr == '.') {
						//小数点之前没有数字的情况
						if (curr == '.' && cnt == 0) {
							//display error message
							System.out.println("The operand is invalid!\nPlease try again...");
							return 0;
						}
						num += Character.toString(curr);//加入到尚不完整的操作数字符串
						cnt++;//该操作数的字符的个数加1
					}
					else {
						/* 当前字符为操作符，且还未加入到后缀表达式的操作数的字符个数不为0*/
						if (cnt != 0) {
							//！除数为0！
							if (num.matches(regex_zero) && !oprs.isEmpty()) {
								if (oprs.topValue().getOpt() == '/') {
									//display error message
									System.out.println("The divisor can't be 0!\nPlease try again...");
									return 0;
								}
							}
							opns.push(Double.parseDouble(num));//完整操作数入操作数栈
							num = "";//重置
							cnt = 0;//重置
						}
						/* 前一个操作符为括号或该为整个表达式第一个有效字符，则'-'为单目负 */
						if ((i==0 || last == '(') && curr == '-') {
							num += '-'; //加入到尚不完整的操作数字符串
						}
						/* 表达式中含有无效字符(如：@#￥&) */
						else if (!"+-*/()（）^%".contains(curr + "")) {
							System.out.println("The expression contains invalid character!\nPlease try again..."); //display error message
							return 0;
						}
						/* 操作符处理 */
						else {
							temp = new Operator(curr);//构造为Operator类，以便后续优先级比较、入栈等
							/* '('优先级最高，直接入栈 */
							if (temp.getPriority() == 4) {
								temp.setPriority(0); //set priority to the lowest
								oprs.push(temp); //入栈
							}
							/* 栈为空时，直接入栈 */
							else if (oprs.isEmpty()) {
								oprs.push(temp); 
							}
							/* 优先级大于栈顶元素优先级，入栈 */
							else if (temp.getPriority() > oprs.topValue().getPriority()) {
								oprs.push(temp);
							}
							/* 出现')' */
							else if (temp.getPriority() == -1) {
								/* pop操作符栈顶元素进行计算，直到栈顶为'('或栈空 */
								while(!oprs.isEmpty() && oprs.topValue().getPriority() != 0) {
									/*运算*/
									right = opns.pop();//先pop出的为右操作数
									left = opns.pop();//后pop出的为左操作数
									//调用uniccl进行运算，并将运算结果push到操作数栈
									opns.push(uniccl(Character.toString(oprs.pop().getOpt()),left,right));		
								}
								/*将栈顶'('pop出，但不输出*/
								if (!oprs.isEmpty() && oprs.topValue().getPriority() == 0) 	oprs.pop(); 
							}
							/*优先级小于等于栈顶元素 */
							else {
								/*pop操作符栈顶元素，直到大于栈顶优先级或栈空*/
								while (!oprs.isEmpty() && temp.getPriority() <= oprs.topValue().getPriority()) {
									/*运算*/
									right = opns.pop();
									left = opns.pop();
									//调用uniccl进行运算，并将运算结果push到操作数栈
									opns.push(uniccl(Character.toString(oprs.pop().getOpt()),left,right));			
								}
								oprs.push(temp); //栈外等待操作符入栈
							}
						}
					}
					last = curr; //存储该轮已处理的操作数或操作符
				}		
			}
			/* 中缀表达式已经遍历结束，将剩余操作数入栈 */
			if (cnt != 0) {
				//！除数为0！
				if (num.matches(regex_zero) && !oprs.isEmpty()) {
					if (oprs.topValue().getOpt() == '/') {
						//display error message
						System.out.println("The divisor can't be 0!\nPlease try again...");
						return 0;
					}
				}
				opns.push(Double.parseDouble(num));
			}
			/* 将操作符栈中操作符全部pop出，依次进行运算*/
			while(!oprs.isEmpty()) {
				/*运算*/
				right = opns.pop();
				left = opns.pop();
				opns.push(uniccl(Character.toString(oprs.pop().getOpt()),left,right));			
			}
			out = opns.pop();//最后运算结果
		
			DecimalFormat df = new DecimalFormat("#.000");//保留三位小数
			out = Double.parseDouble(df.format(out));

			if (opns.isEmpty()) //正确情况栈为空
				return out;
			else { //操作数和二元操作符的数目不匹配
				//display error message
				System.out.println("Calculation Wrong!(The number of operands doesn't matches with the number of operators)\nPlease try again...");
				return 0;
			}
		}
		/* 括号匹配不正确，直接返回*/
		System.out.println("The parentheses match is wrong!\nPlease try again...");
		return 0;
	}
	
}
