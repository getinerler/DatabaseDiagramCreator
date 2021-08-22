package main.application.drawing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Map;
import main.application.model.Column;
import main.application.model.ConstraintPoints;
import main.application.model.Database;
import main.application.model.Table;

public class DrawingHelper {
	private Database database;
	private Graphics2D g2;
	private SymbolDrawing symbolDraw;
	
	public  DrawingHelper(Database database, Graphics2D g2) {
		this.database = database;
		this.g2 = g2;
		symbolDraw = new SymbolDrawing(g2);

		Map<?,?> desktopHints = (Map<?,?>) Toolkit
				.getDefaultToolkit()
				.getDesktopProperty("awt.font.desktophints");

		if (desktopHints != null) {
			g2.setRenderingHints(desktopHints);
		}
	}

	public void DrawDatatables() {
		g2.setStroke(Constants.stroke1);
		for(Table table: database.getTables()) {
			drawTable(table);
		}
		drawConstraints(database);
	}

	private void drawTable(Table table) {
		drawHeader(table);
		for (Column column : table.getColumns()) {
			drawColumn(column, table.getWidth());
		}
		g2.setColor(Constants.DARK_RED);
		g2.drawRect(
				table.getLeftX(), 
				table.getUpperY(),
				table.getWidth(),
				table.getHeight());	
	}

	private void drawHeader(Table table) { 
		g2.setFont(Constants.BOLD_FONT);	
		g2.setColor(Constants.DARK_RED);
		g2.fillRect(
				table.getLeftX(), 
				table.getUpperY(),
				table.getWidth(),
				Constants.HEADER_COLUMN_HEIGHT);			
		g2.drawRect(
				table.getLeftX(), 
				table.getUpperY(),
				table.getWidth(),
				Constants.HEADER_COLUMN_HEIGHT);	
		AlphaComposite alcom2 = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 1);
		g2.setComposite(alcom2);        
		g2.setColor(Color.black);	
		g2.setColor(Color.white);
		g2.drawString(
				table.getName(), 
				table.getTableNameX(), 
				table.getTableNameY()
				);	
		g2.setColor(Color.black);
	}

	private void drawColumn(Column column, int width) { 
		g2.setColor(Color.LIGHT_GRAY);
		g2.setFont(Constants.DEFAULT_FONT);
		g2.drawRect(
				column.getLeftX(), 
				column.getUpperY(),
				width,
				Constants.COLUMN_HEIGHT);
		g2.setColor(Color.black);
		g2.drawString(
				column.getName(),
				column.getLeftX() + 5,
				(
					column.getUpperY() * 2 + 
					Constants.COLUMN_HEIGHT +
					Constants.DEFAULT_FONT.getSize()
				) / 2);

		int symbolCounter = 1;

		int symbolHeight = 
				(Constants.COLUMN_HEIGHT - Constants.SYMBOL_WIDTH)
				/ 
				2;
		if (column.isNullable()) {
			symbolDraw.drawNotNull(
					column.getNthSymbolX(symbolCounter++), 
					column.getUpperY() + symbolHeight);
		}

		if (column.isPrimaryKey()) {
			symbolDraw.drawKey(
					column.getNthSymbolX(symbolCounter++), 
					column.getUpperY() + symbolHeight);
		}

		if (column.isIndexed()) {
			symbolDraw.drawIndex(
					column.getNthSymbolX(symbolCounter++), 
					column.getUpperY() + symbolHeight);
		}

		g2.setFont(Constants.TINY_FONT);
		g2.setColor(Color.gray);
		g2.drawString(
				column.getType(),
				column.getTypeX(),
				(
					column.getUpperY() * 2 + 
					Constants.COLUMN_HEIGHT +
					Constants.TINY_FONT.getSize()
				) / 2);
	}

	private void drawConstraints(Database database) { 
		if (database.getConstraintLines() == null) {
			return;
		}

		g2.setColor(Constants.DARK_ORANGE);
		g2.setStroke(Constants.stroke1);
		for	(ConstraintPoints points: database.getConstraintLines()) {
			if(points.isHovered() || points.isSelected()) {
				continue;
			}
			drawConstraintPoints(points);
		}

		g2.setColor(Color.RED);
		g2.setStroke(Constants.stroke2);
		for	(ConstraintPoints points: database.getConstraintLines()) {
			if(points.isSelected()) {
				drawConstraintPoints(points);
			}
		}	

		g2.setColor(Constants.DARK_ORANGE);
		g2.setStroke(Constants.stroke2);
		for	(ConstraintPoints points: database.getConstraintLines()) {
			if(points.isHovered()) {
				drawConstraintPoints(points);
			}		
		}

		g2.setStroke(Constants.stroke1);
	}

	private void drawConstraintPoints(ConstraintPoints points) {
		Point previousPoint = null;
		for (Point p: points.getPointsList()) {
			if(previousPoint != null) {
				g2.drawLine(
						previousPoint.x,
						previousPoint.y, 
						p.x, 
						p.y);
			} else {
				int halfRadius = Constants.CONSTRAINT_MARK_RADIUS / 2;
				g2.fillOval(
						p.x - halfRadius, 
						p.y - halfRadius, 
						Constants.CONSTRAINT_MARK_RADIUS, 
						Constants.CONSTRAINT_MARK_RADIUS);
			}

			previousPoint = p;							
		}	
	}
}
