package siemens.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private Database database;
	private Connection connection;

	public DatabaseConnection(Database database) {
		this.database = database;
	}

	private void registerJdbcDriver() {
		try {
			Class.forName(database.getJdbcDriver());
		} catch (ClassNotFoundException e) {
			System.err.println("Could not find class driver: " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private void openConnection() {
		System.out.println("Connecting to the database...");
		try {
			setConnection(DriverManager.getConnection(database.getDatabaseUrl(), database.getUsername(),
					database.getPassword()));
		} catch (SQLException e) {
			System.err.println("Could not find open connection: " + e.getMessage());
		}
	}

	public void createConnection() {
		this.registerJdbcDriver();
		this.openConnection();
	}
}
