package db;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stores a collection of records in the database.
 *
 * @since 0.2
 */

class Table {

	// ----- Instance Variables ----- //

	private ArrayList<String> columns;
	private ArrayList<Record> rows;

	// ----- Instance Methods ----- //

	/**
	 * Gets the column names as an array of strings.
	 * 
	 * @return a string array containing the column names.
	 * @since 0.2
	 */
	public String[] getColumns () {
		return columns.toArray(new String[columns.size()]);
	}

	/**
	 * Adds a column to the list of table columns. Also updates the records.
	 *
	 * @param name the name of the column to be added.
	 * @since 0.2
	 */
	public void addColumn (String name) {

		columns.add(name);

		for (Record row : rows) {
			row.addField();
		}

	}

	/**
	 * Removes a column from the list of table columns. Also updates the 
	 * records.
	 *
	 * @param name the name of the column to be removed.
	 * @since 0.2
	 */
	public void deleteColumn (String name) {

		int fieldIndex = columns.indexOf(name);
		columns.remove(fieldIndex);

		for (Record row : rows) {
			row.removeField(fieldIndex);
		}

	}

	/**
	 * Changes the name of a column.
	 *
	 * @param oldName the current name of the column.
	 * @param newName the new name of the column.
	 * @since 0.2
	 */
	public void renameColumn(String oldName, String newName) {
		int columnIndex = columns.indexOf(oldName);
		columns.set(columnIndex, newName);
	}

	/**
	 * Adds a row to the table.
	 *
	 * @param values the values to populate the row being added.
	 * @since 0.2
	 */
	public void addRow(String[] values) {
		Record newRecord = new Record(values);
		rows.add(newRecord);
	}

	/**
	 * Deletes a given row in the table.
	 *
	 * @param number the number of the row to be deleted (zero-indexed).
	 * @since 0.2
	 */
	public void deleteRow(int number) {

		if (number < rows.size()) {
			rows.remove(number);		
		} else {
			System.out.println("Row does not exist.");
		}

	}

	/**
	 * Gets the number of rows in a table.
	 *
	 * @return the integer number of rows.
	 * @since 0.2
	 */
	public int noRecords() {
		return rows.size();		
	}

	// ----- Constructor ----- //

	public Table (String[] newColumns) {
		this.columns = new ArrayList<String>(Arrays.asList(newColumns));
		this.rows = new ArrayList<Record>();
	}

	// ----- Main ----- //

	public static void main(String[] args) {
		
	}

}
