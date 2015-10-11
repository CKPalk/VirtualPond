package virtualpondgui;

import javax.swing.table.AbstractTableModel;

public class ContactsTableReactor extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

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
		return guiCore.getCurrentAddressBook().getFieldAtIndex(columnIndex).toString();
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
		return guiCore.getCurrentAddressBook().getContacts().get(rowIndex).getContactDataAt(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO do nothing, because we're not editable this way
	}
}