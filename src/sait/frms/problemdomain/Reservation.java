package sait.frms.problemdomain;

/**
 * Class description for a Reservation object
 * 
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */
public class Reservation {
	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;

	/**
	 * Reservation constructor
	 * 
	 * @param code        The reservation code
	 * @param flightCode  The flight code
	 * @param airline     The airline
	 * @param name        The client's name
	 * @param citizenship The client's citizenship
	 * @param cost        The cost of the flight
	 */
	public Reservation(String code, String flightCode, String airline, String name, String citizenship, double cost) {

		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;
		this.active = true;
	}

	/**
	 * Returns the client's name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the client's name to the name provided.
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the client's citizenship.
	 * 
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}

	/**
	 * Sets the client's citizenship to the citizenship provided.
	 * 
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	/**
	 * Returns whether or not the reservation is active.
	 * 
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets whether or not the reservation is active or not.
	 * 
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Returns the reservation code.
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Returns the flight code.
	 * 
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}

	/**
	 * Returns the airline name.
	 * 
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * Returns the cost of the flight.
	 * 
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	
	/**
	 * Returns the airline's full name
	 * 
	 * @return the airline's full name
	 */
	public String getAirLineFullName() {
		return getAirline().equalsIgnoreCase("OA") ? "Otto Airlines"
				: getAirline().equalsIgnoreCase("CA") ? "Conned Air"
						: getAirline().equalsIgnoreCase("TB") ? "Try a Bus Airways" : "Vertical Airways";
	}

	@Override
	public String toString() {
		return this.getCode();
	}
	
	
	

}
