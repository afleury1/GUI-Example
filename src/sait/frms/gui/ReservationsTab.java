package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.exception.EmptyStringException;
//import sait.frms.gui.FlightsTab.MyListSelectionListener;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */
public class ReservationsTab extends TabBase {
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	private JList<Reservation> reservationsList;
	private DefaultListModel<Reservation> reservationModel;

	private ArrayList<JTextField> reservationJTextField = new ArrayList<JTextField>();
	private Reservation selectedReservation;
	private JComboBox<String> comboBox;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager) {
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
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);
		return panel;
	}
	
	/**
	 * Creates the main panel.
	 * 
	 * @return JPanel containing: reservationPanel, reservePanel, searchPanel.
	 */
	private JPanel createMainPanel() {
		JPanel mainPanel = new JPanel();
		JPanel reservationPanel = createReservationPanel();
		JPanel reservePanael = createReservePanel();
		JPanel searchPanel = createSearchPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(reservationPanel, BorderLayout.CENTER);
		mainPanel.add(reservePanael, BorderLayout.EAST);
		mainPanel.add(searchPanel, BorderLayout.SOUTH);
		return mainPanel;
	}
	
	/**
	 * Creates the reservationPanel.
	 * 
	 * @return JPanel used by the createMainPanel method.
	 */
	private JPanel createReservationPanel() {
		JPanel reservationPanel = new JPanel();
		reservationPanel.setLayout(new BorderLayout());
		reservationPanel.setBorder(BorderFactory.createTitledBorder("Reservations"));
		reservationModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationModel);
		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);
		reservationsList.addListSelectionListener(new MyListSelectionListener());
		reservationPanel.add(scrollPane);
		return reservationPanel;
	}

	/**
	 * Creates the reservation tab button and associated listener.
	 * 
	 * @return JButton that is used by the ReservationsTab method.
	 */
	private JButton createButton() {
		JButton btn = new JButton("Find Reservations");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTextField();
				reservationModel.clear();
				String code = reservationJTextField.get(6).getText();
				String airline = reservationJTextField.get(7).getText();
				String name = reservationJTextField.get(8).getText();

				ArrayList<Reservation> results = reservationManager.findReservations(code, airline, name);

				for (Reservation res : results) {
					if(res.isActive()) {
					reservationModel.addElement(res);
					}
				}

			}
		});
		return btn;
	}

	/**
	 * Creates the reservePanel.
	 * 
	 * @return reservePanel used by the createMainPanel method.
	 */
	private JPanel createReservePanel() {
		JPanel reservePanel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		layout.setVgap(10);
		reservePanel.setLayout(layout);
		reservePanel.add(createInput("Code: ", false));
		reservePanel.add(createInput("Flight: ", false));
		reservePanel.add(createInput("Airline: ", false));
		reservePanel.add(createInput("Cost: ", false));
		reservePanel.add(createInput("Name: ", true));
		reservePanel.add(createInput("Citizenship: ", true));
		reservePanel.add(createDrop("Status"));
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedReservation != null) {
					
					try {
						String name = reservationJTextField.get(4).getText();
						String citizen = reservationJTextField.get(5).getText();
						boolean isActive = comboBox.getSelectedIndex() == 0 ? true : false;
						if(name == null || citizen == null || name.trim().isEmpty() || citizen.trim().isEmpty()) {
							throw new EmptyStringException();
						}
 
						selectedReservation.setCitizenship(citizen);
						selectedReservation.setName(name);
						selectedReservation.setActive(isActive);

						reservationModel.clear();
						clearTextField();
						
						if(isActive) {
						JOptionPane.showMessageDialog(reservePanel,
								"Reservation has been updated.");
						} else {
							JOptionPane.showMessageDialog(reservePanel,
									"Reservation has been deleted.");
						}
					} catch (EmptyStringException e2) {
						JOptionPane.showMessageDialog(reservePanel, "Name, Citizenship cannot be empty");
					}
					
				}

			}
		});
		reservePanel.add(updateBtn);
		reservePanel.setBorder(BorderFactory.createTitledBorder("Reserve"));
		return reservePanel;
	}

	/**
	 * Creates the searchPanel.
	 * 
	 * @return JPanel used by the createMainPanel method.
	 */
	private JPanel createSearchPanel() {
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search"));
		searchPanel.add(createInput("Code: ", true), BorderLayout.NORTH);
		searchPanel.add(createInput("Airline: ", true));
		searchPanel.add(createInput("Name: ", true), BorderLayout.SOUTH);
		return searchPanel;
	}

	/**
	 * creates the drop menu panel containing status options.
	 * 
	 * @param label is the String input from createReservePanel method's "Status".
	 * @return JPanel used by the createReservePanel method.
	 */
	private JPanel createDrop(String label) {
		String[] lst = { "Active", "Inactive" };
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		comboBox = new JComboBox<String>(lst);
		JLabel jLabel = new JLabel(label);
		panel.add(jLabel);
		panel.add(comboBox);
		return panel;
	}

	/**
	 * Create the input panel used by the createSearchPanel and the createReservePanel methods.
	 * 
	 * @param label is the JLabel is the String input from the createSearchPanel and the createReservePanel methods.
	 * @param editable is used to set the associated JTextField to allow user input.
	 * @return JPanel that is used by the createReservePanel method.
	 */
	private JPanel createInput(String label, boolean editable) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JTextField textField = new JTextField(20);
		JLabel jLabel = new JLabel(label);
		panel.add(jLabel);
		panel.add(textField);
		textField.setEditable(editable);
		reservationJTextField.add(textField);
		return panel;
	}

	/**
	 * This method is called by both overrides in the createMainPanel and createReservePanel methods clear text fields of information.
	 */
	private void clearTextField() {
		selectedReservation = null;
		reservationJTextField.get(0).setText(null);
		reservationJTextField.get(1).setText(null);
		reservationJTextField.get(2).setText(null);
		reservationJTextField.get(3).setText(null);
		reservationJTextField.get(4).setText(null);
		reservationJTextField.get(5).setText(null);

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

				JTextField code = reservationJTextField.get(0);
				JTextField flightCode = reservationJTextField.get(1);
				JTextField airline = reservationJTextField.get(2);
				JTextField cost = reservationJTextField.get(3);
				JTextField name = reservationJTextField.get(4);
				JTextField citizen = reservationJTextField.get(5);

				selectedReservation = reservationsList.getSelectedValue();
				if (selectedReservation != null) {
					code.setText(selectedReservation.getCode());
					flightCode.setText(selectedReservation.getFlightCode());
					airline.setText(selectedReservation.getAirLineFullName());
					cost.setText(String.format("%1.2f", selectedReservation.getCost()));
					name.setText(selectedReservation.getName());
					citizen.setText(selectedReservation.getCitizenship());
					if (selectedReservation.isActive()) {
						comboBox.setSelectedIndex(0);
					} else {
						comboBox.setSelectedIndex(1);
					}
					;

				}

			}

		}

	}

}
