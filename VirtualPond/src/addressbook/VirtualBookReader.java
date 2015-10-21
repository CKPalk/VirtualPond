package addressbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VirtualBookReader {

	// --- Constructor ---
	public VirtualBookReader(String file) {		
		this.file = new File(processFilename(file, false));
	}
	public VirtualBookReader(String file, boolean hidden) {
		this.file = new File(processFilename(file, hidden));
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
	VirtualAddressBook read() {
		
		Scanner file_in;
		try {
			file_in = new Scanner(file);

			// Read first line as field information
			// Atlee says: ".split( regex ) -> .split( regex, -1 ): captures empty fields, as desired"
			String[] field_data = file_in.nextLine().split( VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX, -1 );
			
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
				ArrayList<String> contact_data = new ArrayList<String>(
						Arrays.asList(
								file_in.nextLine()
									// Atlee says: "fixed bug with empty fields not being read"
									// Atlee says: "split( regex ) -> split( regex, -1 ): now reads empty fields"
									.split( VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX, -1 )
									)
						);
				System.out.println("read line:\n" + contact_data );
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
	
	
	/*
	 * This imports the file and should strip off any fields (as we should know their order already)
	 * as well as any extra fields that their custom book might have
	 */
	VirtualAddressBook importBook() {

		Scanner file_in;
		try {
			file_in = new Scanner(file);

			// Read first two lines as field information
			String[] header = file_in.nextLine().split( VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX, -1 ); // This strips the fields for imports
			if( header == null || header.length < Field.NUM_DEFAULT ) {
				file_in.close();
				return null;
			}
			for( int i = 0; i < Field.NUM_DEFAULT; i++ ) {
				if( header[i] == null || !header[i].equals( Field.tsvNames.get( i ) ) ) {
					System.out.println("expected: " + Field.tsvNames.get( i ) + ", got: " + header[i] ); 
					file_in.close();
					return null;
				}
			}
			
			
			// Initialize field and contact arrays
			contacts = new ArrayList<Contact>();
			
			while (file_in.hasNextLine()) {
				// Grabs first 8 elements and creates and adds a new contact
				ArrayList<String> contact_data = new ArrayList<String>(
						Arrays.asList(
								file_in.nextLine().split( VirtualBookIO.FILE_CHARACTER_DIVIDER_REGEX, -1 )
								)
									.subList( 0, Field.NUM_DEFAULT )
						);
				Contact contact = new Contact(contact_data);
				contacts.add(contact);
			}
			
			file_in.close();
			
		} catch (IOException e1) {
			return null;
		}
		
		return new VirtualAddressBook(VirtualBookIO.defaultFields, contacts);
	}
	
	
	
	private static String processFilename(String filename, boolean hidden) {
		// Removes any extensions on filename and appends .pond�
		if (filename.contains(".")) {
			filename = filename.split(".")[0] + "." + VirtualBookIO.VIRTUAL_ADDRESS_BOOK_EXTENSION;
			return (hidden) ? ".".concat(filename) : filename;
		}
		else {
			filename += "." + VirtualBookIO.VIRTUAL_ADDRESS_BOOK_EXTENSION;
			return (hidden) ? ".".concat(filename) : filename;
		}
	}

}
