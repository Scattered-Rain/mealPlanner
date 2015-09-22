package meal.planner.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JMenuBar;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.rootpane.WebFrame;
import com.alee.laf.toolbar.WebToolBar;

import meal.planner.gui.panels.MainPanel;

public class GUI {

	WebFrame mainFrame;

	/** Instantiates the GUI */
	public GUI() {
		initialize();

	}

	public void initialize() {
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

		mainFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}

	public void setupMenu() {
		WebToolBar toolBar = new WebToolBar();

		JMenuBar menuBar = new JMenuBar();
		// menuBar.add(toolBar);

		WebMenu menu = new WebMenu("File");
		menuBar.add(menu);

		mainFrame.setJMenuBar(menuBar);
		mainFrame.revalidate();
		// TODO: populate menu
		// TODO: Why isn't the menu showing up? What did I forget?
	}
}
