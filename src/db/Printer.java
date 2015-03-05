// package db;

/**
 * Handles printing of information for the user.
 *
 * @since 0.4
 */

public class Printer {

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Printer class, must be run with -ea.
	 * 
	 * @since 0.4
	 */
	private void testPrinter () {

		String[] columns = {"colOne", "colTwo", "colThree"};
		Table table = new Table(columns);

		System.out.println("Printing columns:");
		printColumns(columns);

		String[] rowOne = {"one", "two", "three"};
		String[] rowTwo = {"four", "five", "six"};
		try {
			table.addRow(rowOne);		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		try {
			table.addRow(rowTwo);		
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Printing rows:");
		printRows(table.getRows());

		System.out.println("Printing full table:");
		printTable(table);

	}

	/**
	 * Prints out the rows of a table.
	 *
	 * @param rows an array of Record objects.
	 * @since 0.4
	 */
	private void printRows (Record[] rows) {

		for (Record row : rows) {
			printRow(row);
		}

	}

	/**
	 * Prints out the columns of a table.
	 *
	 * @param columns an array containing the column names.
	 * @since 0.4
	 */
	public void printColumns (String[] columns) {

		System.out.print("||");

		for (String attr : columns) {
			System.out.print(" " + attr + " ||");
		}

		System.out.print("\n\n");

	}

	/**
	 * Prints out a single Record.
	 *
	 * @param row a Record object.
	 * @since 0.4
	 */
	public void printRow (Record row) {

		String[] values = row.getValues();

		System.out.print("|");

		for (String value : values) {
			System.out.print(" " + value + " |");
		}

		System.out.print("\n");

	}

	/**
	 * Prints out a table, with columns.
	 *
	 * @param table the table to be printed.
	 * @since 0.4
	 */
	public void printTable (Table table) {

		String[] columns = table.getColumns();
		Record[] rows = table.getRows();

		printColumns(columns);
		printRows(rows);

	}

	// ----- Main ----- //g

	public static void main(String[] args) {

		Printer printer = new Printer();
		printer.testPrinter();

	}

}
