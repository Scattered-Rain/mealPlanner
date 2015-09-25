package meal.planner.gui;

import static meal.planner.GlobalConstants.FILE_DB;
import static meal.planner.GlobalConstants.ICON_EXPORT_PDF;
import static meal.planner.GlobalConstants.ICON_INGREDIENT;
import static meal.planner.GlobalConstants.ICON_NEW;
import static meal.planner.GlobalConstants.ICON_NEW_RECIPE;
import static meal.planner.GlobalConstants.ICON_OPEN;
import static meal.planner.GlobalConstants.ICON_SAVE;
import static meal.planner.GlobalConstants.SERIALIZER;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;

import meal.planner.GlobalConstants;
import meal.planner.Main;
import meal.planner.dataBase.DataBase;
import meal.planner.dataBase.items.Meal;
import meal.planner.external.JSuggestField;
import meal.planner.gui.panels.IngredientPanel;
import meal.planner.gui.panels.MainPanel;
import meal.planner.gui.panels.RecipePanel;

public class GUI {

	WebFrame mainFrame;
	final MainPanel mainPanel = new MainPanel();

	/** Instantiates the GUI */
	public GUI() {
		initialize();

	}

	private void initialize() {
		// Install the look and feel
		WebLookAndFeel.install();

		// Setup the main things
		mainFrame = new WebFrame("MealOMatic V1.0");
		mainFrame.setContentPane(mainPanel);

		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);

				// Serialize and save the database
				DataBase db = Main.getDb();

				String json = SERIALIZER.toJson(db);
				try {
					File f = new File(FILE_DB);
					File dir = new File(GlobalConstants.DIR_DB);
					if (!dir.exists())
						dir.mkdirs();

					if (!f.exists()) {
						f.createNewFile();
					}
					FileWriter writer = new FileWriter(f);
					writer.write(json);
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Set to the size of the screen
		Dimension d = Toolkit	.getDefaultToolkit()
								.getScreenSize();

		mainFrame.setSize(d.width / 2, d.height / 2);
		setupMenu();
		mainFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}

	private void setupMenu() {

		// Setup the toolbar
		WebToolBar fileToolBar = new WebToolBar();
		fileToolBar.setFloatable(false);

		WebToolBar recipeToolbar = new WebToolBar();
		recipeToolbar.setFloatable(false);

		// Initialize and setup components

		//@formatter:off
		WebButton openButton = new WebButton(new ImageIcon(ICON_OPEN)),
				newButton = new WebButton(new ImageIcon(ICON_NEW)),
				newRecButton = new WebButton(new ImageIcon(ICON_NEW_RECIPE)),
				saveButton = new WebButton(new ImageIcon(ICON_SAVE)),
				ingredientButton = new WebButton(new ImageIcon(ICON_INGREDIENT)),
				exportPDFButton = new WebButton(new ImageIcon(ICON_EXPORT_PDF));
		//@formatter:on

		newRecButton.setToolTipText("Add Recipe...");
		openButton.setToolTipText("Open Meal...");
		saveButton.setToolTipText("Save As...");
		exportPDFButton.setToolTipText("Export As PDF...");
		ingredientButton.setToolTipText("Add new ingredient to database...");

		newButton.addActionListener(e -> mainPanel.addTab(null, true));

		openButton.addActionListener(e -> {
			DataBase db = Main.getDb();
			ArrayList<Meal> meals = db.getAllMeals();
			ArrayList<String> suggestions = new ArrayList<>();

			for (Meal m : meals)
				suggestions.add(m.getName());
			
			JSuggestField<String> field = new JSuggestField<>();
			
			WebPanel panel = new WebPanel();
			panel.add(new WebLabel("Meal Name"));
			field.setSuggestData(suggestions);
			panel.add(field);
			
			int result = WebOptionPane.showConfirmDialog(mainFrame, panel, "Open Meal", WebOptionPane.OK_CANCEL_OPTION);
			
			if(result == WebOptionPane.OK_OPTION){
				if(field.getSuggestData().contains(field.getText())){
					ArrayList<Meal> r = db.findMeals(field.getText(), -1, -1, null);
					if(r.size() >0){
						mainPanel.addTab(r.get(0), true);
					}
						
				}
					
			}

		});

		ingredientButton.addActionListener(e -> {
			IngredientPanel panel = new IngredientPanel();
			int result = WebOptionPane.showConfirmDialog(mainFrame, panel, "Title", WebOptionPane.OK_CANCEL_OPTION);
			if (result == WebOptionPane.OK_OPTION) {
				panel.save();
			}
		});

		newRecButton.addActionListener(e -> {

			RecipePanel rPanel = new RecipePanel();
			int result = WebOptionPane.showConfirmDialog(mainFrame, rPanel, "Title", WebOptionPane.OK_CANCEL_OPTION);
			if (result == WebOptionPane.OK_OPTION) {
				rPanel.save();
			}
		});

		saveButton.addActionListener(e -> mainPanel.save(false));
		exportPDFButton.addActionListener(e -> mainPanel.exportPDF());

		// Add Components to the toolbar(s)
		fileToolBar.add(newButton);
		fileToolBar.add(openButton);
		fileToolBar.add(saveButton);
		fileToolBar.add(exportPDFButton);

		recipeToolbar.add(newRecButton);
		recipeToolbar.add(ingredientButton);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileToolBar);
		menuBar.add(recipeToolbar);

		mainFrame.setJMenuBar(menuBar);
		mainFrame.revalidate();
	}
}
