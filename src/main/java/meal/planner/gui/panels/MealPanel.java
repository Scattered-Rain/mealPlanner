package meal.planner.gui.panels;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.spinner.WebSpinner;

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

	public MealPanel() {
		setLayout(new MigLayout("", "[][grow][][]", "[][grow][][grow]"));

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

		// TODO: Finish up content
		// WebScrollPane scrollPane = new WebScrollPane();
		// add(scrollPane, "cell 0 3 4 1,grow");
	}

	public void save() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	// TODO: Implementation
}
