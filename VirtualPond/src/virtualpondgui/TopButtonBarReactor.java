package virtualpondgui;

import addressbook.Contact;

public class TopButtonBarReactor implements TopButtonBar.Reactor {
	private GUICore guiCore;
	
	public TopButtonBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onAdd() {
		guiCore.addContact( guiCore.editContact("Add New Contact", null) );
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
