package virtualpondgui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	private final static String TITLE = "Virtual Pond";
	private final static int WINDOW_WIDTH = 800;
	private final static int WINDOW_HEIGHT = 600;
	private final static String USER_MANUAL = "https://www.assembla.com/spaces/cis422f15-team1/wiki/Software_Documentation";
	private final static String ourNodeName = "com/VirtualDucks/VirtualPond";
	private static URI URI_USER_MANUAL = null;
	private static String initialBookFileName = null; // set in main(...) IF should be used, else remains null
	
	private JFrame mainFrame = null;
	private MainContentPanel mainContentPanel = null;
	private JFileChooser fileChooser = null;

	// current address book information
	private String currentBookName = null; // this is for display purposes only, and should not be used for file interactions
	private String currentBookFileName = null; // if this is null, then the user hasn't chosen a file name yet
	private VirtualAddressBook currentBook = null; // we don't want this to be null: load a default empty VirtualAddressBook ASAP
	private boolean isStale = false; // true => there are unsaved changes.
	
	// GUICore METHODS //
	
	/**
	 * Adds a contact to the address book, and displays it
	 * in the table.
	 */
	@Override
	public void addContact(Contact contact) {
		if( contact == null ) return;
		currentBook.newContact(contact);
		mainContentPanel.addContactToTable(currentBook.getContacts().size());
		makeStale();
	}
	
	@Override
	public void closeFile() {
		if( isStale ) {
			String[] options = {"Save Changes", "Discard Changes", "Don't Close" };
			int n = JOptionPane.showOptionDialog( mainFrame,
					"There are unsaved changes in this address book!\nWhat do you want to do?",
					"Unsaved Changes!",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0] );
			switch( n ) {
			case 0: // Save Changes
				if( saveFile() ) break; // try to save file
				else return; // fail safely by NOT quitting
			case 1: // Discard Changes
				currentBookFileName = null;
				isStale = false;
				break;
			case 2: // Don't Close
				return;
			default:
			}
		}
		prepareEmptyBook();
	}
	
	@Override
	public void deleteContactsByIndex(int[] indices) {
		if( indices == null || indices.length < 1 ) return;
		// it is critical that contacts are removed from higher indices to lower,
		// else the indices would become invalid after a removal.
		Arrays.sort(indices);
		ArrayList<Contact> contacts = currentBook.getContacts();
		for( int i = indices.length - 1; i >= 0; i-- ) contacts.remove(indices[i]);
		mainContentPanel.deleteContacts(indices, true);
		makeStale();
	}
	
	/**
	 * @return true if user selected Save, else false
	 */
	@Override
	public Contact editContactDialog(String title, Contact initialContact) {
		EditContactDialog ecDialog = new EditContactDialog(mainFrame, title, currentBook, initialContact);
		return ecDialog.getResult();
	}
	
	@Override
	public int[] getSelectedIndices() {
		return mainContentPanel.getAllSelectedEntryRows();
	}
	
	@Override
	public VirtualAddressBook getCurrentAddressBook() {
		return currentBook;
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
		if( index >= currentBook.getContacts().size() ) return null;
		return currentBook.getContacts().get(index);
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
	public void onFileNew() {
		if( currentBookFileName == null && !isStale ) {
			prepareEmptyBook(); // this won't do much, if anything
		} else {
			startNewProcess("-n");
		}
	}
	
	@Override
	public void onFileOpen() {
		JFileChooser fileChooser = getFileChooser();
		int choice = fileChooser.showOpenDialog(mainFrame);
		if( choice == JFileChooser.APPROVE_OPTION ) {
			
			File fileToOpen = fileChooser.getSelectedFile();
			if( fileToOpen.exists() ) {
				if( currentBookFileName == null && !isStale ) {
					// reuse this window
					openFile( fileToOpen );
				} else {
					// start a new instance of VirtualPond, pass the filename as a parameter
					startNewProcess(fileToOpen.getAbsolutePath());
				}
				
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"The selected file doesn't exist!",
						"Imaginary file!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	@Override
	public void openFile(File file) {
		writeUnsavedChangesToCurrentFile();
		currentBook = VirtualAddressBook.createFromFile(file);
		currentBookName = file.getName();
		currentBookFileName = file.getAbsolutePath();
		mainContentPanel.resetContactsTable();
		isStale = false;
		updateWindowTitle();
	}
	
	@Override
	public void prepareEmptyBook() {
		writeUnsavedChangesToCurrentFile(); // takes care of the last address book we had loaded, if any
		currentBook = VirtualAddressBook.createDefaultBook(); // gives us default fields with no contacts
		currentBookName = "untitled"; // what will show up in the window title
		currentBookFileName = null; // the file actually associated with this address book
		mainContentPanel.resetContactsTable(); // refresh the table
		isStale = false;
		updateWindowTitle();
	}
	
	@Override
	public void quit() {
		if( isStale ) {
			String[] options = {"Save Changes", "Discard Changes", "Don't Quit" };
			int n = JOptionPane.showOptionDialog( mainFrame,
					"There are unsaved changes in this address book!\nWhat do you want to do?",
					"Unsaved Changes!",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE,
					null, options, options[0] );
			switch( n ) {
			case 0: // Save Changes
				if( saveFile() ) break; // try to save file
				else return; // fail safely by NOT quitting
			case 1: // Discard Changes
				currentBookFileName = null;
				isStale = false;
				break;
			case 2: // Don't Quit
				return;
			default:
			}
		}

		// remember the current address book by filename
		if( currentBookFileName != null ) {
			Preferences.userRoot().node(ourNodeName).put("previousBookFileName", currentBookFileName);
		}
		System.exit(0);
	}
	
	@Override
	public boolean saveFile() {
		return commitAddressBookToFile();
	}
	
	@Override
	public void saveFileAs() {
		JFileChooser fc = getFileChooser();
		int retCode = fc.showSaveDialog(mainFrame);
		if (retCode == JFileChooser.APPROVE_OPTION) {
			
			File file = enforceOurFileExtension(fc.getSelectedFile());
			if( file.exists() && !promptOverwrite(file) ) return;
			
			currentBookName = file.getName();
			currentBookFileName = file.getAbsolutePath();
			commitAddressBookToFile();
			
			isStale = false;
			updateWindowTitle();
		}
	}
	
	@Override
	public void showUserManual() {
		// thanks, http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
		try {
			Desktop.getDesktop().browse(getUserManualURI());
		} catch (IOException e) {
			System.out.println("There was an I/O problem while attempting to open the User Manual. Good luck!");
		}
	}
	
	@Override
	public void updateContactAtIndex(int index, Contact contact) {
		if( index < 0 || contact == null ) return;
		currentBook.getContacts().set(index, contact);
		mainContentPanel.updateContactAtIndex(index);
		makeStale();
	}
	
	// LOCAL METHODS //
	private void makeStale() {
		if( isStale ) return;
		isStale = true;
		updateWindowTitle();
	}
	
	/**
	 * Starts a separate instance of VirtualPond with the specified parameters.
	 * @param parameters
	 */
	private void startNewProcess(String parameters) {
		// get full path of this class in the file system
		ClassLoader loader = this.getClass().getClassLoader();
		String className = "virtualpondgui/VirtualPond.class";
		String execPath = loader.getResource(className).toString();
		
		// format path for operating system
		String OS = System.getProperty("os.name").toLowerCase();
		int startTrim = execPath.indexOf("file:")
				+ ( OS.contains("windows") ? "file:/".length() : "file:".length() );
		
		// are we in a jar?
		// format path for jar/not jar
		boolean inJar = execPath.startsWith("jar:");
		int endTrim = inJar ? execPath.indexOf("!") : execPath.lastIndexOf(className);
		
		// trim path
		execPath = execPath.substring(startTrim, endTrim);
		
		// create new instance of this program in a separate process
		try {
			if( inJar ) {
				Runtime.getRuntime().exec("java -jar " + execPath + " " + parameters);
			} else {
				Runtime.getRuntime().exec("java virtualpondgui.VirtualPond" + " " + parameters,
						null, new File(execPath));
			}
		} catch( IOException e ) {
			System.err.println("Error creating a new instance of VirtualPond!");
		}
	}

	private boolean writeUnsavedChangesToCurrentFile() {
		if( isStale ) return commitAddressBookToFile();
		return true;
	}
	
	/**
	 * Attempts to write out the current address book to a file.
	 * If addressBookFileName == null, prompts the user for a filename,
	 * else uses the existing filename.
	 * @return true on successful save, else false
	 */
	private boolean commitAddressBookToFile() {
		// get a file handle
		File fileToSaveTo = null;
		if( currentBookFileName == null ) { // prompt user for a file name
			int retCode = getFileChooser().showSaveDialog(getMainWindow());
			if (retCode == JFileChooser.APPROVE_OPTION) {
				fileToSaveTo = enforceOurFileExtension(fileChooser.getSelectedFile());
				if( fileToSaveTo.exists() && !promptOverwrite(fileToSaveTo) ) return false;
			} else {
				return false;
			}
		} else { // we already have a file name
			fileToSaveTo = new File(currentBookFileName);
		}
	
		// write the address book to the file
		if( currentBook.writeToFile(fileToSaveTo) ) { // success
			currentBookName = fileToSaveTo.getName();
			currentBookFileName = fileToSaveTo.getAbsolutePath();
			isStale = false;
			updateWindowTitle();
		} else { // failure
			JOptionPane.showMessageDialog(mainFrame,
					"There was a problem saving this address book to the file:\n"
					+ fileToSaveTo.getAbsolutePath(),
					"File save problem!",
					JOptionPane.ERROR_MESSAGE );
			return false;
		}
		return true;
	}
	
	/**
	 * Modal dialog that prompts the user to confirm overwriting the specified file.
	 * @param fileToOverwrite the destination file - this method doesn't touch the actual file
	 * @return true if user confirms, else false
	 */
	private boolean promptOverwrite(File fileToOverwrite) {
		String[] options = {"Overwrite", "Cancel"};
		int n = JOptionPane.showOptionDialog( mainFrame,
				"The selected file,\n\n"
				+ fileToOverwrite.getAbsolutePath() + "\n\n"
				+ "already exists!\n"
				+ "Do you want to overwrite that file?\n\n"
				+ "!! WARNING: this will permanently erase that file's contents !!",
				"File already exists!",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null, options, options[1] );
		return n == 0;
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
		mainFrame.setTitle(currentBookName + (isStale ? "*" : "") + " - " + TITLE);
	}
	
	/**
	 * Implements Runnable.run() to create GUI components,
	 * and provides those components with a GUICore interface to this class.
	 */
	public void run() {
		mainFrame = new JFrame(TITLE);
		
		// behavior
		mainFrame.addWindowListener(new MainWindowListener(this));
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		// contents
		mainFrame.setJMenuBar(new MenuBar(this));
		mainContentPanel = new MainContentPanel(this);
		mainFrame.setContentPane(mainContentPanel);

		// size
		mainFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		mainFrame.pack();
		
		// location		
		mainFrame.setLocationRelativeTo(null);
		
		// show
		mainFrame.setVisible(true);

		// reload previous address book, if possible
		if( initialBookFileName == null ) {
			prepareEmptyBook();
		} else {
			openFile( new File(initialBookFileName) );
		}
	}

	/**
	 * Creates and begins management of the graphical user interface in a new thread.
	 */
	public static void createAndShowGUI() {
		// detect operating system and do special things for consistency
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			// thanks, http://stackoverflow.com/questions/2061194/swing-on-osx-how-to-trap-command-q
			System.setProperty("apple.eawt.quitStrategy", "CLOSE_ALL_WINDOWS");
		}

		// ask Swing to use the Runnable of this class in a new thread
		SwingUtilities.invokeLater(new VirtualPond());
	}
	
	/**
	 * A simple main method.
	 * Immediately calls createAndShowGUI(), then returns.
	 * 
	 * @param args command line arguments:
	 *        -n  open an untitled, empty address book
	 */
	public static void main(String[] args) {

		// crude command-line parsing
		if( args.length > 0 ) {

			// check for -n parameter: "open an untitled, empty address book"
			if( Arrays.asList(args).contains("-n") ) { // do NOT open previous book
				initialBookFileName = null;
			} else {
				// assume last argument is the name of the file to open
				File fileToOpen = new File( args[args.length - 1] );
				if( fileToOpen.exists() ) {
					initialBookFileName = fileToOpen.getAbsolutePath();
				}
			}

		} else if( args.length == 0 ) { // else DO open previous book by default
			
			initialBookFileName = Preferences.userRoot().node(ourNodeName).get("previousBookFileName", null);
		
		}

		createAndShowGUI();
	}

	
}
