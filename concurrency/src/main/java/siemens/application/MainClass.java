package siemens.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import siemens.database.Database;
import siemens.database.DatabaseConnection;
import siemens.model.Person;
import siemens.util.PersonOperations;

public class MainClass {

	static int succededRequests = 0;

	public static void main(String[] args) {

		int n = 10000;
		List<Thread> threads = new ArrayList<>();

		for (int i = 0; i < n; ++i) {
			threads.add(new Thread(new SqlTask(new Person(UUID.randomUUID(), "test"))));
		}

		for (Thread t : threads) {
			t.start();
		}

		for (int i = 0; i < n; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Succeded requests : " + succededRequests);
	}

	private static class SqlTask implements Runnable {
		private Person personToBeAdded;

		public SqlTask(Person person) {
			personToBeAdded = person;
		}

		public void run() {
			Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:src\\resources\\sample.db", "", "");
			DatabaseConnection databaseConnection = new DatabaseConnection(database);
			PersonOperations personOperations = new PersonOperations(databaseConnection);

			long startTime = System.currentTimeMillis();

			if (personOperations.addPerson(personToBeAdded, true, false, true))
				succededRequests++;

			long duration = System.currentTimeMillis() - startTime;
			System.out.println("   SQL Insert completed: " + duration);
		}
	}
}
