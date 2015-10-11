package virtualpondgui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ContactsTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public interface Reactor extends TableModel {

	}
	
	private GUICore guiCore;
	private JTable table;
	private ContactsTableReactor tableModel;
	
	public ContactsTable(GUICore guiCore) {
		this.guiCore = guiCore;
		resetTable();
	}

	public void addContact(int indexAdded) {
		if( table == null ) return;
		tableModel.fireTableRowsInserted(indexAdded, indexAdded);
		System.out.println("Contact added");
	}
	
	public int getSelectedRow() {
		if( table.getSelectedRowCount() != 1 ) return -1;
		return table.getSelectedRow();
	}
	
	public void resetTable() {
		table = null;
		if( guiCore.getCurrentAddressBook() != null ) {
			tableModel = new ContactsTableReactor(guiCore);
			table = new JTable(tableModel);
			setViewportView(table);
		}		
	}
}
