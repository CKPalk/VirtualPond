package addressbook;

public class InputValidation {
	public static boolean validateStringForIndex(String input, int index) {
		switch(index) {
		case 0: // City
		case 5: // Last name
		case 6: // First name
			return input.matches("^[a-zA-Z]+$");
		case 1: // State
			return input.matches("^[a-zA-Z]{2}$");
		case 2: // Zipcode
			return input.matches("^[0-9]+$");
		case 3: // First Address
		case 4: // Second Address
			return input.matches("\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b");
		case 7: // Phone number
			return input.matches("[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}");
		default: // No validation for indexes above our default 8
			return true;
		}
	}
	
	public static String getValidationWarningForIndex(int index) {
		switch (index) {
		case 0: // City
		return "";
		case 1: // State
		return "";
		case 2: // Zipcode
		return "";
		case 3: // First Address
		return "";
		case 4: // Second Address
		return "";
		case 5: // Last name
		return "";
		case 6: // First name
		return "";
		case 7: // Phone number
		return "";
		default:
		return "";
		}
	}
}