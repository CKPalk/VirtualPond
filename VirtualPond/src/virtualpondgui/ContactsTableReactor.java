package virtualpondgui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import addressbook.Field;

/**
 * Responds to contacts table events.
 * 
 * @author atleebrink
 *
 */
public class ContactsTableReactor extends AbstractTableModel {
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

	public ContactsTableReactor(GUICore guiCore) {
		this.guiCore = guiCore;
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
}