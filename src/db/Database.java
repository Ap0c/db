// package db;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

public class Database {

	// ----- Instance Variables ----- //

	private Map<String, Table> tables;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Database class, must be run with -ea.
	 * 
	 * @since 0.5
	 */
	private void testDatabase () {

		String dataDir = "bin/data/";

		String[] columnsOne = {"colOne", "colTwo", "colThree"};
		createTable("testTableOne", columnsOne);
		assert tables.containsKey("testTableOne") : "Table not added correctly";

		Table tableOne = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"stored correctly.";

		String[] columnsTwo = {"colFour", "colFive", "colSix"};
		createTable("testTableTwo", columnsTwo);
		assert tables.containsKey("testTableTwo") : "Table not added correctly";

		try {
			writeTables(dataDir);
		} catch (IOException e) {
			System.err.println("Problem writing file.");
			System.exit(1);
		}

		File testFileOne = new File(dataDir + "testTableOne.ser");
		File testFileTwo = new File(dataDir + "testTableTwo.ser"); 
		assert testFileOne.exists() && testFileTwo.exists():
			"Data file not written correctly.";

		tables.clear();
		try {
			buildTables(dataDir);
		} catch (Exception e) {
			System.out.println("Problem reading from files.");
			System.exit(1);
		}

		assert tables.containsKey("testTableOne") &&
			tables.containsKey("testTableTwo") : "Tables not built correctly";
		Table testTable = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"built correctly.";

		testFileOne.delete();
		testFileTwo.delete();

	}

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
	public void buildTables (String location) throws Exception {

		DataFile dataFile = new DataFile(location);
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
	public void writeTables (String location) throws IOException {

		DataFile dataFile = new DataFile(location);

		for (Map.Entry<String, Table> table : tables.entrySet()) {
			dataFile.saveTable(table.getValue(), table.getKey());
		}

	}

	// ----- Main ----- //

	public static void main(String[] args) {

		Database database = new Database();
		database.testDatabase();
		System.out.println("Database tests complete.");

	}

}
