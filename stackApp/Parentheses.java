package stackApp;

/*检验括号匹配*/
public class Parentheses {
	public static boolean isStdParetheses(String str){
		OptrStack<Integer> l = new OptrStack<Integer>(); //store the index of every left parentheses
		OptrStack<Integer> r = new OptrStack<Integer>(); //store the index of every right parentheses
		int i;
		/*从右至左遍历表达式*/
	 	for (i=str.length()-1; i>=0; i--){
			if (str.charAt(i) == '(' || str.charAt(i) == '（') //左括号进栈
				l.push(i);
			else if (str.charAt(i) == ')' || str.charAt(i) == '）') //右括号进栈
				r.push(i);
		}
	 	/*当栈均不为空时，进行左右括号匹配检验*/
	 	if (!l.isEmpty() && !r.isEmpty()) {
	 		/* 左右括号个数相等，继续检验*/
	 		if (l.length() == r.length()){
	 			/* 匹配检验直到栈为空 */
	 			while(!l.isEmpty() && !r.isEmpty()) {
	 				if (l.pop() >= r.pop()){
	 					//若右括号比相对应的左括号先出现，则不匹配，检验不通过
	 					return false;
	 				}
	 			}
	 			return true;
	 		} else	return false; //左右括号个数不相等，检验不通过
	 	}else if ( (!l.isEmpty() && r.isEmpty()) || (l.isEmpty() && !r.isEmpty()) ) {
	 		return false; //一个为空，一个不为空，则不匹配，检验不通过
	 	}else  return true; //如果都为空，代表表达式中无括号，检验通过
	}
}
