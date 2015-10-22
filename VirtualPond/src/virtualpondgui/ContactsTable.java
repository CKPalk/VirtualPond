package virtualpondgui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
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
			table.setShowGrid(false);
			
			// thanks, http://stackoverflow.com/questions/25679653/unable-to-set-custom-background-color-in-jtable
			final Color alternateColor = new Color(243, 246, 250);
			table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				private static final long serialVersionUID = 1L;
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					if (!isSelected) {
						c.setBackground(row % 2 == 0 ? Color.white : alternateColor);
					}
					return c;
				}
			});
			
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
