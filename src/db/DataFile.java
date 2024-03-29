package db;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Arrays;

/**
 * Handles reading and writing of tables to and from files.
 *
 * @since 0.3
 */

class DataFile {

	// ----- Instance Variables ----- //

	private String dataDir;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the DataFile class, must be run with -ea.
	 * 
	 * @since 0.3
	 */
	private void testDataFile () throws Exception {

		String[] columns = {"colOne", "colTwo", "colThree"};
		Table table = new Table(columns);
		String fileName = "testTable";
		try {
			saveTable(table, fileName);
		} catch (IOException e) {
			throw new IOException("Problem writing to file.", e);
		}

		File test = new File(dataDir + fileName + ".ser");
		assert test.exists() : "File not created correctly.";

		Table testTable = null;

		try {
			testTable = readTable(fileName);
		} catch (IOException e) {
			throw new IOException("Problem reading file.", e);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Class not found in file.", e);
		}

		assert testTable != null : "Table not read in correctly.";
		assert Arrays.equals(columns, testTable.getColumns()) : "Incorrect " +
		"object in file.";

		try {
			assert listTables()[0].equals("testTable") : "File list incorrect.";
			assert listTables().length == 1 : "Wrong number of files listed.";
		} catch (IOException e) {
			throw new Exception("Problem obtaining file list.");
		}

		deleteTable("testTable");
		assert !test.exists() : "Table file not deleted from disk.";

	}

	/**
	 * Saves a table to a file, serializing the object data.
	 * 
	 * @param table the table object to be saved.
	 * @param name name of the table, used to define filename.
	 * @since 0.3
	 */
	void saveTable (Table table, String name) throws IOException {

		FileOutputStream file = new FileOutputStream(dataDir + name + ".ser");
		ObjectOutputStream objectOut = new ObjectOutputStream(file);

		objectOut.writeObject(table);
		objectOut.close();
		file.close();

	}

	/**
	 * Reads a table from file, file must be a serialized Table object.
	 * 
	 * @param tableName the name of the file containing the Table object.
	 * @return the Table object that has been read in.
	 * @since 0.3
	 */
	Table readTable (String tableName)
		throws IOException, ClassNotFoundException {

		FileInputStream file = new FileInputStream(
			dataDir + tableName + ".ser");
		ObjectInputStream objectIn = new ObjectInputStream(file);

		Table table = (Table) objectIn.readObject();
		objectIn.close();
		file.close();

		return table;

	}

	/**
	 * Removes a table file from disk.
	 * 
	 * @param tableName the name of the file containing the Table object.
	 * @since 0.6
	 */
	void deleteTable (String tableName) throws IOException {

		File tableFile = new File(dataDir + tableName + ".ser");

		try {
			tableFile.delete();
		} catch (SecurityException e) {
			throw new IOException("File could not be deleted.");
		}

	}

	/**
	 * Get the names of all the tables stored in files.
	 * 
	 * @return the table names as Strings in an array.
	 * @since 0.5
	 */
	String[] listTables () throws IOException {

		File dataDirectory = new File(dataDir);
		String[] tables = dataDirectory.list();

		for (int i = tables.length - 1; i >= 0; i--) {
			String table = tables[i];
			if (table.contains(".")) {
				tables[i] = table.substring(0, table.lastIndexOf('.'));
			}
		}

		return tables;

	}

	// ----- Constructor ----- //

	DataFile (String dataDirectory) {
		this.dataDir = dataDirectory;
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		DataFile file = new DataFile("bin/data/");

		try {
			file.testDataFile();
			System.out.println("DataFile tests complete.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
