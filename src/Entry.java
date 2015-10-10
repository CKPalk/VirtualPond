import java.util.HashMap;


/** Class representing a single contact entry in the address book.
 */
public class Entry {
	/** Generate new blank entry.
	 */
	public  Entry() {}

	/** Unique entry identifier.
	 *  <p>
	 *  If set to <code>-1</code>, indicates Entry is not associated with an EntryList.
	 */
	private int    id = -1;
	/** Core storage of {@link Field#id}:data pairs.
	 */
	private HashMap<Integer, String> data;

	/** Gets the unique ID of this entry.
	 *  <p>
	 *  Public visibility as Entry objects are indexed by ID in an EntryList.
	 *
	 *  @return {@link #id}
	 */
	public  int    getID() {}

	/** Gets the data associated with the given field, or an empty string if
	 *    that field has not been set.
	 *
	 *  @param f The Field to retrieve
	 *
	 *  @return The String representation of the data contained in that field
	 */
	public  String getData(Field f) {}
	/** Assigns the given data to the specified field.
	 *
	 *  @param f The Field to assign to
	 *  @param d The data to store
	 */
	public  void   setData(Field f, String d) {}
	// We don't need removeData(Field) as setData should simply overwrite
	//   any existing contents (including with an empty string) and we only
	//   save the contents referenced by the associated FieldList.
}
