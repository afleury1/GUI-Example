package sait.frms.exception;

/**
 * Class contains the custom exception used in the FlightsTab class's method createReservePanel method if any text field is null.
 * 
 * @author Daniel Choi
 * @version 24/03/2021
 *
 */
public class EmptyStringException extends Exception {

	public EmptyStringException() {
		super("String cannot be empty or null");
	}
	
	
}
