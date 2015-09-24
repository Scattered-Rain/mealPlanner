package meal.planner.gui.panels;

import static meal.planner.GlobalConstants.ICON_IMPORT;
import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

import net.miginfocom.swing.MigLayout;

/**
 * A panel for either defining a new recipe or importing an existing recipe
 * 
 * @author pieter
 *
 */
public class RecipePanel
		extends WebPanel {
	/**
	 * Default serial
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public RecipePanel() {
		setLayout(new MigLayout("", "[][grow]", "[][][grow][][]"));

		WebButton btnImport = new WebButton("Import", new ImageIcon(ICON_IMPORT));
		add(btnImport, "cell 0 0");

		JLabel lblName = new JLabel("Name:");
		add(lblName, "cell 0 1,alignx trailing");

		textField = new JTextField();
		add(textField, "cell 1 1,growx");
		textField.setColumns(10);

		JLabel lblIngredients = new JLabel("Ingredients:");
		add(lblIngredients, "cell 0 2");

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, "cell 1 2,grow");

		WebButton button = new WebButton(new ImageIcon(ICON_PLUS));
		add(button, "flowx,cell 1 3");

		WebButton btnCancel = new WebButton("Cancel");
		btnCancel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(btnCancel, "flowx,cell 1 4,alignx right");

		WebButton button_1 = new WebButton(new ImageIcon(ICON_MIN));
		add(button_1, "cell 1 3");

		WebButton btnAdd = new WebButton("Add");
		btnAdd.setHorizontalAlignment(SwingConstants.RIGHT);
		add(btnAdd, "cell 1 4,alignx right");
	}

}
