package virtualpondgui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import addressbook.Contact;
import addressbook.Field;
import addressbook.VirtualAddressBook;

public class EditContactDialog extends JDialog {
	private JButton buttonCancel, buttonSave;
	
	private Contact sourceContact;
	private ArrayList<EntryFieldEditor> entryFields;
	
	private boolean doSave;
	
	public EditContactDialog(Frame owner, String title, VirtualAddressBook addressBook, Contact contact) {
		super(owner, title, true);
		JPanel panel = new JPanel();

		entryFields = new ArrayList<>();
		ArrayList<Field> fields = addressBook.getFields();
		
		for( int i = 0; i < fields.size(); i++ ) {
			String label = fields.get(i).toString();
			String initialValue = (contact == null ? "" : contact.getContactDataArray().get(i));
			EntryFieldEditor entryField = new EntryFieldEditor(label, initialValue, fields.get(i));
			entryFields.add(entryField);
			panel.add(entryField);
		}
		
		// add cancel, save buttons
		buttonCancel = new JButton("Cancel");
		buttonSave = new JButton("Save Contact");
		
		buttonCancel.addActionListener(event -> onCancel());
		buttonSave.addActionListener(event -> onSave());
		
		panel.add(buttonCancel);
		panel.add(buttonSave);
		
		add(panel);
		
		setPreferredSize(new Dimension(400, 400));
		pack();
		setVisible(true);
	}
	
	public void onCancel() {
		doSave = false;
		dispose();
	}
	
	public void onSave() {
		doSave = true;
		for( int i = 0; i < entryFields.size(); i++ ) {
			sourceContact.getContactDataArray().set(i, entryFields.get(i).getFieldValue());
		}
		dispose();
	}
}
