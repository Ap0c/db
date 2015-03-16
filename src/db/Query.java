package db;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles querying of the database.
 *
 * @since 0.7
 */

public class Query {

	// ----- Instance Variables ----- //

	private Database db;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Query class, must be run with -ea.
	 * 
	 * @since 0.7
	 */
	private void testQuery () throws Exception {

		String[] columns = {"colOne", "colTwo", "colThree"};
		db.createTable("testTable", columns);

		String[] selectCols = {"colOne", "colThree"};
		int[] indices = columnIndices(db.getTable("testTable"), selectCols);
		assert Arrays.equals(indices, new int[]{0, 2}) : 
			"Column indices not obtained correctly.";

		String[] values = {"valOne", "valTwo", "valThree"};
		db.query.insert("testTable", values);
		Table testTable = db.getTable("testTable");
		assert Arrays.equals(testTable.getRows()[0], values) :
			"Values not inserted correctly.";

		String[][] moreValues = {{"valFour", "valFive", "valSix"},
			{"valSeven", "valEight", "valNine"}};
		db.query.insert("testTable", moreValues);
		testTable = db.getTable("testTable");
		assert Arrays.equals(testTable.getRows()[1], moreValues[0]) :
			"Multiple values not inserted correctly.";
		assert Arrays.equals(testTable.getRows()[2], moreValues[1]) :
			"Multiple values not inserted correctly.";

		db.query.add("testTable", "colFour", "default");
		testTable = db.getTable("testTable");
		assert testTable.getColumns()[3].equals("colFour") :
			"Column not added correctly.";
		assert testTable.getRows()[0][3].equals("default") :
			"Default values not inserted on column add.";

		db.query.rename("testTable", "colOne", "firstCol");
		testTable = db.getTable("testTable");
		assert testTable.getColumns()[0].equals("firstCol") :
			"Column not renamed correctly.";

		db.query.dropColumn("testTable", "colTwo");
		testTable = db.getTable("testTable");
		String[] newCols = testTable.getColumns();
		assert newCols.length == 3 && newCols[1].equals("colThree") :
			"Column not deleted correctly.";

		String[] subCols = {"firstCol", "colFour"};
		Table result = select("testTable", subCols);
		assert Arrays.equals(result.getColumns(), subCols) :
			"Selection columns incorrect.";
		String[] firstRow = {"valOne", "default"};
		assert Arrays.equals(result.getRows()[0], firstRow) :
			"Selection rows incorrect.";
		assert result.getRows()[0].length == 2 :
			"Selection returns incorrect object.";

		db.query.delete("testTable", "valFour");
		result = db.getTable("testTable");
		String[][] tableRows = result.getRows();
		assert tableRows.length == 2 : "Row not deleted correctly.";
		String[] secondRow = {"valSeven", "valNine", "default"};
		assert Arrays.equals(tableRows[1], secondRow) :
			"Row not deleted correctly.";

	}

	/**
	 * Gets the indices of columns to be selected.
	 * 
	 * @param table the Table object being queried.
	 * @param selectCols an array of names of the columns to be selected.
	 * @return an integer array of indices.
	 * @since 0.7
	 */
	private int[] columnIndices (Table table, String[] selectCols)
		throws Exception {

		int noCols = selectCols.length;
		int[] indices = new int[noCols];

		ArrayList<String> columns = new ArrayList<String>(
			Arrays.asList(table.getColumns()));

		for (int i = 0; i < noCols; i++) {

			String selectCol = selectCols[i];

			if (columns.contains(selectCol)) {
				indices[i] = (columns.indexOf(selectCol));
			} else {
				throw new Exception("No such column.");
			}

		}

		return indices;

	}

	/**
	 * For each row in the table, selects only the columns specified. Populates
	 * a result list with these reduced rows.
	 * 
	 * @param table the Table object being queried.
	 * @param columns the integer indices of the columns to be selected.
	 * @return a linked list of the resultant rows.
	 * @since 0.7
	 */
	private LinkedList<String[]> resultRows (Table table, int[] columnIndices)
		throws Exception {

		Record[] rows = table.getRecords();
		LinkedList<String[]> result = new LinkedList<String[]>();

		int noCols = columnIndices.length;

		for (Record record : rows) {
			String[] row = new String[noCols];
			for (int i = 0; i < noCols; i++) {
				int field = columnIndices[i];
				row[i] = record.getValue(field);
			}
			result.add(row);
		}

		return result;

	}

	/**
	 * Builds a Table with the result rows and specified columns.
	 * 
	 * @param rows linked list of the result rows.
	 * @param columns an array of names of the columns that were selected.
	 * @return a Table object containing the result.
	 * @since 0.7
	 */
	private ResultTable selection (LinkedList<String[]> rows, String[] cols)
		throws Exception {

		ResultTable table = new ResultTable(cols);

		for (String[] row : rows) {
			table.addRow(row);
		}

		return table;

	}

	/**
	 * Returns array of results showing only specified columns.
	 * 
	 * @param table the name of the table being queried.
	 * @param cols an array of names of the columns to be selected.
	 * @return a ResultTable containing the results of the query.
	 * @since 0.7
	 */
	public ResultTable select (String table, String[] cols) throws Exception {

		Table selectTable = db.getTable(table);

		int[] columnIndices = columnIndices(selectTable, cols);
		LinkedList<String[]> result = resultRows(selectTable, columnIndices);

		return selection(result, cols);

	}

	/**
	 * Adds a column to the specified table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param column the name of the column to be added.
	 * @param value the default value to be placed in the added fields.
	 * @since 0.7
	 */
	public void add (String tableName, String column, String value)
		throws Exception {
		db.getTable(tableName).addColumn(column, value);
		db.schema.addColumn(tableName, column);
	}

	/**
	 * Drops a column from the specified table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param column the name of the column to be dropped.
	 * @since 0.7
	 */
	public void dropColumn (String tableName, String column)
		throws Exception {
		db.getTable(tableName).deleteColumn(column);
		db.schema.dropColumn(tableName, column);
	}

	/**
	 * Renames a column in the specified table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param oldName the current name of the column.
	 * @param newName the new name of the column.
	 * @since 0.7
	 */
	public void rename (String tableName, String oldName, String newName)
		throws Exception {
		db.getTable(tableName).renameColumn(oldName, newName);
		db.schema.renameColumn(tableName, oldName, newName);
	}

	/**
	 * Inserts a single row into a table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param values an array of values to be inserted, must be the same length
	 * as there are number of columns.
	 * @since 0.7
	 */
	public void insert (String tableName, String[] values) throws Exception {
		db.getTable(tableName).addRow(values);
	}

	/**
	 * Inserts a set of rows into a table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param valSet an array in which each field contains an array of values
	 * to be inserted. Each values array must be the same length
	 * as there are number of columns.
	 * @since 0.7
	 */
	public void insert (String tableName, String[][] valSet) throws Exception {

		Table table = db.getTable(tableName);

		for (String[] values : valSet) {
			table.addRow(values);
		}

	}

	/**
	 * Deletes a row in the table by its primary key.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param primaryKey the primary key of the row to be deleted.
	 * @since 0.7
	 */
	public void delete (String tableName, String primaryKey) throws Exception {
		db.getTable(tableName).deleteRow(primaryKey);
	}

	// ----- Constructor ----- //

	Query (Database database) {
		this.db = database;
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		try {
			Database database = new Database("bin/data/");
			database.query.testQuery();
			System.out.println("Query tests complete.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
