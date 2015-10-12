package virtualpondgui;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

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
	private String addressBookName = null; // this is for display purposes only, and should not be used for file interactions
	private String addressBookFileName = null; // if this is null, then the user hasn't chosen a file name yet
	private VirtualAddressBook addressBook = null; // we don't want this to be null: load a default empty VirtualAddressBook ASAP
	private boolean isStale = false; // true => there are unsaved changes.
	
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
	public void closeFile() {
		prepareEmptyBook();
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
		makeStale();
	}
	
	/**
	 * @return true if user selected Save, else false
	 */
	@Override
	public Contact editContactDialog(String title, Contact initialContact) {
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
			FileFilter fileFilter = new FileNameExtensionFilter("Pond Address Book", "pond", "pond™"); 
			fileChooser.setFileFilter(fileFilter);
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
	public void openFile(File file) {
		writeUnsavedChangesToFile();
		addressBook = VirtualAddressBook.createFromFile(file);
		addressBookName = file.getName();
		addressBookFileName = file.getAbsolutePath();
		mainContentPanel.resetContactsTable();
		isStale = false;
		updateWindowTitle();
	}
	
	@Override
	public void prepareEmptyBook() {
		writeUnsavedChangesToFile(); // takes care of the last address book we had loaded, if any
		addressBook = VirtualAddressBook.createDefaultBook(); // gives us default fields with no contacts
		addressBookName = "untitled"; // what will show up in the window title
		addressBookFileName = null; // the file actually associated with this address book
		mainContentPanel.resetContactsTable(); // refresh the table
		isStale = false;
		updateWindowTitle();
	}
	
	@Override
	public void saveFile() {
		commitAddressBookToFile();
	}
	
	@Override
	public void saveFileAs() {
		JFileChooser fc = getFileChooser();
		int retCode = fc.showSaveDialog(mainFrame);
		if (retCode == JFileChooser.APPROVE_OPTION) {
			// TODO: check if the chosen file already exists: if so, confirm overwrite
			File file = enforceOurFileExtension(fc.getSelectedFile());
			addressBookName = file.getName();
			addressBookFileName = file.getAbsolutePath();
			commitAddressBookToFile();
			isStale = false;
			updateWindowTitle();
		}
	}
	
	@Override
	public void updateContactAtIndex(int index, Contact contact) {
		if( index < 0 || contact == null ) return;
		addressBook.getContacts().set(index, contact);
		mainContentPanel.updateContactAtIndex(index);
		makeStale();
	}
	
	@Override
	public void quit() {
		// TODO: IF unsaved changes THEN ask user to save OR cancel OR discard changes.
		if( !writeUnsavedChangesToFile() ) {
			// TODO: we weren't able to commit the address book to a file, what do we do?
			// for now, print an error and continue to exit
			System.err.println("commitAddressBookToFile() failed!");
		}
		System.exit(0);
	}
	
	// LOCAL METHODS //

	private void makeFresh() {
		if( isStale ) {
			isStale = false;
			updateWindowTitle();
		}
	}

	private void makeStale() {
		if( isStale ) return;
		isStale = true;
		updateWindowTitle();
	}

	private boolean writeUnsavedChangesToFile() {
		if( isStale ) return commitAddressBookToFile();
		return true;
	}
	
	/**
	 * Attempts to write out the current address book to a file.
	 * If addressBookFileName == null, prompts the user for a filename,
	 * else uses the existing filename.
	 * @return true on success, false otherwise
	 */
	private boolean commitAddressBookToFile() {
		//if( isAddressBookFresh ) return true; // nothing to do
		// else we need to make sure we have a filename for saving to
		
		File fileToSaveTo = null;
		// WARNING: I changed this comparator and I don't know what effect it causes
		// but it got the write working as opening a file means it has a name I guess.
		if( addressBookFileName == null ) {
			JFileChooser fc = getFileChooser();
			int retCode = fc.showSaveDialog(getMainWindow());
			if (retCode == JFileChooser.APPROVE_OPTION) {
				fileToSaveTo = enforceOurFileExtension(fileChooser.getSelectedFile());
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
		addressBookName = fileToSaveTo.getName();
		addressBookFileName = fileToSaveTo.getAbsolutePath();
		makeFresh();
		return true;
	}
	
	private File enforceOurFileExtension(File file) {
		String filename = file.getAbsolutePath();
		// TODO: get these extensions elsewhere and check all of them
		if( !filename.endsWith(".pond") && !filename.endsWith(".pond™") ) {
			// TODO: get preferred extension and stick it on
			file = new File(filename + ".pond");
		}
		return file;
	}
	
	private void updateWindowTitle() {
		mainFrame.setTitle(addressBookName + (isStale ? "*" : "") + " - " + TITLE);
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

		prepareEmptyBook();
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
