package meal.planner.gui.panels;

import static meal.planner.GlobalConstants.ICON_IMPORT;
import static meal.planner.GlobalConstants.ICON_MIN;
import static meal.planner.GlobalConstants.ICON_PLUS;
import static meal.planner.GlobalConstants.SERIALIZER;

import java.io.File;
import java.io.FileReader;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.alee.laf.button.WebButton;
import com.alee.laf.filechooser.WebFileChooser;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;

import meal.planner.Main;
import meal.planner.dataBase.DataBase;
import meal.planner.dataBase.items.Recipe;
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

	public RecipePanel() {
		setLayout(new MigLayout("", "[][grow]", "[][][grow][][]"));

		WebButton btnImport = new WebButton("Import", new ImageIcon(ICON_IMPORT));
		add(btnImport, "cell 0 0");

		btnImport.addActionListener(e -> {
			File f = WebFileChooser.showOpenDialog();
			if (f.exists() && f	.getName()
								.endsWith(".recipe") && f.isFile()) {
				try {
					SERIALIZER.fromJson(new FileReader(f), Recipe.class);
				} catch (Exception e1) {
					WebOptionPane.showMessageDialog(this, "Unable to import file.", "Error", WebOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}

		});

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

		WebButton button_1 = new WebButton(new ImageIcon(ICON_MIN));
		add(button_1, "cell 1 3");
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

		// TODO: Populate the recipe from the fields

		return r;
	}

}
