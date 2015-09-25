package meal.planner.gui.panels;

import static lombok.AccessLevel.PRIVATE;
import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.table.WebTable;

import lombok.experimental.FieldDefaults;
import meal.planner.Main;
import meal.planner.dataBase.DataBase;
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

	ArrayList<Recipe> recipes;
	private JTextArea txtDescription;

	public MealPanel() {
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

		WebLabel lblPrice = new WebLabel("Price");
		add(lblPrice, "cell 2 2");

		WebLabel lblValprice = new WebLabel("valPrice");
		add(lblValprice, "cell 3 2");

		recipeTable = new WebTable(new String[0][0], new String[] { "ID", "Name" });
		recipeTable.setEditable(false);
		recipeTable.setColumnSelectionAllowed(false);
		recipeTable.setRowSelectionAllowed(true);

		WebCollapsiblePane recipeCPane = new WebCollapsiblePane("Recipes", recipeTable);
		add(recipeCPane, "cell 0 3 3 1,growx");

		JButton btnAdd = new JButton("Add", new ImageIcon(ICON_PLUS));
		add(btnAdd, "cell 0 4");

		recipeAddField = new JSuggestField<Meal>();
		// TODO: Populate suggestions

		add(recipeAddField, "cell 1 4,growx");
		recipeAddField.setColumns(10);

		JButton btnRemove = new JButton("Remove", new ImageIcon(ICON_MIN));
		add(btnRemove, "cell 3 4");

		WebCollapsiblePane shoppingPane = new WebCollapsiblePane("Shopping List", new JLabel("Lorem ipsum BLABLABLA"));
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
				// TODO: Retrieve recipe by name
				recipe = null;
			}

			if (recipe != null) {
				addRecipe(recipe);
			}
		});

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
			// TODO: Implement saving to database
			meal = db.newMeal();

			meal.setDescription(txtDescription.getText());
			meal.setName(txtName.getText()); // TODO: Check on valid input

		}
		return meal;
	}

	public void load(Meal m) {
		txtName.setText(m.getName());
		recipeAddField.setText(m.getDescription());
		id = m.getId();

		// TODO: Load in recipes
	}

	private void addRecipe(Recipe recipe) {

		assert recipe != null : "Recipe was null";

		recipes.add(recipe);
		DefaultTableModel tModel = (DefaultTableModel) recipeTable.getModel();
		tModel.addRow(new Object[] { recipe.getId(), recipe.getName() });

	}
}
