package meal.planner;

import static lombok.AccessLevel.PRIVATE;
import static meal.planner.GlobalConstants.DIR_DB;
import static meal.planner.GlobalConstants.FILE_DB;
import static meal.planner.GlobalConstants.SERIALIZER;

import java.io.File;
import java.io.FileReader;
import java.util.Date;

import com.alee.laf.optionpane.WebOptionPane;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meal.planner.dataBase.DataBase;
import meal.planner.gui.GUI;

@NoArgsConstructor(access = PRIVATE)
public class Main {
	@Getter
	private static DataBase db;


	public static void main(String[] args) {

		// Load the database
		File database = new File(FILE_DB);

		if (database.exists()) {
			try {
				db = SERIALIZER.fromJson(new FileReader(database), DataBase.class);
			} catch (Exception e) {
				Date date = new Date();

				// Copy the corrupted file
				File copy = new File(DIR_DB + "CORRUPTED DB - " + date.toString() + ".json");
				boolean succesfullCopy = database.renameTo(copy);

				System.err.println("Unable to load database, database appears to be corrupt. Creating a new empty database instead.");

				if (succesfullCopy)
					WebOptionPane.showMessageDialog(null,
													String.format(	"Could not load database. Format appears to be corrupted.\n Created a backupfile of database at: %s",
																	copy.getAbsolutePath()),
													"Database Corruption Detected!",
													WebOptionPane.ERROR_MESSAGE);
				else {
					int answer = WebOptionPane.showConfirmDialog(	null,
																	"Could not load database. Format appears to be corrupted.\n Could not create a backup of the database. Overwrite database?",
																	"Database Corruption Detected!",
																	WebOptionPane.ERROR_MESSAGE);
					if (answer == WebOptionPane.NO_OPTION)
						System.exit(1);// Exit with error, unhandled corrupt database
				}
				db = new DataBase();
			}
		} else {
			// No database available, creating a new empty database.
			db = new DataBase();
		}
		new GUI(db);
	}
}
