package meal.planner.gui.panels;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;

/**
 * The main panel designed to hold all sub panels (in tabs)
 * TODO: Elaborate comments
 * 
 * @author pieter
 *
 */
public class MainPanel
		extends WebPanel {

	private WebTabbedPane tabbedPane;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainPanel() {
		super();
		initialize();

	}

	private void initialize() {
		tabbedPane = new WebTabbedPane();
		tabbedPane.addTab("New Meal", new WebScrollPane(new MealPanel()));

		add(tabbedPane);
	}

}
