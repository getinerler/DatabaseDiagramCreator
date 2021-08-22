package main.service.models;

import java.util.List;

public class GroupedTable {
	private String name;
	private List<GroupedColumn> columns;
	private int orderNo;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<GroupedColumn> getColumns() {
		return columns;
	}
	
	public void setColumns(List<GroupedColumn> columns) {
		this.columns = columns;
	}
	
	public int getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}
