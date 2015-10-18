package virtualpondgui;

import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;

import addressbook.Field;

/**
 * Responds to contacts table events.
 * 
 * @author atleebrink
 *
 */
public class ContactsTableReactor extends AbstractTableModel implements ContactsTable.Reactor {
	private static final long serialVersionUID = 1L;
	private static final Map<Integer, Integer> columnMap = new HashMap<Integer, Integer>(Field.NUM_DEFAULT) {
		private static final long serialVersionUID = 1L;
	{
		put( 0, Field.FIRSTNAME );
		put( 1, Field.LASTNAME );
		put( 2, Field.DELIVERY );
		put( 3, Field.SECOND );
		put( 4, Field.CITY );
		put( 5, Field.STATE );
		put( 6, Field.ZIP );
		put( 7, Field.PHONE );
	}};

	private GUICore guiCore;
	private JTable table;

	public ContactsTableReactor(GUICore guiCore) {
		this.guiCore = guiCore;
		table = null;
	}

	@Override
	public void setTable(JTable table) {
		this.table = table;
	}
	
	@Override
	public int getRowCount() {
		return guiCore.getCurrentAddressBook().getContacts().size();
	}

	@Override
	public int getColumnCount() {
		return guiCore.getCurrentAddressBook().getFields().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		int realColumn = columnMap.get( columnIndex );
		return Field.friendlyNames.getOrDefault(realColumn, "");
		//return guiCore.getCurrentAddressBook().getFieldAtIndex(realColumn).toString();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		int realColumn = columnMap.get( columnIndex );
		return guiCore.getCurrentAddressBook().getContacts().get(rowIndex).getContactDataAt(realColumn);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO do nothing, because we're not editable this way
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// thanks, http://stackoverflow.com/questions/4795586/how-can-i-check-which-jtable-cell-has-been-clicked-selected
		int row = table.rowAtPoint(e.getPoint());
		if( row < 0 ) {
			// clicked on an empty row -> deselect everything
			table.clearSelection();
			// NOTE: on OSX Mavericks, there is still a faint blue box around the cell that was last clicked during selecting.
			// I can't figure out how to make it go away.
			// It doesn't affect functionality, it is just annoying.
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortByColumn(int column) {
		int realColumn = columnMap.get( column );
		// TODO: may want to do sorting in another thread if slow
		guiCore.getCurrentAddressBook().sortByFieldAtIndex( realColumn );
		fireTableDataChanged();
	}
}