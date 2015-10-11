package virtualpondgui;

import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import addressbook.Field;

public class EditArea extends JPanel {
	private GUICore guiCore;
	private ArrayList<EntryFieldEditor> entryFields;
	
	public EditArea(GUICore guiCore) {
		super(new FlowLayout(FlowLayout.CENTER));
		this.guiCore = guiCore;
		populate();
	}
	
	public void populate() {
		if(guiCore.getCurrentAddressBook() == null) return;
		removeAll();
		ArrayList<Field> fields = guiCore.getCurrentAddressBook().getFields();
		entryFields = new ArrayList<>();
		for( Field field : fields) {
			EntryFieldEditor entryField = new EntryFieldEditor(field.toString(), "", field);
			entryFields.add(entryField);
			add(entryField);
		}
	}
}
