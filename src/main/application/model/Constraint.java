package main.application.model;

import java.util.List;

public class Constraint {	
	private Column column;
	private Column referencedColumn;

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Column getReferencedColumn() {
		return referencedColumn;
	}

	public void setReferencedColumn(Column referencedColumn) {
		this.referencedColumn = referencedColumn;
	}

	public int getOrderNo() {
		return referencedColumn.getOrderNo();
	}

	public String getTableName() {
		return column.getName();
	}

	public String getReferencedTableName() {
		return referencedColumn.getTable().getName();
	}

	public boolean isConnectedToTable(List<String> tables) {
		String tableName = column.getTable().getName();
		String referencedTableName = referencedColumn.getTable().getName();	
		if (!tables.contains(tableName)) {
			return false;
		}
		if (!tables.contains(referencedTableName)) {
			return false;
		}
		return true;
	}
}