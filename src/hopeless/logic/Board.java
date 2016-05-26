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
	private int[] dificulty = { 3, 4, 5, 6 };
	private ArrayList<Character> colours = new ArrayList<Character>();
	private int nr_of_colours;
	private ArrayList<Position> visited = new ArrayList<Position>();

	public Board(int rows, int columns, int dificulty) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[rows][columns];
		if (dificulty == 3 || dificulty == 4 || dificulty == 5 || dificulty == 6) {
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

	public char getPositionContent(int row, int column) {
		return board[row][column];
	}

	public char[][] getBoard() {
		return board;
	}

	public void createBoard() {
		ArrayList<Character> list = new ArrayList<Character>(this.nr_of_colours);
		Collections.shuffle(colours);
		for (int x = 0; x < this.nr_of_colours; x++) {
			list.add(colours.get(x));
		}
		Collections.shuffle(list);

		Random rc = new Random();

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				setPositionContent(r, c, list.get(rc.nextInt(list.size())));
				// System.out.print(board[r][c] + " ");
			}
		}
	}

	public void printBoard() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.print("\n");
		}
	}

	public void Click(int row, int col) {
		if (row == -1 || col == -1) {
			System.out.println("invalid arguments");
			return;
		}

		char colour = board[row][col];

		professionalalgorithm(colour, row, col);
		System.out.println("rows: " + this.rows);
		System.out.println("columns: " + this.columns);
		/*DEBUG 
		for(int i = 0 ; i<visited.size();i++)
			System.out.println("visited " + i + ": (" + visited.get(i).getRow() + "," + visited.get(i).getCol() + ")");
			*/
		
		//Passo 1 - Ver os visited e apagá-los do board
		//Passo 2 - Limpar o array visited;
		//Passo 3 - Puxar para baixo e esquerda todas as coisas para completar espaços
		//Passo 4 - atualizar this.rows this.columns
	}

	public void professionalalgorithm(char colour, int row, int col) {
		visited.add(new Position(row, col));

		if (row - 1 >= 0 && !visited.contains(new Position(row - 1, col))) {
			if(colour==board[row-1][col])
				professionalalgorithm(colour, row - 1, col);
		}if (col - 1 >= 0 && !visited.contains(new Position(row, col - 1))) {
			if(colour==board[row][col-1])
				professionalalgorithm(colour, row, col - 1);
		} if (row + 1 < rows && !visited.contains(new Position(row + 1, col))) {
			if(colour==board[row+1][col])
				professionalalgorithm(colour, row + 1, col);
		} if (col + 1 < columns && !visited.contains(new Position(row, col + 1))) {
			if(colour==board[row][col+1])
				professionalalgorithm(colour, row, col + 1);
		}
	}
}
