// package db;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

public class Database {

	// ----- Instance Variables ----- //

	private Map<String, Table> tables;

	// ----- Instance Methods ----- //

	/**
	 * Creates a table and adds it to the list of table objects in memory.
	 * 
	 * @param name the name of the table to be created.
	 * @param columns an array of Strings containing the column names.
	 * @since 0.5
	 */
	public void createTable (String name, String[] columns) {

		if (!tables.containsKey(name)) {
			Table table = new Table(columns);
			tables.put(name, table);
		} else {
			System.err.println("Error, table already exists.");
		}

	}

	/**
	 * Creates all tables as objects in memory from the table data files.
	 * 
	 * @since 0.5
	 */
	public void buildTables () throws Exception {

		DataFile dataFile = new DataFile("bin/data/");
		String[] tableNames = dataFile.listTables();

		for (String tableName : tableNames) {
			Table table = dataFile.readTable(tableName);
			tables.put(tableName, table);
		}

	}

	/**
	 * Writes all the tables held in memory back to file.
	 * 
	 * @since 0.5
	 */
	public void writeTables () throws IOException {

		DataFile dataFile = new DataFile("bin/data/");

		for (Map.Entry<String, Table> table : tables.entrySet()) {
			dataFile.saveTable(table.getValue(), table.getKey());
		}

	}

	// ----- Main ----- //

	public static void main(String[] args) {

		Database database = new Database();

	}

}
