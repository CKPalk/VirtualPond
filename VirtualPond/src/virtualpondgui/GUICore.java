package virtualpondgui;

import java.awt.Component;
import java.io.File;
import java.net.URI;

import javax.swing.JFileChooser;

import addressbook.Contact;
import addressbook.VirtualAddressBook;

/**
 * The interface through which GUI components can talk to the main program class.
 * 
 * @author atleebrink
 *
 */
public interface GUICore {
	/**
	 * Does nothing if contact == null,
	 * else adds this contact to the current address book.
	 * @param contact an extant Contact or null.
	 */
	void addContact(Contact contact);

	/**
	 * If there is an unmodified file open, then prompts to save.
	 * Else just prepares default empty address book.
	 */
	void closeFile();

	/**
	 * Deletes 0 or more contacts from the current address book.
	 * @param indices an array of indices into the address book.
	 */
	void deleteContactsByIndex(int[] indices);

	/**
	 * Pops up a modal Edit Contact dialog box.
	 * @param contact the initial Contact to edit, which may be null.
	 * @return the modified Contact, or null if the user cancelled.
	 */
	Contact editContactDialog(String title, Contact contact);

	/**
	 * Returns an array of selected contact indices.
	 * @return an array of indices, which may be empty.
	 */
	int[] getSelectedIndices();

	VirtualAddressBook getCurrentAddressBook();

	JFileChooser getFileChooser();

	Component getMainWindow();

	/**
	 * Returns a reference to the indexed Contact.
	 * @param index
	 * @return the indexed Contact or null.
	 */
	Contact getContactByIndex(int index);

	/**
	 * @return a URI to an HTML User Manual, or null.
	 */
	URI getUserManualURI();

	/**
	 * IF an untitled, empty address book is loaded, do nothing.
	 * IF a non-empty address book is loaded,
	 *   THEN open a new instance of the program with an empty, untitled address book.  
	 */
	void onFileNew();

	/**
	 * Prompts user for file name FIRST,
	 * THEN IF it seems to be valid:
	 *   IF an untitled, empty address book is loaded, reuse this window,
	 *   ELSE start a new instance of the program and load it there.
	 * ALLOW the user to cancel.
	 */
	void onFileOpen();
	
	/**
	 * Attempts to open a file and make it the current address book for the current window.
	 * @param file an extant file to open
	 */
	void openFile(File file);
	
	/**
	 * If there is a loaded address book then unloads it.
	 * Creates a new VirtualAddressBook with default columns and no contacts.
	 */
	void prepareEmptyBook();
	
	/**
	 * Attempts to save the current address book to the current file,
	 * or prompts the user if there is no current filename.
	 */
	boolean saveFile();
	
	/**
	 * Prompts the user for a new filename, and attempts to save there.
	 */
	void saveFileAs();
	
	/**
	 * Shows the user manual.
	 */
	void showUserManual();
	
	/**
	 * Overwrites a contact at an existing index with new values.
	 * @param index the index of the modification.
	 * @param contact the new values to put there.
	 */
	void updateContactAtIndex(int index, Contact contact);
	
	/**
	 * Tells the main program that the user wants to quit.
	 * This may result in a confirmation prompt, and thus
	 * may not actually exit the program. 
	 */
	void quit();
	
}
