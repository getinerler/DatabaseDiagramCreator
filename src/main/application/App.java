package main.application;

import javax.swing.SwingUtilities;
import main.application.controller.Controller;
import main.application.view.View;

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void createAndShowGUI() throws Exception {
		View view = new View();
		Controller controller = new Controller(view);
		controller.initController();
	}
}
