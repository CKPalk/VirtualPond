/** Enum listing allowable types for Field formatting and validation.
 *  <p>
 *  Can iterate through all types using <code>FieldType.values()</code>.
 *  <p>
 *  Restricted to package visibility as has no reason to be accessed outside {@link Field}.
 */
enum FieldType {
	/** General text field requiring no validatior.  */
	TEXT  ('t'),
	// Continue for other types (zip, phone, etc.)
	;

	/** Unique code identifying type.
	 *  <p>
	 *  Used as prefix in .pond file header line.
	 */
	private final char typeCode;

	/** Internal constructor to initialize enum.
	 *
	 *  @param code The unique prefix identifying the type
	 */
	private FieldType(char code) {}

	/** Retrieve prefix for type.
	 *
	 *  @return {@link #typeCode}
	 */
	public char getPrefix() {}
}
