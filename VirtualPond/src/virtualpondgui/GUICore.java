package virtualpondgui;

import java.awt.Component;
import java.io.File;
import java.net.URI;

import javax.swing.JFileChooser;

import addressbook.VirtualAddressBook;

public interface GUICore {
	// TODO: expose public methods of GUICore
	
	VirtualAddressBook getCurrentAddressBook();
	
	JFileChooser getFileChooser();
	
	Component getMainWindow();
	
	/**
	 * @return a URI to an HTML User Manual, or null.
	 */
	URI getUserManualURI();
		
	/**
	 * Attempts to open a file and make it the current address book for the current window.
	 * @param file an extant file to open
	 */
	void openFile(File file);
	
	/**
	 * Tells the main program that the user wants to quit.
	 * This may result in a confirmation prompt, and thus
	 * may not actually exit the program. 
	 */
	void quit();
}
