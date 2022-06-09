package sait.frms.application;

import sait.frms.gui.MainWindow;

import java.io.IOException;

/**
 * Application driver, which starts the Manager class.
 * @author Daniel Choi, Alexander Fleury, Liam Zimmerman, Pol-Wung Yung.
 * @version 24/03/2021
 */
public class AppDriver {

	/**
	 * Entry point to Java application.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}

}
