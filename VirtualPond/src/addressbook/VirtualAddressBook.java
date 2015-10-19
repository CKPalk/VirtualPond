package addressbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VirtualAddressBook {
	// Final static members
	// TODO: decide if having a character that is hard to type on Windows should be in our file extension.
	// Atlee says, "probably not wise".
	public static final String DEFAULT_DATA_FILENAME = "address_book_default_data"; // will be *.pond�
	public static final String FILE_EXTENSION = "pond�";

	// --- Private Data Members ---
	List<Field> fields;
	List<Contact> contacts;
	int indexToSortBy;

	// --- Constructor ---
	public VirtualAddressBook() {
		this(getDefaultBook());
	}
	
	VirtualAddressBook(File addressBookFile) {
		this(new VirtualBookReader(addressBookFile).read());
	}
	
	public VirtualAddressBook(List<Field> fields, List<Contact> contactsSet) {
		this.fields = fields;
		this.contacts = contactsSet;
		this.indexToSortBy = 0;
	}
	
	// private copy constructor
	public VirtualAddressBook(VirtualAddressBook vab) {
		this(vab.fields, vab.contacts);
	}
	
	
	
	// --- Public Class Methods ---
	public static VirtualAddressBook createDefaultBook() {
		return new VirtualAddressBook( Field.createDefaultFieldList(), new ArrayList<Contact>());	
	}

	// --- File I/O ---
	public static VirtualAddressBook createFromFile(File file) {
		return VirtualBookIO.openBook(file);
	}
	public static VirtualAddressBook importFromFile(File file) {
		return VirtualBookIO.importBook(file);
	}
	
	public boolean exportToFile(File file) {
		return VirtualBookIO.exportBook(this, file);
	}
	
	public boolean writeToFile(File file) {
		return VirtualBookIO.saveBook(this, file);
	}
	
	public void switchFields(int index1, int index2) {
		Collections.swap(fields, index1, index2);
		for (Contact contact : contacts) {
			Collections.swap(contact.getContactDataArray(), index1, index2);
		}
	}
	/**
	 * Compares ONLY the selected field, without trying to break ties.
	 * Empty fields are put LAST.
	 * TODO: decide if we WANT empty fields to be LAST.
	 * @param contact1
	 * @param contact2
	 * @param fieldIndex such as Field.FIRSTNAME
	 */
	private int compareSimple(Contact contact1, Contact contact2, int fieldIndex) {
		String value1 = contact1.getContactDataAt(fieldIndex);
		String value2 = contact2.getContactDataAt(fieldIndex);
		boolean v1empty = value1 == null || value1.isEmpty();
		boolean v2empty = value2 == null || value2.isEmpty();
		if( v1empty ) {
			if( v2empty ) return 0;
			return 1;
		}
		if( v2empty ) return -1;
		return value1.compareToIgnoreCase(value2);
	}
	public void sortByFieldAtIndex(int index) {
		// Custom comparators to sort by specified index
		this.indexToSortBy = index;
		Comparator<Contact> comparator = null;
		switch( index ) {
		case Field.LASTNAME:
			final Comparator<Contact> lastNameComparator = new Comparator<Contact>() {
				public int compare(Contact contact1, Contact contact2) {
					int comp = compareSimple(contact1, contact2, Field.LASTNAME);
					if( comp == 0 ) comp = compareSimple(contact1, contact2, Field.FIRSTNAME);
					return comp;
//					String last1 = contact1.getContactDataAt(Field.LASTNAME);
//					String last2 = contact2.getContactDataAt(Field.LASTNAME);
//					int comp = last1.compareToIgnoreCase(last2);
//					if( comp == 0 ) { // break tie by comparing first names
//						String first1 = contact1.getContactDataAt(Field.FIRSTNAME);
//						String first2 = contact2.getContactDataAt(Field.FIRSTNAME);
//						comp = first1.compareToIgnoreCase(first2);
//					}
//					return comp;
				}
			};
			comparator = lastNameComparator;
			break;
		case Field.ZIP:
			final Comparator<Contact> zipComparator = new Comparator<Contact>() {
				public int compare(Contact contact1, Contact contact2) {
					int comp = compareSimple(contact1, contact2, Field.ZIP);
					if( comp == 0 ) { // break ties by last name
						comp = compareSimple(contact1, contact2, Field.LASTNAME);
						if( comp == 0 ) { // break ties by first name
							comp = compareSimple(contact1,contact2, Field.FIRSTNAME);
						}
					}
					return comp;
//					String zip1 = contact1.getContactDataAt(Field.ZIP);
//					String zip2 = contact2.getContactDataAt(Field.ZIP);
//					int comp = zip1.compareToIgnoreCase(zip2);
//					if( comp == 0 ) { // break tie by comparing last names
//						String last1 = contact1.getContactDataAt(Field.LASTNAME);
//						String last2 = contact2.getContactDataAt(Field.LASTNAME);
//						comp = last1.compareToIgnoreCase(last2);
//					}
//					return comp;
				}
			};
			comparator = zipComparator;
			break;
		default:
			final Comparator<Contact> defaultComparator = new Comparator<Contact>() {
				public int compare(Contact contact1, Contact contact2) {
					return compareSimple(contact1, contact2, index);
					//return contact1.getContactDataAt(index).compareToIgnoreCase(contact2.getContactDataAt(index));
				}
			};
			comparator = defaultComparator;
		}
		Collections.sort(contacts, comparator);
	}
	
	
	// SETTERS:
	public void newField(Field newField) {
		fields.add(newField);
		for (Contact contact : contacts) {
			contact.newCell();
		}
	}
	public void newFieldAtIndex(Field newField, int index) {
		fields.add(index, newField);
		for (Contact contact : contacts) {
			contact.newCellAtIndex(index);
		}
	}
	public void setFieldAtIndex(String newData, int index) {
		fields.get(index).name = newData;
	}
	public void newContact(Contact contact) {
		contacts.add(contact);
		
		// Do not sort when adding a new contact
		// sortByFieldAtIndex(indexToSortBy);
	}
	public void setContactAtIndexes(String newData, int contactIndex, int fieldIndex) {
		contacts.get(contactIndex).editDataAtIndex(newData, fieldIndex);;
	}
	// GETTERS:
	public Field getFieldAtIndex(int index) {
		return fields.get(index);
	}
	public List<Field> getFields() {
		return fields;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	
	// --- Private Class Methods ---
	private static VirtualAddressBook getDefaultBook() {
		return new VirtualBookReader(DEFAULT_DATA_FILENAME, true).read();
	}
}
