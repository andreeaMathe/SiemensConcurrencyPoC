package siemens.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import siemens.database.DatabaseConnection;
import siemens.model.Person;

public class PersonOperations {

	private DatabaseConnection databaseConnection;

	public PersonOperations(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

	public List<Person> getAllPersons() {
		databaseConnection.createConnection();
		String query = "SELECT id, name FROM person";
		List<Person> persons = new ArrayList<Person>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = databaseConnection.getConnection().prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				Person person = new Person();
				person.setId(UUID.fromString(rs.getString("id")));
				person.setName(rs.getString("name"));

				persons.add(person);
			}
		} catch (SQLException e) {
			System.err.println("Error when creating query: " + e.getMessage());
		} finally {
			try {
				rs.close();
				ps.close();
				databaseConnection.getConnection().close();
			} catch (SQLException e) {
				System.err.println("Failed closing streams: " + e.getMessage());
			}
		}

		return persons;
	}

	public boolean addPerson(Person person, boolean asynchronous, boolean journalToMemory) {
		databaseConnection.createConnection();

		if (asynchronous == true)
			turnOffSync();

		if (journalToMemory == true)
			setJournalModeToWal();

		String query = "INSERT INTO person VALUES (?, ?)";
		PreparedStatement ps = null;
		try {
			ps = databaseConnection.getConnection().prepareStatement(query);
			ps.setString(1, person.getId().toString());
			ps.setString(2, person.getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error when creating query: " + e.getMessage());
			return false;
		} finally {
			try {
				ps.close();
				databaseConnection.getConnection().close();
			} catch (SQLException e) {
				System.err.println("Failed closing streams: " + e.getMessage());
			}
		}

		return true;
	}

	private void turnOffSync() {
		String settingsUpdate = "PRAGMA synchronous=OFF";
		PreparedStatement st;
		try {
			st = databaseConnection.getConnection().prepareStatement(settingsUpdate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setJournalModeToMemory() {
		String settingsUpdate = "PRAGMA journal_mode = MEMORY";
		PreparedStatement st;
		try {
			st = databaseConnection.getConnection().prepareStatement(settingsUpdate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setJournalModeToWal() {
		String settingsUpdate = "PRAGMA journal_mode = WAL";
		PreparedStatement st;
		try {
			st = databaseConnection.getConnection().prepareStatement(settingsUpdate);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deletePerson(Person person) {
		databaseConnection.createConnection();
		String query = "DELETE FROM person WHERE Id = (?)";
		PreparedStatement ps = null;
		try {
			ps = databaseConnection.getConnection().prepareStatement(query);
			ps.setString(1, person.getId().toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error when creating query: " + e.getMessage());
			return false;
		} finally {
			try {
				ps.close();
				databaseConnection.getConnection().close();
			} catch (SQLException e) {
				System.err.println("Failed closing streams: " + e.getMessage());
			}
		}

		return true;
	}

	public void printListOfPersons(List<Person> persons) {
		for (Person stud : persons) {
			System.out.println(stud.toString());
		}
	}
}