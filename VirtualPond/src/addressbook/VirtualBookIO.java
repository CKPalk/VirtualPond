package addressbook;

import java.io.File;
import java.util.ArrayList;

public class VirtualBookIO {

	final static String VIRTUAL_ADDRESS_BOOK_EXTENSION = "pond";
	final static String USER_SAVETO_FOLDER_NAME = "Virtual Address Book";
	final static String FILE_CHARACTER_DIVIDER_REGEX = "\t"; // Tab
	final static boolean DEFAULT_REQUIRED_FIELD_BOOL = false;
	
	public static final ArrayList<Field> getDefaultFields = Field.createDefaultFieldList();
	
	
	public static VirtualAddressBook openBook(File file) {
		return new VirtualBookReader(file).read();
	}
	
	public static boolean writeBook(VirtualAddressBook vab, File file) {
		return new VirtualBookWriter(vab, file).write();
	}
	
	
}
