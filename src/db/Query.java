// package db;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Handles all querying of the database, all read and update operations.
 *
 * @since 0.7
 */

public class Query {

	// ----- Instance Variables ----- //

	private Database db;


	// ----- Instance Methods ----- //

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
		String[] row = new String[noCols];

		for (Record record : rows) {
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
	private Table resultTable (LinkedList<String[]> rows, String[] columns)
		throws Exception {

		Table table = new Table(columns);

		for (String[] row : rows) {
			table.addRow(row);
		}

		return table;

	}

	/**
	 * Returns array of results showing only specified columns.
	 * 
	 * @param table the name of the table being queried.
	 * @param columns an array of names of the columns to be selected.
	 * @return a 2D array containing the results of the query.
	 * @since 0.7
	 */
	public Table select (String table, String[] columns) throws Exception {

		Table selectTable = db.getTable(table);

		int[] columnIndices = columnIndices(selectTable, columns);
		LinkedList<String[]> result = resultRows(selectTable, columnIndices);

		return resultTable(result, columns);

	}

	/**
	 * Returns array of results showing only specified columns, based upon
	 * certain criteria.
	 * 
	 * @param table the name of the table being queried.
	 * @param columns an array of names of the columns to be selected.
	 * @param parameters
	 * @return a 2D array containing the results of the query.
	 * @since 0.7
	 */
	// public String[][] selectWhere (
	// 	String table, String[] columns, String[] parameters) {



	// }


	/**
	 * Adds a column to the specified table.
	 * 
	 * @param tableName the name of the table being queried.
	 * @param columns an array of names of the columns to be selected.
	 * @param value the default value to be placed in the added fields.
	 * @return a 2D array containing the results of the query.
	 * @since 0.7
	 */
	public void add (String tableName, String column, String value)
		throws Exception {
		db.getTable(tableName).addColumn(column, value);
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

	public void delete (String tableName, String primaryKey) throws Exception {
		db.getTable(tableName).deleteRow(primaryKey);
	}

	// public void deleteWhere (String table, String[] paremeters) {};

	// public void update (String table, String[] columns, String[] values) {};

	// ----- Constructor ----- //

	Query (Database database) {
		this.db = database;
	}

}
