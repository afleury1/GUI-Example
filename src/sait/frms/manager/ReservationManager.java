package sait.frms.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import sait.frms.exception.NoMoreSeatsException;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;


/**
 * Class description: Manages Reservations.
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */

public class ReservationManager {

	/**
	 * Stores the arrayList of reservations
	 */
	private ArrayList<Reservation> reservations;
	private static final String BINARY_FILE = "res/reservation.bin";
	private static final String MODE = "rw";
	private static final int RESERVATION_SIZE = 233; // 7 + 8 + 4 + 102 + 102 + 8 +1

	/**
	 * THe instance of the Random Access File.
	 */
	private RandomAccessFile raf;

	
	/**
	 * Creates an Instance of ReservationManager, initializes the raf, and calls the populateFromBinary method.
	 * @throws IOException
	 */
	public ReservationManager() throws IOException {
		reservations = new ArrayList<Reservation>();

		try {
			this.raf = new RandomAccessFile(BINARY_FILE, MODE);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		populateFromBinary();
	}

	/**
	 * If seat is available, reservation is created and added the the reservations arrayList;
	 * @param flight flight of the reservation
	 * @param name name of the person making the reservation
	 * @param citizenship citizenship of the person making the reservation.
	 * @return If seat is available returns reservation else returns null.
	 * @throws NoMoreSeatsException
	 */
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws NoMoreSeatsException {
		try {
			Reservation reservation = new Reservation(generateReservationCode(flight), flight.getCode(),
					flight.getAirlineName(), name, citizenship, flight.getCostPerSeat());
			if (getAvailableSeats(flight) > 0) {
				reservations.add(reservation);
				return reservation;
			} else {
				throw new NoMoreSeatsException();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		

		return null;
	}

	/**
	 * Finds all the flight that has matching reservation code, airline, or name.
	 * @param code code of the reservation.
	 * @param airline airline code
	 * @param name name of the client
	 * @return ArrayList of reservation that matches the parameter.
	 */
	public ArrayList<Reservation> findReservations(String code, String airline, String name) {
		ArrayList<Reservation> foundReservations = new ArrayList<Reservation>();
		for (Reservation obj : reservations) {
			if (code.equalsIgnoreCase(obj.getCode()) || airline.equalsIgnoreCase(obj.getAirLineFullName())
					|| airline.equalsIgnoreCase(obj.getAirline()) || name.equalsIgnoreCase(obj.getName())) {
				foundReservations.add(obj);
			}
		}
		return foundReservations;
	}

	/**
	 * Finds reservation by reservation code
	 * @param code reservation code
	 * @return If matching reservation code returns reservation, else null.
	 */
	public Reservation findReservationByCode(String code) {
		for (Reservation obj : reservations) {
			if (code.equalsIgnoreCase(obj.getCode())) {
				return obj;
			}
		}
		return null;
	}

	/**
	 * Saves reservations arrayList as Binary bin file
	 * @throws IOException
	 */
	public void persist() throws IOException {
		this.raf.setLength(0);
		this.raf.seek(0);
		for (Reservation reservation : reservations) {

			if (reservation.isActive()) {
				// reservation code is exactly 5 characters.
				String reservationCode = reservation.getCode();
				this.raf.writeUTF(reservationCode);

				// flight code is exactly 6 characters.
				String flightCode = reservation.getFlightCode();
				this.raf.writeUTF(flightCode);

				// Airline name is exactly 2 characters.
				String airline = reservation.getAirline();
				this.raf.writeUTF(airline);

				// Fixed length of 100 for name and citizenship
				String name = String.format("%-100s", reservation.getName());
				this.raf.writeUTF(name);

				String citizen = String.format("%-100s", reservation.getCitizenship());
				this.raf.writeUTF(citizen);

				double cost = reservation.getCost();
				this.raf.writeDouble(cost);

				boolean isActive = reservation.isActive();
				this.raf.writeBoolean(isActive);
			}
		}

	}

	/**
	 * Gets the amount of available seats left in a flight
	 * @param flight flight object
	 * @return available seats left in a flight
	 */
	private int getAvailableSeats(Flight flight) {
		int totalSeats = flight.getSeats();
		for (Reservation reservation : reservations) {
			totalSeats -= reservation.getFlightCode().equalsIgnoreCase(flight.getCode()) ? 1
					: 0;
		}
		return totalSeats;
	}

	/**
	 * Generates a reservation code
	 * @param flight flight that is to be reserved
	 * @return generated reservation code.
	 */
	private String generateReservationCode(Flight flight) {
		String L = (flight.getFrom().substring(0, 1).equalsIgnoreCase("Y")
				&& flight.getTo().substring(0, 1).equalsIgnoreCase("Y")) ? "D" : "L";
		int DDDD = (int) (Math.random() * (9999 - 1000) + 1000);
		return L + DDDD;
	}

	/**
	 * Reads from Binary Bin file, and populates the reservations arrayList.
	 * @throws IOException
	 */
	private void populateFromBinary() throws IOException {
		try {

			for (long position = 0; position < this.raf.length(); position += RESERVATION_SIZE) {
				this.raf.seek(position);
				String reservationCode = this.raf.readUTF().trim();
				String flightCode = this.raf.readUTF().trim();
				String airline = this.raf.readUTF().trim();
				String name = this.raf.readUTF().trim();
				String citizen = this.raf.readUTF().trim();
				double cost = this.raf.readDouble();
				boolean isActive = this.raf.readBoolean();

				Reservation reservation = new Reservation(reservationCode, flightCode, airline, name, citizen, cost);
				reservations.add(reservation);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO: MAKEMETHOD
	}

}
