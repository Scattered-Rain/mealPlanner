package meal.planner.gui.panels;

import java.util.HashMap;

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
	private HashMap<WebScrollPane, DataPanel> panelMapping = new HashMap<>();
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

		if (meal == null) {
			MealPanel mealPanel = new MealPanel();
			WebScrollPane sPane = new WebScrollPane(mealPanel);
			tabbedPane.add("New Meal", sPane);
			panelMapping.put(sPane, mealPanel);
		} else {
			MealPanel mealPanel = new MealPanel(meal);
			WebScrollPane sPane = new WebScrollPane(mealPanel);
			tabbedPane.add(meal.getName(), sPane);
			panelMapping.put(sPane, mealPanel);
		}
		// Add extra functionality
		tabbedPane.getTabComponentAt(tabbedPane.getTabCount() - 1);

		if (select)
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);

	}

	public void save(boolean all) {
		int selIndex = tabbedPane.getSelectedIndex();
		WebScrollPane sPane = (WebScrollPane) tabbedPane.getComponent(selIndex);
		DataPanel p = panelMapping.get(sPane);
		p.save();
	}

	public void exportPDF() {
		throw new UnsupportedOperationException();
		// TODO: Implement this
	}

}
