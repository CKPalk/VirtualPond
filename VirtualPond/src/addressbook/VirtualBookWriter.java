package addressbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class VirtualBookWriter implements VirtualBookIO {

	// --- Constructor ---
	public VirtualBookWriter(VirtualAddressBook addressBook) {
		this.addressBook = addressBook;
		this.file = new File(this.toString());
	}
	public VirtualBookWriter(VirtualAddressBook addressBook, File file) {
		this.addressBook = addressBook;
		this.file = file;
	}
	
	
	// --- Private Data Members ---
	private VirtualAddressBook addressBook;
	private File file;
	
	
	// --- Public Class Methods ---
	
	// FUNCTIONALITY:
	public void write() {
		
		if (!saveLocationValid()) {
			// Prompt user that the folder name $USER_SAVETO_FOLDER_NAME is being created to save their calendar in.
			buildUserFolders(USER_SAVETO_FOLDER_NAME);
		}
		
		try {
				
			FileOutputStream fos = new FileOutputStream(file);
			
			BufferedWriter file_writer = new BufferedWriter(new OutputStreamWriter(fos));
			
			// Print header lines separated by our spacing element
			file_writer.write(getPrintFormattedFields() + "\n");
			file_writer.write(getPrintFormattedFieldValidation() + "\n");
			file_writer.write(String.join("\n", getPrintFormattedContactsArray()));
			
			file_writer.close();
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("PRINTING BOOK");
		
		
	}
	
	public String getPrintFormattedFields() {
		return addressBook.fields.stream().map(Object::toString).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX));
	}
	
	public String getPrintFormattedFieldValidation() {
		return addressBook.fields.stream().map(Field::getValCode).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX));
	}
	
	// TODO: figure out why this crashes with an ArrayList index out of bounds exception: Index 0, Size 0
	public ArrayList<String> getPrintFormattedContactsArray() {
		ArrayList<String> formattedContacts = new ArrayList<String>(addressBook.contacts.size());
		for (int contact_index = 0; contact_index < addressBook.contacts.size(); contact_index++) {
			formattedContacts.set(contact_index, addressBook.contacts.get(contact_index).getContactDataArray().stream().map(Object::toString).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX)));
		}
		return formattedContacts;
	}
	
	public static boolean saveLocationValid() {
		return new File("./" + USER_SAVETO_FOLDER_NAME).exists();
	}
	
	public static void buildUserFolders(String foldername) {
		new File("./" + foldername).mkdir();
	}
	
	
	
	// SETTERS:
	public void setBook(VirtualAddressBook newBook) {
		this.addressBook = newBook;
	}
	public void setFile(File newFile) {
		this.file = newFile;
	}
	// GETTERS:
	public File getFile() {
		return this.file;
	}
	public String getDividerRegex() {
		return FILE_CHARACTER_DIVIDER_REGEX;
	}
	
	
}
