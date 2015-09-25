package meal.planner;

import com.google.gson.Gson;

/** A class containing all constants used throughout the game */
public class GlobalConstants {
	// @formatter:off
	
	/** Directory paths */
	public static final String 
		DIR_ICONS = "src/main/resources/assets/icons/", 
		DIR_DB = System.getenv("APPDATA") + "/mealPlanner/";
	
	public static final String
		FILE_DB = DIR_DB + "db.json";
	/** Icon Assets */
	public static final String 
		ICON_EXPORT_PDF = DIR_ICONS + "document-pdf-text.png",
		ICON_OPEN = DIR_ICONS + "folder-open.png",
		ICON_NEW_RECIPE = DIR_ICONS + "plate-cutlery.png",
		ICON_MIN = DIR_ICONS + "minus-button.png",
		ICON_NEW = DIR_ICONS + "document--plus.png",
		ICON_PLUS = DIR_ICONS + "plus-button.png",
		ICON_RECIPE = DIR_ICONS + "hamburger.png",
		ICON_SAVE = DIR_ICONS + "disk.png",
		ICON_IMPORT = DIR_ICONS + "document-import.png",
		ICON_SAVE_ALL =DIR_ICONS + "disks.png";
	
	//@formatter:on

	/** Global serializer instance used throughout the program */
	public static Gson SERIALIZER = new Gson();
}
