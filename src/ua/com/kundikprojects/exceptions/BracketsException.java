package ua.com.kundikprojects.exceptions;

public class BracketsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BracketsException(String s) {
		System.err.println("'"+ s + "' expected.");
	}
}
