package main.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.data.models.Index;
import main.data.models.TableColumn;
import main.data.models.PrimaryKey;
import main.data.models.TableConstraint;
import main.data.models.TablesRequest;

public class MySqlRepository implements ISqlRepository {
	public List<String> getTables(TablesRequest request)
		throws ClassNotFoundException, SQLException {
		List<String> tables = new ArrayList<String>();	
		
		Connection con = getConnection(request);  
		Statement stmt = con.createStatement();  
		ResultSet resultSet = stmt.executeQuery("SHOW DATABASES;");  
			
		while(resultSet.next()) {			
			tables.add(resultSet.getString(1));
		} 
		con.close();  	
		return tables;
	}
	
	public List<TableColumn> getTableColumns(TablesRequest request) 
			throws ClassNotFoundException, SQLException {	
		List<TableColumn> tables = new ArrayList<TableColumn>();
		
		Connection con = getConnection(request);  
		Statement stmt = con.createStatement();  
		ResultSet resultSet = stmt.executeQuery(
			"SELECT " + 
			" TABLE_NAME, " + 
			" COLUMN_NAME, " + 
			" COLUMN_TYPE, " + 
			" ORDINAL_POSITION, " + 
			" IS_NULLABLE, " + 
			" COLUMN_KEY " +
			"FROM " + 
			" INFORMATION_SCHEMA.COLUMNS " + 
			"WHERE " + 
			" TABLE_SCHEMA = '" + request.getDatabase("master") + "';");  
			
		while(resultSet.next()) {
			TableColumn table = new TableColumn();
			table.setTableName(resultSet.getString(1));
			table.setColumnName(resultSet.getString(2));
			table.setType(resultSet.getString(3));
			table.setOrderNo(resultSet.getInt(4));
			table.setNullable(resultSet.getString(5).equals("YES"));
			table.setPrimaryKey(resultSet.getString(6).equals("PRI"));
			tables.add(table);
		} 
		con.close();  
		return tables;
	} 
	
	public List<PrimaryKey> getPrimaryKeys(TablesRequest request)
			throws SQLException, ClassNotFoundException {	
		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();	
		
		Connection connection = getConnection(request);
		Statement statement = connection.createStatement();
		String query = 
				    "SELECT " + 
		            " tab.table_name," + 
				    " sta.column_name " + 
				    "FROM "
				    + "information_schema.tables as tab " + 
				    "INNER JOIN information_schema.statistics as sta " + 
				    "   on sta.table_schema = tab.table_schema " + 
				    "     and sta.table_name = tab.table_name " + 
				    "     and sta.index_name = 'primary' " + 
				    "WHERE "
				    + "tab.table_schema = '" + 
				    request.getDatabase("master") + "'" + 
				    "    and tab.table_type = 'BASE TABLE'";     
		ResultSet resultSet = statement.executeQuery(query);
		
		while (resultSet.next()) {
			PrimaryKey table = new PrimaryKey();
			table.setTable(resultSet.getString(1));
			table.setColumn(resultSet.getString(2));
		
			primaryKeys.add(table);
		}	 
		return primaryKeys;	
	}
	
	public List<TableConstraint> getConstraintList(TablesRequest request) 
			throws SQLException, ClassNotFoundException {	
		List<TableConstraint> constraints = new ArrayList<TableConstraint>();
		
		Connection con = getConnection(request);  
		Statement stmt=con.createStatement();  
		ResultSet resultSet = stmt.executeQuery(
			"SELECT " + 
			" TABLE_NAME," + 
			" COLUMN_NAME," + 
			" CONSTRAINT_NAME," + 
			" REFERENCED_TABLE_NAME," + 
			" REFERENCED_COLUMN_NAME " + 
			"FROM " + 
			" INFORMATION_SCHEMA.KEY_COLUMN_USAGE " + 
			"WHERE " + 
			" REFERENCED_TABLE_SCHEMA = '" +
				request.getDatabase("master") + "'");  
		
		while(resultSet.next()) {
			TableConstraint table = new TableConstraint();
			table.setTable(resultSet.getString(1));
			table.setColumn(resultSet.getString(2));
			table.setReferencedTable(resultSet.getString(4));
			table.setReferencedColumn(resultSet.getString(5));
			constraints.add(table);
		} 
		con.close();  	
		return constraints;
	}

	public List<Index> getIndexList(TablesRequest request) 
			throws ClassNotFoundException, SQLException {
		List<Index> indexes = new ArrayList<Index>();
		
		Connection con = getConnection(request);  
		Statement stmt=con.createStatement();  
		ResultSet resultSet = stmt.executeQuery(
			"SELECT " + 
			" TABLE_NAME," + 
			" COLUMN_NAME " + 
			"FROM " + 
			" INFORMATION_SCHEMA.STATISTICS " + 
			"WHERE " + 
			"TABLE_SCHEMA = '" + request.getDatabase("master") + "';");  
		
		while(resultSet.next()) {
			Index table = new Index();
			table.setTableName(resultSet.getString(1));
			table.setColumnName(resultSet.getString(2));	
			indexes.add(table);
		} 
		con.close();  		
		return indexes;
	}
	
	private Connection getConnection(TablesRequest request) 
			throws ClassNotFoundException, SQLException {
		String connString = String.format("jdbc:mysql://%s:%s", 
				request.getServer(), 
				request.getPort());
		Class.forName("com.mysql.cj.jdbc.Driver");  
		Connection connection = DriverManager.getConnection(
			connString,
			request.getUsername(),
			request.getPassword());  
		return connection;
	}
}