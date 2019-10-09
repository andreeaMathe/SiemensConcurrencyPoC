package siemens.database;

public class Database {

	private String jdbcDriver;

	private String databaseUrl;

	private String username;

	private String password;

	public Database(String jdbcDriver, String databaseUrl, String username, String password) {
		this.jdbcDriver = jdbcDriver;
		this.databaseUrl = databaseUrl;
		this.username = username;
		this.password = password;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
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

	@Override
	public String toString() {
		return "Database [jdbcDriver=" + jdbcDriver + ", databaseUrl=" + databaseUrl + ", username=" + username
				+ ", password=" + password + "]";
	}
}