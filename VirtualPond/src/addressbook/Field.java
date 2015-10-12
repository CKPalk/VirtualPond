package addressbook;

public class Field {

	public Field(String data, boolean required) {
		this.name = data;
		this.required = required;
	}
	
	public String name;
	public InputValidationCode valCode;
	private boolean required;
	
	
	// --- Public Class Methods ---
	@Override public String toString() {
		return this.name;
	}
	
	public String getValCode() {
		return this.valCode.getData();
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
