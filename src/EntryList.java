import java.util.HashMap;


/** List of Entry objects to encapsulate implementation.
 */
public class EntryList {
	/** Generate empty EntryList.
	 */
	public  EntryList() {}

	/** Core collection of Entry objects.
	 *  <p>
	 *  We can handle sorting purely within the UI, so allowing fast random
	 *    access here is better than keeping an ordered list.
	 */
	private HashMap<Integer, Entry> entries;
	/** Counter for generating unique entry IDs.
	 *  <p>
	 *  Should be increased by one each time to ensure uniqueness, and
	 *    likewise never decreased.
	 */
	private int   availableID = 0;

	/** Counts number of Entry objects in the address book.
	 *
	 *  @return {@link HashMap#size() entries.size()}
	 */
	public  int   size() {}

	/** Gets the Entry with the specified ID number.
	 *
	 *  @param id Index of the entry to retrieve
	 *
	 *  @return The requested Entry
	 */
	public  Entry getEntry(int id) {}
	/** Insert the given entry into the address book.
	 *  <p>
	 *  Set {@link Entry#id} from {@link #availableID} if necessary; map
	 *    key should be the same number.
	 *
	 *  @param e The entry to add
	 *
	 *  @return The ID number of the inserted Entry
	 */
	public  int   addEntry(Entry e) {}
	/** Removes the Entry with the specified ID number.
	 *
	 *  @param id ID of the entry to remove
	 *
	 *  @return The removed entry to facilitate eg. undo
	 */
	public  Entry removeEntry(int id) {}
}
