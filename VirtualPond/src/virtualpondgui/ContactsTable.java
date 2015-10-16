package virtualpondgui;

import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * The JScrollPane which contains a table for showing a list of contacts.
 * 
 * @author atleebrink
 *
 */
public class ContactsTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public interface Reactor extends TableModel {

	}
	
	private GUICore guiCore;
	private JTable table;
	private ContactsTableReactor tableModel;
	
	public ContactsTable(GUICore guiCore) {
		this.guiCore = guiCore;
		table = null;
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
		if( table == null && guiCore.getCurrentAddressBook() != null ) {
			tableModel = new ContactsTableReactor(guiCore);
			table = new JTable(tableModel);
			setViewportView(table);
		} else if( tableModel != null ) {
			tableModel.fireTableStructureChanged();
			tableModel.fireTableDataChanged();
		}
	}
	
	public void updateContact(int index) {
		if( table == null || index < 0 ) return;
		tableModel.fireTableRowsUpdated(index, index);;
	}
}
