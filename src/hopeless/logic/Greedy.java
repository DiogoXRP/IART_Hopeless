package hopeless.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

public class Greedy {
	private Board current_board;
	private Board last;
	private static GreedyBoardComparator comp = new GreedyBoardComparator(); // in case prio_queue
	private ArrayList<Board> open = new ArrayList<Board>();
	private ArrayList<Board> closed = new ArrayList<Board>();
	private ArrayList<Position> visited = new ArrayList<Position>();

	public Greedy() {

	}

	public ArrayList<Board> Activate(Board starting) {

		current_board = (Board) deepClone(starting);
		int aux = 0;
		while (!current_board.checkGameOver()) {
			//System.out.println("index:  " + index);
			ArrayList<Board> setcontent = new ArrayList<Board>();
			ArrayList<Board> neighbours = getNeighbours(current_board);
			open.addAll(neighbours);
			setcontent.clear();
			setcontent.addAll(open);
			Collections.sort(setcontent, Board.getCompByName());
			last = current_board;
			aux = setcontent.size()-1;
			while(aux > 0  && current_board.mattoString().equals(setcontent.get(aux).mattoString())){
				System.out.println("\n\n IN \n\n");
				aux--;
			}
			
			System.out.println("open size: " + open.size());
			current_board = setcontent.get(aux);
			for(int i = 0; i < open.size(); i++){
				setcontent.get(i).printBoard();
				System.out.println("\nScore " +setcontent.get(i).getScore());
				
			}
			
			closed.add(current_board);
			System.out.println("\n Current: \n");
			current_board.printBoard();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			open.clear();
		}
		
		return closed;

	}

	public ArrayList<Board> getNeighbours(Board current) {
		ArrayList<Board> neighb = new ArrayList<Board>();
		ArrayList<Board> test_boards = new ArrayList<Board>();
		int indexx = 0;
		current_board = new Board(current);
		Vector col_height = current_board.getcol_height();
		int columns = current_board.getColumns();
		boolean alternate = false;
		if(col_height.size() > 0)
		for (int slice = 0; slice < (int) Collections.max(col_height) + columns - 1; slice++) {
			// System.out.println("slice " + slice);
			int z1 = slice < columns ? 0 : slice - columns + 1;
			int z2 = slice < (int) Collections.max(col_height) ? 0 : slice - (int) Collections.max(col_height) + 1;
			System.out.print("\nDiagonal: ");
			if (!alternate)
				for (int jj = slice - z2; jj >= z1; jj--) {
					// System.out.println(board[jj][slice-jj]);
					System.out.print(current_board.getBoard()[jj][slice-jj]);
					if (!current_board.getVisited().contains(new Position(jj, slice - jj))) {
						Board demo = (Board) deepClone(current_board);
						test_boards.add(demo);

						current_board.professionalalgorithm(current_board.getBoard()[jj][slice - jj], jj, slice - jj);
						test_boards.get(indexx).clearVisited();
						test_boards.get(indexx).professionalalgorithm(test_boards.get(indexx).getBoard()[jj][slice - jj],
								jj, slice - jj);

						if (test_boards.get(indexx).getVisited().size() > 1) {
							test_boards.get(indexx).Click(jj, slice - jj);
							test_boards.get(indexx).Heuristic();
							test_boards.get(indexx).calcFinalCost();
							//test_boards.get(indexx).setParent(current_board); // TODO
																				// Check
																				// if
																				// right

							neighb.add(test_boards.get(indexx));
							// open.get(index).printBoard();
							indexx++;
						} else
							test_boards.remove(indexx);
					}
				}
			alternate = !alternate;
			// if(alternate)
			// System.out.println("\n");
		}
		return neighb;
	}

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
	
	
}
