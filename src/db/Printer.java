package db;

/**
 * Handles printing of information for the user.
 *
 * @since 0.4
 */

public class Printer implements java.io.Serializable {

	// ----- Instance Variables ----- //

	private Table table;

	// ----- Instance Methods ----- //

	/**
	 * Tests the methods in the Printer class, must be run with -ea.
	 * 
	 * @since 0.4
	 */
	private void testPrinter () throws Exception {

		System.out.println("Printing columns:");
		columns();

		String[] rowOne = {"one", "two", "three"};
		String[] rowTwo = {"four", "five", "six"};
		table.addRow(rowOne);		
		table.addRow(rowTwo);		

		System.out.println("Printing rows:");
		rows();

		System.out.println("\nPrinting full table:");
		all();

	}

	/**
	 * Prints out the rows of a table.
	 *
	 * @since 0.4
	 */
	private void rows () {

		int noRows = table.noRecords();

		for (int i = 0; i < noRows; i++) {
			row(i);
		}

	}

	/**
	 * Prints out a single Record.
	 *
	 * @since 0.4
	 */
	private void row (int rowNumber) {

		String[] values = table.getRows()[rowNumber];

		System.out.print("|");

		for (String value : values) {
			System.out.print(" " + value + " |");
		}

		System.out.print("\n");

	}

	/**
	 * Prints out the columns of a table.
	 *
	 * @since 0.4
	 */
	public void columns () {

		System.out.print("||");

		for (String attr : table.getColumns()) {
			System.out.print(" " + attr + " ||");
		}

		System.out.print("\n\n");

	}

	/**
	 * Prints out a table, with columns.
	 *
	 * @since 0.4
	 */
	public void all () {

		String[] cols = table.getColumns();
		Record[] rows = table.getRecords();

		columns();
		rows();
		System.out.print("\n");

	}

	// ----- Constructor ----- //

	Printer (Table table) {
		this.table = table;
	}

	// ----- Main ----- //

	public static void main(String[] args) {

		String[] cols = {"colOne", "colTwo", "colThree"};
		Table table = new Table(cols);

		try {
			table.print.testPrinter();
			System.out.println("\nPrinter tests complete.\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
