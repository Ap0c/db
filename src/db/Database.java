// package db;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

class Database {

	// ----- Instance Variables ----- //

	private Map<String, Table> tables;

	// ----- Instance Methods ----- //

	public void createTable (String[] columns) {



	}

	/**
	 * Creates all tables as objects in memory from the table data files.
	 * 
	 * @since 0.5
	 */
	public void buildTables () {

		DataFile datafile = new DataFile("bin/data/");



	}

	/**
	 * Writes all the tables held in memory back to file.
	 * 
	 * @since 0.5
	 */
	public void writeTables () throws IOException {

		DataFile datafile = new DataFile("bin/data/");

		for (Map.Entry<String, Table> table : tables.entrySet()) {
			datafile.saveTable(table.getValue(), table.getKey());
		}

	}

}