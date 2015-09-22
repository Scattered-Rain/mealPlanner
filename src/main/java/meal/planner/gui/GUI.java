package meal.planner.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.rootpane.WebFrame;

public class GUI {

	WebFrame mainFrame;

	/** Instantiates the GUI */
	public GUI() {
		initialize();

	}

	public void initialize() {
		mainFrame = new WebFrame();

		// Set to the size of the screen
		Dimension d = Toolkit	.getDefaultToolkit()
								.getScreenSize();
		mainFrame.setSize(d);

		mainFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}

	public void setupMenu() {
		WebMenuBar menuBar = new WebMenuBar();
		mainFrame.setJMenuBar(menuBar);

		WebMenu menu = new WebMenu("File");
		menuBar.add(menu);

		// TODO: populate menu
	}
}