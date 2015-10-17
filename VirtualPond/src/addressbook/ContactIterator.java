package addressbook;

import java.util.Iterator;
import java.util.List;

public class ContactIterator implements Iterator<String> {

	private List<String> contacts;
	private int position;
	
	public ContactIterator(Contact contact) {
		this.contacts = contact.getContacts();
	}
	
	
	@Override
	public boolean hasNext() {
		return position < contacts.size();
	}

	@Override
	public String next() {
		String contactObj = contacts.get(position);
		position++;
		return contactObj;
	}
	
}
