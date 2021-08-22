package main.application.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

public class SymbolDrawing {
	private Graphics2D g2;
	
	public SymbolDrawing (Graphics2D g2) {
		this.g2 = g2;
	}
	
	public void drawKey(int x, int y) {		
		Path2D.Double path = new Path2D.Double();
		
		path.moveTo(x + 15, y + 9);
		path.lineTo(x + 3, y + 9);
		
		path.moveTo(x + 3, y + 9);
		path.lineTo(x + 1, y + 10);
		
		path.moveTo(x + 1, y + 10);
		path.lineTo(x + 3, y + 11);
		
		path.moveTo(x + 3, y + 11);
		path.lineTo(x + 15, y + 11);
		
		path.moveTo(x + 4, y + 11);
		path.lineTo(x + 4, y + 13);
		
		path.moveTo(x + 7, y + 11);
		path.lineTo(x + 7, y + 13);
		path.closePath();
		
		g2.setColor(Color.black);
		g2.draw(path);
		g2.setColor(Color.yellow);
		g2.fill(path);
			
		g2.setColor(Color.yellow);	
		g2.fillOval(x + 10, y + 5, 10, 10);
		g2.setColor(Color.black);	
		g2.drawOval(x + 10, y + 5, 10, 10);
		
		g2.setColor(Color.white);	
		g2.fillOval(x + 15, y + 8, 3, 3);
		g2.setColor(Color.black);	
		g2.drawOval(x + 15, y + 8, 3, 3);
	}
	
	public void drawForeignKey(int x, int y) {
		g2.setColor(Color.ORANGE);
		g2.fillOval(
				x, 
				y + 7, 
				Constants.CONSTRAINT_MARK_RADIUS, 
				Constants.CONSTRAINT_MARK_RADIUS);
		g2.drawLine(x, y + 10, x + 20, y + 10);
	}
	
	
	public void drawNotNull(int x, int y) {
		g2.setColor(Color.gray);
		g2.drawString("N", x + 5, y + 15);
	}
	
	public void drawIndex(int x, int y) {
		g2.setColor(Color.black);
		int side = Constants.SYMBOL_WIDTH;
		int squareWidth = side / 5;	
		g2.fillRect(
				x + squareWidth * 2,
				y + squareWidth, 
				squareWidth, 
				squareWidth);	
		g2.fillRect(
				x,
				y + squareWidth * 3, 
				squareWidth, 
				squareWidth);	
		g2.fillRect(
				x + squareWidth * 2, 
				y + squareWidth * 3, 
				squareWidth, 
				squareWidth);	
		g2.fillRect(
				x + squareWidth * 4, 
				y + squareWidth * 3,
				squareWidth, 
				squareWidth);	
		g2.drawLine(
				x + side / 2, 
				y + squareWidth + squareWidth / 2,
				x + squareWidth / 2,
				y + squareWidth + side / 2);
		g2.drawLine(
				x + side / 2,
				y + squareWidth + squareWidth / 2, 
				x + side / 2, 
				y + squareWidth + side / 2);
		g2.drawLine(
				x + side / 2, 
				y + squareWidth + squareWidth / 2, 
				x + side * 9 / 10,
				y + squareWidth + side / 2);
	}
}
