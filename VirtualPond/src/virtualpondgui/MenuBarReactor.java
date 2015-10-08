package virtualpondgui;

/**
 * Implements reactions to user inputs from the MenuBar.
 * Some actions are handled here, while others are
 * re-delegated from here to GUICore.
 * 
 * @author atleebrink
 */
public class MenuBarReactor implements GUIMenuBar.Reactor {
	private GUICore guiCore;
	
	public MenuBarReactor(GUICore guiCore) {
		this.guiCore = guiCore;
	}
	
	public void onFileNew() {
		System.out.println("File/New not implemented");
	}
	
	public void onFileOpen() {
		System.out.println("File/Open not implemented");
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
		// TODO: guiCore should probably handle quit, BECAUSE
		// there are various ways in which quit might be requested:
		// by closing the window, by selection File/Quit,
		// from the operating system, and maybe other ways.
		guiCore.quit();
	}
	
	public void onHelpUserManual() {
		System.out.println("Help/UserManual not implemented");
	}
	
	public void onHelpAbout() {
		// TODO: show an About dialog with some basic info about this program,
		// such as version (build date maybe), authors, etc.
		System.out.println("Help/About not implemented");
	}
}
