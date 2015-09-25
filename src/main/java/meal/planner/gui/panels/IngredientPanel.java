package meal.planner.gui.panels;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;

import meal.planner.Main;
import meal.planner.dataBase.items.Ingredient;
import net.miginfocom.swing.MigLayout;

/** Used for adding ingredients */
public class IngredientPanel
		extends WebPanel
		implements DataPanel<Ingredient> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JSpinner spinner;

	public IngredientPanel() {
		setLayout(new MigLayout("", "[][grow]", "[][]"));

		JLabel lblName = new JLabel("Name");
		add(lblName, "cell 0 0,alignx trailing");

		textField = new JTextField();
		add(textField, "cell 1 0,growx");
		textField.setColumns(10);

		JLabel lblPrice = new JLabel("Price");
		add(lblPrice, "cell 0 1");

		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0.0, 0.0, 999999999, 1.0)); // Sorry, can't be bothered to do this
		// properly :( Deadline coming!
		add(spinner, "cell 1 1,growx");
	}

	@Override
	public Ingredient save() {

		Ingredient ing = Main	.getDb()
								.newIngredient();
		ing.setName(textField.getText());
		double price = (double) spinner.getValue();
		if (price < 0f) {

			WebOptionPane.showMessageDialog(null, "Price must be higher than 0!");
			return null;
		}

		ing.setPrice(price);
		return ing;
	}

}
