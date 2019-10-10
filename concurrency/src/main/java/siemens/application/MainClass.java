package siemens.application;

public class MainClass {

	public static void main(String[] args) {

		int numberOfThreads = 10000;
		
		SqliteConfigurationChecks app = new SqliteConfigurationChecks();
		app.Run(numberOfThreads);	
	}
}
