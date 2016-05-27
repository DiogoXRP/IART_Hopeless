package hopeless.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class Board {
	private char[][] board;
	private int rows;
	private int columns;
	private ArrayList<Character> colours = new ArrayList<Character>();
	private int nr_of_colours;
	private ArrayList<Position> visited = new ArrayList<Position>();
	private Vector col_height = new Vector();
	private static int level;
	private static int score = 0;
	private static int temp_score = 0;
	private static int dif;
	
	int heuristicCost = 0; // Heuristic cost
	int finalCost = 0; // G+H

	public Board(int rows, int columns, int dificulty) {
		this.rows = rows;
		this.columns = columns;
		this.board = new char[rows][columns];
		if (dificulty == 2 || dificulty == 3 || dificulty == 4 || dificulty == 5 || dificulty == 6) {
			this.nr_of_colours = dificulty;
			this.dif = dificulty;
		}
		colours.add('A');
		colours.add('B');
		colours.add('C');
		colours.add('D');
		colours.add('E');
		colours.add('F');

		for (int i = 0; i < 20; i++) {
			col_height.add(10);
		}

	}

	public Board(int dificulty) {
		this.rows = 10;
		this.columns = 20;
		char temp[][] = {
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'C', 'A', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B' },
				{ 'B', 'C', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B',
						'B' } };
		this.board = temp;
		if (dificulty == 3 || dificulty == 4 || dificulty == 5 || dificulty == 6) {
			this.nr_of_colours = dificulty;
		}
		colours.add('A');
		colours.add('B');
		colours.add('C');
		colours.add('D');
		colours.add('E');
		colours.add('F');

		for (int i = 0; i < 20; i++) {
			col_height.add(10);
		}
		this.dif = dificulty;
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

		/*
		 * DEBUG System.out.println("rows: " + this.rows); System.out.println(
		 * "columns: " + this.columns); for(int i = 0 ; i<visited.size();i++)
		 * System.out.println("visited " + i + ": (" + visited.get(i).getRow() +
		 * "," + visited.get(i).getCol() + ")");
		 */

		// Passo 1 - Ver os visited e apagá-los do board
		for (int v = 0; v < visited.size(); v++) {
			board[visited.get(v).getRow()][visited.get(v).getCol()] = ' ';
		}
		
		// Pass 1.5 - Adicionar ao score;
		this.score += this.dif*Math.pow((visited.size()-1), 2);

		// Passo 2 - Limpar o array visited;
		visited.clear();

		// Passo 3 - Puxar para baixo e esquerda todas as coisas para completar
		// espaços

		for (int ci = 0; ci < columns; ci++) {
			level = 10 - (int) col_height.get(ci);
			for (int ri = 9; ri >= level; ri--) {

				if (board[ri][ci] == ' ') {
					pullDownFrom(ri, ci);
					ri++;
				}
			}
		}
		clearEmptyCols();

		System.out.println("\nAfter Clear empty col\n");
		printBoard();

	}

	public void clearEmptyCols() {
		int ind;
		while ((ind = col_height.indexOf(0)) != -1) {
			pullLeftFrom(ind);
			col_height.remove(ind);
		}
	}

	public void pullLeftFrom(int col) {

		for (int i = col; i < columns; i++) {

			if (i == columns - 1) {
				for (int k = 0; k < rows; k++) {
					board[k][i] = ' ';
				}
				columns--;
			} else {
				for (int j = 0; j < rows; j++) {
					board[j][i] = board[j][i + 1];
				}
			}
		}

	}

	public void pullDownFrom(int row, int col) {

		while (row > (10 - (int) col_height.get(col))) {

			board[row][col] = board[row - 1][col];
			row--;
		}

		board[10 - (int) col_height.get(col)][col] = ' ';
		col_height.set(col, (int) col_height.get(col) - 1);
		level++;

	}

	public void professionalalgorithm(char colour, int row, int col) {
		visited.add(new Position(row, col));

		if (row - 1 >= 0 && !visited.contains(new Position(row - 1, col))) {
			if (colour == board[row - 1][col])
				professionalalgorithm(colour, row - 1, col);
		}
		if (col - 1 >= 0 && !visited.contains(new Position(row, col - 1))) {
			if (colour == board[row][col - 1])
				professionalalgorithm(colour, row, col - 1);
		}
		if (row + 1 < rows && !visited.contains(new Position(row + 1, col))) {
			if (colour == board[row + 1][col])
				professionalalgorithm(colour, row + 1, col);
		}
		if (col + 1 < columns && !visited.contains(new Position(row, col + 1))) {
			if (colour == board[row][col + 1])
				professionalalgorithm(colour, row, col + 1);
		}
	}

	public boolean checkGameOver() {
		char selected_colour;
		visited.clear();
		ArrayList<Integer> polygon_sizes = new ArrayList<Integer>();
		ArrayList<Position> total_visited = new ArrayList<Position>();
		if (board.length != 0) {
			for (int y = 0; y < columns; y++) {
				for (int x = 9; x >= (10 - (int) col_height.get(y)); x--) {
					if (!total_visited.contains(new Position(x, y))) {
						selected_colour = board[x][y];
						professionalalgorithm(selected_colour, x, y);
						polygon_sizes.add(visited.size());
						total_visited.addAll(visited);
						visited.clear();
					}

				}
			}
			total_visited.clear();
			for (int p_size : polygon_sizes)
				if (p_size != 1)
					return false;
		}
		return true;

	}
	
	public int getScore(){
		return this.score;
	}
	
	
	
}
