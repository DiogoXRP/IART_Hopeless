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
	private ArrayList<Character> colours = new ArrayList<Character>();
	private int nr_of_colours;

	public Board(int rows, int columns, int dificulty) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[rows][columns];
		if (dificulty==3 || dificulty==4 || dificulty==5 || dificulty==6) {
			this.nr_of_colours = dificulty;
		}
		colours.add('A');
		colours.add('B');
		colours.add('C');
		colours.add('D');
		colours.add('E');
		colours.add('F');
		
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

	public void createBoard() {
		ArrayList<Character> list = new ArrayList<Character>(this.nr_of_colours);
		Collections.shuffle(colours);
		for (int x = 0; x < this.nr_of_colours ; x++){
			list.add(colours.get(x));
		}
		Collections.shuffle(list);

		Random rc = new Random();

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				setPositionContent(r, c, list.get(rc.nextInt(list.size())));
				//System.out.print(board[r][c] + " ");
			}
		}
	}
	
	public void printBoard(){
		for(int r = 0; r<board.length; r++){
			for(int c = 0; c< board[r].length; c++){
				System.out.print(board[r][c] + " ");
			}
			System.out.print("\n");
		}
	}
}
