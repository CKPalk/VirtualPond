package virtualpondgui;

import java.awt.*;
import java.io.File;
import java.net.URI;

import javax.swing.*;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	final static String USER_MANUAL = "https://www.assembla.com/spaces/cis422f15-team1/wiki/Software_Documentation";
	private static URI URI_USER_MANUAL = null;
	
	private JFrame mainFrame = null;
	
	/* GUICore methods:
	 * These methods are exposed to Reactors,
	 * which are classes that process user input from the GUI.
	 */
	
	@Override
	public Component getMainWindow() {
		return mainFrame;
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
		// TODO: attempt to open a file	
		System.out.println("attempting to open file" + file.getAbsolutePath());
	}
	
	@Override
	public void quit() {
		// TODO: IF unsaved changes THEN ask user to save OR cancel OR discard changes.
		System.exit(0);
	}
	
	/* Private methods:
	 * These methods do secret things,
	 * that other classes don't need to know about.
	 */

	/**
	 * Implements Runnable.run() to create GUI components,
	 * and provides those components with a GUICore interface to this class.
	 */
	public void run() {
		mainFrame = new JFrame("(empty address book)" + " - " + TITLE);

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainFrame.setJMenuBar(new MenuBar(this));
		
		mainFrame.setContentPane(new MainContentPanel(this));

		mainFrame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		mainFrame.pack();
		mainFrame.setVisible(true);
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
