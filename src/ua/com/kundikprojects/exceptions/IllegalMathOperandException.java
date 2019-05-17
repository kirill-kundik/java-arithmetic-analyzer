package ua.com.kundikprojects.exceptions;

public class IllegalMathOperandException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalMathOperandException(String s){
		System.err.println("This math operand: '"+ s +"' is illegal!");
	}
}
