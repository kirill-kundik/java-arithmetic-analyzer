package ua.com.kundikprojects;

import java.util.Stack;

import ua.com.kundikprojects.exceptions.BracketsException;
import ua.com.kundikprojects.exceptions.DigitExpectedException;
import ua.com.kundikprojects.exceptions.IllegalMathOperandException;
import ua.com.kundikprojects.exceptions.MathOperandExpectedException;

public class ArithmeticAnalizer {

	private Stack<Integer> stack;
	private Stack<String> stackForOperands;
	private String input;
	private StringBuilder output;
	private String postfix;
	private int result;

	public static void main(String[] args) {

		try {
			System.out.println("Result is: " + new ArithmeticAnalizer("(6^(4-2)+4)/10").getResult());
		} catch (ArithmeticException | BracketsException | IllegalMathOperandException | DigitExpectedException
				| MathOperandExpectedException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

	}

	public ArithmeticAnalizer(String s) throws ArithmeticException, BracketsException, IllegalMathOperandException,
			DigitExpectedException, MathOperandExpectedException {
		stack = new Stack<Integer>();
		this.input = s;
		System.out.println(input);
		errorAnalyses();
		inputToPostfix();
		countResult();
	}

	private void errorAnalyses() throws BracketsException, IllegalMathOperandException, DigitExpectedException,
			MathOperandExpectedException {
		int openSkobkaCounter = 0;
		int closeSkobkaCounter = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ')
				continue;
			else if (input.charAt(i) == '(')
				openSkobkaCounter++;
			else if (input.charAt(i) == ')')
				closeSkobkaCounter++;
			else if (input.charAt(i) != '+' && input.charAt(i) != '-' && input.charAt(i) != '*'
					&& input.charAt(i) != '/' && input.charAt(i) != '^' && input.charAt(i) != '('
					&& input.charAt(i) != ')' && !Character.isDigit(input.charAt(i)))
				throw new IllegalMathOperandException(Character.toString(input.charAt(i)));

			else if (Character.isDigit(input.charAt(i))) {
				int k = i;
				boolean isTrue = false;
				while (k < input.length()) {
					if (Character.isDigit(input.charAt(k))) {

						if (isTrue)
							throw new MathOperandExpectedException();
						else {
							k++;
							continue;
						}

					}

					else if (input.charAt(k) == ' ') {
						isTrue = true;
						k++;
						continue;
					} else if (!Character.isDigit(input.charAt(k)))
						break;
				}
			}

			else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*'
					|| input.charAt(i) == '/' || input.charAt(i) == '^' || input.charAt(i) == '('
					|| input.charAt(i) == ')') {

				int k = i;
				boolean isTrue = false;
				boolean isNormal = false;

				while (k > 0) {
					if (input.charAt(k) == ' ' || input.charAt(k) == '(' || input.charAt(k) == ')') {
						k--;
						continue;
					} else if (Character.isDigit(input.charAt(k))) {
						isNormal = true;
						break;
					}

					else if (!Character.isDigit(input.charAt(k)) && isTrue) {
						// throw new DigitExpectedException();
					} else {
						isTrue = true;
						k--;
						continue;
					}
				}
				if (k == 0 && !isNormal) {
					// throw new DigitExpectedException();
				}

				isTrue = false;
				isNormal = false;
				k = i;

				while (k < input.length()) {
					if (input.charAt(k) == ' ' || input.charAt(k) == '(' || input.charAt(k) == ')') {
						k++;
						continue;
					} else if (Character.isDigit(input.charAt(k))) {
						isNormal = true;
						break;
					}

					else if (!Character.isDigit(input.charAt(k)) && isTrue) {
						throw new DigitExpectedException();
					} else {
						isTrue = true;
						k++;
						continue;
					}
				}

				if (k == input.length() && !isNormal) {
					throw new DigitExpectedException();
				}

			}

		}
		if (openSkobkaCounter > closeSkobkaCounter)
			throw new BracketsException(")");
		else if (openSkobkaCounter < closeSkobkaCounter)
			throw new BracketsException("(");
	}

	private void inputToPostfix() {
		stackForOperands = new Stack<String>();
		output = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == ' ')
				continue;

			else if (Character.isDigit(input.charAt(i))) {
				String s = "";
				int k = i;
				// System.out.println("SOS");
				while (k != input.length() && Character.isDigit(input.charAt(k))) {
					s += input.charAt(k);
					// System.out.println(s);
					k++;
				}
				output.append(s + " ");
				i = k - 1;
				// System.out.println(output.toString());
			}

			else {
				StringBuilder str = new StringBuilder();
				str.append(input.charAt(i));
				operandToStack(str.toString());
				// System.out.println(input.charAt(i));
				// System.out.println(stackForOperands.toString());
			}
		}

		while (!stackForOperands.isEmpty())
			output.append(stackForOperands.pop());

		postfix = output.toString();
		System.out.println(postfix.toString());
		stackForOperands.clear();
	}

	private void operandToStack(String c) {

		if (c.equals("^")) {
			stackForOperands.push(c);
		}

		else if (c.equals("/") || c.equals("*")) {
			while (true) {
				if (!stackForOperands.isEmpty() && (stackForOperands.peek().equals("^")
						|| stackForOperands.peek().equals("/") || stackForOperands.peek().equals("*"))) {
					output.append(stackForOperands.pop());
				} else {
					stackForOperands.push(c);
					break;
				}
			}
		} else if (c.equals("+") || c.equals("-")) {
			while (!stackForOperands.isEmpty() && !stackForOperands.peek().equals("(")) {
				output.append(stackForOperands.pop());
			}
			stackForOperands.push(c);
		} else if (c.equals("("))
			stackForOperands.push(c);
		else if (c.equals(")")) {
			while (!stackForOperands.peek().equals("(")) {
				output.append(stackForOperands.pop());
			}
			stackForOperands.pop();
		}
	}

	private void countResult() throws ArithmeticException {
		stack.clear();
		for (int i = 0; i < postfix.length(); i++) {
			if (postfix.charAt(i) == ' ')
				continue;
			if (Character.isDigit(postfix.charAt(i))) {
				String s = "";
				int k = i;
				while (Character.isDigit(postfix.charAt(k)) && k != postfix.length() - 1) {
					s += postfix.charAt(k);
					k++;
				}

				stack.push(Integer.parseInt(s));
				i = k;
			} else {
				int l2 = stack.pop();
				int l1 = stack.pop();
				switch (postfix.charAt(i)) {
				case '+':
					stack.push(l1 + l2);
					break;
				case '-':
					stack.push(l1 - l2);
					break;
				case '*':
					stack.push(l1 * l2);
					break;
				case '/':
					stack.push(l1 / l2);
					break;
				case '^':
					stack.push((int) Math.pow(l1, l2));
					break;
				default:
					throw new IllegalArgumentException(postfix);
				}
			}
			// System.out.println(stack.toString());
		}
		result = stack.peek();
		stack.clear();
	}

	public int getResult() {
		return result;
	}
}
