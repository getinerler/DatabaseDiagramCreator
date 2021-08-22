package main.application.model;

import java.util.Comparator;
import java.util.List;
import main.application.drawing.Constants;

public class Database {
	private List<Table> tables;
	private List<ConstraintPoints> constraintLines;
	private List<Constraint> constraints;

	public List<Table> getTables() {
		return this.tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<ConstraintPoints> getConstraintLines() {
		return constraintLines;
	}

	public void setConstraintLines(List<ConstraintPoints> constraints) {
		this.constraintLines = constraints;
	}

	public int getLowerY() {
		Table lowest = tables
				.stream()
				.max(Comparator.comparing(Table::getLowerY))
				.orElse(null);		
		if(lowest == null) {
			return 0;
		}
		return lowest.getLowerY() + Constants.TABLE_TOP_MARGIN;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}
}