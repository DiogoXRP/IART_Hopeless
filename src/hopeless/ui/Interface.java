package hopeless.ui;

import java.util.ArrayList;
import java.util.Scanner;

import hopeless.logic.Board;
import hopeless.logic.Position;

import hopeless.logic.Astar;
import hopeless.logic.Board;

public class Interface {

	private static final String CLEAR = "\033[H\033[2J";
	private static final int ROWS = 10;
	private static final int COLUMNS = 20;
	private static boolean game_over = false;

	
	//ASTAR:
	// PASSO 1 : 
	// PERCORRE NA DIAGONAL, Calcula/Testa [sem alterar o board] inverso do custo OnClick;
	// PASSO 2 :
	// Heurística : Percorrer a matriz toda [tambem na diagonal] e, sem pullDown/Left ver quantos pontos no maximo fariamos.
	// Nota: Começar pelo maior poligono encontrado;
	
	// PAsso 3 :
	// Implementar A* em si;
	
	
	public static void main(String[] args) {
		System.out.println("Welcome to Hopeless\n");
		// System.out.print(CLEAR);
		Scanner scanner = new Scanner(System.in);
		int dificulty = 0;
		System.out.println("Please, choose the game's dificulty:\n\n" + "3 - Easy;\n" + "4 - Medium;\n" + "5 - Hard;\n"
				+ "6 - Extreme;\n");
		if (scanner.hasNextInt())
			dificulty = scanner.nextInt();
		// System.out.print(CLEAR);
		//Board board = new Board(ROWS, COLUMNS, dificulty);
		Board board = new Board(dificulty);
		//board.createBoard();
		board.printBoard();

		System.out.println("ASTAR: \n\n");
		 
		Astar as = new Astar(board);
		// click
		/*while (!game_over) {
			
			int click_row = -1;
			int click_col = -1;

			Scanner gamescanner = new Scanner(System.in);
			System.out.print("Insert line number: ");

			if (gamescanner.hasNextInt())
				click_row = gamescanner.nextInt();

			gamescanner = new Scanner(System.in);
			System.out.print("Insert column number: ");

			if (gamescanner.hasNextInt())
				click_col = gamescanner.nextInt();

			board.Click(click_row, click_col);
			game_over = board.checkGameOver();
			//board.ReverseCost();
		}*/
		
		/* DEBUG ArrayList<Position> boardStates = new ArrayList<Position>();
		for (Position boardState : boardStates){
			board.Click(boardState.getRow(), boardState.getCol());
			try {
				Thread.sleep(500);
			} catch (InterruptedException i) {
				i.printStackTrace();
			}
		}*/
		
		System.out.println("Parents: ");
		ArrayList<Board> cl = as.getClosed();
		Board current = cl.get(cl.size()-1);
		current.printBoard();
		System.out.println("Score " + current.getScore());
		System.out.println("\n");
		//while(current.getParent() != null){
			//System.out.print("\n");
		while(true){
			
			current.getParent().printBoard();
			System.out.println("Score " + current.getParent().getScore());
			
			System.out.println("\n");
			for(int i = 0; i < cl.size(); i++){
				if(cl.get(i).equals(current.getParent()))
					current = new Board((Board) as.deepClone(cl.get(i)));
			}
			if(current.getParent().equals(board))
				break;
		}
			
		System.out.println("\n");
		board.printBoard();
		System.out.println("GAME OVERRRRR !!!!!!!! ");
		//System.out.println("Just kidding, it's: " + board.getScore());

	}
	
	

}
