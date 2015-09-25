package meal.planner.gui.panels;

import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.table.WebTable;

import meal.planner.Main;
import meal.planner.dataBase.DataBase;
import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Recipe;
import meal.planner.external.JSuggestField;
import net.miginfocom.swing.MigLayout;

/**
 * A panel for either defining a new recipe or importing an existing recipe
 * 
 * @author pieter
 *
 */
public class RecipePanel
		extends WebPanel
		implements DataPanel<Recipe> {
	/**
	 * Default serial
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	private long id = -1;

	private HashMap<Ingredient, Double> ingredients;
	WebTable ingredientTable;
	JTextArea txtDesc;

	public RecipePanel() {

		ingredients = new HashMap<>();
		setLayout(new MigLayout("", "[][grow]", "[][grow,fill][grow][][]"));

		txtDesc = new JTextArea();
		WebScrollPane sPane = new WebScrollPane(txtDesc);
		add(sPane, "cell 1 2, wmin 300, hmin 200");
		add(new WebLabel("Description"), "cell 0 2");
		JLabel lblName = new JLabel("Name:");
		add(lblName, "cell 0 1,alignx trailing");

		textField = new JTextField();
		add(textField, "cell 1 1,growx");
		textField.setColumns(10);

		JLabel lblIngredients = new JLabel("Ingredients:");
		add(lblIngredients, "cell 0 3");

		ingredientTable = new WebTable();
		ingredientTable.setModel(new DefaultTableModel());
		DefaultTableModel dtm = (DefaultTableModel) ingredientTable.getModel();
		dtm.setColumnIdentifiers(new Object[] { "Ingredient Name", "Amount" });
		JScrollPane scrollPane = new JScrollPane(ingredientTable);
		add(scrollPane, "cell 1 3,grow,wmin 300, hmin 400");

		WebButton button = new WebButton(new ImageIcon(ICON_PLUS));
		add(button, "flowx,cell 1 4");

		JSuggestField ingredient = new JSuggestField<String>();
		ingredient.setColumns(10);

		ArrayList<String> suggestions = new ArrayList<>();
		ArrayList<Ingredient> ings = Main	.getDb()
											.getAllIngsSortedByName();
		for (Ingredient i : ings) {
			suggestions.add(i.getName());
		}

		ingredient.setSuggestData(suggestions);

		add(ingredient, "flowx,cell 1 4");

		WebLabel lblAmnt = new WebLabel("Amount");
		add(lblAmnt, "cell 1 4");

		WebSpinner spnrAmnt = new WebSpinner();
		spnrAmnt.setModel(new SpinnerNumberModel(0.0, 0.0, 999999, 1.0));

		add(spnrAmnt, "cell 1 4");

		button.addActionListener(e -> {
			String text = ingredient.getText();
			if (suggestions.contains(text)) {
				ArrayList<Ingredient> result = Main	.getDb()
													.findIngredients(text, -1, -1, null);

				if (result.size() > 0) {
					Ingredient i = result.get(0);
					ingredients.put(i, (Double) spnrAmnt.getValue());
					dtm.addRow(new Object[] { i.getName(), spnrAmnt.getValue() });
				}

			}
		});

		WebButton btnRemove = new WebButton(new ImageIcon(ICON_MIN));
		add(btnRemove, "cell 1 3");
		btnRemove.addActionListener(e -> {
			int selRow = ingredientTable.getSelectedRow();
			if (selRow < 0)
				return;

			String name = (String) dtm.getValueAt(selRow, 0);
			Double amount = (Double) dtm.getValueAt(selRow, 1);
			Ingredient toRemove = null;
			for (Ingredient i : ingredients.keySet()) {
				if (i	.getName()
						.equals(name)) {
					if (ingredients.get(i) == amount)
						toRemove = i;
				}
			}

			ingredients.remove(toRemove);
			dtm.removeRow(selRow);
		});
	}

	@Override
	public Recipe save() {
		DataBase db = Main.getDb();

		Recipe r;
		if (id > -1) {
			r = db.getRecipe(id);
			if (r == null)
				r = db.newRecipe();
		} else {
			r = db.newRecipe();
		}

		r.setName(textField.getText());
		r.setDescription(txtDesc.getText());
		for (Ingredient i : ingredients.keySet())
			r.addIngredient(i, ingredients.get(i));

		return r;
	}

}
