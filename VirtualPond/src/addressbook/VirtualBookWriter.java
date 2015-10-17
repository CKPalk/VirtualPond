package addressbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class VirtualBookWriter {

	public VirtualBookWriter(VirtualAddressBook addressBook, File file) {
		this.addressBook = addressBook;
		this.file = file;
	}
	
	
	// --- Private Data Members ---
	private VirtualAddressBook addressBook;
	private File file;
	
	
	// --- Public Class Methods ---
	
	// FUNCTIONALITY:
	public boolean write() {
		
		try {
			
			// Create a new file if necessary
			if (!file.exists()) {
				file.createNewFile();
			}
			
			// Check read and write access
			if(file.canRead()&&file.canWrite()) {//can read and write free to do what is needed
			   System.out.println("Assured file write permissions.");
			}else {
				System.err.println("Can't read or write to files.");
				return false;
			}
			
			FileWriter file_writer = new FileWriter(file.getAbsolutePath());
			BufferedWriter buffered_writer = new BufferedWriter(file_writer);
			
			// Print header line separated by our spacing element
			buffered_writer.write(getPrintFormattedFields() + "\n");
			buffered_writer.write(String.join("\n", getPrintFormattedContactsArray()));
			
			buffered_writer.close();

			System.out.println("PRINTING BOOK TO: " + file.getAbsolutePath());
				
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	
	boolean exportBook() {
		try {
			
			// Create a new file if necessary
			if (!file.exists()) {
				file.createNewFile();
			}
			
			// Check read and write access
			if(file.canRead()&&file.canWrite()) {//can read and write free to do what is needed
			   System.out.println("Assured file write permissions.");
			}else {
				System.err.println("Can't read or write to files.");
				return false;
			}
			
			FileWriter file_writer = new FileWriter(file.getAbsolutePath());
			BufferedWriter buffered_writer = new BufferedWriter(file_writer);
			
			// Print header line separated by our spacing element
			buffered_writer.write(getPrintFormattedFields() + "\n");
			buffered_writer.write(String.join("\n", getPrintFormattedTrimmedContactsArray()));
			
			buffered_writer.close();

			System.out.println("PRINTING BOOK TO: " + file.getAbsolutePath());
				
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	
	public String getPrintFormattedFields() {
		return addressBook.fields.stream().map(Object::toString).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX));
	}
	
	
	// TODO: figure out why this crashes with an ArrayList index out of bounds exception: Index 0, Size 0
	public ArrayList<String> getPrintFormattedContactsArray() {
		ArrayList<String> formattedContacts = new ArrayList<String>(addressBook.contacts.size());
		for (int contact_index = 0; contact_index < addressBook.contacts.size(); contact_index++) {
			formattedContacts.add(addressBook.contacts.get(contact_index).getContactDataArray().stream().map(Object::toString).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX)));
		}
		return formattedContacts;
	}
	
	/*
	 * This is used by export to trim any extra contact data off the array before writing to file
	 * Sublist the contact data from indexes 0 to 8, removing custom fields
	 */
	private ArrayList<String> getPrintFormattedTrimmedContactsArray() {
		ArrayList<String> formattedContacts = new ArrayList<String>(addressBook.contacts.size());
		for (int contact_index = 0; contact_index < addressBook.contacts.size(); contact_index++) {
			formattedContacts.add(addressBook.contacts.get(contact_index).getContactDataArray().subList(0, 8).stream().map(Object::toString).collect(Collectors.joining(VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX)));
		}
		return formattedContacts;
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
	
	
}
