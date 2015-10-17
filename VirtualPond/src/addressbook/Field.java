package addressbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Field {

	// hard-coded indices: follow these for default address books
	public static final int CITY = 0;
	public static final int STATE = 1;
	public static final int ZIP = 2;
	public static final int DELIVERY = 3;
	public static final int SECOND = 4;
	public static final int LASTNAME = 5;
	public static final int FIRSTNAME = 6;
	public static final int PHONE = 7;
	public static final int NUM_DEFAULT = 8;

	// maps hard-coded indices to friendly names to use for display purposes
	public static final HashMap<Integer, String> friendlyNames = new HashMap<Integer, String>(NUM_DEFAULT) {
		private static final long serialVersionUID = 1L;
	{
		put(CITY, "City");
		put(STATE, "State");
		put(ZIP, "Zip");
		put(DELIVERY, "Address 1");
		put(SECOND, "Address 2");
		put(LASTNAME, "Last Name");
		put(FIRSTNAME, "First Name");
		put(PHONE, "Phone Number");		
	}};

	// maps hard-coded indices to Tab Separated Value formatted file columns
	public static final HashMap<Integer, String> tsvNames = new HashMap<Integer, String>(NUM_DEFAULT) {
		private static final long serialVersionUID = 1L;
	{
		put(CITY, "CITY");
		put(STATE, "STATE");
		put(ZIP, "ZIP");
		put(DELIVERY, "Delivery");
		put(SECOND, "Second");
		put(LASTNAME, "LastName");
		put(FIRSTNAME, "FirstName");
		put(PHONE, "Phone");
	}};
	
	// this field's data
	public String name;
	public InputValidationCode valCode;

	// constructor
	public Field(String data) {
		this.name = data;
	}
	
	// utility
	public static List<Field> createDefaultFieldList() {
		return new ArrayList<Field>(NUM_DEFAULT) {
			private static final long serialVersionUID = 1L;
		{
			for( int i = 0; i < NUM_DEFAULT; i++ ) add( new Field( tsvNames.get(i) ) );
		}};
	}
	
	// --- Public Class Methods ---
	@Override
	public String toString() {
		return name;
	}
	
	public String getValCode() {
		return valCode.getData();
	}
	
	// --- FIELD VALIDATION ---
	public static InputValidationCode resolveValidationCode(String valString) {
		if (valString.length() == 0) {
			return InputValidationCode.NONE;
		}
		else {
			switch(valString.charAt(0)) {
			case 't':
				return InputValidationCode.TEXT;
			case 'n':
				return InputValidationCode.NUMBER;
			case 'p':
				return InputValidationCode.PHONE;
			case 'e':
				return InputValidationCode.EMAIL;
			case 'a':
				return InputValidationCode.ADDRESS;
			default:
				System.err.println("Unknown validation code " + valString.charAt(0) + " in String " + valString);
				return InputValidationCode.NONE;
			}
		}
	}
	
	public boolean validate(String data) {
		
		// TODO: Complete data validation when we come up with a system
		return true;
		
		
		
		/* ReGeX validators
		
		switch(this.valCode) {
		case NONE:
			return true;
		case TEXT:
			return data.matches("^[a-zA-Z]+$");
		case NUMBER:
			return data.matches("^[0-9]+$");
		case PHONE:
			return data.matches("[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}");
		case EMAIL:
			return data.matches("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
		case ADDRESS:
			return true;
		default: // Shouldn't happen.
			System.err.println("Validation code did not hit when validating string #" + data);
			return false;
		}
		
		*/
		
	}

}
