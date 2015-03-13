// package db;

import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

public class Database {

	// ----- Instance Variables ----- //

	private Map<String, Table> tables;
	private String dataDir;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Database class, must be run with -ea.
	 * 
	 * @since 0.5
	 */
	private void testDatabase () {

		String[] columnsOne = {"colOne", "colTwo", "colThree"};
		try {
			createTable("testTableOne", columnsOne);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		assert tables.containsKey("testTableOne") : "Table not added correctly";

		Table tableOne = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"stored correctly.";

		String[] columnsTwo = {"colFour", "colFive", "colSix"};
		try {
			createTable("testTableTwo", columnsTwo);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		assert tables.containsKey("testTableTwo") : "Table not added correctly";

		try {
			writeTables();
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
			buildTables();
		} catch (Exception e) {
			System.out.println("Problem reading from files.");
			System.exit(1);
		}

		assert tables.containsKey("testTableOne") &&
			tables.containsKey("testTableTwo") : "Tables not built correctly";
		Table testTable = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"built correctly.";

		try {
			dropTable("testTableOne");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		assert !tables.containsKey("testTableOne") : "Table not dropped.";

		testFileOne.delete();
		testFileTwo.delete();

	}

	/**
	 * Creates all tables as objects in memory from the table data files.
	 * 
	 * @since 0.5
	 */
	private void buildTables () throws Exception {

		DataFile dataFile = new DataFile(dataDir);
		String[] tableNames = dataFile.listTables();

		for (String tableName : tableNames) {
			tableName = tableName.substring(0, tableName.lastIndexOf("."));
			Table table = dataFile.readTable(tableName);
			tables.put(tableName, table);
		}

	}

	/**
	 * Writes all the tables held in memory back to file, and deletes tables
	 * that do not exist any more.
	 * 
	 * @since 0.5
	 */
	private void writeTables () throws IOException {

		DataFile dataFile = new DataFile(dataDir);

		for (Map.Entry<String, Table> table : tables.entrySet()) {
			dataFile.saveTable(table.getValue(), table.getKey());
		}

		String[] tableNames = dataFile.listTables();

		for (String tableName : tableNames) {
			if (!tables.containsKey(tableName)) {
				dataFile.deleteTable(tableName);
			}
		}

	}

	/**
	 * Creates a table and adds it to the list of table objects in memory.
	 * 
	 * @param name the name of the table to be created.
	 * @param columns an array of Strings containing the column names.
	 * @since 0.5
	 */
	public void createTable (String name, String[] columns) throws Exception {

		if (!tables.containsKey(name)) {
			Table table = new Table(columns);
			tables.put(name, table);
		} else {
			throw new Exception("Error, table already exists.");
		}

	}

	/**
	 * Drops a table from the list of tables, if it exists.
	 * 
	 * @param name the name of the table to be removed.
	 * @since 0.6
	 */
	public void dropTable (String name) throws Exception {

		if (tables.containsKey(name)) {
			tables.remove(name);
		} else {
			throw new Exception("Error, table does not exist.");
		}

	}

	/**
	 * Commits any changes the user has made to disk. This MUST be called at
	 * the end of a session, otherwise some changes may not be added.
	 * 
	 * @since 0.6
	 */
	public void commit () throws Exception {

		try {
			writeTables();
		} catch (IOException e) {
			throw new Exception("Error, changes could not be committed.");
		}

	}

	// ----- Constructor ----- //

	public Database (String location) throws Exception {

		this.tables = new HashMap<>();
		this.dataDir = location;

		try {
			buildTables();
		} catch (Exception e) {
			throw new Exception("Problem with database directory.");
		}

	}

	// ----- Main ----- //

	public static void main(String[] args) {

		try {
			Database database = new Database("bin/data/");
			database.testDatabase();
			System.out.println("Database tests complete.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
