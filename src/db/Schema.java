package db;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Maintains metadata about the database.
 * 
 * @since 0.8
 */
public class Schema {

	// ----- Instance Variables ----- //

	private Map<String, ArrayList<String>> tables;
	private int noTables;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Schema class, must be run with -ea.
	 * 
	 * @since 0.8
	 */
	private void testSchema () throws Exception {

		

	}

	/**
	 * Puts a table name and its columns into the tables map.
	 *
	 * @param name the name of the table to be added.
	 * @param columns an array of column names.
	 * @since 0.8
	 */
	void createTable (String name, String[] columns) throws Exception {

		if (!tables.containsKey(name)) {
			tables.put(name, new ArrayList<String>(Arrays.asList(columns)));
		} else {
			throw new Exception("Table '" + name + "' already exists.");
		}

		noTables++;

	}

	/**
	 * Drops a table from the list of tables, if it exists.
	 * 
	 * @param name the name of the table to be removed.
	 * @since 0.8
	 */
	void dropTable (String name) throws Exception {

		if (tables.containsKey(name)) {
			tables.remove(name);
		} else {
			throw new Exception("Table '" + name + "' does not exist.");
		}

		noTables--;

	}

	/**
	 * Drops a column from a specific table, if it exists.
	 * 
	 * @param table the name of the table to be updated.
	 * @param col the name of the column to be dropped.
	 * @since 0.8
	 */
	void dropColumn (String table, String col) throws Exception {

		if (tables.containsKey(table)) {

			ArrayList<String> columns = tables.get(table);

			if (columns.contains(col)) {
				columns.remove(col);
			} else {
				throw new Exception(
					"No such column '" + col + "' in '" + table + "'.");
			}

		} else {
			throw new Exception("No such table: " + table);
		}

	}

	/**
	 * Adds a column to the list for a specific table.
	 * 
	 * @param table the name of the table to be updated.
	 * @param col the name of the column to be added.
	 * @since 0.8
	 */
	void addColumn (String table, String col) throws Exception {

		if (tables.containsKey(table)) {

			ArrayList<String> columns = tables.get(table);

			if (!columns.contains(col)) {
				columns.add(col);
			} else {
				throw new Exception(
					"Table '" + table + "' already has '" + col + "'.");
			}
		} else {
			throw new Exception("No such table: " + table);
		}

	}

	/**
	 * Changes the name of a column for a specific table.
	 * 
	 * @param oldName the current name of the column.
	 * @param newName the new name of the column.
	 * @since 0.8
	 */
	void renameColumn (String table, String oldName, String newName)
		throws Exception {

		if (tables.containsKey(table)) {

			ArrayList<String> columns = tables.get(table);

			if (columns.contains(oldName)) {
				int columnIndex = columns.indexOf(oldName);
				columns.set(columnIndex, newName);
			} else {
				throw new Exception(
					"No such column '" + oldName + "' in '" + table + "'.");
			}

		} else {
			throw new Exception("No such table: " + table);
		}

	}

	/**
	 * Gets an array of columns names for a specific table.
	 * 
	 * @param name the name of the table to be accessed.
	 * @return an array of column names.
	 * @since 0.8
	 */
	public String[] table (String name) throws Exception {

		if (tables.containsKey(name)) {
			ArrayList<String> columns = tables.get(name);
			return columns.toArray(new String[columns.size()]);
		} else {
			throw new Exception("No such table: " + name);
		}

	}

	/**
	 * Gets the entire schema for the database, i.e. table names and the set of
	 * columns for each table.
	 * 
	 * @return a map containing the table information, with table names as keys
	 * and arrays of columns as values.
	 * @since 0.8
	 */
	public Map<String, String[]> all () throws Exception {

		Map<String, String[]> allTables = new HashMap<String, String[]>();

		for (Map.Entry<String, ArrayList<String>> entry :
			tables.entrySet()) {

			ArrayList<String> cols = entry.getValue();

			allTables.put(
				entry.getKey(),
				cols.toArray(new String[cols.size()])
			);

		}

		return allTables;

	}

	// ----- Constructor ----- //

	Schema () {
		this.tables = new HashMap<>();
		this.noTables = 0;
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		try {
			Database database = new Database("bin/data/");
			database.schema.testSchema();
			System.out.println("Schema tests complete.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
