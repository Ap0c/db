package db;

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
	private void testDatabase () throws Exception {

		String[] columnsOne = {"colOne", "colTwo", "colThree"};

		createTable("testTableOne", columnsOne);
		assert tables.containsKey("testTableOne") : "Table not added correctly";

		Table tableOne = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"stored correctly.";

		String[] columnsTwo = {"colFour", "colFive", "colSix"};
		createTable("testTableTwo", columnsTwo);
		assert tables.containsKey("testTableTwo") : "Table not added correctly";

		writeTables();
		File testFileOne = new File(dataDir + "testTableOne.ser");
		File testFileTwo = new File(dataDir + "testTableTwo.ser"); 
		assert testFileOne.exists() && testFileTwo.exists():
			"Data file not written correctly.";

		tables.clear();
		buildTables();
		assert tables.containsKey("testTableOne") &&
			tables.containsKey("testTableTwo") : "Tables not built correctly";
		Table testTable = tables.get("testTableOne");
		assert Arrays.equals(columnsOne, tableOne.getColumns()) : "Table not " +
			"built correctly.";

		dropTable("testTableOne");
		assert !tables.containsKey("testTableOne") : "Table not dropped.";

		String[] columnsThree = {"colSeven", "colEight", "colNine"};
		createTable("testTableThree", columnsThree);
		commit();
		File testFileThree = new File(dataDir + "testTableThree.ser");
		assert testFileThree.exists() : "Table not committed correctly.";
		assert !testFileOne.exists() : "Dropped table not deleted on disk.";

		testFileOne.delete();
		testFileTwo.delete();
		testFileThree.delete();

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
			e.printStackTrace();
		}

	}

}
