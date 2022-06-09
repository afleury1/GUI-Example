package sait.frms.problemdomain;

/**
 * Class description for a Flight object.
 * 
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */
public class Flight {
	private String code;
	private String airlineName;
	private String from;
	private String to;
	private String weekday;
	private String time;
	private int seats;
	private double costPerSeat;
	private boolean isDomestic;

	/**
	 * Default constructor for Flight
	 */
	public Flight() {
	}

	/**
	 * Flight constructor
	 * 
	 * @param code        The flight Code
	 * @param airlineName The airline name
	 * @param from        Where the flight is from
	 * @param to          Where the flight is heading
	 * @param weekday     The day of the week
	 * @param time        The flight time
	 * @param seats       The number of seats
	 * @param costPerSeat The cost per seat
	 */
	public Flight(String code, String airlineName, String from, String to, String weekday, String time, int seats,
			double costPerSeat) {

		this.code = code;
		this.airlineName = airlineName;
		this.from = from;
		this.to = to;
		this.weekday = weekday;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;
	}

	/**
	 * Returns the flight code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the airline name.
	 * 
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * Returns the from location.
	 * 
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Returns the to location.
	 * 
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Returns the day of the week.
	 * 
	 * @return the weekday
	 */
	public String getWeekday() {
		return weekday;
	}

	/**
	 * Returns the flight time.
	 * 
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * Returns the number of seats.
	 * 
	 * @return the seats
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * Returns the cost per seat.
	 * 
	 * @return the costPerSeat
	 */
	public double getCostPerSeat() {
		return costPerSeat;
	}

	/**
	 * Returns whether or not the flight is domestic.
	 * 
	 * @return the isDomestic
	 */
	public boolean isDomestic() {
		return isDomestic;
	}

	/**
	 * Returns the airline's full name
	 * 
	 * @return the airline's full name
	 */
	public String getAirLineFullName() {
		return getAirlineName().equalsIgnoreCase("OA") ? "Otto Airlines"
				: getAirlineName().equalsIgnoreCase("CA") ? "Conned Air"
						: getAirlineName().equalsIgnoreCase("TB") ? "Try a Bus Airways" : "Vertical Airways";
	}

	@Override
	public String toString() {

		return code + ", From: " + from + ", To: " + to + ", Day: " + weekday + ", Cost: "
				+ String.format("%1.2f", costPerSeat);
	}

}
