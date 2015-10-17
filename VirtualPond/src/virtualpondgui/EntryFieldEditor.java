package virtualpondgui;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import addressbook.Field;

/**
 * A JLabel + JTextField pair used to display and edit a contact field.
 * 
 * @author atleebrink
 *
 */
public class EntryFieldEditor extends JPanel {
	private static final long serialVersionUID = 1L;

	private JTextField field;
	
	public EntryFieldEditor( String name, String initialContent, Field contactField, int prefWidth ) {
		setLayout( new BoxLayout( this, BoxLayout.X_AXIS ) );

		add( new JLabel(name) );
		add( Box.createHorizontalStrut(5) );
		field = new JTextField(initialContent);
		field.setInputVerifier( new FieldVerifier(contactField) );
		add(field);

		Dimension dim = getPreferredSize();
		dim.width = prefWidth;
		setPreferredSize( dim );
	}
	
	public String getFieldValue() {
		return field.getText();
	}
	
	public class FieldVerifier extends InputVerifier {
		private Field contactField;
		
		public FieldVerifier(Field contactField) {
			this.contactField = contactField;
		}
		
		@Override
		public boolean verify(JComponent input) {
			JTextField textField = (JTextField)input;
			return contactField.validate(textField.getText());
		}
	}
}
