package virtualpondgui;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TopButtonBar extends JPanel {
	private static final long serialVersionUID = 1L;

	public interface Reactor {
		void onAdd();
		void onEdit();
		void onDelete();
	}
	
	private JButton buttonAdd, buttonEdit, buttonDelete;
	
	public TopButtonBar(Reactor reactor) {
		super(new FlowLayout());

		// buttons
		buttonAdd = new JButton("Add");
		buttonEdit = new JButton("Edit");
		buttonDelete = new JButton("Delete");
		
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
