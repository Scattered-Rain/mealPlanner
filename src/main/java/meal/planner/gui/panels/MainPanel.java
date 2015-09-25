package meal.planner.gui.panels;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;

import meal.planner.dataBase.items.Meal;

/**
 * The main panel designed to hold all sub panels (in tabs)
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
		addTab(null, true);
		add(tabbedPane);
	}

	/**
	 * Adds a new Meal tab to the mainpanel.
	 * 
	 * @param select
	 *            Whether the new tab should be selected or not.
	 */
	public void addTab(Meal meal, boolean select) {

		if (meal == null)
			tabbedPane.add("New Meal", new WebScrollPane(new MealPanel()));
		else {
			// TODO: Check if name is not empty and/or null
			tabbedPane.add(meal.getName(), new WebScrollPane(new MealPanel()));
		}
		// Add extra functionality
		tabbedPane.getTabComponentAt(tabbedPane.getTabCount() - 1);

		if (select)
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

	}

	public void save(boolean all) {
		throw new UnsupportedOperationException();
		// TODO: Implement this
	}

	public void exportPDF() {
		throw new UnsupportedOperationException();
		// TODO: Implement this
	}

}
