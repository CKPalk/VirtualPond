package virtualpondgui;

import java.util.ArrayList;

import addressbook.Contact;

public class TopButtonBarReactor implements TopButtonBar.Reactor {
	private GUICore guiCore;
	
	public TopButtonBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onAdd() {
		ArrayList<String> newFieldValues = new ArrayList<>();
		int numFields = guiCore.getCurrentAddressBook().getFields().size();
		for( int i = 0; i < numFields; i++ ) newFieldValues.add("");
		Contact newContact = new Contact(newFieldValues);
		guiCore.editContact("Add New Contact", newContact);
		// todo: if successful, add new contact to book
	}
	
	public void onEdit() {
		Contact currentContact = guiCore.getSelectedContact();
		if( currentContact == null ) {
			System.err.println("error: nothing selected");
		} else {
			guiCore.editContact("Edit Contact", currentContact);
			// todo: refresh contact in view
		}
	}
	
	public void onDelete() {
		System.out.println("delete not implemented");
	}
}
