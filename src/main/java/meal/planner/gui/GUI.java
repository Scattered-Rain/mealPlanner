package meal.planner.gui;

import static meal.planner.GlobalConstants.ICON_EXPORT_PDF;
import static meal.planner.GlobalConstants.ICON_NEW;
import static meal.planner.GlobalConstants.ICON_NEW_RECIPE;
import static meal.planner.GlobalConstants.ICON_OPEN;
import static meal.planner.GlobalConstants.ICON_SAVE;
import static meal.planner.GlobalConstants.ICON_SAVE_ALL;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;

import meal.planner.GlobalConstants;
import meal.planner.dataBase.DataBase;
import meal.planner.dataBase.items.Meal;
import meal.planner.gui.panels.MainPanel;
import meal.planner.gui.panels.RecipePanel;

public class GUI {

	WebFrame mainFrame;
	final MainPanel mainPanel = new MainPanel();

	/** Instantiates the GUI */
	public GUI(DataBase db) {
		initialize();

	}

	private void initialize() {
		// Install the look and feel
		WebLookAndFeel.install();

		// Setup the main things
		mainFrame = new WebFrame("MealOMatic V1.0");
		mainFrame.setContentPane(mainPanel);

		// Set to the size of the screen
		Dimension d = Toolkit	.getDefaultToolkit()
								.getScreenSize();

		// TODO: Set back to fullscreen after finishing rest
		mainFrame.setSize(d.width / 2, d.height / 2);
		// TODO: Set the proper size (dealing with toolbars etc.)
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
				saveAllButton = new WebButton(new ImageIcon(ICON_SAVE_ALL)),
				exportPDFButton = new WebButton(new ImageIcon(ICON_EXPORT_PDF));
		//@formatter:on

		newRecButton.setToolTipText("Add Recipe...");
		openButton.setToolTipText("Open Meal...");
		saveButton.setToolTipText("Save As...");
		exportPDFButton.setToolTipText("Export As PDF...");

		// TODO: Button listeners
		newButton.addActionListener(e -> mainPanel.addTab(null, true));

		openButton.addActionListener(e -> {
			// TODO: Possible filter on what can be opened
			File f = WebFileChooser.showOpenDialog();


			if (f != null && f.isFile()) {
				if (f	.getName()
						.endsWith(".meal")) {
					// Opening a meal
					try {
						Meal meal = GlobalConstants.SERIALIZER.fromJson(new FileReader(f), Meal.class);
						mainPanel.addTab(meal, true);
					} catch (Exception e1) {
						WebOptionPane.showMessageDialog(mainFrame,
														"Unable to load meal, data appears corrupt",
														"Error Loading File",
														WebOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}

				}
				// TODO: Perhaps else do something with a recipe?
			}
		});

		newRecButton.addActionListener(e -> {

			RecipePanel rPanel = new RecipePanel();
			int result = WebOptionPane.showConfirmDialog(mainFrame, rPanel, "Title", WebOptionPane.OK_CANCEL_OPTION);
		});

		saveButton.addActionListener(e -> mainPanel.save(false));
		saveAllButton.addActionListener(e -> mainPanel.save(true));
		exportPDFButton.addActionListener(e -> mainPanel.exportPDF());



		// Add Components to the toolbar(s)
		fileToolBar.add(newButton);
		fileToolBar.add(openButton);
		fileToolBar.add(saveButton);
		fileToolBar.add(saveAllButton);
		fileToolBar.add(exportPDFButton);

		recipeToolbar.add(newRecButton);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileToolBar);
		menuBar.add(recipeToolbar);

		mainFrame.setJMenuBar(menuBar);
		mainFrame.revalidate();
		// TODO: populate menu
		// TODO: Why isn't the menu showing up? What did I forget?
	}
}
