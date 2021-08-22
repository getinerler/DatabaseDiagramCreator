package main.service.models;

import java.util.List;

public class GroupedDatabase {
	private List<GroupedTable> tables;
	private List<GroupedConstraint> constraints;
	
	public List<GroupedTable> getTables() {
		return this.tables;
	}
	
	public void setTables(List<GroupedTable> tables) {
		this.tables = tables;
	}

	public List<GroupedConstraint> getConstraints() {
		return constraints;
	}
	
	public void setConstraints(List<GroupedConstraint> constraints) {
		this.constraints = constraints;
	}
}