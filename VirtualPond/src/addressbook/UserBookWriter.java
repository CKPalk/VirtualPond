package addressbook;

import java.nio.file.Path;

public class UserBookWriter {
	
	// --- Constructor ---
	UserBookWriter(UserAddressBook addressBook) {
		this.addressBook = addressBook;
	}
	
	
	// --- Private Data Members ---
	@SuppressWarnings("unused")
	private UserAddressBook addressBook;
	private Path filepath;
	
	
	// --- Public Class Methods ---
	
	// FUNCTIONALITY:
	public void write() {
		
		
		// TODO: Print user book to file. Whichever custom way he chooses.
		
		
	}
	
	
	
	// SETTERS:
	public void setBook(UserAddressBook newBook) {
		addressBook = newBook;
	}
	public void setPath(Path newpath) {
		filepath = newpath;
	}
	// GETTERS:
	public Path getPath() {
		return filepath;
	}
	
}
