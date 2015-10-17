package virtualpondgui;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
		
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout( panel, BoxLayout.Y_AXIS ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 10, 15, 10, 15 ) );

		// common to all fields
		List<Field> fields = addressBook.getFields();
		editors = new EntryFieldEditor[ Field.NUM_DEFAULT ];
		EntryFieldEditor editor;
		int i;
				
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
		// TODO: extract edited values into a new Contact
		// TODO: ask the new Contact if it is valid:
		//         IF NOT, ask the USER to confirm adding an incomplete contact
		//           IF YES, save the contact
		//           IF CANCEL, go back to editing
		//         ELSE save the contact
		ArrayList<String> fieldValues = new ArrayList<>();
		for( int i = 0; i < editors.length; i++ ) {
			fieldValues.add(editors[i].getFieldValue());
		}
		editedContact = new Contact(fieldValues);
		dispose();
	}
}
