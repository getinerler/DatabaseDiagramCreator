package main.application.model;

import java.awt.Point;
import java.util.List;
import main.application.drawing.Constants;

public class Table {
	private String name;
	private List<Column> columns;
	private int row;
	private int column;	
	private int leftX;
	private int upperY;
	private int width;	
	private int height;	
	private int tableNameX;	
	private int tableNameY;	
	private int orderNo;
	private Point[][] constraintLnes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLeftX() {
		return leftX;
	}

	public int getRightX() {
		return leftX + width;
	}

	public void setLeftX(int x) {
		this.leftX = x;
	}

	public int getLowerY() {
		return upperY + 
				Constants.HEADER_COLUMN_HEIGHT  +  
				Constants.COLUMN_HEIGHT * columns.size();
	}

	public int getUpperY() {
		return upperY;
	}

	public void setUpperY(int headerY) {
		this.upperY = headerY;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public Point[][] getConstraintLnes() {
		return constraintLnes;
	}

	public void setConstraintLnes(Point[][] constraintLnes) {
		this.constraintLnes = constraintLnes;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTableNameX() {
		return tableNameX;
	}

	public void setTableNameX(int tableNameX) {
		this.tableNameX = tableNameX;
	}

	public int getTableNameY() {
		return tableNameY;
	}

	public void setTableNameY(int tableNameY) {
		this.tableNameY = tableNameY;
	}

	@Override
	public String toString() {
		return getName();
	}
}