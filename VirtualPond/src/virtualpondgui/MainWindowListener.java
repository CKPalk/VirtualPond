package virtualpondgui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Responds to main window events.
 * 
 * @author atleebrink
 *
 */
public class MainWindowListener implements WindowListener {
	private GUICore guiCore;
	
	public MainWindowListener( GUICore guiCore ) {
		this.guiCore = guiCore;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		guiCore.quit();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
