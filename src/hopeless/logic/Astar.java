package hopeless.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

/// Tab inicial percorre todos os clickables [diagonais] e descobre o score que o novo Tabuleiro teria associado [g];
// Calcula heuristica para esse tabuleiro-filho[h] e, deste modo, tabuleiro-filho fica com f [g+h]

// Board states em vez de (x,y) sao identificados pela similaridade da char[][];

//http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
public class Astar {

	private Board current_board;
	// private static BoardComparator comp = new BoardComparator();
	private ArrayList<Board> open = new ArrayList<Board>();
	private ArrayList<Position> visited = new ArrayList<Position>();

	public Astar() {

	}

	public Astar(Board starting) {
		ArrayList<Board> test_boards = new ArrayList<Board>();
		int index = 0;
		current_board = new Board(starting);
		Vector col_height = current_board.getcol_height();
		int columns = current_board.getColumns();

		boolean alternate = false;
		for (int slice = 0; slice < (int) Collections.max(col_height) + columns - 1; slice++) {
			// System.out.println("slice " + slice);
			int z1 = slice < columns ? 0 : slice - columns + 1;
			int z2 = slice < (int) Collections.max(col_height) ? 0 : slice - (int) Collections.max(col_height) + 1;
			if (!alternate)
				for (int jj = slice - z2; jj >= z1; jj--) {
					// System.out.println(board[jj][slice-jj]);
					if (!current_board.getVisited().contains(new Position(jj, slice - jj))) {
						System.out.println("entrou");
						Board demo = (Board)deepClone(current_board);
						test_boards.add(demo);
						
						current_board.professionalalgorithm(current_board.getBoard()[jj][slice - jj], jj, slice - jj);
						System.out.println("curr_b");
						current_board.printBoard();
						System.out.println("END CURR B \n");
						test_boards.get(index).clearVisited();
						test_boards.get(index).professionalalgorithm(test_boards.get(index).getBoard()[jj][slice - jj],
								jj, slice - jj);

						if (test_boards.get(index).getVisited().size() > 1) {
							System.out.println("mais a dentro");
							test_boards.get(index).Click(jj, slice - jj);
							test_boards.get(index).Heuristic();
							test_boards.get(index).calcFinalCost();
							open.add(test_boards.get(index));
							open.get(index).printBoard();
							index++;
						} else
							test_boards.remove(index);
					}
				}

			alternate = !alternate;
			// if(alternate)
			// System.out.println("\n");
		}
		for (int i = 0; i < open.size(); i++) {
			open.get(i).printBoard();
			System.out.println("foi o " + i);
		}

		/*
		 * while (true) {
		 * 
		 * }
		 */
	}

	/*
	 * public void ReverseCost(){
	 * 
	 * //(9,0) -> (9-1, 0 + 1) // (8, 1) --> Pai; // Pra baixo (subir linha e
	 * coluna até encontrar x = 0; // Volta ao (8,1) // Pra cima esquerda (desce
	 * linha e coluna até encontrar col = 0 OU x = col_height.get(col); // Volta
	 * ao (8,1) // x - 1
	 * 
	 * ArrayList<Position> sucessor_costs = new ArrayList<Position>(); boolean
	 * alternate = false;
	 * 
	 * for(int slice = 0; slice < (int)Collections.max(col_height) + columns -
	 * 1; slice++){ //System.out.println("slice " + slice); int z1 = slice <
	 * columns ? 0 : slice - columns +1; int z2 = slice <
	 * (int)Collections.max(col_height) ? 0 : slice -
	 * (int)Collections.max(col_height) + 1;
	 * 
	 * if(!alternate) for(int jj = slice - z2; jj>= z1 ; jj--){
	 * //System.out.println(board[jj][slice-jj]);
	 * professionalalgorithm(board[jj][slice-jj], jj, slice-jj);
	 * sucessor_costs.add(new Position(jj, slice-jj, this.temp_score +
	 * (1/this.dif*Math.pow((visited.size()-1), 2)))); visited.clear(); }
	 * alternate = !alternate; //if(alternate) // System.out.println("\n"); } }
	 */

	public static Object deepClone(Object object) {
		   try {
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     ObjectOutputStream oos = new ObjectOutputStream(baos);
		     oos.writeObject(object);
		     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		     ObjectInputStream ois = new ObjectInputStream(bais);
		     return ois.readObject();
		   }
		   catch (Exception e) {
		     e.printStackTrace();
		     return null;
		   }
		 }
}
