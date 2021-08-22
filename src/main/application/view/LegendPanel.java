package main.application.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import main.application.drawing.Constants;
import main.application.drawing.SymbolDrawing;

public class LegendPanel extends JPanel {
	public LegendPanel() {
		setPreferredSize(new Dimension(300, 160));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		SymbolDrawing symbolDraw = new SymbolDrawing(g2);
		
		g2.drawRect(5, 5, this.getWidth() -15, this.getHeight() -15);
		g2.setFont(Constants.DEFAULT_FONT);

		
		symbolDraw.drawKey(15, 15);
		symbolDraw.drawForeignKey(15, 45);
		symbolDraw.drawIndex(15, 75);
		symbolDraw.drawNotNull(15, 105);
		
		g2.setColor(Color.black);
		drawExplanation(g2, "Primary Key", 30);
		drawExplanation(g2, "Foreign Key", 60);
		drawExplanation(g2, "Index", 90);
		drawExplanation(g2, "Nullable", 120);
	}
	
	private void drawExplanation(Graphics2D g2 , String explanation, int y) {
		 int length = g2.getFontMetrics(Constants.DEFAULT_FONT)
			.stringWidth(explanation);
		 g2.drawString(explanation, this.getWidth() - length - 20, y);
	}
}
