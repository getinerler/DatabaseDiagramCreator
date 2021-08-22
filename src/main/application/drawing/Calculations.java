package main.application.drawing;

import java.awt.Graphics2D;
import java.util.List;
import main.application.model.Column;
import main.application.model.ConstraintPoints;
import main.application.model.Database;
import main.application.model.Table;

public class Calculations {
	private Graphics2D graphics;
	private Database database;
    private int width;
    
	public Calculations(Graphics2D graphics, Database database, int width) {
		this.graphics = graphics;
		this.width = width;
		this.database = database;
	}

	public void CalculateTable() {
		int row = 1;
		int column = 1;
		int height = Constants.TABLE_TOP_MARGIN;
		int tempX = Constants.TABLE_SIDE_MARGIN;

		List<Table> tables = database.getTables();

		for (Table table : tables) {
			int tableWidth = getTableWidth(table);
			table.setWidth(tableWidth);
			table.setLeftX(tempX);
			tempX += tableWidth + Constants.TABLE_SIDE_MARGIN;

			if (tempX >= width) {
				tempX = Constants.TABLE_SIDE_MARGIN;
				table.setLeftX(tempX);
				tempX += tableWidth + Constants.TABLE_SIDE_MARGIN;
				row++;
				column = 1;
				
				final int[] currentRow = new int[1];
				currentRow[0] = row;

				height += tables
						.stream()
						.filter(x -> x.getRow() == currentRow[0] - 1)
						.map(x -> x.getColumns().size())
						.max(Integer::compare)
						.orElse(0) * Constants.COLUMN_HEIGHT + 
						Constants.HEADER_COLUMN_HEIGHT +
						Constants.TABLE_TOP_MARGIN;
			}
			
			table.setColumn(column++);
			table.setUpperY(height);
			table.setRow(row);

			calculateTableColumns(table, table.getLeftX(), height);

			table.setHeight(
					table.getColumns().get(table.getColumns().size() - 1)
							.getLowerY()
						-
					table.getUpperY());
			
			calculateTableNames(table);
			calculateColumnNames(table);
		}

		database.setConstraintLines(calculateConstraintLines());
	}

	private void calculateTableColumns(Table table, int x, int y) {
		for (int i = 0; i < table.getColumns().size(); i++) {
			Column column = table.getColumns().get(i);

			column.setLeftX(x);
			column.setUpperY(
					y + 
					Constants.HEADER_COLUMN_HEIGHT + 
					Constants.COLUMN_HEIGHT * i);
			
			column.setNameLength(
					graphics.getFontMetrics().stringWidth(
							column.getName()));
			
			column.setTypeLength(
					graphics.getFontMetrics().stringWidth(
							column.getType()));
		}
	}
	
	private void calculateTableNames(Table table) {
		int nameX = table.getLeftX() +
				(
					table.getWidth() - 
					graphics.getFontMetrics(Constants.BOLD_FONT)
						.stringWidth(table.getName())
				) / 2;
		
		int nameY = table.getUpperY() +
				(graphics.getFontMetrics(Constants.BOLD_FONT)
				.getHeight());
		
		table.setTableNameX(nameX);				
		table.setTableNameY(nameY);
	}

	private void calculateColumnNames(Table table) {
		for (Column column: table.getColumns()) {
			int nameLength = getColumnNameLength(column, graphics);				
			column.setTypeX(column.getTable().getLeftX() + nameLength + 15);
		}			
	}
	
	private List<ConstraintPoints> calculateConstraintLines() {
		return new ConstraintLinesHelper(
				database.getConstraints(), 
				database.getTables())
				.calculateConstraintLines();
	}

	private int getTableWidth(Table groupedTable) {
		int longestNameLength = groupedTable.getColumns()
				.stream()
				.map(column -> getColumnTotalLength(column, graphics))
				.max(Integer::compare)
				.get();	
		int headerLength = getHeaderLength(groupedTable, graphics);		
		return headerLength > longestNameLength ? 
				headerLength : 
					longestNameLength;
	}
	
	private int getColumnNameLength(Column column, Graphics2D g) {
		return g.getFontMetrics(Constants.DEFAULT_FONT)
				.stringWidth(column.getName());
	}

	private int getColumnTotalLength(Column column, Graphics2D g) {
		String text = column.getName() + column.getType();
		return g.getFontMetrics().stringWidth(text) + 30 + 30 + 30 + 5;
	}
	
	private int getHeaderLength(Table table, Graphics2D g) {
		return g.getFontMetrics(Constants.BOLD_FONT)
				.stringWidth(table.getName()) + 30 + 30 + 30 + 5;
	}
}
