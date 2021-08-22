package main.application.model;

import main.application.drawing.Constants;

public class Column {
	private String name;
	private String type;
	private boolean isIndexed;
	private boolean isPrimaryKey;
	private boolean isNullable;
	private Table table;
	private int orderNo;
	private int leftX;
	private int upperY;	
	private int nameLength;
	private int typeLength;
	private int typeX;

	public Column(Table table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isIndexed() {
		return isIndexed;
	}

	public void setIndexed(boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Table getTable() {
		return table;
	}

	public int getLeftX() {
		return leftX;
	}

	public void setLeftX(int leftX) {
		this.leftX = leftX;
	}

	public int getUpperY() {
		return upperY;
	}

	public void setUpperY(int upperY) {
		this.upperY = upperY;
	}

	public int getRightX() {
		return this.getLeftX() + table.getWidth();
	}

	public int getLowerY() {
		return upperY + Constants.COLUMN_HEIGHT;
	}

	public int getNameLength() {
		return nameLength;
	}

	public void setNameLength(int nameLength) {
		this.nameLength = nameLength;
	}

	public int getTypeLength() {
		return typeLength;
	}

	public void setTypeLength(int typeLength) {
		this.typeLength = typeLength;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public int getRow() {
		return this.table.getRow();
	}

	public int getColumn() {
		return this.table.getColumn();
	}

	public int getWidth() {
		return this.table.getWidth();
	}

	public int getTypeX() {
		return typeX;
	}

	public void setTypeX(int typeX) {
		this.typeX = typeX;
	}

	public int getNthSymbolX(int n) {
		return getRightX() - Constants.SYMBOL_WIDTH * n - 5 * n;
	}

	public boolean isNullable() {
		return isNullable;
	}

	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}
}