package sait.frms.exception;

/**
 * Class contains the custom exception used in the ReservationManager class's method makeReservation if the number of seats is zero.
 * 
 * @author Daniel Choi
 * @version 24/03/2021
 *
 */
public class NoMoreSeatsException extends Exception {

	public NoMoreSeatsException() {
		super("No more seats available");
	}



}
