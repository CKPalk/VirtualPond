package virtualpondgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Reactor {
	abstract void onAdd();

	public class onAddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) { onAdd(); }
	}
	
	public onAddListener onaddlistener = new onAddListener();
}
