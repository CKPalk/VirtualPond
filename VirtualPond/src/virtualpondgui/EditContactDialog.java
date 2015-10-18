package virtualpondgui;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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

	private VirtualAddressBook addressBook;
	private Contact initialContact;
	private Contact editedContact;
	private EntryFieldEditor[] editors;
	
	// content width into which fields will be crammed
	private static final int WIDTH = 480;
	
	private EntryFieldEditor registerEditor( int fieldIndex, int width ) {
		return ( editors[fieldIndex] = new EntryFieldEditor(
				Field.friendlyNames.get(fieldIndex),
				initialContact.getContactDataAt(fieldIndex),
				addressBook.getFields().get(fieldIndex), 
				width ) );
	}
	
	public EditContactDialog(Frame owner, String title, VirtualAddressBook addressBook, Contact initialContact) {
		super(owner, title, true);
		
		this.addressBook = addressBook;
		this.initialContact = initialContact;
		editors = new EntryFieldEditor[ Field.NUM_DEFAULT ];
		
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 10, 15, 10, 15 ) );

		// First Name ____________ Last Name _________________
		JPanel rowFirstLast = new JPanel();
		rowFirstLast.setLayout( new BoxLayout( rowFirstLast, BoxLayout.X_AXIS ) );
		rowFirstLast.add( registerEditor( Field.FIRSTNAME, (WIDTH - 20) / 2 ) );
		rowFirstLast.add( Box.createHorizontalStrut( 20 ) );
		rowFirstLast.add( registerEditor( Field.LASTNAME, (WIDTH - 20) / 2 ) );		
		panel.add( rowFirstLast );

		// space
		panel.add( Box.createVerticalStrut(5) );
		
		// Address 1 ______________________________________________
		panel.add( registerEditor( Field.DELIVERY, WIDTH ) );
		
		// space
		panel.add( Box.createVerticalStrut(5) );
		
		// Address 2 _______________________________________________
		panel.add( registerEditor( Field.SECOND, WIDTH ) );

		// space
		panel.add( Box.createVerticalStrut(5) );

		// City ___________________ State ________ Zip _______________
		JPanel rowCityStateZip = new JPanel();
		rowCityStateZip.setLayout( new BoxLayout( rowCityStateZip, BoxLayout.X_AXIS ) );
		rowCityStateZip.add( registerEditor( Field.CITY, WIDTH - 2 * ((WIDTH - 40) / 3 ) ) );
		rowCityStateZip.add( Box.createHorizontalStrut( 20 ) );		
		rowCityStateZip.add( registerEditor( Field.STATE, (WIDTH - 40) / 3 ) );
		rowCityStateZip.add( Box.createHorizontalStrut( 20 ) );
		rowCityStateZip.add( registerEditor( Field.ZIP, (WIDTH - 40) / 3 ) );
		panel.add( rowCityStateZip );
		
		// space
		panel.add( Box.createVerticalStrut(5) );
		
		// Phone Number ________________________
		JPanel rowPhone = new JPanel();
		rowPhone.setLayout( new BoxLayout( rowPhone, BoxLayout.X_AXIS ) );
		rowPhone.add( registerEditor( Field.PHONE, WIDTH / 2 ) );
		rowPhone.add( Box.createHorizontalStrut( WIDTH / 2 ) );
		panel.add( rowPhone );

		// big space
		panel.add( Box.createVerticalStrut(10) );
		
		// create bottom area for Cancel, Save buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new BoxLayout( buttonPanel, BoxLayout.X_AXIS ) );
		
		// create Cancel, Save buttons
		buttonCancel = new JButton("Cancel");
		buttonSave = new JButton("Save Contact");
		
		// associate buttons with methods
		buttonCancel.addActionListener(event -> onCancel());
		buttonSave.addActionListener(event -> onSave());
		
		// add buttons to the panel
		buttonPanel.add( Box.createHorizontalGlue() );
		// "Cancel, Save" on mac, "Save, Cancel" on Windows/Other
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) {
			buttonPanel.add(buttonCancel);
			buttonPanel.add(buttonSave);
		} else {
			buttonPanel.add(buttonSave);
			buttonPanel.add(buttonCancel);
		}
		
		// add button panel to dialog
		panel.add(buttonPanel);
		
		// add the panel to this dialog
		add(panel);
		
		//setPreferredSize(new Dimension(400, 400));
		pack();
		
		// since this dialog is modal, setVisible(true) blocks,
		// and we will only get out of this dialog if the user
		// presses one of the buttons or closes the dialog manually.
		// As such, we want the default result to be a null Contact.
		this.editedContact = null;
		// center within parent
		setLocationRelativeTo(null);
		// disable resizing
		setResizable(false);
		// show
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
	
	// TODO: hopefully we can put this functionality elsewhere.
	// Atlee says: "I hear that Cameron has a validator mostly working, but I don't think he's uploaded it yet."
	// Atlee says: "I wrote this stub so that I can write the rest of the onSave() method."
	private boolean isFieldValid( int fieldIndex, String value ) {
		switch( fieldIndex ) {
		// TODO: add cases for each type, such as case Field.PHONE
		default: // anything not specified above is automatically valid
			return true;
		}
	}
	
	public void onSave() {
		
		// extract the Strings from the dialog's editable fields and place into a new Contact
		ArrayList<String> fieldValues = new ArrayList<>();
		ArrayList<Integer> invalidFields = new ArrayList<>();
		for( int i = 0; i < editors.length; i++ ) {
			String fieldValue = editors[i].getFieldValue();
			
			// check if this particular field is valid or not
			if( !isFieldValid( i, fieldValue ) ) {
				invalidFields.add(i);
			}
			
			// add value to list of values
			fieldValues.add(editors[i].getFieldValue());
		}

		// if there were any invalid fields, ask the user about all of them at once
		int numInvalid = invalidFields.size();
		if( numInvalid > 0 ) {
			StringBuilder badList = new StringBuilder();
			for( int i = 0; i < numInvalid; i++ ) {
				int badIndex = invalidFields.get(i);
				badList.append( "    " + Field.friendlyNames.get(badIndex) + ": "
				+ fieldValues.get(badIndex) + "\n" );
			}
			String[] options = {"Use Anyway", "Cancel"};
			int n = JOptionPane.showOptionDialog( this,
					"Nonstandard field value" + (numInvalid > 1 ? "s" : "") +  " detected:\n\n"
					+ badList.toString()
					+ "\n"
					+ "Do you want to use these values anyway?",
					"Nonstandard field values!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0] );
			if( n != 0 ) { // Cancel
				return;
			}
		}
		
		// check that the contact has at least one non-empty name (first or last),
		// and at least one other nonempty field
		boolean hasFirst = !fieldValues.get( Field.FIRSTNAME ).isEmpty();
		boolean hasLast = !fieldValues.get( Field.LASTNAME ).isEmpty();
		boolean hasOther = false;
		for( int i = 0; i < fieldValues.size(); i++ ) {
			if( i != Field.FIRSTNAME && i != Field.LASTNAME
					&& !fieldValues.get( i ).isEmpty() ) {
				hasOther = true;
			}
		}
		if( !( ( hasFirst || hasLast ) && hasOther ) ) {
			String[] options = {"Use Anyway", "Cancel"};
			int n = JOptionPane.showOptionDialog( this,
					"A contact ought to have a name (first or last),\n"
					+ "and at least one other piece of information.\n\n"
					+ "Do you want to use what you've entered, anyway?",
					"Low information contact!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0] );
			if( n != 0 ) { // Cancel
				return;
			}			
		}

		Contact potentialContact = new Contact(fieldValues);

		// at this point we have decided to keep the edited contact,
		// so we place it in 'editedContact' and 'dispose()' to close this dialog.
		editedContact = potentialContact;
		dispose();
	}
}
