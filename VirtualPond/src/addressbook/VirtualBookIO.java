package addressbook;

import java.io.File;

public interface VirtualBookIO {

	final static String VIRTUAL_ADDRESS_BOOK_EXTENSION = "pond™";
	final static String USER_SAVETO_FOLDER_NAME = "Virtual Address Book™";
	final static String FILE_CHARACTER_DIVIDER_REGEX = "\t"; // Tab
	final static boolean DEFAULT_REQUIRED_FIELD_BOOL = false;
	
	public String getDividerRegex();
	
	public void setFile(File newFile);
	public File getFile();
	
	
}
