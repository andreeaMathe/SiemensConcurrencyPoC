package siemens.application;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import siemens.database.Database;
import siemens.database.DatabaseConnection;
import siemens.model.Person;
import siemens.util.PersonOperations;

public class MainClass {
	
//	static int succededRequests = 0;
	
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
		
//		System.out.println("Number of requests completed : " + succededRequests);
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

			personOperations.addPerson(personToBeAdded, false);

			long duration = System.currentTimeMillis() - startTime;
			System.out.println("   SQL Insert completed: " + duration);
//			succededRequests++;
		}
	}

//	public void readPeople(int numberOfThreads) {
//		List<Person> people = new ArrayList<>();
//		ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
//		CountDownLatch latch = new CountDownLatch(1);
//		AtomicBoolean running = new AtomicBoolean();
//		AtomicInteger overlaps = new AtomicInteger();
//		Collection<Future<Integer>> futures = new ArrayList<>(numberOfThreads);
//		for (int t = 0; t < numberOfThreads; ++t) {
//			final String title = String.format("Book #%d", t);
//			futures.add(service.submit(() -> {
//				latch.await();
//				if (running.get()) {
//					overlaps.incrementAndGet();
//				}
//				running.set(true);
//				int id = books.add(title);
//				running.set(false);
//				return id;
//			}));
//		}
//		latch.countDown();
//		Set<Integer> ids = new HashSet<>();
//		for (Future<Integer> f : futures) {
//			ids.add(f.get());
//		}
//		assertThat(overlaps.get(), greaterThan(0));
//
//		Person books = new Person();
//		MatcherAssert.assertThat(t -> {
//			String title = String.format("Book #%d", t.getAndIncrement());
//			int id = books.add(title);
//			return books.title(id).equals(title);
//		}, new RunsInThreads<>(new AtomicInteger(), 10));
//	}
}
