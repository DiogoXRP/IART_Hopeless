package hopeless.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/// Tab inicial percorre todos os clickables [diagonais] e descobre o score que o novo Tabuleiro teria associado [g];
// Calcula heuristica para esse tabuleiro-filho[h] e, deste modo, tabuleiro-filho fica com f [g+h]

// Board states em vez de (x,y) sao identificados pela similaridade da char[][];

//http://www.codebytes.in/2015/02/a-shortest-path-finding-algorithm.html
public class Astar {

	public static final int DIAGONAL_COST = 14;
	public static final int V_H_COST = 10;

	/*static class boardState {
		int heuristicCost = 0; // Heuristic cost
		int finalCost = 0; // G+H
		int i, j;
		boardState parent;

		boardState(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public String toString() {
			return "[" + this.i + ", " + this.j + "]";
		}
	}*/

	// Blocked boardStates are just null boardState values in grid
	static boardState[][] grid = new boardState[5][5];

	static PriorityQueue<boardState> open;

	static boolean closed[][];
	static int startI, startJ;
	static int endI, endJ;

	public static void setBlocked(int i, int j) {
		grid[i][j] = null;
	}

	public static void setStartboardState(int i, int j) {
		startI = i;
		startJ = j;
	}

	public static void setEndboardState(int i, int j) {
		endI = i;
		endJ = j;
	}

	static void checkAndUpdateCost(boardState current, boardState t, int cost) {
		if (t == null || closed[t.i][t.j])
			return;
		int t_final_cost = t.heuristicCost + cost;

		boolean inOpen = open.contains(t);
		if (!inOpen || t_final_cost < t.finalCost) {
			t.finalCost = t_final_cost;
			t.parent = current;
			if (!inOpen)
				open.add(t);
		}
	}

	public static void AStar() {

		// add the start location to open list.
		open.add(grid[startI][startJ]);

		boardState current;

		while (true) {
			current = open.poll();
			if (current == null)
				break;
			closed[current.i][current.j] = true;

			if (current.equals(grid[endI][endJ])) {
				return;
			}

			boardState t;
			if (current.i - 1 >= 0) {
				t = grid[current.i - 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i - 1][current.j - 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i - 1][current.j + 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}
			}

			if (current.j - 1 >= 0) {
				t = grid[current.i][current.j - 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.j + 1 < grid[0].length) {
				t = grid[current.i][current.j + 1];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);
			}

			if (current.i + 1 < grid.length) {
				t = grid[current.i + 1][current.j];
				checkAndUpdateCost(current, t, current.finalCost + V_H_COST);

				if (current.j - 1 >= 0) {
					t = grid[current.i + 1][current.j - 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}

				if (current.j + 1 < grid[0].length) {
					t = grid[current.i + 1][current.j + 1];
					checkAndUpdateCost(current, t, current.finalCost + DIAGONAL_COST);
				}
			}
		}
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

	public void Heuristic(int row, int col) {
	}
}
