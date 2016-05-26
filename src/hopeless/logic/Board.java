package hopeless.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Board {
	private char[][] board;
	private int rows;
	private int columns;
	private int[] dificulty = {3, 4, 5, 6};
	private char[] colours = { 'A', 'B', 'C', 'D', 'E', 'F' };
	private int nr_of_colours;

	public Board(int rows, int columns, int dificulty) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[rows][columns];
		if (Arrays.asList(this.dificulty).contains(dificulty)) {
			this.nr_of_colours = dificulty;
		}
	}

	public void setPositionContent(int row, int column, char content) {
		board[row][column] = content;
	}
	
	public char getPositionContent(int row, int column){
		return board[row][column];
	}

	public char[][] getBoard() {
		return board;
	}

	public void printBoard() {
		List<Character> list = new ArrayList<Character>(this.nr_of_colours);
		for (char i : this.colours)
			list.add(i);
		Collections.shuffle(list);

		Random rc = new Random();

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				//board[c][r] = list.get(rc.nextInt(list.size()));
				setPositionContent(r, c, list.get(rc.nextInt(list.size())));
				System.out.println(board[r][c] + " ");
			}
		}
	}
}
