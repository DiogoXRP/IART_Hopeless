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
	private static BoardComparator comp = new BoardComparator();
	private PriorityQueue<Board> open = new PriorityQueue<Board>(40, comp);
	private ArrayList<Board> closed = new ArrayList<Board>();
	private ArrayList<Position> visited = new ArrayList<Position>();
	private int max_open_size;
	private Greedy greedy = new Greedy();
	private ArrayList<Board> onestepgreedychilds = new ArrayList<Board>();
	private Board finalBoard;

	public Astar() {

	}

	public Astar(Board starting) {
		closed.add(starting);
		open.addAll(getNeighbours(starting));
		// TODO quando limparmos o open, limpamos o closed;

		/*
		 * DEBUG for (int i = 0; i < open.size(); i++) {
		 * open.get(i).printBoard(); System.out.println("foi o " + i); }
		 */
		/*
		 * System.out.println("prints: \n"); while(!open.isEmpty()){
		 * open.poll().printBoard(); System.out.println("\n"); }
		 */
		boolean only_once = true;
		while (true) {


			max_open_size = open.size();
			if (open.size() == max_open_size) {
				for (int o = 0; o < max_open_size; o++) {
					if(current_board.checkGameOver()){
						finalBoard = current_board;
						return;
					}
					current_board = (Board) deepClone(open.poll());
					onestepgreedychilds.add(greedy.oneStepGreedy(current_board));
				}
				Collections.sort(onestepgreedychilds, Board.getCompByName());
				Collections.reverse(onestepgreedychilds);
				current_board = (Board) deepClone(onestepgreedychilds.get(0));
				open.clear();
				closed.clear();
			}
			
			
			if(current_board.checkGameOver()){
				finalBoard = current_board;
				return;
			}
			
			ArrayList<Board> neighbours = getNeighbours(current_board);
			
			//open.addAll(neighbours);
			
			//max_open_size = neighbours.size();
			for (int n = 0; n < neighbours.size(); n++) {
				if (!closed.contains(neighbours.get(n))) {
					PriorityQueue<Board> aux = new PriorityQueue<Board>();
					// System.out.println("OPEN: ");
					// while (!open.isEmpty())
					// open.poll(); // TODO Check if (why) this works lol
					// System.out.println("\n");

					// System.out.println("DONE OPEN \n");
					try {
						aux.addAll(open);
					} catch (Exception e) {
						// System.out.println("Add all print : ");
						// while(!open.isEmpty())
						// open.poll().printBoard();
					}
					Board existing = null;
					if (open.contains(neighbours.get(n)))
						existing = (Board) deepClone(retrieveBoard(neighbours.get(n), aux));
					if ((open.contains(neighbours.get(n)) && neighbours.get(n).isPathShorterThan(existing))
							|| !open.contains(neighbours.get(n))) {
						if (open.contains(neighbours.get(n))) {
							neighbours.get(n).setParent(current_board);
							PriorityQueue<Board> replace = new PriorityQueue<Board>();// set
																						// neighbours.get(n)
																						// to
																						// replace
																						// previous
																						// open
																						// element
							PriorityQueue<Board> aux2 = new PriorityQueue<Board>();
							aux2.addAll(open);
							replace.addAll(replace(neighbours.get(n), aux2));
							open.clear(); // empty open
							open.addAll(replace);
						}
						if (!open.contains(neighbours.get(n))) {
							System.out.print(".");
							neighbours.get(n).setParent(current_board);
							open.add(neighbours.get(n));
						}

					}
				}

			}
		}
	}

	public PriorityQueue<Board> replace(Board board, PriorityQueue<Board> aux) {
		ArrayList<Board> popped = new ArrayList<Board>();
		int index = 0;
		while (!aux.isEmpty()) {
			Board temp = aux.poll(); // pop
			if (board.equals(temp))
				popped.add(board);
			else
				popped.add(temp);
			index++;
		}
		PriorityQueue<Board> result = new PriorityQueue<Board>();
		result.addAll(popped);
		return result;

	}

	public Board retrieveBoard(Board toFind, PriorityQueue<Board> aux) {
		while (!aux.isEmpty()) {
			Board temp = aux.poll();
			if (toFind.equals(temp))
				return temp;
		}
		return null;
	}

	public ArrayList<Board> getNeighbours(Board current) {
		ArrayList<Board> neighb = new ArrayList<Board>();
		ArrayList<Board> test_boards = new ArrayList<Board>();
		int index = 0;
		current_board = new Board(current);
		Vector col_height = current_board.getcol_height();
		int columns = current_board.getColumns();
		boolean alternate = false;
		if (col_height.size() > 0)
			for (int slice = 0; slice < 10 + columns - 1; slice++) {
				// System.out.println("slice " + slice);
				int z1 = slice < columns ? 0 : slice - columns + 1;
				int z2 = slice < 10 ? 0 : slice - 10 + 1;
				if (!alternate)
					for (int jj = slice - z2; jj >= z1; jj--) {
						// System.out.println(board[jj][slice-jj]);
						if (current_board.getBoard()[jj][slice - jj] != ' ') {
							if (!current_board.getVisited().contains(new Position(jj, slice - jj))) {
								Board demo = (Board) deepClone(current_board);
								test_boards.add(demo);

								current_board.professionalalgorithm(current_board.getBoard()[jj][slice - jj], jj,
										slice - jj);
								test_boards.get(index).clearVisited();
								test_boards.get(index).professionalalgorithm(
										test_boards.get(index).getBoard()[jj][slice - jj], jj, slice - jj);

								if (test_boards.get(index).getVisited().size() > 1) {
									test_boards.get(index).Click(jj, slice - jj);
									test_boards.get(index).Heuristic();
									test_boards.get(index).calcFinalCost();
									test_boards.get(index).setParent(current_board); // TODO
																						// Check
																						// if
																						// right
									neighb.add(test_boards.get(index));
									// open.get(index).printBoard();
									index++;
								} else
									test_boards.remove(index);
							}
						}
					}

				alternate = !alternate;
				// if(alternate)
				// System.out.println("\n");
			}
		return neighb;
	}

	public ArrayList<Board> getClosed() {
		return this.closed;
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
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Board getFinalBoard(){
		return finalBoard;
	}
}
