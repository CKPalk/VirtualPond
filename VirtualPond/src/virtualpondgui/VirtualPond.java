package virtualpondgui;

import java.awt.*;
import javax.swing.*;

import addressbook.*;

public class VirtualPond implements Runnable, GUICore {
	final static String TITLE = "Virtual Pond";
	final static int WINDOW_WIDTH = 800;
	final static int WINDOW_HEIGHT = 600;
	
	/* GUICore methods:
	 * These methods are exposed to Reactors,
	 * which are classes that process user input from the GUI.
	 */
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
		JFrame frame = new JFrame("(empty address book)" + " - " + TITLE);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setJMenuBar(new MenuBar(this));
		
		frame.setContentPane(new MainContentPanel(this));

		frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		frame.pack();
		frame.setVisible(true);
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
