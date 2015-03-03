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

	// ----- Class Variables ----- //

	private static final String dataDir = "data/";

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the DataFile class, must be run with -ea.
	 * 
	 * @since 0.3
	 */
	private void testFile () {

		String[] columns = {"colOne", "colTwo", "colThree"};
		Table table = new Table(columns);
		String fileName = "testTable";
		try {
			saveTable(table, fileName);
		} catch (IOException e) {
			System.err.println("Problem writing to file.");
			System.exit(1);
		}

		File test = new File(dataDir + fileName);
		assert test.exists() : "File not created correctly.";

		Table testTable = null;

		try {
			testTable = readTable(fileName);
		} catch (IOException e) {
			System.err.println("Problem reading file.");
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found in file.");
			System.exit(1);
		}

		assert testTable != null : "Table not read in correctly.";
		assert Arrays.equals(columns, testTable.getColumns()) : "Incorrect " +
		"object in file.";

	}

	/**
	 * Saves a table to a file, serializing the object data.
	 * 
	 * @param table the table object to be saved.
	 * @param name name of the table, used to define filename.
	 * @since 0.3
	 */
	public void saveTable (Table table, String name) throws IOException {

		FileOutputStream file = new FileOutputStream(dataDir + name);
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
	public Table readTable (String tableName)
		throws IOException, ClassNotFoundException {

		FileInputStream file = new FileInputStream(dataDir + tableName);
		ObjectInputStream objectIn = new ObjectInputStream(file);

		Table table = (Table) objectIn.readObject();
		objectIn.close();
		file.close();

		return table;

	}

	// ----- Main ----- //

	public static void main(String[] args) {

		DataFile file = new DataFile();
		file.testFile();
		System.out.println("File tests complete.");

	}
	
}
