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

public class MsSqlRepository implements ISqlRepository {
	public List<String> getTables(TablesRequest request)
			throws ClassNotFoundException, SQLException {		
		List<String> tables = new ArrayList<String>();
		
		Connection con = getConnection(request);  
		Statement stmt = con.createStatement();  
		String query = "SELECT name FROM sysdatabases";
		ResultSet resultSet = stmt.executeQuery(query); 
		
		while(resultSet.next()) {			
			tables.add(resultSet.getString(1));
		} 
		con.close();  		
		return tables;
	}

	public List<TableColumn> getTableColumns(TablesRequest request)
			throws SQLException, ClassNotFoundException {	
		List<TableColumn> tables = new ArrayList<TableColumn>();
		
		Connection connection = getConnection(request);
		Statement statement = connection.createStatement();
		String query = 
				"SELECT " + 
				" TABLE_NAME, " + 
				" COLUMN_NAME, " + 
				" DATA_TYPE, " + 
				" CHARACTER_MAXIMUM_LENGTH, " + 
				" ORDINAL_POSITION, " + 
				" IS_NULLABLE " + 
				"FROM " + 
				" INFORMATION_SCHEMA.COLUMNS " + 
				"WHERE " + 
				" TABLE_CATALOG = '" + request.getDatabase("master") + "';";
		ResultSet resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			TableColumn table = new TableColumn();
			table.setTableName(resultSet.getString(1));
			table.setColumnName(resultSet.getString(2));
			table.setType(getColumnType(
					resultSet.getString(3), 
					resultSet.getString(4)));
			table.setOrderNo(resultSet.getInt(5));
			table.setNullable(resultSet.getString(6).equals("YES"));
			tables.add(table);
		} 
		return tables;
	}

	public List<PrimaryKey> getPrimaryKeys(TablesRequest request)
			throws SQLException, ClassNotFoundException {
		List<PrimaryKey> primaryKeys = new ArrayList<PrimaryKey>();	
		
		Connection connection = getConnection(request);
		Statement statement = connection.createStatement();
		String query = 
				    "SELECT "
				  + " Col.TABLE_NAME, "
				  + " COLUMN_NAME "
				  + "FROM "
				  + " INFORMATION_SCHEMA.TABLE_CONSTRAINTS Tab, "
				  + " INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE Col "
				  + "WHERE "
				  + " Col.CONSTRAINT_NAME = Tab.CONSTRAINT_NAME"
				  + " AND Col.Table_Name = Tab.Table_Name"
				  + " AND CONSTRAINT_TYPE = 'PRIMARY KEY'"
				  + " AND Col.TABLE_CATALOG = '" + 
				  request.getDatabase("master") + "'";     
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
		
		Connection connection = getConnection(request);
		Statement statement = connection.createStatement();
		String query = 
				  "SELECT"
				+ " OBJECT_NAME(f.parent_object_id),"
				+ " COL_NAME(fc.parent_object_id,fc.parent_column_id),"
				+ " f.name,"
				+ " OBJECT_NAME(t.object_id),"
				+ " COL_NAME(t.object_id,fc.referenced_column_id) "
				+ "FROM "
				+ " sys.foreign_keys AS f,"
				+ " sys.foreign_key_columns AS fc,"
				+ " sys.tables t "
				+ "WHERE "
				+ " f.OBJECT_ID = fc.constraint_object_id"
				+ " AND t.OBJECT_ID = fc.referenced_object_id";     
		ResultSet resultSet = statement.executeQuery(query);
		
		while (resultSet.next()) {
			TableConstraint table = new TableConstraint();
			table.setTable(resultSet.getString(1));
			table.setColumn(resultSet.getString(2));
			table.setReferencedTable(resultSet.getString(4));
			table.setReferencedColumn(resultSet.getString(5));			
			constraints.add(table);
		}	 
		return constraints;
	}

	public List<Index> getIndexList(TablesRequest request) 
			throws SQLException, ClassNotFoundException {
		List<Index> indices = new ArrayList<Index>();		
		Connection connection = getConnection(request);
		Statement statement = connection.createStatement();

		String selectSql = 
				  "SELECT "
				  + " tab.name,"
				  + " COL_NAME(ix.object_id, ixc.column_id) "
				  + "FROM "
				  + " sys.indexes ix "
				  + "INNER JOIN sys.index_columns ixc "
				  + "  ON ix.object_id = ixc.object_id "
				  + "   AND ix.index_id = ixc.index_id "
				  + "INNER JOIN sys.tables tab "
				  + "  ON ix.object_id = tab.object_id";  
		ResultSet resultSet = statement.executeQuery(selectSql);
		
		while (resultSet.next()) {
			Index table = new Index();
			table.setTableName(resultSet.getString(1));
			table.setColumnName(resultSet.getString(2));
			indices.add(table);
		}	 
		return indices;
	}
	
	private String getColumnType(String type, String length) {
		if(length == null) {
			return type;
		}
		if(length.equals("-1")) {
			return type + "(max)";
		}
		return type + "(" + length + ")";
	}
	
	private Connection getConnection(TablesRequest request) 
			throws SQLException, ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl =
				String.format("jdbc:sqlserver://%s:%s;"
						+ "databaseName=%s;"
						+ "user=%s;"
						+ "password=%s;"
						+ "encrypt=false;"
						+ "trustServerCertificate=true;"
						+ "loginTimeout=30;",
						request.getServer(),
						request.getPort(),
						request.getDatabase("master"),
						request.getUsername(),
						request.getPassword());
		Connection connection = DriverManager.getConnection(connectionUrl);			
		return connection;
	}
}
