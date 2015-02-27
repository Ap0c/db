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

	// ----- Instance Methods ----- //

	/**
	 * Saves a table to a file, serializing the object data.
	 * 
	 * @param table the table object to be saved.
	 * @since 0.3
	 */
	public void saveTable (Table table) throws IOException {}

	/**
	 * Reads a table from file, file must be a serialized Table object.
	 * 
	 * @param tableName the name of the file containing the Table object.
	 * @since 0.3
	 */
	public void readTable (String tableName)
		throws IOException, ClassNotFoundException {}
	
}