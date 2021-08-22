package main.application.controller;

import java.util.List;
import java.util.stream.Collectors;
import main.application.model.*;
import main.service.models.*;

public class Mapping {
	public Mapping() {
		throw new AssertionError();
	}
	
	public static Database mapDatabase(GroupedDatabase database) {
		Database model = new Database();
		model.setTables(mapTables(database.getTables()));
		model.setConstraints(mapConstraints(
				model.getTables(),
				database.getConstraints()));	
		return model;
	}
	
	private static List<Table> mapTables(List<GroupedTable> tables) {
		List<Table> list = tables
				.stream()
				.map(table -> mapTable(table))
				.collect(Collectors.toList());	
		return list;
	}
	
	private static Table mapTable(GroupedTable table) {
		Table model = new Table();
		model.setName(table.getName());
		model.setOrderNo(table.getOrderNo());
		model.setColumns(mapColumns(model, table.getColumns()));
		return model;
	}
	
	private static List<Column> mapColumns(
			Table table, 
			List<GroupedColumn> columns) {
		List<Column> list = columns
				.stream()
				.map(column -> mapColumn(table, column))
				.collect(Collectors.toList());		
		return list;
	}
	
	private static Column mapColumn(
			Table table, 
			GroupedColumn column) {
		Column model = new Column(table);
		model.setName(column.getName());
		model.setType(column.getType());
		model.setOrderNo(column.getOrderNo());
		model.setPrimaryKey(column.isPrimaryKey());
		model.setIndexed(column.isIndexed());
		model.setNullable(column.isNullable());
		return model;
	}
	
	private static List<Constraint> mapConstraints(
			List<Table> tables,
			List<GroupedConstraint> constraints) {
		List<Constraint> list = constraints
				.stream()
				.map(constraint -> mapConstraint(tables, constraint))
				.collect(Collectors.toList());	
		return list;
	}
	
	private static Constraint mapConstraint(
			List<Table> tables,
			GroupedConstraint constraint) {
		Constraint model = new Constraint();	
		model.setColumn(getConstraintColumn(
				tables,
				constraint.getColumn().getTable().getName(), 
				constraint.getColumn().getName()));
		model.setReferencedColumn(getConstraintColumn(
				tables,
				constraint.getReferencedColumn().getTable().getName(), 
				constraint.getReferencedColumn().getName()));
		return model;
	}
	
	private static Column getConstraintColumn(
			List<Table> tables,
			String tableName, 
			String columnName) {
		Table columnTable = tables
				.stream()
				.filter(table -> table.getName().equals(tableName))
				.findFirst()
				.orElse(null);	
		Column constraintColumn = columnTable.getColumns()
				.stream()
				.filter(column -> column.getName().equals(columnName))
				.findFirst()
				.orElse(null);	
		return constraintColumn;
	}
}