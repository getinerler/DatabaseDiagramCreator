package main.data.models;

public class TablesRequest {
	private String server;
	private String port;
	private String username;
	private String password;
	private SqlType type;
	private String database;

	public String getServer() {
		return server;
	}
	
	public void setServer(String server) {
		this.server = server;
	}
	
	public String getPort() {
		if(this.port == null || !isNumeric(this.port)) {
			return "1433";
		}
		return port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public SqlType getType() {
		return type;
	}
	
	public void setType(SqlType type) {
		this.type = type;
	}

	public String getDatabase(String alternative) {
		if (database == null) {
			return alternative;
		}
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	private boolean isNumeric(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
