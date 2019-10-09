package siemens.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import siemens.database.Database;
import siemens.database.DatabaseConnection;
import siemens.model.Person;
import siemens.util.PersonOperations;

public class MainClass {

	public static void main(String[] args) {
		int n = 100;
		List<SqlTask> tasks = new ArrayList<>();

		for (int i = 0; i < n; ++i) {
			tasks.add(new SqlTask(new Person(UUID.randomUUID(), "test")));
		}

		Thread threads[] = new Thread[n];
		for (int i = 0; i < n; i++)
			threads[i] = new Thread(tasks.get(i));

		for (int i = 0; i < n; i++)
			threads[i].start();

		for (int i = 0; i < n; i++)
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}

	private static class SqlTask implements Runnable {
		private Person personToBeAdded;

		public SqlTask(Person person) {
			personToBeAdded = person;
		}

		public void run() {
			Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:sample.db", "", "");
			DatabaseConnection databaseConnection = new DatabaseConnection(database);
			PersonOperations personOperations = new PersonOperations(databaseConnection);

			long startTime = System.currentTimeMillis();

			personOperations.addPerson(personToBeAdded, true, true);

			long duration = System.currentTimeMillis() - startTime;
			System.out.println("   SQL Insert completed: " + duration);
		}
	}
}
