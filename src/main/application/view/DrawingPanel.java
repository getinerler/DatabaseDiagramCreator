package main.application.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.EventListener;
import javax.swing.JPanel;
import main.application.drawing.Constants;
import main.application.drawing.Calculations;
import main.application.drawing.DrawingHelper;
import main.application.model.Database;

public class DrawingPanel extends JPanel implements EventListener{

	private Database model;
	private boolean calculated;
	
	public DrawingPanel() {
		calculated = false;
		setBackground(Color.white);
		setPreferredSize(new Dimension(Constants.DRAWING_PANEL_WIDTH, 500));
	}
	
	public void drawDatabase(Database databaseModel) {
		this.model = databaseModel;
		calculated = false;
		repaint(); 
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		if(model != null) {
			if (!calculated) {
				new Calculations(g2, model, getWidth())
				.CalculateTable();
					calculated = true;
			}
			new DrawingHelper(model, g2).DrawDatatables();
			setPreferredSize(
					new Dimension(
							Constants.DRAWING_PANEL_WIDTH,
							model.getLowerY()));
		}
	}
}