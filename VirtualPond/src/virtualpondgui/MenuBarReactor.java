package virtualpondgui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

/**
 * Implements reactions to user inputs from the MenuBar.
 * Some actions are handled here, while others are
 * re-delegated from here to GUICore.
 * 
 * @author atleebrink
 */
public class MenuBarReactor implements MenuBar.Reactor {
	private GUICore guiCore;
	
	public MenuBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onFileNew() {
		System.out.println("File/New not implemented");
	}
	
	public void onFileOpen() {
		// TODO: (should this reuse the current window or open a new instance of the program?)
		//   * close current address book
		//   * present the user with a file chooser
		//   * IF the user chose something, try to open it
		//   * ELSE the user cancelled, in which case we have an empty address book state
		// TODO: should this functionality be in GUICore?
		//   we may want to move if there if we find ourselves wanting to open files elsewhere
		
		onFileClose();
		
		// will probably want to move this so it can be shared with the Save and SaveAs codes
		JFileChooser fileChooser = guiCore.getFileChooser();
		
		// WARNING: this line blocks until the user makes a choice
		int retCode = fileChooser.showOpenDialog(guiCore.getMainWindow());
		if (retCode == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			guiCore.openFile(file);
		} else {
			// the user selected Cancel or dismissed the dialog or there was an error
		}
	}
	
	public void onFileClose() {
		System.out.println("File/Close not implemented");
	}
	
	public void onFileSave() {
		System.out.println("File/Save not implemented");
	}
	
	public void onFileSaveAs() {
		System.out.println("File/SaveAs not implemented");
	}
	
	public void onFileQuit() {
		guiCore.quit();
	}
	
	public void onHelpUserManual() {
		// thanks, http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
		// IF this is running on a desktop (which is probably is)
		if(Desktop.isDesktopSupported()) {
			// THEN try to open our user manual in a browser
			try {
				Desktop.getDesktop().browse(guiCore.getUserManualURI());
			} catch (IOException e) {
				// TODO: handle the case where the URI cannot be reached;
				// for now, show text on the console
				System.out.println("There was an I/O problem while attempting to open the User Manual. Good luck!");
			}
		} else {
			// ELSE
			// this case shouldn't happen since this is a desktop program, so let's pretend that it didn't happen.
		}
	}
	
	public void onHelpAbout() {
		// TODO: show an About dialog with some basic info about this program,
		// such as version (build date maybe), authors, etc.
		System.out.println("Help/About not implemented");
	}
}
