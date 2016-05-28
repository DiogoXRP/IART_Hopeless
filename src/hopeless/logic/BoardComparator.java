package hopeless.logic;

import java.util.Comparator;

public class BoardComparator implements Comparator<Board>{

	@Override
	public int compare(Board b1, Board b2){
		return Double.compare(b1.finalCost, b2.finalCost);
	}
}
