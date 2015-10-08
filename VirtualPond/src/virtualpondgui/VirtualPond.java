package virtualpondgui;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class VirtualPond implements Runnable {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	
	public JMenuBar createMenuBar() {
		// TODO: pare down to just those features that we want for our
		//	initial iteration.
		
		// contains File, Help
		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu file = new JMenu("File");
		JMenuItem fileNew = new JMenuItem("New");
		JMenuItem fileOpen = new JMenuItem("Open...");
		JMenuItem fileClose = new JMenuItem("Close");
		JMenuItem fileSave = new JMenuItem("Save");
		JMenuItem fileSaveAs = new JMenuItem("Save As...");
		JMenuItem fileQuit = new JMenuItem("Quit");
		file.add(fileNew);
		file.add(fileOpen);
		file.add(fileClose);
		file.addSeparator();
		file.add(fileSave);
		file.add(fileSaveAs);
		file.addSeparator();
		file.add(fileQuit);

		// Help menu
		JMenu help = new JMenu("Help");
		JMenuItem helpUserManual = new JMenuItem("User Manual");
		JMenuItem helpAbout = new JMenuItem("About");
		help.add(helpUserManual);
		help.add(helpAbout);

		// add menus to menuBar
		menuBar.add(file);
		menuBar.add(help);

		menuBar.setVisible(true);

		return menuBar;
	}
	
	/**
	 * Creates and returns a new JComponent populated with some buttons.
	 * @param glue the callback interface.
	 * @return a new JComponent.
	 */
	public JComponent createButtonBar() {
		JPanel buttonBar = new JPanel(new FlowLayout());
		
		JButton add = new JButton("Add");
		JButton edit = new JButton("Edit");
		JButton find = new JButton("Find");
		JButton delete = new JButton("Delete");
		
		buttonBar.add(add);
		buttonBar.add(edit);
		buttonBar.add(Box.createHorizontalStrut(20));
		buttonBar.add(find);
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

		frame.setJMenuBar(createMenuBar());
		
		frame.setContentPane(createMainContentPane());

		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Begins execution of the graphical user interface.
	 * This includes the program window and application menu.
	 * <p>
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
