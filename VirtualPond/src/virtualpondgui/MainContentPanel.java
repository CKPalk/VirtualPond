package virtualpondgui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComponent buttonBar;
	private ContactsTable contactsTable;

	public MainContentPanel(GUICore guiCore) {
		super(new BorderLayout());
		
		buttonBar = new TopButtonBar(new TopButtonBarReactor(guiCore));
		add(buttonBar, BorderLayout.NORTH);
		
		contactsTable = new ContactsTable(guiCore); 
		add(contactsTable, BorderLayout.CENTER);
	}
	
	public void resetContactsTable(GUICore guiCore) {
		contactsTable.resetTable(guiCore);
	}
}
