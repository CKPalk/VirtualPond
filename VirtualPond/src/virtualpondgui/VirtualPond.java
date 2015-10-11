package virtualpondgui;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	final static String USER_MANUAL = "https://www.assembla.com/spaces/cis422f15-team1/wiki/Software_Documentation";
	private static URI URI_USER_MANUAL = null;
	
	private boolean isMac;
	private JFrame mainFrame = null;
	private MainContentPanel mainContentPanel = null;
	private JFileChooser fileChooser = null;

	// current address book information
	private String addressBookFileName = null;
	private VirtualAddressBook addressBook = null;
	private boolean isAddressBookFresh = true;

	// Constructor //
	public VirtualPond(boolean isMac) {
		this.isMac = isMac;
	}
	
	// GUICore METHODS //
	
	/**
	 * Adds a contact to the address book, and displays it
	 * in the table.
	 */
	@Override
	public void addContact(Contact contact) {
		if( contact == null ) return;
		addressBook.newContact(contact);
		mainContentPanel.addContactToTable(addressBook.getContacts().size());
		makeStale();
	}
	
	@Override
	public void deleteContactsByIndex(int[] indices) {
		if( indices == null || indices.length < 1 ) return;
		// it is critical that contacts are removed from higher indices to lower,
		// else the indices would become invalid after a removal.
		Arrays.sort(indices);
		ArrayList<Contact> contacts = addressBook.getContacts();
		for( int i = indices.length - 1; i >= 0; i-- ) contacts.remove(indices[i]);
		mainContentPanel.deleteContacts(indices, true);
	}
	
	/**
	 * @return true if user selected Save, else false
	 */
	@Override
	public Contact editContact(String title, Contact initialContact) {
		EditContactDialog ecDialog = new EditContactDialog(mainFrame, title, addressBook, initialContact);
		return ecDialog.getResult();
	}
	
	@Override
	public int[] getSelectedIndices() {
		return mainContentPanel.getAllSelectedEntryRows();
	}
	
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
	public Contact getContactByIndex(int index) {
		if( index < 0 ) return null;
		if( index >= addressBook.getContacts().size() ) return null;
		return addressBook.getContacts().get(index);
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
	public boolean isMac() {
		return isMac;
	}
	
	@Override
	public void openFile(File file) {
		// TODO: make this method cleaner: I feel that there are going to be bugs popping up around here.
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
		mainContentPanel.resetContactsTable();
		mainContentPanel.resetEditArea();
		mainFrame.pack();
	}
	
	@Override
	public void quit() {
		// TODO: IF unsaved changes THEN ask user to save OR cancel OR discard changes.
		if( !commitAddressBookToFile() ) {
			// TODO: we weren't able to commit the address book to a file, what do we do?
			// for now, print an error and continue to exit
			System.err.println("commitAddressBookToFile() failed!");
		}
		System.exit(0);
	}
	
	// LOCAL METHODS //

	/**
	 * Falsifies freshness and updates window title.
	 */
	private void makeStale() {
		if( !isAddressBookFresh ) return;
		isAddressBookFresh = false;
		updateWindowTitle();
	}

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
		
		// center window on desktop		
		mainFrame.setLocationRelativeTo(null);
		
		mainFrame.setVisible(true);
		

		openFile(null);
	}

	/**
	 * Creates and begins management of the graphical user interface in a new thread.
	 */
	public static void createAndShowGUI(/* what args? */) {
		// detect operating system
		boolean isMac = false;
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) { // do Mac stuff
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			isMac = true;
		}

		// ask Swing to use the Runnable of this class in a new thread
		SwingUtilities.invokeLater(new VirtualPond(isMac));
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
