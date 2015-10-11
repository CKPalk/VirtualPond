package virtualpondgui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComponent buttonBar;
	private ContactsTable contactsTable;
	//private EditArea editArea;

	public MainContentPanel(GUICore guiCore) {
		super(new BorderLayout());

		buttonBar = new ToolBar(new ToolBarReactor(guiCore));
		add(buttonBar, BorderLayout.NORTH);

		add(Box.createHorizontalStrut(10), BorderLayout.WEST);

		contactsTable = new ContactsTable(guiCore); 
		add(contactsTable, BorderLayout.CENTER);

		add(Box.createHorizontalStrut(10), BorderLayout.EAST);
		add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		
		/*
		editArea = new EditArea(guiCore);
		editArea.setMaximumSize(new Dimension(100000, 100));
		editArea.setPreferredSize(new Dimension(600, 100));
		editArea.setMinimumSize(new Dimension(1, 100));
		add(editArea, BorderLayout.SOUTH);
		*/
	}
	
	public void addContactToTable(int indexAdded) {
		contactsTable.addContact(indexAdded);
	}
	
	public void deleteContacts(int[] indices, boolean isSorted) {
		contactsTable.deleteContacts(indices, isSorted);
	}
	
	public int[] getAllSelectedEntryRows() {
		return contactsTable.getSelectedRows();
	}

	public void resetContactsTable() {
		contactsTable.resetTable();
	}
	
	// deprecated
	public void resetEditArea() {
		//editArea.populate();
	}
	
	public void updateContactAtIndex(int index) {
		contactsTable.updateContact(index);
	}
	
}
