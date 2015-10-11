package virtualpondgui;

import java.util.Arrays;

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
	}
	
	public void deleteContacts(int[] indicesDeleted, boolean isSorted) {
		if( table == null ) return;
		if( !isSorted ) Arrays.sort(indicesDeleted);
		for( int i = indicesDeleted.length - 1; i >= 0; i--) {
			int index = indicesDeleted[i];
			tableModel.fireTableRowsDeleted(index, index);
		}
	}
	
	public int[] getSelectedRows() {
		return table.getSelectedRows();
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
