package meal.planner.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.alee.laf.rootpane.WebFrame;

public class GUI {

	WebFrame mainFrame;

	/** Instantiates the GUI */
	public GUI() {
		mainFrame = new WebFrame();

		// Set to the size of the screen
		Dimension d = Toolkit	.getDefaultToolkit()
								.getScreenSize();
		mainFrame.setSize(d);

		mainFrame.setDefaultCloseOperation(WebFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);

	}
}
