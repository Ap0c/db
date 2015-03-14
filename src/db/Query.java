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
	private LinkedList<String[]> selectResult (Table table, int[] columnIndices)
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
	 * Returns array of results showing only specified columns.
	 * 
	 * @param table the name of the table being queried.
	 * @param columns an array of names of the columns to be selected.
	 * @return a 2D array containing the results of the query.
	 * @since 0.7
	 */
	public String[][] select (String table, String[] columns) throws Exception {

		Table selectTable = db.getTable(table);

		int[] columnIndices = columnIndices(selectTable, columns);
		LinkedList<String[]> result = selectResult(selectTable, columnIndices);
		int noRows = result.size();

		return result.toArray(new String[noRows][]);

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

	public class Alter {

		private Table table;

		public void addColumn (String column) {
			table.addColumn(column);
		}

		public void deleteColumn (String column) throws Exception {
			table.deleteColumn(column);
		}

		public void renameColumn (String oldName, String newName)
			throws Exception {
			table.renameColumn(oldName, newName);
		}

		public Alter (String tableName) throws Exception {
			this.table = db.getTable(tableName);
		}

	}

	public void insert (String tableName, String[] values) throws Exception {
		Table table = db.getTable(tableName);
		table.addRow(values);
	}

	public void delete (String tableName, String primaryKey) throws Exception {
		Table table = db.getTable(tableName);
		table.deleteRow(primaryKey);
	}

	public void deleteWhere (String table, String[] paremeters) {};

	public void update (String table, String[] columns, String[] values, String[] parameters) {};

	// ----- Constructor ----- //

	Query (Database database) {
		this.db = database;
	}

}
