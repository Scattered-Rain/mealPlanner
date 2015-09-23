package meal.planner.gui.panels;

import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.alee.extended.panel.WebCollapsiblePane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;
import com.alee.laf.table.WebTable;

import meal.planner.Main;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;
import net.miginfocom.swing.MigLayout;

/**
 * A panel for showing and editting all information for a meal.
 * TODO: Elaborate comments
 * 
 * @author pieter
 *
 */
public class MealPanel
		extends WebPanel
		implements SerializablePanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField recipeAddField;
	private int id;

	public MealPanel() {
		setLayout(new MigLayout("", "[][grow][][]", "[][grow][][grow][][]"));

		WebLabel lblName = new WebLabel("Name:");
		add(lblName, "cell 0 0,alignx trailing");

		txtName = new JTextField();
		txtName.setText("name");
		add(txtName, "cell 1 0 3 1,growx");
		txtName.setColumns(10);

		WebLabel lblDescription = new WebLabel("Description:");
		add(lblDescription, "cell 0 1");

		JTextArea txtrValdesc = new JTextArea();
		txtrValdesc.setText("valDesc");
		add(txtrValdesc, "cell 1 1 3 1,grow");

		WebLabel lblAttendees = new WebLabel("Attendees:");
		add(lblAttendees, "cell 0 2");

		WebSpinner spinner = new WebSpinner();
		add(spinner, "cell 1 2");

		WebLabel lblPrice = new WebLabel("Price");
		add(lblPrice, "cell 2 2");

		WebLabel lblValprice = new WebLabel("valPrice");
		add(lblValprice, "cell 3 2");

		WebTable recipeTable = new WebTable(new String[0][0], new String[] { "ID", "Name" });
		recipeTable.setEditable(false);
		recipeTable.setColumnSelectionAllowed(false);
		recipeTable.setRowSelectionAllowed(true);

		WebCollapsiblePane recipeCPane = new WebCollapsiblePane("Recipes", recipeTable);
		add(recipeCPane, "cell 0 3 3 1,growx");

		JButton btnAdd = new JButton("Add", new ImageIcon(ICON_PLUS));
		add(btnAdd, "cell 0 4");

		recipeAddField = new JTextField();
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
			if (text.matches("^-?\\d+$")) {
				// Do a lookup by ID
				// TODO: Retrieve recipe from
				Main.getDb();

			} else {
				// TODO: Lookup by name
			}
		});

	}

	public MealPanel(Meal m) {
		this();
		load(m);
	}

	@Override
	public void save() {
		// TODO Implementation
		throw new UnsupportedOperationException();
	}

	public void load(Meal m) {
		txtName.setText(m.getName());
		recipeAddField.setText(m.getDescription());
		id = m.getId();

		// TODO:Load recipes
	}
}
