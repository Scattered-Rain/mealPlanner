package meal.planner.gui;

import static meal.planner.GlobalConstants.ICON_EXPORT_PDF;
import static meal.planner.GlobalConstants.ICON_NEW;
import static meal.planner.GlobalConstants.ICON_OPEN;
import static meal.planner.GlobalConstants.ICON_SAVE;
import static meal.planner.GlobalConstants.ICON_SAVE_ALL;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;

import meal.planner.gui.panels.MainPanel;

public class GUI {

	WebFrame mainFrame;

	/** Instantiates the GUI */
	public GUI() {
		initialize();

	}

	private void initialize() {
		// Install the look and feel
		WebLookAndFeel.install();

		// Setup the main things
		MainPanel mainPanel = new MainPanel();
		mainFrame = new WebFrame("MealOMatic V1.0");
		mainFrame.setContentPane(mainPanel);

		// Set to the size of the screen
		Dimension d = Toolkit	.getDefaultToolkit()
								.getScreenSize();
		mainFrame.setSize(d);
		// TODO: Set the proper size (dealing with toolbars etc.)
		setupMenu();
		mainFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}

	private void setupMenu() {

		// Setup the toolbar
		WebToolBar fileToolBar = new WebToolBar();
		fileToolBar.setFloatable(false);

		// Initialize and setup components

		//@formatter:off
		WebButton openButton = new WebButton(new ImageIcon(ICON_OPEN)),
				newButton = new WebButton(new ImageIcon(ICON_NEW)),
				saveButton = new WebButton(new ImageIcon(ICON_SAVE)),
				saveAllButton = new WebButton(new ImageIcon(ICON_SAVE_ALL)),
				exportPDFButton = new WebButton(new ImageIcon(ICON_EXPORT_PDF));
		//@formatter:on

		// TODO: Button listeners

		// Add Components to the toolbar(s)
		fileToolBar.add(newButton);
		fileToolBar.add(openButton);
		fileToolBar.add(saveButton);
		fileToolBar.add(saveAllButton);
		fileToolBar.add(exportPDFButton);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileToolBar);

		mainFrame.setJMenuBar(menuBar);
		mainFrame.revalidate();
		// TODO: populate menu
		// TODO: Why isn't the menu showing up? What did I forget?
	}
}
