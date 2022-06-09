package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

import sait.frms.exception.EmptyStringException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the flights tab.
 * 
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021 
 */
public class FlightsTab extends TabBase {
	/**
	 * Instance of flight manager.
	 */
	private FlightManager flightManager;

	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;

	private DefaultListModel<Flight> flightsModel;

	private ArrayList<JComboBox<String>> comboBoxs = new ArrayList<JComboBox<String>>();

	private ArrayList<JTextField> jTextFields = new ArrayList<JTextField>();

	private Flight selectedFlight;

	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager      Instance of FlightManager.
	 * @param reservationManager Instance of ReservationManager
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) {
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel mainPanel = createMainPanel();
		panel.add(mainPanel, BorderLayout.CENTER);

		JButton btn = createButton();
		panel.add(btn, BorderLayout.SOUTH);

	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);
		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		JPanel flightsPanel = createFlightsPanel();
		JPanel reservePanel = createReservePanel();
		JPanel finderPanel = createFinderPanel();

		mainPanel.setLayout(new BorderLayout());

		mainPanel.add(flightsPanel, BorderLayout.CENTER);
		mainPanel.add(reservePanel, BorderLayout.EAST);
		mainPanel.add(finderPanel, BorderLayout.SOUTH);

		return mainPanel;
	}

	/**
	 * Creates the button that is used in the flight manager window.
	 *
	 * @return JButton that goes into the top tab location.
	 */
	private JButton createButton() {
		JButton btn = new JButton("Find Flights");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				flightsModel.clear();
				String from = comboBoxs.get(0).getSelectedItem().toString();
				String to = comboBoxs.get(1).getSelectedItem().toString();
				String day = comboBoxs.get(2).getSelectedItem().toString();

				ArrayList<Flight> results = flightManager.findFlights(from, to, day);
				for (Flight flight : results) {
					flightsModel.addElement(flight);
				}

			}
		});
		return btn;
	}

	/**
	 * Creates the flights information panel.
	 *
	 * @return JPanel that is added into the createMainPanel method.
	 */
	private JPanel createFlightsPanel() {
		JPanel flightsPanel = new JPanel();
		flightsPanel.setLayout(new BorderLayout());
		flightsPanel.setBorder(BorderFactory.createTitledBorder("Flights"));
		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(this.flightsList);
		flightsList.addListSelectionListener(new MyListSelectionListener());
		flightsPanel.add(scrollPane);

		return flightsPanel;
	}

	/**
	 * Creates the reservations information panel.
	 *
	 * @return JPanel that is added into the createMainPanel method.
	 * @Override exists to facilitate creation of reservations.
	 */
	private JPanel createReservePanel() {
		JPanel reservePanel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		layout.setVgap(10);
		reservePanel.setLayout(layout);
		reservePanel.add(createInput("Flight: ", false));
		reservePanel.add(createInput("Airline: ", false));
		reservePanel.add(createInput("Day: ", false));
		reservePanel.add(createInput("Time: ", false));
		reservePanel.add(createInput("Cost: ", false));
		reservePanel.add(createInput("Name: ", true));
		reservePanel.add(createInput("Citizenship: ", true));

		JButton reserveBtn = new JButton("Reserve");
		reserveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedFlight != null) {
					Reservation reservation;
					try {

						if (jTextFields.get(5).getText() != null && jTextFields.get(5).getText() != null
								&& !jTextFields.get(5).getText().trim().isEmpty()
								&& !jTextFields.get(5).getText().trim().isEmpty()) {
							reservation = reservationManager.makeReservation(selectedFlight,
									jTextFields.get(5).getText(), jTextFields.get(6).getText());
							if (reservation != null) {
								JOptionPane.showMessageDialog(reservePanel,
										"Reservation created. Your code is " + reservation.getCode());
								
							}
						} else {
							throw new EmptyStringException();
						}

					} catch (NoMoreSeatsException e1) {
						JOptionPane.showMessageDialog(reservePanel,
								"Reservation failed. Please check if additional seats are available");
					} catch (EmptyStringException e2) {
						JOptionPane.showMessageDialog(reservePanel, "Name, Citizenship cannot be empty");
					}
  
				}

			}
		});
		reservePanel.add(reserveBtn);
		reservePanel.setBorder(BorderFactory.createTitledBorder("Reserve"));

		return reservePanel;

	}

	/**
	 * Creates the user input panel.
	 *
	 * @param label	is the JLabel is the String input from the createReservePanel method.
	 * @param editable is used to set the associated JTextField to allow user input.
	 * @return JPanel that is used by the createReservePanel method.
	 */
	private JPanel createInput(String label, boolean editable) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JTextField textField = new JTextField(20);
		jTextFields.add(textField);
		JLabel jLabel = new JLabel(label);
		panel.add(jLabel);
		panel.add(textField);
		textField.setEditable(editable);

		return panel;

	}

	/**
	 * Creates the three drop menus in the south of the main panel.
	 *
	 * @return JPanel used by the createMainPanel method.
	 */
	private JPanel createFinderPanel() {
		JPanel flightFinderPanel = new JPanel();
		flightFinderPanel.setLayout(new BorderLayout());
		flightFinderPanel.setBorder(BorderFactory.createTitledBorder("Flight Finder"));
		flightFinderPanel.add(createDrop("From:", flightManager.getAirportCodeArray()), BorderLayout.NORTH);
		flightFinderPanel.add(createDrop("To:", flightManager.getAirportCodeArray()), BorderLayout.CENTER);
		flightFinderPanel.add(createDrop("Day:", flightManager.getDayArray()), BorderLayout.SOUTH);

		return flightFinderPanel;
	}

	/**
	 * Creates the selection arrays of flight information.
	 * 
	 * @param label is the JLabel String input from the createFinderPanel method.
	 * @param comboArray is the String Array input from the createFinderPanel method.
	 * @return JPanel that is used by the createFinderPanel method.
	 */
	private JPanel createDrop(String label, String[] comboArray) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JComboBox<String> box = new JComboBox<String>(comboArray);
		comboBoxs.add(box);
		JLabel jLabel = new JLabel(label);
		panel.add(jLabel);
		panel.add(box);
		return panel;
	}

	/**
	 * Creates all action listeners used by the JList called flightslist in the createFlightsPanel method.
	 */
	private class MyListSelectionListener implements ListSelectionListener {
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				JTextField code = jTextFields.get(0);
				JTextField airline = jTextFields.get(1);
				JTextField day = jTextFields.get(2);
				JTextField time = jTextFields.get(3);
				JTextField cost = jTextFields.get(4);

				selectedFlight = flightsList.getSelectedValue();
				if (selectedFlight != null) {
					code.setText(selectedFlight.getCode());
					airline.setText(selectedFlight.getAirLineFullName());
					day.setText(selectedFlight.getWeekday());
					time.setText(selectedFlight.getTime());
					cost.setText(String.format("%1.2f", selectedFlight.getCostPerSeat()));
				}

			}
		}

	}
}
