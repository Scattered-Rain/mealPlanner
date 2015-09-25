package meal.planner.gui.panels;

import static lombok.AccessLevel.PRIVATE;
import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.table.WebTable;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import meal.planner.Main;
import meal.planner.dataBase.DataBase;
import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;
import meal.planner.external.JSuggestField;
import net.miginfocom.swing.MigLayout;

/**
 * A panel for showing and editting all information for a meal.
 * 
 * @author pieter
 *
 */
@FieldDefaults(level = PRIVATE)
public class MealPanel
		extends WebPanel
		implements DataPanel<Meal> {
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	JTextField txtName;
	JSuggestField<Meal> recipeAddField;
	long id = -1;
	WebTable recipeTable;
	WebTable ingredientTable;

	@Getter
	ArrayList<Recipe> recipes;
	private JTextArea txtDescription;

	private MealPanel() {
		recipes = new ArrayList<>();
		setLayout(new MigLayout("", "[][grow][][]", "[][grow][][grow][][]"));

		WebLabel lblName = new WebLabel("Name:");
		add(lblName, "cell 0 0,alignx trailing");

		txtName = new JTextField();
		txtName.setText("name");
		add(txtName, "cell 1 0 3 1,growx");
		txtName.setColumns(10);

		WebLabel lblDescription = new WebLabel("Description:");
		add(lblDescription, "cell 0 1");

		txtDescription = new JTextArea();
		add(txtDescription, "cell 1 1 3 1,grow");

		WebLabel lblAttendees = new WebLabel("Attendees:");
		add(lblAttendees, "cell 0 2");

		WebSpinner spinner = new WebSpinner();
		add(spinner, "cell 1 2");

		ingredientTable = new WebTable();
		DefaultTableModel ingDtm = new DefaultTableModel();
		ingredientTable.setModel(ingDtm);
		ingDtm.setColumnIdentifiers(new String[] { "Ingredient", "amount" });

		WebLabel lblPrice = new WebLabel("Price:");
		add(lblPrice, "cell 2 2");

		WebLabel lblValprice = new WebLabel("");
		add(lblValprice, "cell 3 2");

		spinner.addChangeListener(e -> {
			// Update the price
			updatePrice(spinner, lblValprice);
		});

		recipeTable = new WebTable();
		DefaultTableModel dtm = ingDtm;
		dtm.setColumnIdentifiers(new String[] { "ID", "Name" });
		recipeTable.setModel(dtm);
		recipeTable.setEditable(false);
		recipeTable.setColumnSelectionAllowed(false);
		recipeTable.setRowSelectionAllowed(true);

		WebCollapsiblePane recipeCPane = new WebCollapsiblePane("Recipes", recipeTable);
		add(recipeCPane, "cell 0 3 3 1,growx");

		JButton btnAdd = new JButton("Add", new ImageIcon(ICON_PLUS));
		add(btnAdd, "cell 0 4");

		recipeAddField = new JSuggestField<Meal>();
		ArrayList<Recipe> recipes = Main.getDb()
										.getAllRecs();
		ArrayList<String> rNames = new ArrayList<>();
		for (Recipe r : recipes) {
			rNames.add(r.getName());
		}
		recipeAddField.setSuggestData(rNames);

		add(recipeAddField, "cell 1 4,growx");
		recipeAddField.setColumns(10);

		JButton btnRemove = new JButton("Remove", new ImageIcon(ICON_MIN));
		add(btnRemove, "cell 3 4");

		btnRemove.addActionListener(e -> {
			int selRow = recipeTable.getSelectedRow();

			if (selRow < 0)
				return;

			String rName = (String) dtm.getValueAt(selRow, 1);

			// Remove from recipes list
			for (int i = 0; i < recipes.size(); i++) {
				if (recipes	.get(i)
							.getName()
							.equals(rName)) {
					recipes.remove(i);
					updatePrice(spinner, lblValprice);
					refreshShoppingList();
					break;
				}
			}

			dtm.removeRow(selRow);
		});

		WebCollapsiblePane shoppingPane = new WebCollapsiblePane("Shopping List", ingredientTable);
		shoppingPane.setExpanded(false);
		add(shoppingPane, "cell 0 5 3 1,growx");

		/** Listeners */
		btnAdd.addActionListener(e -> {
			String text = recipeAddField.getText();

			// If numeric value only
			Recipe recipe;
			DataBase db = Main.getDb();
			if (text.matches("^-?\\d+$")) {
				// Do a lookup by ID
				recipe = db.getRecipe(Integer.parseInt(text));

			} else {
				ArrayList<Recipe> results = db.findRecipes(text, -1, -1, null);
				if (results.size() > 0)
					recipe = results.get(0);
				else
					recipe = null;
			}

			if (recipe != null) {
				addRecipe(recipe);
				updatePrice(spinner, lblValprice);
				refreshShoppingList();
			}
		});

	}

	private void updatePrice(WebSpinner spinner, WebLabel lblValprice) {
		lblValprice.setText("$" + calcTotalPrice() * ((Integer) spinner.getValue()).doubleValue());
	}

	public MealPanel(Meal m) {
		this();
		load(m);
	}

	@Override
	public Meal save() {
		DataBase db = Main.getDb();
		Meal meal = db.getMeal(id);
		if (meal == null) {
			meal = db.newMeal();
			id = meal.getId();

			meal.setDescription(txtDescription.getText());
			meal.setName(txtName.getText());

		}
		return meal;
	}

	public void load(Meal m) {
		txtName.setText(m.getName());
		recipeAddField.setText(m.getDescription());
		id = m.getId();

		ArrayList<Recipe> recipes = m.getSubItems();
		for (Recipe r : recipes) {
			DefaultTableModel dtm = (DefaultTableModel) recipeTable.getModel();
			dtm.addRow(new Object[] { r.getName(), r.getId() });
		}
	}

	private void addRecipe(Recipe recipe) {

		assert recipe != null : "Recipe was null";

		recipes.add(recipe);
		DefaultTableModel tModel = (DefaultTableModel) recipeTable.getModel();
		tModel.addRow(new Object[] { recipe.getId(), recipe.getName() });

	}

	private double calcTotalPrice() {
		double price = 0;
		for (Recipe r : recipes) {
			price += r.getPrice();
		}

		return price;
	}

	private void refreshShoppingList() {
		HashMap<Long, Integer> ingIDAmountMap = new HashMap<>();
		for (Recipe r : recipes) {
			HashMap<Ingredient, Double> ings = r.getHashIngs();
			for (Ingredient i : ings.keySet()) {
				int amount = ingIDAmountMap.get(i.getId());
				amount += ings.get(i);

				ingIDAmountMap.put(i.getId(), amount);

			}
		}

		DefaultTableModel dtm = (DefaultTableModel) ingredientTable.getModel();
		dtm.setRowCount(0);
		DataBase db = Main.getDb();
		for (long ingID : ingIDAmountMap.keySet()) {
			Ingredient i = db.getIngredient(ingID);
			dtm.addRow(new Object[] { i.getName(), ingIDAmountMap.get(ingID) });
		}
	}

	public void refreshSuggestions() {
		ArrayList<Recipe> recipes = Main.getDb()
										.getAllRecs();
		ArrayList<String> rNames = new ArrayList<>();
		for (Recipe r : recipes) {
			rNames.add(r.getName());
		}
		recipeAddField.setSuggestData(rNames);
	}
}
