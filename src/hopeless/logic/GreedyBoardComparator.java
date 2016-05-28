package hopeless.logic;

import java.util.Comparator;

public class GreedyBoardComparator implements Comparator<Board>{
	@Override
	public int compare(Board b1, Board b2){
		return Float.compare((1/b1.getScore()), (1/b2.getScore()));
	}
	
}
