package virtualpondgui;

import javax.swing.JOptionPane;

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
		guiCore.onFileNew();
	}
	
	public void onFileOpen() {
		guiCore.onFileOpen();
	}
	
	public void onFileClose() {
		guiCore.closeFile();
	}
	
	public void onFileSave() {
		guiCore.saveFile();
	}
	
	public void onFileSaveAs() {
		guiCore.saveFileAs();
	}
	
	public void onFileQuit() {
		guiCore.quit();
	}
	
	public void onHelpUserManual() {
		guiCore.showUserManual();
	}
	
	public void onHelpAbout() {
		// TODO: show an About dialog with some basic info about this program,
		// such as version (build date maybe), authors, etc.
		JOptionPane.showMessageDialog(guiCore.getMainWindow(),
				"VirtualPond Address Book\nBy the Virtual Ducks, 2015",
				"About VirtualPond",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
