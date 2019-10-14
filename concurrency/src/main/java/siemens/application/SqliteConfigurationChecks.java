package siemens.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import siemens.database.Database;
import siemens.database.DatabaseConnection;
import siemens.model.Person;
import siemens.util.JournalMode;
import siemens.util.PersonOperations;
import siemens.util.SynchronousSetting;

public class SqliteConfigurationChecks {

	static int succededRequests = 0;

	private static SynchronousSetting synchronousSetting;
	private static JournalMode journalMode;

	public SqliteConfigurationChecks(SynchronousSetting ss, JournalMode jm) {
		synchronousSetting = ss;
		journalMode = jm;
	}

	public void Run(int numberOfThreads) {

		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < numberOfThreads; ++i) {
			threads.add(new Thread(new SqlTask(new Person(UUID.randomUUID(), "test"))));
		}

		for (Thread t : threads) {
			t.start();
		}

		for (int i = 0; i < numberOfThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Succeded requests : " + succededRequests);
	}

	private class SqlTask implements Runnable {
		private Person personToBeAdded;

		public SqlTask(Person person) {
			personToBeAdded = person;
		}

		public void run() {
			Database database = new Database("org.sqlite.JDBC", "jdbc:sqlite:src\\resources\\sample.db", "", "");
			DatabaseConnection databaseConnection = new DatabaseConnection(database);
			PersonOperations personOperations = new PersonOperations(databaseConnection);

			long startTime = System.currentTimeMillis();

			if (personOperations.addPerson(personToBeAdded, synchronousSetting, journalMode)) {
				synchronized (this) {
					succededRequests++;
				}
			}

			long duration = System.currentTimeMillis() - startTime;
			System.out.println("\tSQL Insert completed: " + duration);
		}
	}
}
