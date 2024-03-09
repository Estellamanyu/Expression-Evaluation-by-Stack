package stackApp;

/*Implement the algorithm that converts the infix expression to the post expression.*/
public class Medium2Post {
	
	/*考虑输入十六进制，八进制，二进制，科学计数法*/
	
	public static String m2p(String medium) {
		OptrStack<Operator> oprs = new OptrStack<Operator>(); //Temporarily store the operators.
		String post = new String(); //the post expression finally obtained 
		String num = new String(); //暂时存储每个完整操作数（含小数点、单目负）
		int cnt = 0; //每个完整的操作数的字符个数
		Operator temp;//构造以temp为Operator中opt的Operator，以便数据类型一致
		char curr; //中缀表达式中正在判断的字符
		char last = '\u0000'; //初始化为null，存储上一个已处理的操作数或者操作符
		String regex_zero = "[0]+[.]?[0]*";//“0”的正则表达式
		
		if (medium == null) { //!EmptyExpressionException!
			System.out.println("The expression can't be empty!\nPlease try again...");//display error message 
			return null;
		}
		while (Parentheses.isStdParetheses(medium)) { //！判断括号是否匹配正确!
			for (int i = 0; i < medium.length();i++) { //遍历中缀表达式的每一个字符
				curr = medium.charAt(i);
				if (curr != ' ') { //忽略空格
					/* 数字或'.'处理 */
					if ((curr <= '9' && curr >= '0') || curr == '.') { 
						//小数点之前没有数字的情况
						if (curr == '.' && cnt == 0) {
							//display error message
							System.out.println("The operand is invalid!\nPlease try again...");
							return null;
						}
						num += Character.toString(curr); //加入到尚不完整的操作数字符串
						cnt++; //该操作数的字符的个数加1
					}
					else {
						/* 当前字符为操作符，且还未加入到后缀表达式的操作数的字符个数不为0*/
						if (cnt != 0) { 
							//！除数为0！
							if (num.matches(regex_zero) && !oprs.isEmpty()) {
								if (oprs.topValue().getOpt() == '/') {
									//display error message
									System.out.println("The divisor can't be 0!\nPlease try again...");
									return null;
								}
							}
							num += ' ';  //后缀表达式的每个操作数和操作符用空格分隔开，以便后续计算
							post += num; //完整操作数加入到后缀表达式
							num = ""; //重置
							cnt = 0;  //重置
						}
						/* 前一个操作符为括号或该为整个表达式第一个有效字符，则'-'为单目负 */
						if ((i == 0 || last == '(') && curr == '-') { 
							num += '-'; //加入到尚不完整的操作数字符串
						}
						/* 表达式中含有无效字符(如：@#￥&) */
						else if (!"+-*/()（）^%".contains(curr + "")) {
							System.out.println("The expression contains invalid character!\nPlease try again..."); //display error message
							return null;
						}
						/* 操作符处理 */
						else{ 
							temp = new Operator(curr); //构造为Operator类，以便后续优先级比较、入栈等
							/* '('优先级最高，直接进栈 */
							if (temp.getPriority() == 4) {
								temp.setPriority(0); //优先级降到最低
								oprs.push(temp); //入栈
							}
							/* 栈为空时，直接进栈 */
							else if (oprs.isEmpty()) {
								oprs.push(temp);
							}
							/* 优先级大于栈顶元素优先级，进栈 */
							else if (temp.getPriority() > oprs.topValue().getPriority()) {
								oprs.push(temp); //入栈
							}
							/* 出现')' */
							else if(temp.getPriority() == -1) {
								/* pop栈顶元素，直到栈顶为'('或栈空 */
								while(!oprs.isEmpty() && oprs.topValue().getPriority() != 0) {
									post += (Character.toString(oprs.pop().getOpt())+' '); //添加到后缀表达式字符串
								}
								/*将栈顶'('pop出，但不输出*/
								if (!oprs.isEmpty() && oprs.topValue().getPriority() == 0) oprs.pop();
							}
							/*优先级小于等于栈顶元素 */
							else {
								/*pop栈顶元素，直到大于栈顶优先级或栈空*/
								while (!oprs.isEmpty() && temp.getPriority() <= oprs.topValue().getPriority()){
									post += (Character.toString(oprs.pop().getOpt())+' '); //添加到后缀表达式字符串
								}
								oprs.push(temp); //入栈
							}
						}
					}
					last = curr; //存储该轮已处理的操作数或操作符
				}
			}
			/* 中缀表达式已经遍历结束，将剩余操作数添加至后缀表达式字符串 */
			if (cnt != 0) {
				//！除数为0！
				if (num.matches(regex_zero) && !oprs.isEmpty()) {
					if (oprs.topValue().getOpt() == '/') {
						//display error message
						System.out.println("The divisor can't be 0!\nPlease try again...");
						return null;
					}
				}
				num += ' ';
				post += num; //添加至后缀表达式字符串
			}
			/* 将栈中操作符全部pop出，添加至后缀表达式字符串*/
			while(!oprs.isEmpty()) {
				post += (Character.toString(oprs.pop().getOpt())+' ');	//添加至后缀表达式字符串
			}
			return post; //返回后缀表达式
		}
		/* 括号匹配不正确，直接返回*/
		System.out.println("The parentheses match is wrong!\nPlease try again..."); //display error message
		return null;
	}
}
