package sait.frms.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.problemdomain.Flight;

/**
 * Class descripttion: Manages flights on the flights tab
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */
public class FlightManager {

	private static final String FLIGHTS_FILE = "res/flights.csv";
	private static final String AIRPORTS_FILE = "res/airports.csv";
	public static final String WEEKDAY_ANY = "Any";
	public static final String WEEKDAY_SUNDAY = "Sunday";
	public static final String WEEKDAY_MONDAY = "Monday";
	public static final String WEEKDAY_TUESDAY = "Tuesday";
	public static final String WEEKDAY_WEDNESDAY = "Wednesday";
	public static final String WEEKDAY_THURSDAY = "Thursday";
	public static final String WEEKDAY_FRIDAY = "Friday";
	public static final String WEEKDAY_SATURDAY = "Saturday";

	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private ArrayList<String> airports = new ArrayList<String>();

	/**
	 * Creates an instance of FlightManager, and calls the populateFlights, and populateAirports method.
	 */
	public FlightManager() {
		try {
			populateFlights();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		populateAirports();
	}

	/**
	 * @return the flights
	 */
	public ArrayList<Flight> getFlights() {

		return flights;
	}

	/**
	 * @return the airports
	 */
	public ArrayList<String> getAirports() {

		return airports;
	}

	/**
	 * Searches the airport list by airport code and returns the matching airport full name.
	 * @param code code of Airport.
	 * @return If match is found returns airports full name, else returns null.
	 */
	public String findAirportByCode(String code) {
		for (String airport : airports) {
			String[] airportArry = airport.split(",");
			if (airportArry[0].equalsIgnoreCase(code)) {
				return airportArry[1];
			}
		}

		return null;
	}

	/**
	 * Searches the flights list by flight code, and returns the matching flight.
	 * @param code code of flight
	 * @return If match is found returns flight, else returns null.
	 */
	public Flight findFlightByCode(String code) {

		for (Flight flight : flights) {
			if (flight.getCode().equalsIgnoreCase(code)) {
				return flight;
			}
		}

		return null;

	}

	/**
	 * Gets all of the flights that matches the parameter.
	 * @param from origin airport by airport code
	 * @param to destination airport by airport code
	 * @param weekday day of departure
	 * @return ArrayList of flights that matches the search parameter.
	 */
	public ArrayList<Flight> findFlights(String from, String to, String weekday) {
		ArrayList<Flight> matchingFlights = new ArrayList<Flight>();
		for (Flight flight : flights) {

			if (!weekday.equalsIgnoreCase("Any")) {
				if (flight.getFrom().equalsIgnoreCase(from) && flight.getTo().equalsIgnoreCase(to)
						&& flight.getWeekday().equalsIgnoreCase(weekday)) {
					matchingFlights.add(flight);
				}
			} else {
				if (flight.getFrom().equalsIgnoreCase(from) && flight.getTo().equalsIgnoreCase(to)) {
					matchingFlights.add(flight);
				}
			}
		}
		return matchingFlights;

	}
 
	
	/**
	 * Populates the Flights ArrayList from file.
	 * @throws FileNotFoundException
	 */
	private void populateFlights() throws FileNotFoundException {

		Scanner sc = new Scanner(new File(FLIGHTS_FILE));

		do {
			try {
				String[] line = sc.nextLine().split(",");
				String code = line[0];
				String airline = line[0].split("-")[0];
				String from = line[1];
				String to = line[2];
				String weekday = line[3];
				String time = line[4];
				int seats = Integer.parseInt(line[5]);
				double costPerSeat = Double.parseDouble(line[6]);
				if (code.matches("^[A-Z]{2}[-][0-9]{4}$")) {
					Flight flight = new Flight(code, airline, from, to, weekday, time, seats, costPerSeat);
					flights.add(flight);
				} else {
					throw new InvalidFlightCodeException(code);
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		} while (sc.hasNext());

		sc.close();

	}

	/**
	 * Populates the airports ArrayList from file
	 */
	private void populateAirports() {
		try {
			Scanner sc = new Scanner(new File(AIRPORTS_FILE));
			while (sc.hasNext()) {
				String aiport = sc.nextLine();
				airports.add(aiport);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}

	/**
	 * Generates and return String[] of airport codes.
	 * @return String[] of airport codes
	 */
	public String[] getAirportCodeArray() {
		ArrayList<String> airportCodeArrayList = new ArrayList<String>();
		airports.forEach((el) -> {
			airportCodeArrayList.add(el.split(",")[0]);
		});

		return airportCodeArrayList.toArray(new String[airportCodeArrayList.size()]);
	}

	/**
	 * Gets String[] of days.
	 * @return String[] of days
	 */
	public String[] getDayArray() {
		return new String[] { WEEKDAY_ANY, WEEKDAY_MONDAY, WEEKDAY_TUESDAY, WEEKDAY_THURSDAY, WEEKDAY_WEDNESDAY,
				WEEKDAY_THURSDAY, WEEKDAY_FRIDAY, WEEKDAY_SATURDAY };
	}

}
