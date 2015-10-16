package virtualpondgui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * The JMenuBar that acts as the main menu for the program.
 * 
 * @author atleebrink
 *
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public interface Reactor {
		void onFileNew();
		void onFileOpen();
		void onFileClose();
		void onFileSave();
		void onFileSaveAs();
		void onFileQuit();
		void onHelpUserManual();
		void onHelpAbout();
	}
	
	public MenuBar(GUICore guiCore) {
		MenuBarReactor reactor = new MenuBarReactor(guiCore);
		
		// File menu
		JMenu file = new JMenu("File");
		
		// File menu items
		JMenuItem fileNew = new JMenuItem("New");
		JMenuItem fileOpen = new JMenuItem("Open...");
		JMenuItem fileClose = new JMenuItem("Close");
		JMenuItem fileSave = new JMenuItem("Save");
		JMenuItem fileSaveAs = new JMenuItem("Save As...");
		JMenuItem fileQuit = new JMenuItem("Quit");

		// reactions to File menu items
		fileNew.addActionListener(event -> reactor.onFileNew());
		fileOpen.addActionListener(event -> reactor.onFileOpen());
		fileClose.addActionListener(event -> reactor.onFileClose());
		fileSave.addActionListener(event -> reactor.onFileSave());
		fileSaveAs.addActionListener(event -> reactor.onFileSaveAs());
		fileQuit.addActionListener(event -> reactor.onFileQuit());

		// add items to File menu
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
		
		// Help menu items
		JMenuItem helpUserManual = new JMenuItem("User Manual");
		JMenuItem helpAbout = new JMenuItem("About");
		
		// reactions to Help menu items
		helpUserManual.addActionListener(event -> reactor.onHelpUserManual());
		helpAbout.addActionListener(event -> reactor.onHelpAbout());
		
		// add items to Help menu
		help.add(helpUserManual);
		help.add(helpAbout);

		// add menus to menuBar
		add(file);
		add(help);

		// make sure the menuBar is not hidden
		setVisible(true);
	}
}
