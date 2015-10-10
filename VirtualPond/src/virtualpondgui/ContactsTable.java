package virtualpondgui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ContactsTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public interface Reactor extends TableModel {

	}
	
	private GUICore guiCore;
	
	public ContactsTable(GUICore guiCore) {
		this.guiCore = guiCore;
		resetTable();
	}
	
	public void resetTable() {
		if( guiCore.getCurrentAddressBook() != null ) {
			ContactsTableReactor contactsTableReactor = new ContactsTableReactor(guiCore);
			setViewportView(new JTable(contactsTableReactor));
		}		
	}
}
