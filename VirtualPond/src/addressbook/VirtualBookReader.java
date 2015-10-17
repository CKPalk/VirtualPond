package addressbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class VirtualBookReader implements VirtualBookIO {

	// --- Constructor ---
	public VirtualBookReader(String file) {		
		this.file = new File(processedFilename(file, false));
	}
	public VirtualBookReader(String file, boolean hidden) {
		this.file = new File(processedFilename(file, hidden));
	}
	public VirtualBookReader(File file) {
		this.file = file;
	}

	// --- Private Data Members ---
	private File file;
	private ArrayList<Field> fields;
	private ArrayList<Contact> contacts;
	
	
	// --- Public Class Methods ---
	
	// FUNCTIONALITY:
	public VirtualAddressBook read() {
		
		Scanner file_in;
		try {
			file_in = new Scanner(file);
			
			// File validation could be done now.
			
			// Read first two lines as field information
			String[] field_data = file_in.nextLine().split(FILE_CHARACTER_DIVIDER_REGEX);
			
			int cell_count = field_data.length;
			
			// Initialize field and contact arrays
			fields 	 = new ArrayList<Field>();
			contacts = new ArrayList<Contact>();
			
			for (int field_index = 0; field_index < cell_count; field_index++) {
				try {
					fields.add(new Field( field_data[field_index] )); // Set field required to false by default (no current way to read required or not)
				}
				catch (IndexOutOfBoundsException e) {
					System.err.println("Field data corrupt, mismatch entry count.\n" + e.getMessage());
				}
			}
			
			while (file_in.hasNextLine()) {
				ArrayList<String> contact_data = new ArrayList<String>(Arrays.asList(file_in.nextLine().split(FILE_CHARACTER_DIVIDER_REGEX)));
				Contact contact = new Contact(contact_data);
				contacts.add(contact);
			}
			
			// Reached end of file
			file_in.close();
		} catch (IOException e1) {
			// TODO throw custom error for file reading gone wrong
			e1.printStackTrace();
		}
		
		
		
		return new VirtualAddressBook(fields, contacts);
		
	}
	
	private String processedFilename(String filename, boolean hidden) {
		// Removes any extensions on filename and appends .pond�
		if (filename.contains(".")) {
			filename = filename.split(".")[0] + "." + VIRTUAL_ADDRESS_BOOK_EXTENSION;
			return (hidden) ? ".".concat(filename) : filename;
		}
		else {
			filename += "." + VIRTUAL_ADDRESS_BOOK_EXTENSION;
			return (hidden) ? ".".concat(filename) : filename;
		}
	}
	
	// --- Private Class Methods ---
	
	// SETTERS:
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
