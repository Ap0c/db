// package db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * Stores a collection of records in the database.
 *
 * @since 0.2
 */

public class Table implements java.io.Serializable {

	// ----- Instance Variables ----- //

	private ArrayList<String> columns;
	private LinkedList<Record> rows;
 
	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Table class, must be run with -ea.
	 * 
	 * @since 0.2
	 */
	private void testTable () throws Exception {

		assert Arrays.equals(columns.toArray(), getColumns()) : "Table " +
			"columns not added correctly.";

		String[] values = {"valOne", "valTwo", "valThree"};
		addRow(values);		
		assert rows.size() == 1 : "Row not added correctly.";
		assert rows.get(0).getValue(1).equals("valTwo") : "Row not added " +
			"correctly";
		assert noRecords() == 1 : "Rows not counted correctly.";

		try {
			addRow(values);
		} catch (Exception e) {
			if (!e.getMessage().equals(
				"Primary key must be unique: " + values[0])) {
				throw e;
			}
		}

		addColumn("colFour");
		assert columns.size() == 4 : "Column not added correctly.";
		assert columns.get(3) == "colFour" : "Column not added correctly.";
		assert rows.get(0).noFields() == 4 : "Row field not added correctly.";

		renameColumn("colFour", "colFive");
		assert columns.get(3) == "colFive" : "Column not renamed correctly.";

		deleteColumn("colTwo");
		assert columns.size() == 3 : "Column not removed correctly.";
		assert columns.get(1).equals("colThree") : "Column not removed " +
			"correctly.";

		try {
			assert rows.get(0).getValue(3) == null : "Row field not removed " + 
			"correctly.";
		} catch (Exception e) {
			if (!e.getMessage().equals("Field does not exist.")) {
				throw e;
			}
		}

		String[] moreValues = {"valFour", "valFive", "valSix"};
		addRow(moreValues);		

		Record[] retrievedRows = getRecords();
		for (int i = noRecords() - 1; i >= 0; i--) {
			String[] rowValues = rows.get(i).getValues();
			assert Arrays.equals(rowValues, retrievedRows[i].getValues()) : 
				"Rows not retrieved correctly.";
		}

		deleteRow("valOne");
		assert noRecords() == 1 : "Row not removed correctly.";

		String[][] stringRows = getRows();
		for (int i = noRecords() - 1; i >= 0; i--) {
			String[] rowValues = rows.get(i).getValues();
			assert Arrays.equals(rowValues, stringRows[i]) : 
				"Rows not retrieved as Strings correctly.";
		}

	}

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
	 * Gets all of the rows in the table.
	 * 
	 * @return a Record array containing the rows.
	 * @since 0.4
	 */
	public Record[] getRecords () {
		return rows.toArray(new Record[rows.size()]);
	}

	/**
	 * Gets all of the rows in the table as a 2D String array.
	 * 
	 * @return a String 2D array containing the rows, in format [row][column].
	 * @since 0.6
	 */
	public String[][] getRows () {

		LinkedList<String[]> returnRows = new LinkedList<String[]>();

		for (Record row : rows) {
			returnRows.add(row.getValues());
		}

		return returnRows.toArray(new String[returnRows.size()][]);

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
	public void deleteColumn (String name) throws Exception {

		int fieldIndex = columns.indexOf(name);

		if (fieldIndex != 0) {

			columns.remove(fieldIndex);

			for (Record row : rows) {
				row.removeField(fieldIndex);
			}

		} else {
			throw new Exception("Cannot delete primary key.");
		}

	}

	/**
	 * Changes the name of a column.
	 *
	 * @param oldName the current name of the column.
	 * @param newName the new name of the column.
	 * @since 0.2
	 */
	public void renameColumn (String oldName, String newName) throws Exception {

		if (columns.contains(oldName)) {
			int columnIndex = columns.indexOf(oldName);
			columns.set(columnIndex, newName);
		} else {
			throw new Exception("No such column.");
		}
		
	}

	/**
	 * Adds a row to the table if primary key is unique.
	 *
	 * @param values the values to populate the row being added.
	 * @since 0.2
	 */
	public void addRow (String[] values) throws Exception {

		if (values.length != columns.size()) {
			throw new Exception("Incorrect number of values.");
		}

		String primKey = values[0];

		for (Record row : rows) {
			if (row.getValue(0).equals(primKey)) {
				throw new Exception("Primary key must be unique: " + primKey);
			}
		}

		Record newRecord = new Record(values);
		rows.add(newRecord);

	}

	/**
	 * Deletes a given row in the table.
	 *
	 * @param primaryKey the primary-key of the row to be deleted.
	 * @since 0.2
	 */
	public void deleteRow (String primaryKey) throws Exception {

		for (Record row : rows) {
			if (row.getValue(0).equals(primaryKey)) {
				rows.remove(row);
				return;
			}
		}

		throw new Exception("Row does not exist.");

	}

	/**
	 * Gets the number of rows in a table.
	 *
	 * @return the integer number of rows.
	 * @since 0.2
	 */
	public int noRecords () {
		return rows.size();		
	}

	// ----- Constructor ----- //

	Table (String[] newColumns) {
		this.columns = new ArrayList<String>(Arrays.asList(newColumns));
		this.rows = new LinkedList<Record>();
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		String[] columns = {"colOne", "colTwo", "colThree"};
		Table table = new Table(columns);
		try {
			table.testTable();
			System.out.println("Table tests complete.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
