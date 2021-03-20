package com.hsm;

import java.util.Stack;

/**
 * @description: 根据 逆波兰表示法，求表达式的值。
 * 有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
 * @author: huangsm
 * @createDate: 2021/3/20
 */
public class Solution150 {

    public static void main(String[] args) {
        System.out.println(new Solution150().evalRPN(new String[]{"2","1","+","3","*"}));
        System.out.println(new Solution150().evalRPN(new String[]{"4","13","5","/","+"}));
        System.out.println(new Solution150().evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
    }

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack();
        for (int i = 0; i < tokens.length; i++) {
            if (!isSymbol(tokens[i])) {
                stack.push(Integer.parseInt(tokens[i]));
            }else{
                int secondData = stack.pop();
                int firstData = stack.pop();
                switch (tokens[i]){
                    case "+":stack.push(firstData+secondData);break;
                    case "-":stack.push(firstData-secondData);break;
                    case "*":stack.push(firstData*secondData);break;
                    case "/":stack.push(firstData/secondData);break;
                }
            }
        }
        return stack.pop();
    }

    /**
     * 是否是符号
     *
     * @return
     */
    boolean isSymbol(String str) {
        if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
            return true;
        }
        return false;
    }
}
