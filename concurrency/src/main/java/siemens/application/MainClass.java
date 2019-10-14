package siemens.application;

import siemens.util.JournalMode;
import siemens.util.SynchronousSetting;

public class MainClass {

	public static void main(String[] args) {

		int numberOfThreads = 10000;
		
		SqliteConfigurationChecks app = new SqliteConfigurationChecks(SynchronousSetting.Off, JournalMode.None);
		app.Run(numberOfThreads);	
	}
}
