package hopeless.logic;

import java.io.Serializable;

public class Position implements Serializable {

	private int row;
	private int col;
	private double cost;
	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	public Position(int row, int col, double cost){
		this.row = row;
		this.col = col;
		this.cost = cost;
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
