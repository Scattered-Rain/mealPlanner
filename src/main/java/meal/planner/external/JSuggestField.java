package meal.planner.external;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import lombok.Setter;
import lombok.val;

/**
 * Provides a text-field that makes suggestions using a provided data-vector.
 * You might have seen this on Google (tm), this is the Java implementation.
 * 
 * @author David von Ah
 * @version 0.5
 */
@SuppressWarnings("serial")
public class JSuggestField<T>
		extends JTextField {

	protected JDialog d;
	protected Point location;
	@SuppressWarnings("rawtypes")
	protected JList list;

	/**
	 * Vectors containing the original data and the filtered data for the
	 * suggestions.
	 */
	protected ArrayList<String> data, suggestions;

	protected HashMap<String, T> mapping;

	/**
	 * Separate matcher-thread, prevents the text-field from hanging while the
	 * suggestions are beeing prepared.
	 */
	protected InterruptableMatcher matcher;

	/**
	 * Fonts used to indicate that the text-field is processing the request,
	 * i.e. looking for matches
	 */
	protected Font busy, regular;

	/** Needed for the new narrowing search, so we know when to reset the list */
	protected String lastWord = "";

	/**
	 * The last chosen variable which exists. Needed if user continued to type
	 * but didn't press the enter key
	 * */
	protected String lastChosenExistingVariable;

	/**
	 * Hint that will be displayed if the field is empty
	 */
	protected String hint;

	/** Listeners, fire event when a selection as occured */
	protected LinkedList<ActionListener> listeners;

	protected SuggestMatcher suggestMatcher = new ContainsMatcher();

	protected boolean caseSensitive = false;

	/**
	 * Create a new JSuggestField.
	 * 
	 * @param owner
	 *            Frame containing this JSuggestField
	 */
	public JSuggestField() {
		super();
		data = new ArrayList<String>();
		suggestions = new ArrayList<String>();
		listeners = new LinkedList<ActionListener>();
		mapping = new HashMap<String, T>();

		val compListener = new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {
				relocate();
			}

			@Override
			public void componentResized(ComponentEvent e) {
				relocate();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				relocate();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				relocate();
			}
		};

		val windListener = new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
				d.setVisible(false);
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				d.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				d.dispose();
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		};

		this.addHierarchyListener(new HierarchyListener() {

			private Window parent = null;

			@Override
			public void hierarchyChanged(HierarchyEvent arg0) {

				if (parent != null) {
					parent.removeWindowListener(windListener);
					parent.removeComponentListener(compListener);

					// Our parent changed, so we hide the suggestion
					d.setVisible(false);
				}

				Window parent = SwingUtilities.getWindowAncestor(JSuggestField.this);

				if (parent != null) {

					d = new JDialog(parent);
					d.setUndecorated(true);
					d.setFocusableWindowState(false);
					d.setFocusable(false);

					setMinimumSuggestSize(new Dimension(200, 50));
					d.add(new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
					d.pack();

					parent.addComponentListener(compListener);
					parent.addWindowListener(windListener);
				}

				this.parent = parent;

			}
		});

		addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				d.setVisible(false);

				if (getText().equals("") && e.getOppositeComponent() != null && e.getOppositeComponent()
																					.getName() != null) {
					if (!e.getOppositeComponent()
							.getName()
							.equals("suggestFieldDropdownButton")) {
						setText(hint);
					}
				} else if (getText().equals("")) {
					setText(hint);
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				if (!suggestions.contains(getText()))
					showSuggest();

			}
		});

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			private int selected;

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (selected == list.getSelectedIndex()) {
					// provide double-click for selecting a suggestion
					setText((String) list.getSelectedValue());
					lastChosenExistingVariable = list.getSelectedValue()
														.toString();
					fireActionEvent();
					d.setVisible(false);
				}
				selected = list.getSelectedIndex();
			}
		});

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				relocate();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					d.setVisible(false);
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (d.isVisible()) {
						list.setSelectedIndex(list.getSelectedIndex() + 1);
						list.ensureIndexIsVisible(list.getSelectedIndex() + 1);
						return;
					} else {
						showSuggest();
					}
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					list.setSelectedIndex(list.getSelectedIndex() - 1);
					list.ensureIndexIsVisible(list.getSelectedIndex() - 1);
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER & list.getSelectedIndex() != -1 & suggestions.size() > 0) {
					setText((String) list.getSelectedValue());
					lastChosenExistingVariable = list.getSelectedValue()
														.toString();
					fireActionEvent();
					d.setVisible(false);
					return;
				}
				showSuggest();
			}
		});
		regular = getFont();
		busy = new Font(getFont().getName(), Font.ITALIC, getFont().getSize());
	}

	/**
	 * Create a new JSuggestField.
	 * 
	 * @param owner
	 *            Frame containing this JSuggestField
	 * @param data
	 *            Available suggestions (we use toString() on this)
	 */
	public JSuggestField(List<T> data) {
		this();

		ArrayList<String> names = new ArrayList<String>();
		for (T object : data) {
			mapping.put(object.toString(), object);
			names.add(object.toString());
		}

		setSuggestDataInternal(names);
	}

	/**
	 * Create a new JSuggestField.
	 * 
	 * @param owner
	 *            Frame containing this JSuggestField
	 * @param data
	 *            Available suggestions
	 */
	public JSuggestField(T[] data) {
		this(Arrays.asList(data));
	}

	/**
	 * Constructor on which you can supply your keys
	 * 
	 * @param data
	 * @param keys
	 */
	public JSuggestField(List<T> data, List<String> keys) {
		this();

		for (T object : data) {
			mapping.put(object.toString(), object);
		}

		setSuggestDataInternal(keys);
		relocate();
	}

	/**
	 * Returns the selected object, given the text from the field.
	 * 
	 * @return
	 */
	public T getSelectedObject() {
		return (T) getObjectMapping(getText());
	}

	/**
	 * Sets the selected object based on the given instance.
	 * 
	 * @param instance
	 */
	public void setSelectedObject(T instance) {
		if (instance == null) {
			setText("");
			return;
		}

		for (Entry<String, T> check : mapping.entrySet()) {
			if (check.getValue()
						.equals(instance)) {
				// Set this
				setText(check.getKey());
				return;
			}
		}

		setText("");
	}

	/**
	 * Returns the Object linked to the name provided.
	 * 
	 * @param selection
	 * @return
	 */
	public Object getObjectMapping(String selection) {
		return mapping.get(selection);
	}

	/**
	 * Sets the suggestion Data without mapping it to anything.
	 * 
	 * @param data
	 */
	public void setSuggestData(List<String> data) {
		mapping.clear();

		setSuggestDataInternal(data);
	}

	public void setSuggestData(String[] keys, T[] data) {
		mapping.clear();

		for (int i = 0; i < keys.length; i++) {
			mapping.put(keys[i], data[i]);
		}

		setSuggestDataInternal(Arrays.asList(keys));
	}

	/**
	 * Sets new data used to suggest similar words.
	 * 
	 * @param data
	 *            Vector containing available words
	 * @return success, true unless the data-vector was null
	 */
	protected boolean setSuggestDataInternal(List<String> data) {
		if (data == null) {
			return false;
		}
		Collections.sort(data);
		this.data = new ArrayList<String>(data);
		list.setListData(data.toArray());
		return true;
	}

	/**
	 * Get all words that are available for suggestion.
	 * 
	 * @return Vector containing Strings
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getSuggestData() {
		return (ArrayList<String>) data.clone();
	}

	/**
	 * Set preferred size for the drop-down that will appear.
	 * 
	 * @param size
	 *            Preferred size of the drop-down list
	 */
	public void setPreferredSuggestSize(Dimension size) {
		d.setPreferredSize(size);
	}

	/**
	 * Set minimum size for the drop-down that will appear.
	 * 
	 * @param size
	 *            Minimum size of the drop-down list
	 */
	public void setMinimumSuggestSize(Dimension size) {
		d.setMinimumSize(size);
	}

	/**
	 * Set maximum size for the drop-down that will appear.
	 * 
	 * @param size
	 *            Maximum size of the drop-down list
	 */
	public void setMaximumSuggestSize(Dimension size) {
		d.setMaximumSize(size);
	}

	/**
	 * Force the suggestions to be displayed (Useful for buttons e.g. for using
	 * JSuggestionField like a ComboBox)
	 */
	public void showSuggest() {
		if (!getText().toLowerCase()
						.contains(lastWord.toLowerCase())) {
			suggestions.clear();
		}
		if (suggestions.isEmpty()) {
			suggestions.addAll(data);
		}
		if (matcher != null) {
			matcher.stop = true;
		}
		matcher = new InterruptableMatcher();
		// matcher.start();
		SwingUtilities.invokeLater(matcher);
		lastWord = getText();

		relocate();
	}

	/**
	 * Force the suggestions to be hidden (Useful for buttons, e.g. to use
	 * JSuggestionField like a ComboBox)
	 */
	public void hideSuggest() {
		d.setVisible(false);
	}

	/**
	 * @return boolean Visibility of the suggestion window
	 */
	public boolean isSuggestVisible() {
		return d.isVisible();
	}

	/**
	 * Place the suggestion window under the JTextField.
	 */
	private void relocate() {
		try {
			location = getLocationOnScreen();
			location.y += getHeight();
			d.setLocation(location);
		} catch (IllegalComponentStateException e) {
			return; // might happen on window creation
		}
	}

	/**
	 * Inner class providing the independent matcher-thread. This thread can be
	 * interrupted, so it won't process older requests while there's already a
	 * new one.
	 */
	public class InterruptableMatcher
			extends Thread {

		/** flag used to stop the thread */
		@Setter
		protected volatile boolean stop;

		/**
		 * Standard run method used in threads responsible for the actual search
		 */
		@Override
		public void run() {
			try {
				setFont(busy);
				Iterator<String> it = suggestions.iterator();
				String word = getText();
				while (it.hasNext()) {
					if (stop) {
						return;
					}
					// rather than using the entire list, let's rather remove
					// the words that don't match, thus narrowing
					// the search and making it faster
					if (caseSensitive) {
						if (!suggestMatcher.matches(it.next(), word))
							it.remove();
					} else {
						if (!suggestMatcher.matches(it.next()
														.toLowerCase(), word.toLowerCase()))
							it.remove();
					}
				}
				setFont(regular);
				if (suggestions.size() > 0) {
					list.setListData(suggestions.toArray());
					list.setSelectedIndex(0);
					list.ensureIndexIsVisible(0);
					d.setVisible(true);
				} else {
					d.setVisible(false);
				}
			} catch (Exception e) {
				// Despite all precautions, external changes have occurred.
				// Let the new thread handle it...
				return;
			}
		}
	}

	/**
	 * Adds a listener that notifies when a selection has occured
	 * 
	 * @param listener
	 *            ActionListener to use
	 */
	public void addSelectionListener(ActionListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes the Listener
	 * 
	 * @param listener
	 *            ActionListener to remove
	 */
	public void removeSelectionListener(ActionListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Use ActionListener to notify on changes so we don't have to create an
	 * extra event
	 */
	private void fireActionEvent() {
		ActionEvent event = new ActionEvent(this, 0, getText());
		for (ActionListener listener : listeners) {
			listener.actionPerformed(event);
		}
	}

	/**
	 * Returns the selected value in the drop down list
	 * 
	 * @return selected value from the user or null if the entered value does
	 *         not exist
	 */
	public String getLastChosenExistingVariable() {
		return lastChosenExistingVariable;
	}

	/**
	 * Get the hint that will be displayed when the field is empty
	 * 
	 * @return The hint of null if none was defined
	 */
	public String getHint() {
		return hint;
	}

	public JList getList() {
		return list;
	}

	/**
	 * Set a text that will be displayed when the field is empty
	 * 
	 * @param hint
	 *            Hint such as "Search..."
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}

	/**
	 * Determine how the suggestions are generated. Default is the simple {@link ContainsMatcher}
	 * 
	 * @param suggestMatcher
	 *            matcher that determines if a data word may be suggested for
	 *            the current search word.
	 */
	public void setSuggestMatcher(SuggestMatcher suggestMatcher) {
		this.suggestMatcher = suggestMatcher;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public interface SuggestMatcher {
		public boolean matches(String dataWord, String searchWord);
	}

	public class ContainsMatcher
			implements SuggestMatcher {
		@Override
		public boolean matches(String dataWord, String searchWord) {
			return dataWord.contains(searchWord);
		}
	}

	public class EndsWithMatcher
			implements SuggestMatcher {
		@Override
		public boolean matches(String dataWord, String searchWord) {
			return dataWord.endsWith(searchWord);
		}
	}

	public class StartsWithMatcher
			implements SuggestMatcher {
		@Override
		public boolean matches(String dataWord, String searchWord) {
			return dataWord.startsWith(searchWord);
		}
	}
}
