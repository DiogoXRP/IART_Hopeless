package hopeless.logic;

public class Position {

	private int row;
	private int col;
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public int getRow(){
		return this.row;
	}
	public int getCol(){
		return this.col;
	}
	
	@Override
	public boolean equals(Object o) 
	{
	    if (o instanceof Position) 
	    {
	      Position c = (Position) o;
	      if ( this.row == c.getRow() && this.col == c.getCol()) //whatever here
	         return true;
	    }
	    return false;
	}
}
