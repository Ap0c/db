package db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds a single record in the database, aka a row.
 *
 * @since 0.1
 */

public class Record implements java.io.Serializable {

	// ----- Instance Variables ----- //

	private ArrayList<String> values;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Record class, must be run with -ea.
	 * 
	 * @since 0.1
	 */
	private void testRecord () {

		assert getValue(0).equals("one") : "Got incorrect field value.";
		assert getValue(3) == null : "Index should be out of range.";
		setValue(2, "four");
		assert values.get(2).equals("four") : "Value not set correctly.";
		assert noFields() == 3 : "Incorrect number of fields.";
		removeField(1);
		assert noFields() == 2 : "Field not removed properly.";
		assert values.get(1).equals("four") : "Field not removed properly.";
		addField();
		assert getValue(2) == null : "Field not removed properly.";

	}

	/**
	 * Gets the value of a record field at a given index.
	 *
	 * @param index the index of the field.
	 * @return the value stored in the field.
	 * @since 0.1
	 */
	public String getValue (int index) {

		if (index < values.size()) {
			return values.get(index);
		} else {
			System.out.println("Field does not exist.");
			return null;
		}

	}

	/**
	 * Gets the array of values stored in a record.
	 *
	 * @return an array of Strings containing the values.
	 * @since 0.4
	 */
	public String[] getValues () {
		return values.toArray(new String[values.size()]);
	}

	/**
	 * Sets the value of a record field at a given index.
	 * 
	 * @param index the index of the field.
	 * @param value the value to store in the field.
	 * @since 0.1
	 */
	public void setValue (int index, String value) {

		if (index < values.size()) {
			values.set(index, value);		
		} else {
			System.out.println("Field does not exist.");
		}

	}

	/**
	 * Adds a field to the record.
	 * 
	 * @since 0.2
	 */
	public void addField() {
		values.add(null);
	}

	/**
	 * Removes a field from the record.
	 *
	 * @param index the index of the field to be removed.
	 * @since 0.2
	 */
	public void removeField(int index) {

		if (index < values.size()) {
			values.remove(index);		
		} else {
			System.out.println("Field does not exist.");
		}

	}

	/**
	 * Gets the number of fields in a record.
	 * 
	 * @return the integer number of fields.
	 * @since 0.1
	 */
	public int noFields () {
		return values.size();
	}

	// ----- Constructor ----- //

	/**
	 * Class constructor.
	 *
	 * @param vals An array of values to populate the record with.
	 * @since 0.1
	 */
	public Record (String[] vals) {
		this.values = new ArrayList<String>(Arrays.asList(vals));
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		String[] values = {"one", "two", "three"};
		Record record = new Record(values);
		record.testRecord();
		System.out.println("Record tests complete.");

	}

}
