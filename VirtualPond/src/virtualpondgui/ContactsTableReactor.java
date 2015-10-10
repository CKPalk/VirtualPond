package virtualpondgui;

import javax.swing.event.TableModelListener;

import addressbook.VirtualAddressBook;

public class ContactsTableReactor implements ContactsTable.Reactor {
	private VirtualAddressBook addressBook;
	
	public ContactsTableReactor(GUICore guiCore) {
		addressBook = guiCore.getCurrentAddressBook();
	}

	@Override
	public int getRowCount() {
		return addressBook.getContacts().size();
	}

	@Override
	public int getColumnCount() {
		return addressBook.getFields().size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return addressBook.getFieldAtIndex(columnIndex).toString();
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
		return addressBook.getContacts().get(rowIndex).getContacts().get(columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO do nothing, because we're not editable this way
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO do nothing, because we don't like eave-droppers
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO do nothing, because we don't like eave-droppers
	}
}
