package meal.planner.gui.panels;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.itextpdf.text.DocumentException;

import meal.planner.Main;
import meal.planner.dataBase.PdfDataWriter;
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

			Meal newMeal = Main.getDb()
													.newMeal();
			newMeal.setName("New Meal");
			MealPanel mealPanel = new MealPanel(newMeal);
			WebScrollPane sPane = new WebScrollPane(mealPanel);
			tabbedPane.add("New Meal", sPane);
			panelMapping.put(sPane, mealPanel);
		} else {
			MealPanel mealPanel = new MealPanel(meal);
			WebScrollPane sPane = new WebScrollPane(mealPanel);
			tabbedPane.add(meal.getName(), sPane);
			panelMapping.put(sPane, mealPanel);
		}


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
		File loc = WebFileChooser.showSaveDialog();
		PdfDataWriter writer = new PdfDataWriter();
		 Meal meal = ((MealPanel)panelMapping.get(tabbedPane.getSelectedComponent())).save();
		 try {
			writer.writePdf(meal, loc);
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
		// TODO: Export shopping list
	}

}
