package virtualpondgui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	
	/* GUICore methods:
	 * These methods are exposed to Reactors,
	 * which are classes that process user input from the GUI.
	 */
	@Override
	public void quit() {
		// TODO: IF unsaved changes THEN ask user to save OR cancel OR discard changes.
		System.exit(0);
	}
	
	/* Private methods:
	 * These methods do secret things,
	 * that other classes don't need to know about.
	 */
	/**
	 * Creates and returns a new JComponent populated with some buttons.
	 * @param glue the callback interface.
	 * @return a new JComponent.
	 */
	public JComponent createButtonBar() {
		JPanel buttonBar = new JPanel(new FlowLayout());
		
		JButton add = new JButton("Add");
		JButton edit = new JButton("Edit");
		JButton delete = new JButton("Delete");
		
		buttonBar.add(add);
		buttonBar.add(edit);
		buttonBar.add(Box.createHorizontalStrut(20));
		buttonBar.add(delete);
				
		return buttonBar;
	}
	
	public JComponent createTable() {
		
		// the default fields will come from the AddressBook,
		// but for mock-up purposes I will assume the following fields:
		// First, Last, Address, City, State, ZIP, Phone, Email
		String columnNames[] = {"First", "Last", "Address", "City", "State",
			"ZIP", "Phone", "Email"};
		JTable table = new JTable(new DefaultTableModel(null, columnNames));
		JScrollPane scrollpane = new JScrollPane(table);
		
		return scrollpane;
	}
	
	public JPanel createMainContentPane() {
		JPanel contentPane = new JPanel(new BorderLayout());
		
		// TODO: add buttons, table, (status bar?)
		contentPane.add(createButtonBar(), BorderLayout.NORTH);
		contentPane.add(createTable(), BorderLayout.CENTER);
		
		return contentPane;
	}

	public void run()
	{
		JFrame frame = new JFrame("(empty address book)" + " - " + TITLE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setJMenuBar(GUIMenuBar.create(new MenuBarReactor(this)));
		
		frame.setContentPane(createMainContentPane());

		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Begins execution of the graphical user interface.
	 * This includes the program window and application menu.
	 *
	 * This method should be called after all other non-GUI
	 * initialization has completed.
	 *
	 */
	public static void createAndShowGUI(/* what args? */)
	{
		// todo: one program instance should contain:
		//		* one address book at a time
		//		* one window at a time
		//		* one application menu at a time
		// todo: by default, Java allows multiple instances of the same program,
		//		* this is good and fine
		//		* we -may- want to keep track of running instances,
		//			and populate the Window menu with them,
		//			though this may not be simple to implement,
		//			nor important.
		// todo: close application when one window closes
		
		
		// detect operating system
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) { // do Mac stuff
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		// launch window
		SwingUtilities.invokeLater(new VirtualPond());
		
	}
	
	public static void main(String[] args)
	{
		createAndShowGUI();
	}
}
