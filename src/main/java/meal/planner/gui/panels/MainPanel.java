package meal.planner.gui.panels;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.itextpdf.text.DocumentException;

import meal.planner.Main;
import meal.planner.dataBase.PdfDataWriter;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

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

			Meal newMeal = Main	.getDb()
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
		JPanel panel = new JPanel();
		panel.add(new JLabel("Content:"));
		JComboBox box = new JComboBox<>(new Object[] { "Shopping List", "Meal" });
		panel.add(box);
		int result = WebOptionPane.showConfirmDialog(null, panel, "Export to PDF...", WebOptionPane.OK_CANCEL_OPTION);
		if (result == WebOptionPane.OK_OPTION) {
			File loc = WebFileChooser.showSaveDialog();

			if (loc == null)
				return;

			PdfDataWriter writer = new PdfDataWriter();
			MealPanel dataPanel = (MealPanel) panelMapping.get(tabbedPane.getSelectedComponent());

			if (box.getSelectedIndex() == 0) {
				ArrayList<Recipe> recipes = dataPanel.getRecipes();
				HashMap<Long, Double> ingIDAmountMap = new HashMap<>();
				// for (Recipe r : recipes) {
				// HashMap<Ingredient, Double> ings = r.get
				// for (Ingredient i : ings.keySet()) {
				// double amount = ingIDAmountMap.get(i.getId());
				// amount += ings.get(i);
				//
				// ingIDAmountMap.put(i.getId(), amount);
				//
				// }
				// }

				// writer.writePdf(dataPanel.ge, file);
			} else {

				Meal meal = (dataPanel).save();
				try {
					writer.writePdf(meal, loc);
				} catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
