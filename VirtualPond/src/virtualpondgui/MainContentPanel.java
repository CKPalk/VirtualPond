package virtualpondgui;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class MainContentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComponent buttonBar, contactsTable;
	
	public MainContentPanel(GUICore guiCore) {
		super(new BorderLayout());
		
		buttonBar = new TopButtonBar(new TopButtonBarReactor(guiCore));
		add(buttonBar, BorderLayout.NORTH);
		
		contactsTable = new ContactsTable(new ContactsTableReactor(guiCore)); 
		add(contactsTable, BorderLayout.CENTER);
	}
}
