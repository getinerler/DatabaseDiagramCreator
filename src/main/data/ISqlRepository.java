package main.data;

import java.sql.SQLException;
import java.util.List;
import main.data.models.*;

public interface ISqlRepository {
	public List<String> getTables(TablesRequest request) 
			throws SQLException, ClassNotFoundException;

	public List<TableColumn> getTableColumns(TablesRequest request) 
			throws SQLException, ClassNotFoundException;

	public List<PrimaryKey> getPrimaryKeys(TablesRequest request) 
			throws SQLException, ClassNotFoundException;

	public List<TableConstraint> getConstraintList(TablesRequest request) 
			throws SQLException, ClassNotFoundException;

	public List<Index> getIndexList(TablesRequest request)
			throws SQLException, ClassNotFoundException;
}
