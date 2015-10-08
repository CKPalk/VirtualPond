package virtualpondgui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIMenuBar {
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
	
	public static JMenuBar create(Reactor reactor) {
		// contains File, Help
		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu file = new JMenu("File");
		
		// create File menu items
		JMenuItem fileNew = new JMenuItem("New");
		JMenuItem fileOpen = new JMenuItem("Open...");
		JMenuItem fileClose = new JMenuItem("Close");
		JMenuItem fileSave = new JMenuItem("Save");
		JMenuItem fileSaveAs = new JMenuItem("Save As...");
		JMenuItem fileQuit = new JMenuItem("Quit");

		// assign reactions to File menu items
		fileNew.addActionListener(event -> reactor.onFileNew());
		fileOpen.addActionListener(event -> reactor.onFileOpen());
		fileClose.addActionListener(event -> reactor.onFileClose());
		fileSave.addActionListener(event -> reactor.onFileSave());
		fileSaveAs.addActionListener(event -> reactor.onFileSaveAs());
		fileQuit.addActionListener(event -> reactor.onFileQuit());

		// add menu items to File menu
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
		
		// create Help menu items
		JMenuItem helpUserManual = new JMenuItem("User Manual");
		JMenuItem helpAbout = new JMenuItem("About");
		
		// assign reactions to Help menu items
		helpUserManual.addActionListener(event -> reactor.onHelpUserManual());
		helpAbout.addActionListener(event -> reactor.onHelpAbout());
		
		// add menu items to Help menu
		help.add(helpUserManual);
		help.add(helpAbout);

		// add menus to menuBar
		menuBar.add(file);
		menuBar.add(help);

		// make sure the menuBar is not hidden
		menuBar.setVisible(true);

		// return our newly populated menuBar
		return menuBar;
	}
}
