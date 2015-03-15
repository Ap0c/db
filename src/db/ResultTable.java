package db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

/**
 * Presents the results of a database query in table format.
 *
 * @since 0.7
 */

public class ResultTable extends Table {

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the ResultTable class, must be run with -ea.
	 * 
	 * @since 0.7
	 */
	private void testResultTable () throws Exception {

		String[] valuesOne = {"one", "two", "three"};
		String[] valuesTwo = {"four", "five", "six"};

		addRow(valuesOne);
		addRow(valuesTwo);
		assert noRecords() == 2 : "Rows not added correctly.";
		assert Arrays.equals(getRows()[0], valuesOne) :
			"Rows not added correctly.";

		deleteRow(0);
		assert noRecords() == 1 : "Row not deleted correctly.";
		assert Arrays.equals(getRows()[0], valuesTwo) :
			"Row not deleted correctly.";

		deleteColumn("colTwo");
		assert getColumns().length == 2 : "Column not deleted correctly.";
		String[] updatedRow = {"four", "six"};
		assert Arrays.equals(getRows()[0], updatedRow) :
			"Row field not deleted correctly.";


	}

	/**
	 * Removes a column from the list of table columns. Also updates the 
	 * rows.
	 *
	 * @param name the name of the column to be removed.
	 * @since 0.7
	 */
	public void deleteColumn (String name) throws Exception {

		if (!columns.contains(name)) {
			throw new Exception("Column does not exist.");
		}

		int fieldIndex = columns.indexOf(name);
		columns.remove(fieldIndex);

		for (Record row : rows) {
			row.removeField(fieldIndex);
		}

	}

	/**
	 * Deletes a given row in the table.
	 *
	 * @param rowNumber the number of the row to be deleted, zero-indexed.
	 * @since 0.7
	 */
	public void deleteRow (int rowNumber) throws Exception {
		rows.remove(rowNumber);
	}

	/**
	 * Adds a row to the table.
	 *
	 * @param values the values to populate the row being added.
	 * @since 0.7
	 */
	public void addRow (String[] values) throws Exception {

		if (values.length != columns.size()) {
			throw new Exception("Incorrect number of values.");
		}

		Record newRecord = new Record(values);
		rows.add(newRecord);

	}

	// ----- Constructor ----- //

	ResultTable (String[] newColumns) {
		super(newColumns);
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		String[] columns = {"colOne", "colTwo", "colThree"};
		ResultTable table = new ResultTable(columns);

		try {
			table.testResultTable();
			System.out.println("ResultTable tests complete.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
