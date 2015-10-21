package addressbook;

public class InputValidation {
	
	/* Validation for user inputs 
	 * The static field indexes can be found in Field.java 
	 */
	public static boolean isValidStringForFieldIndex(String input, int index) {
		
		// Checks for empty inputs and of course those are valid.
		// Removed .trim() on input so any input with trailing spaces is probably going to return false
		if (input.equals("")) return true;
		
		switch(index) {
		case Field.CITY: // City
		case Field.LASTNAME: // Last name
		case Field.FIRSTNAME: // First name
			return input.matches( "^[a-zA-Z-]+$" );
		case Field.STATE: // State
			return input.matches( "^[a-zA-Z]{2}$" );
		case Field.ZIP: // Zipcode
			return input.matches( "^[0-9]{5}(-[0-9]{4})?$" );
		case Field.DELIVERY: // First Address
		case Field.SECOND: // Second Address
			return input.matches("^[a-zA-Z0-9. \\/-]+$");
			// This is for email validation, but we aren't using that
			// return input.matches( "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b" );
		case Field.PHONE: // Phone number
			return input.matches( "^[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}$" );
		default: 
			// No validation for indexes above our default 8
			// Always returns true
			return true;
		}
	}
	
	public static String getValidationWarningForIndex(int index) {
		switch (index) {
		case Field.CITY: // City
			return "The city usually are composed of letters only.";
		case Field.STATE: // State
			return "The state usually are composed of letters only.";
		case Field.ZIP: // Zipcode
			return "Zipcodes usually match a pattern such as ###### or ######-####.";
		case Field.DELIVERY: // First Address
			return "Your address has characters that are not usually in an address.";
		case Field.SECOND: // Second Address
			return "Your second address has characters that are not usually in an address.";
		case Field.LASTNAME: // Last name
			return "Last names are usually composed of letters and/or hyphens.";
		case Field.FIRSTNAME: // First name
			return "First names are usually composed of letters and/or hyphens.";
		case Field.PHONE: // Phone number
			return "Phone numbers usually match a pattern such as ###-###-####";
		default:
			System.err.println("Default hit in switch statement while getting validation warning.");
			return "Have a wonderful day!";
		}
	}
}