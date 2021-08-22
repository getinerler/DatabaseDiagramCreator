package main.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import main.data.ISqlRepository;
import main.data.MsSqlRepository;
import main.data.MySqlRepository;
import main.data.models.*;
import main.service.mapping.ConstraintMapping;
import main.service.mapping.TableMapping;
import main.service.models.*;

public class SqlService {
	private ISqlRepository repo;
	
	public List<String> getTables(TablesRequest request) throws Exception {
		repo = getRepository(request);		
		return repo.getTables(request)
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	public GroupedDatabase getSqlTableModel(TablesRequest request)
			throws Exception {
		repo = getRepository(request);		
		List<TableColumn> tablesColumns = repo
				.getTableColumns(request);
		List<TableConstraint> constraints = repo.getConstraintList(request);	
		List<Index> indices = repo.getIndexList(request);
		List<PrimaryKey> primaryKeys = repo.getPrimaryKeys(request);
		
		List<GroupedTable> groupedTables = TableMapping
				.mapTables(tablesColumns, primaryKeys, indices)
				.stream()
				.sorted(Comparator.comparing(GroupedTable::getName))
				.collect(Collectors.toList());	
		List<GroupedConstraint> groupedConstraints = ConstraintMapping
				.mapConstraints(groupedTables, constraints);
		
		GroupedDatabase database = new GroupedDatabase();
		database.setTables(groupedTables);
		database.setConstraints(groupedConstraints);
		return database;
	}

	private ISqlRepository getRepository(TablesRequest request) 
			throws Exception {
		ISqlRepository repo;
		if(request.getType() == SqlType.MySql) {
			repo = new MySqlRepository();
		} else if (request.getType() == SqlType.MsSql) {
			repo = new MsSqlRepository();
		} else {
			throw new Exception("Unknown SQL type.");
		}	
		return repo;
	}
}
