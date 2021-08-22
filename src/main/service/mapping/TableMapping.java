package main.service.mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import main.data.models.Index;
import main.data.models.TableColumn;
import main.data.models.PrimaryKey;
import main.service.models.GroupedColumn;
import main.service.models.GroupedTable;

public class TableMapping {	
	public TableMapping() throws Exception {
		throw new AssertionError();
	}
	
	public static List<GroupedTable> mapTables(
			List<TableColumn> tableColumns,
			List<PrimaryKey> primaryKeys,
			List<Index> indices) {
		List<GroupedTable> groupedTables = new ArrayList<GroupedTable>();	

		List<String> names = tableColumns
				.stream()
				.map(TableColumn::getTableName)
				.distinct()
				.collect(Collectors.toList());
		
		for(String name : names) {		
			GroupedTable groupedTable = new GroupedTable();
			groupedTable.setName(name);
			List<GroupedColumn> filteredList = tableColumns
					.stream()
					.filter(column -> column.getTableName().equals(name))
					.map(column -> mapTableColumn(
							column, 
							groupedTable,
							primaryKeys, 
							indices))
					.sorted(Comparator.comparingInt(GroupedColumn::getOrderNo))
					.collect(Collectors.toList());
			groupedTable.setColumns(filteredList);
			groupedTables.add(groupedTable);
		}
		
		return groupedTables;
	}
	
	private static GroupedColumn mapTableColumn(
			TableColumn column,
			GroupedTable table,
			List<PrimaryKey> primaryKeys,
			List<Index> indices) {

		Index index = indices
				.stream()
				.filter(idx -> 
					idx.getTableName().equals(table.getName()) &&
					idx.getColumnName().equals(column.getColumnName()))
				.findFirst()
				.orElse(null);

		GroupedColumn groupedColumn = new GroupedColumn(table);
		groupedColumn.setName(column.getColumnName());
		groupedColumn.setType(column.getType());
		groupedColumn.setOrderNo(column.getOrderNo());	
		groupedColumn.setPrimaryKey(column.isPrimaryKey());
		groupedColumn.setNullable(column.isNullable());
		
		PrimaryKey primaryKey = primaryKeys
				.stream()
				.filter(x -> 
					x.getTable().equals(table.getName()) && 
					x.getColumn().equals(column.getColumnName()))
				.findFirst()
				.orElse(null);
		
		if(primaryKey != null) {
			groupedColumn.setPrimaryKey(true);
		}
		
		groupedColumn.setIndexed(index != null);
		
		return groupedColumn;
	}
}
