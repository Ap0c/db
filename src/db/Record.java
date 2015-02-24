package db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Holds a single record in the database, aka a row.
 */

public class Record {

	// ----- Instance Variables ----- //

	private ArrayList<String> values;

	// ----- Instance Methods ----- //

	/**
	 * Gets the value of a record field at a given index.
	 *
	 * @param index the index of the field.
	 * @return the value stored in the field.
	 */
	public String getValue (int index) {

		if (index < values.size()) {
			return values.get(index);		
		} else {
			System.out.println("Record does not exist.");
			return null;
		}

	}

	/**
	 * Sets the value of a record field at a given index.
	 * 
	 * @param index the index of the field.
	 * @param value the value to store in the field.
	 */
	public void setValue (int index, String value) {

		if (index < values.size()) {
			values.set(index, value);		
		} else {
			System.out.println("Record does not exist.");
		}

	}

	/**
	 * Gets the number of fields in a record.
	 * 
	 * @return the integer number of fields.
	 */
	public int noFields () {
		return values.size();
	}

	/**
	 * Class constructor.
	 *
	 * @param vals An array of values to populate the record with.
	 */
	public Record (String[] vals) {
		this.values = new ArrayList<String>(Arrays.asList(vals));
	}

}
