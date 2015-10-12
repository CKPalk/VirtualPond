package virtualpondgui;

import javax.swing.JOptionPane;

import addressbook.Contact;

public class ToolBarReactor implements ToolBar.Reactor {
	private GUICore guiCore;
	
	public ToolBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onAdd() {
		guiCore.addContact( guiCore.editContactDialog("Add New Contact", null) );
	}
	
	public void onEdit() {
		int[] indices = guiCore.getSelectedIndices();
		if( indices.length != 1 ) {
			System.err.println("need exactly one entry selected to edit");
			return;
		}
		Contact currentContact = guiCore.getContactByIndex(indices[0]);
		if( currentContact == null ) {
			System.err.println("error: nothing selected");
		} else {
			currentContact = guiCore.editContactDialog("Edit Contact", currentContact);
			if( currentContact != null ) {
				guiCore.updateContactAtIndex(indices[0], currentContact);
			}
		}
	}
	
	public void onDelete() {
		int[] selected = guiCore.getSelectedIndices();
		if( selected.length > 0 ) {
			String plural = selected.length == 1 ? "contact" : (selected.length + " contacts");
			Object[] options = {"Yes", "No"};
			int n = JOptionPane.showOptionDialog(guiCore.getMainWindow(),
					"Are you sure you want to delete the selected " + plural +" from this address book?",
					"Delete " + plural + "?", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if( n == 0 ) { // Yes
				guiCore.deleteContactsByIndex(selected);
			}
		} else {
			System.err.println("need at least one entry selected to delete");
		}
	}
}
