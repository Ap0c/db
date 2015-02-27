package db;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/**
 * Handles reading and writing of tables to and from files.
 *
 * @since 0.3
 */

class File {

	// ----- Class Variables ----- //

	private static final String dataDir = "data/";

	// ----- Instance Methods ----- //

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

		objectOut.write(table);
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
	
}