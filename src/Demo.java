import db.Database;
import db.ResultTable;
 
public class Demo {

	public static void main(String[] args) {

		Database db;

		// Connects to the database.
		try {
			System.out.println("Connecting to database...");
			db = new Database("bin/data/");
		} catch (Exception e) {
			System.out.println(
				"Database could not be created: " + e.getMessage());
			return;
		}

		// Adds a new table.
		String[] columns = {"demoColOne", "demoColTwo", "demoColThree"};
		try {
			System.out.println("Adding table 'demoTable'...");
			db.createTable("demoTable", columns);
		} catch (Exception e) {
			System.out.println("Problem creating table: " + e.getMessage());
			return;
		}

		// Adds values to the table.
		String[][] values = {
			{"valOne", "valTwo", "valThree"},
			{"valFour", "valFive", "valSix"}
		};
		try {
			System.out.println("Inserting values into table...");
			db.query.insert("demoTable", values);
		} catch (Exception e) {
			System.out.println("Values could not be added: " + e.getMessage());
			return;
		}

		// Saves database to disk.
		try {
			System.out.println("Committing changes...");
			db.commit();
		} catch (Exception e) {
			System.out.println(
				"Changes could not be committed: " + e.getMessage());
		}

		// Performs select on first and last columns.
		String[] selectCols = {"demoColOne", "demoColThree"};
		try {
			ResultTable result = db.query.select("demoTable", selectCols);
			System.out.println("\nThe result of a select:\n");
			result.print.all();
		} catch (Exception e) {
			System.out.println("Could not select columns: " + e.getMessage());
		}

		// Drops the last column, and renames the second column.
		try {
			System.out.println("Updating columns...");
			db.query.dropColumn("demoTable", "demoColThree");
			db.query.rename("demoTable", "demoColTwo", "lastColumn");
		} catch (Exception e) {
			System.out.println("Could alter columns: " + e.getMessage());
		}

		// Performs select on first and last columns.
		String[] newCols = {"demoColOne", "lastColumn"};
		try {
			String[] result = db.schema.table("demoTable");
			System.out.println("\nThe new columns:");
			for (String column : result) {
				System.out.print(column + ", ");
			}
			System.out.print("\n\n");
		} catch (Exception e) {
			System.out.println("Could not select columns: " + e.getMessage());
		}

		// Drops the table and updates database on disk to fully remove it.
		try {
			System.out.println("Dropping table and committing changes...");
			db.dropTable("demoTable");
			db.commit();
		} catch (Exception e) {
			System.out.println("Table could not be removed: " + e.getMessage());
		}

		System.out.println("Finished.");

	}

}