package virtualpondgui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	public interface Reactor extends TableModel, MouseListener {
		void setTable(JTable table);
		void sortByColumn(int column);
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
			tableModel.setTable(table);
			this.addMouseListener(tableModel);
			table.getTableHeader().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int column = table.columnAtPoint(e.getPoint());
					if( column >= 0 ) {
						tableModel.sortByColumn(column);
					}
				}
			});
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
