package main.service.models;

public class GroupedConstraint {
	private GroupedColumn column;
	private GroupedColumn referencedColumn;
	
	public GroupedColumn getColumn() {
		return column;
	}
	
	public void setColumn(GroupedColumn column) {
		this.column = column;
	}
	
	public GroupedColumn getReferencedColumn() {
		return referencedColumn;
	}
	
	public void setReferencedColumn(GroupedColumn referencedColumn) {
		this.referencedColumn = referencedColumn;
	}
}
