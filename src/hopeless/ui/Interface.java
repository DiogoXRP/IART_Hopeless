package hopeless.ui;

import java.util.Scanner;

import hopeless.logic.Board;

public class Interface {
	
	private static final String CLEAR = "\033[H\033[2J";
	private static final int ROWS = 10;
	private static final int COLUMNS = 20;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Hopeless\n");
		//System.out.print(CLEAR);
		Scanner scanner = new Scanner(System.in);
		int dificulty = 0;
		System.out.println("Please, choose the game's dificulty:\n\n"
				+ "3 - Easy;\n"
				+ "4 - Medium;\n"
				+ "5 - Hard;\n"
				+ "6 - Extreme;\n");
		if (scanner.hasNextInt())
			dificulty = scanner.nextInt();
		//System.out.print(CLEAR);
		Board board = new Board(ROWS, COLUMNS, dificulty);
		board.createBoard();
		board.printBoard();
		
	}

}
