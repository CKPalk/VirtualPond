package virtualpondgui;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ContactsTable extends JScrollPane {
	private static final long serialVersionUID = 1L;

	public interface Reactor {
		
	}
	
	public ContactsTable(Reactor reactor) {
		// the default fields will come from the AddressBook,
		// but for mock-up purposes I will assume the following fields:
		// First, Last, Address, City, State, ZIP, Phone, Email
		String columnNames[] = {"First", "Last", "Address", "City", "State",
			"ZIP", "Phone", "Email"};
		JTable table = new JTable(new DefaultTableModel(null, columnNames));

		setViewportView(table);
	}

}
