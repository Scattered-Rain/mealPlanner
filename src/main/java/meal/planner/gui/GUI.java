package meal.planner.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.alee.laf.WebLookAndFeel;
import com.alee.laf.menu.MenuBarStyle;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
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

		WebMenuBar menuBar = new WebMenuBar(MenuBarStyle.attached);
		// menuBar.add(toolBar);
		mainFrame.setJMenuBar(menuBar);

		WebMenu menu = new WebMenu("File");
		menuBar.add(menu);

		mainFrame.revalidate();
		// TODO: populate menu
	}
}
