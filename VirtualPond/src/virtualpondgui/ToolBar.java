package virtualpondgui;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The JPanel that makes up the tool bar.
 * 
 * @author atleebrink
 *
 */
public class ToolBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public interface Reactor {
		void onAdd();
		void onEdit();
		void onDelete();
	}
	
	private JButton buttonAdd, buttonEdit, buttonDelete;
	
	public ToolBar(Reactor reactor) {
		super(new FlowLayout(FlowLayout.LEFT));

		// buttons
		buttonAdd = new JButton("Add Contact");
		buttonEdit = new JButton("Edit Selected Contact");
		buttonDelete = new JButton("Delete Selected Contact(s)");

		// reactions
		buttonAdd.addActionListener(event -> reactor.onAdd());
		buttonEdit.addActionListener(event -> reactor.onEdit());
		buttonDelete.addActionListener(event -> reactor.onDelete());

		// add to self
		add(buttonAdd);
		add(buttonEdit);
		add(Box.createHorizontalStrut(20));
		add(buttonDelete);
	}
}
