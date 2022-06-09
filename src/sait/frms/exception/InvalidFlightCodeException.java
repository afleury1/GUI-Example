package sait.frms.exception;

/**
 * Class contains the custom exception used in the FlightManager class's method populateFlights if the generated flight code does not match.
 * 
 * @author Daniel Choi
 * @version 24/03/2021
 *
 */
public class InvalidFlightCodeException extends Exception {
	String code;

	public InvalidFlightCodeException(String code) {
		super("Flight code: " + code + " is invalid");
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}



}
