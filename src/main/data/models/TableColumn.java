package main.data.models;

public class TableColumn {
	private String tableName;
	private String type;
	private String columnName;
	private int orderNo;
	private boolean isPrimaryKey;
	private boolean isNullable;
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String name) {
		this.tableName = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public void setColumnName(String column) {
		this.columnName = column;
	}	
	
	public int getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}
	
	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	
	public boolean isNullable() {
		return isNullable;
	}
	
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
}
