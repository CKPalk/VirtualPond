package virtualpondgui;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import addressbook.Contact;
import addressbook.Field;
import addressbook.VirtualAddressBook;

/**
 * The JDialog that allows a user to edit all the fields of a contact.
 * 
 * @author atleebrink
 *
 */
public class EditContactDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private JButton buttonCancel, buttonSave;
	
	private Contact editedContact;
	private ArrayList<EntryFieldEditor> entryFieldEditors;
	
	public EditContactDialog(Frame owner, String title, VirtualAddressBook addressBook, Contact initialContact) {
		// JDialog(parent, dialog window title, isModal)
		super(owner, title, true);
		
		JPanel panel = new JPanel();

		ArrayList<Field> fields = addressBook.getFields();
		if( initialContact == null ) initialContact = new Contact(fields.size());
		entryFieldEditors = new ArrayList<>();
		
		// populate dialog with field editors,
		// using the initialContact as initial field values,
		// and setting labels from field.toString().
		int numFields = fields.size();
		for( int i = 0; i < numFields; i++ ) {
			Field field = fields.get(i);
			String label = field.toString();
			String initialValue = initialContact.getContactDataAt(i);
			
			EntryFieldEditor entryField = new EntryFieldEditor(label, initialValue, field);
			entryFieldEditors.add(entryField);
			panel.add(entryField);
		}
		
		// add cancel, save buttons
		buttonCancel = new JButton("Cancel");
		buttonSave = new JButton("Save Contact");
		
		// associate buttons with methods
		buttonCancel.addActionListener(event -> onCancel());
		buttonSave.addActionListener(event -> onSave());
		
		// add buttons to the panel
		panel.add(buttonCancel);
		panel.add(buttonSave);
		
		// add the panel to this dialog
		add(panel);
		
		// TODO: find a better layout for this dialog
		setPreferredSize(new Dimension(400, 400));
		pack();
		
		// center within parent
		setLocationRelativeTo(null);
		
		// since this dialog is modal, setVisible(true) blocks,
		// and we will only get out of this dialog if the user
		// presses one of the buttons or closes the dialog manually.
		// As such, we want the default result to be a null Contact.
		this.editedContact = null;
		setVisible(true);
	}
	
	/**
	 * @return the edited Contact, or null if the user cancelled.
	 */
	public Contact getResult() {
		return editedContact;
	}
	
	public void onCancel() {
		dispose();
	}
	
	public void onSave() {
		ArrayList<String> fieldValues = new ArrayList<>();
		for( int i = 0; i < entryFieldEditors.size(); i++ ) {
			fieldValues.add(entryFieldEditors.get(i).getFieldValue());
		}
		editedContact = new Contact(fieldValues);
		dispose();
	}
}
