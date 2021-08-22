package main.application.drawing;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.application.model.Column;
import main.application.model.Constraint;
import main.application.model.ConstraintPoints;
import main.application.model.Table;

public class ConstraintLinesHelper {
	private List<Table> tables;
	private List<Constraint> constraints;

	public ConstraintLinesHelper(
			List<Constraint> constraints,
			List<Table> tables) {
		this.constraints = constraints;
		this.tables = tables;
	}

	public List<ConstraintPoints> calculateConstraintLines() {
		List<ConstraintPoints> constraintGroupLines =
				new ArrayList<ConstraintPoints>();
		
		for(Constraint constraint: constraints) {
			Point[] points = calculateConstraint(
					constraint.getColumn(),
					constraint.getReferencedColumn());
			ConstraintPoints constraintPoints = new ConstraintPoints();
			constraintPoints.setPointsList(points);
			constraintGroupLines.add(constraintPoints);
		}		
		return constraintGroupLines;
	}

	private Point[] calculateConstraint(
			Column column,
			Column referencedColumn) {
		PointArrayCreator pointCreator = new PointArrayCreator();
		ToColumnSpace(pointCreator, column, referencedColumn);
		ToNearestRowSpaceToTarget(pointCreator, column, referencedColumn);
		ToTargetColumn(pointCreator, column, referencedColumn);	
		return pointCreator.getPoints();
	}

	private void ToColumnSpace(
			PointArrayCreator pointCreator,
			Column column,
			Column referencedColumn) {
		if (referencedColumn.getColumn() >= column.getColumn()) {
			pointCreator.add(
					referencedColumn.getLeftX(),
					(int)(referencedColumn.getUpperY() + 
							Constants.COLUMN_HEIGHT * safeRandom()));

			pointCreator.addWithAddedX(
					-(int)(Constants.TABLE_SIDE_MARGIN * safeRandom()));
		} else {
			pointCreator.add(
					referencedColumn.getLeftX() + referencedColumn.getWidth(),
					(int)(referencedColumn.getUpperY() +
							Constants.COLUMN_HEIGHT * safeRandom()));

			pointCreator.addWithAddedX(
					(int)(Constants.TABLE_SIDE_MARGIN * safeRandom()));
		}
	}

	private void ToNearestRowSpaceToTarget(
			PointArrayCreator pointCreator,
			Column column, 
			Column referencedColumn) {
		int newY = referencedColumn.getRow() >= column.getRow() ?
				getRowLowerSpaceY(column.getRow()) :
					getRowUpperSpaceY(column.getRow());

		int targetX = referencedColumn.getColumn() >= column.getColumn()  ?
				getTableLeftSpaceX(column) :
					getTableRightSpaceX(column) ;

		do {
			Table tableBetween = getFirstTableBetweenY(
					pointCreator.getCurrentX(),
					pointCreator.getCurrentY(),
					newY);

			if(tableBetween != null) {
				boolean down = pointCreator.getCurrentY() < newY;
				int upperSpaceY = getRowUpperSpaceY(tableBetween.getRow());
				int lowerSpaceY = getRowLowerSpaceY(tableBetween.getRow());
				if (down) {
					if (pointCreator.getCurrentY() != upperSpaceY) {
						pointCreator.addWithNewY(upperSpaceY);
					}
				} else  {
					if (pointCreator.getCurrentY() != lowerSpaceY) {
						pointCreator.addWithNewY(lowerSpaceY);
					}
				}

				if (pointCreator.getCurrentX() > targetX) {
					pointCreator.addWithNewX(getTableLeftSpaceX(tableBetween));
				} else {
					pointCreator.addWithNewX(getTableRightSpaceX(tableBetween));
				}

			} else {
				pointCreator.addWithNewY(newY);
			}
		}
		while(Math.abs(pointCreator.getCurrentY() - newY) >= 
				Constants.TABLE_TOP_MARGIN);
	}

	private void ToTargetColumn(
			PointArrayCreator pointCreator,
			Column column, 
			Column referencedColumn) {
		if (referencedColumn.getColumn() > column.getColumn()) {
			pointCreator.addWithNewX(getTableLeftSpaceX(column));
			pointCreator.addWithNewY((int)(column.getUpperY() +
					Constants.COLUMN_HEIGHT * safeRandom()));
			pointCreator.addWithNewX(column.getLeftX());
		} else {
			pointCreator.addWithNewX(getTableRightSpaceX(column));
			pointCreator.addWithNewY((int)(column.getUpperY() + 
					Constants.COLUMN_HEIGHT * safeRandom()));
			pointCreator.addWithNewX(column.getLeftX() + column.getWidth());
		}
	}
	
	private int getRowUpperSpaceY(final int row) {
		Table tableOfRow = tables
				.stream()
				.filter(table -> table.getRow() == row)
				.findAny()
				.get();

		return  (
				tableOfRow.getUpperY() -
				(int)(Constants.TABLE_TOP_MARGIN * safeNarrowRandom()));
	}

	private int getRowLowerSpaceY(final int row) {
		int maxLowerY = tables
				.stream()
				.filter(table -> table.getRow() == row)
				.map(table -> table.getLowerY())
				.max(Integer::compare)
				.get();
		return (int) (maxLowerY + (Constants.TABLE_TOP_MARGIN * safeRandom()));
	}

	private int getTableLeftSpaceX(Table table) {
		return (int) (table.getLeftX() - 
				(Constants.TABLE_SIDE_MARGIN * safeRandom()));
	}

	private int getTableRightSpaceX(Table table) {
		return (int) (table.getLeftX() +
				table.getWidth() +
				(Constants.TABLE_SIDE_MARGIN * safeRandom()));
	}

	private int getTableLeftSpaceX(Column column) {
		return (int) (
				column.getTable().getLeftX() - 
				(Constants.TABLE_SIDE_MARGIN * safeRandom()));
	}

	private int getTableRightSpaceX(Column column) {
		return (int) (column.getTable().getLeftX() +
				column.getWidth() +
				(Constants.TABLE_SIDE_MARGIN * Math.random()));
	}

	private Table getFirstTableBetweenY(int x, int yStart, int yEnd) {
		int smallerY = yStart > yEnd ? yEnd : yStart;
		int greaterY = yStart > yEnd ? yStart : yEnd;

		List<Table> existingTables = new ArrayList<Table>();
		for (Table table: tables) {
			if (x < table.getLeftX() || x > table.getRightX()) {
				continue;
			}
			if (smallerY < table.getUpperY() && greaterY < table.getUpperY()) {
				continue;
			}
			if (smallerY > table.getLowerY()) {
				continue;
			}
			existingTables.add(table);
		}

		if(existingTables.size() > 0) {
			if(yStart == smallerY) {
				return existingTables.stream()
						.sorted(Comparator.comparingInt(Table::getUpperY))
						.findFirst()
						.orElse(null);
			}
			else {
				return existingTables.stream()
						.sorted(Comparator.comparingInt(Table::getUpperY)
								.reversed())
						.findFirst()
						.orElse(null);
			}
		} else {
			return null;
		}
	}
	
	private double safeNarrowRandom() {
		return 0.4 + (Math.random() * 0.6);
	}
	
	private double safeRandom() {
		return 0.2 + (Math.random() * 0.8);
	}
}