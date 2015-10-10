package virtualpondgui;

import java.awt.*;
import java.io.File;
import java.net.URI;

import javax.swing.*;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	final static String USER_MANUAL = "https://www.assembla.com/spaces/cis422f15-team1/wiki/Software_Documentation";
	private static URI URI_USER_MANUAL = null;
	
	private JFrame mainFrame = null;
	private MainContentPanel mainContentPanel = null;
	private JFileChooser fileChooser = null;

	// current address book information
	private String addressBookFileName = null;
	private VirtualAddressBook addressBook = null;
	private boolean isAddressBookFresh = true;

	/* GUICore methods:
	 * These methods are exposed to Reactors,
	 * which are classes that process user input from the GUI.
	 */
	
	@Override
	public VirtualAddressBook getCurrentAddressBook() {
		return addressBook;
	}
	
	@Override
	public JFileChooser getFileChooser() {
		if( fileChooser == null ) {
			fileChooser = new JFileChooser();
		}
		return fileChooser;
	}
	
	@Override
	public Component getMainWindow() {
		return mainFrame;
	}

	@Override
	public URI getUserManualURI() {
		if (URI_USER_MANUAL == null) {
			try {
				URI_USER_MANUAL = new URI(USER_MANUAL);
			} catch (Exception e) {
				// TODO: we may want to have our User Manual as a local file that we can refer to,
				// but until then allow the return of null
			}
		}
		return URI_USER_MANUAL;
	}
	
	@Override
	public void openFile(File file) {
		if( addressBook != null) { // there is already an open address book
			if( !commitAddressBookToFile() ) { // we weren't able to save the current address book
				System.out.println("error: openFile(...) tried to commitAddressBookToFile() and failed!");
				return; // DON'T open a new file
			}
		}
		if( file == null ) {
			file = new File("./.address_book_default_data.pond™");
			addressBookFileName = null;
		} else {
			addressBookFileName = file.getName();
		}
		System.out.println("attempting to open file" + file.getAbsolutePath());
		VirtualBookReader vbReader = new VirtualBookReader(file);
		addressBook = vbReader.read();
		// TODO: check that the addressBook was correctly opened
		// for now, assume that it was
		isAddressBookFresh = true;
		updateWindowTitle();
		mainContentPanel.resetContactsTable(this);
	}
	
	@Override
	public void quit() {
		// TODO: IF unsaved changes THEN ask user to save OR cancel OR discard changes.
		if( !commitAddressBookToFile() ) {
			// TODO: we weren't able to commit the address book to a file, what do we do?
			// for now, print an error and continue to exit
			System.out.println("error: commitAddressBookToFile() failed!");
		}
		System.exit(0);
	}
	
	/* Private methods:
	 * These methods do secret things,
	 * that other classes don't need to know about.
	 */

	/**
	 * Attempts to write out the current address book to a file.
	 * If the address book is fresh, does nothing and returns true.
	 * If the address book is stale and has a filename, writes
	 * the address book and returns true.
	 * If the address book is stale and does not have a filename,
	 * prompts the user for a filename, and if the user provides one,
	 * writes the address book and returns true.
	 * Else returns false.
	 * @return true on success, false otherwise
	 */
	private boolean commitAddressBookToFile() {
		if( isAddressBookFresh ) return true; // nothing to do
		// else we need to make sure we have a filename for saving to
		File fileToSaveTo = null;
		if( addressBookFileName == null ) {
			// we don't have a name, so we need to ask the user for one
			JFileChooser fc = getFileChooser();

			int retCode = fc.showSaveDialog(getMainWindow());
			if (retCode == JFileChooser.APPROVE_OPTION) {
				fileToSaveTo = fileChooser.getSelectedFile();
			} else {
				return false;
			}
		} else {
			// we have a name, so just save there
			fileToSaveTo = new File(addressBookFileName);
		}
		VirtualBookWriter vbWriter = new VirtualBookWriter(addressBook, fileToSaveTo);
		vbWriter.write();
		// TODO: check if the write succeeded; for now assume it did
		return true;
	}
	
	private void updateWindowTitle() {
		mainFrame.setTitle((addressBookFileName == null ? "untitled" : addressBookFileName)
				+ (isAddressBookFresh ? "" : "*") + " - " + TITLE);
	}
	
	/**
	 * Implements Runnable.run() to create GUI components,
	 * and provides those components with a GUICore interface to this class.
	 */
	public void run() {
		mainFrame = new JFrame(TITLE);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setJMenuBar(new MenuBar(this));

		mainContentPanel = new MainContentPanel(this);
		mainFrame.setContentPane(mainContentPanel);

		mainFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		mainFrame.pack();
		mainFrame.setVisible(true);
		

		openFile(null);
	}

	/**
	 * Creates and begins management of the graphical user interface in a new thread.
	 */
	public static void createAndShowGUI(/* what args? */) {
		// detect operating system
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) { // do Mac stuff
			System.setProperty("apple.laf.useScreenMenuBar", "true");
		}

		// ask Swing to use the Runnable of this class in a new thread
		SwingUtilities.invokeLater(new VirtualPond());
	}
	
	/**
	 * A simple main method.
	 * Immediately calls createAndShowGUI(), then returns.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		createAndShowGUI();
	}
}
