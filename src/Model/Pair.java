package Model;

import java.awt.Rectangle;

/**
 * Class that contains two pairs that have reactions to each other
 * @author Cameron
 *
 */
public class Pair {
	private Piece one;
	private Piece two;
	private String direction;
	private Rectangle rect;
	private int x1,y1,x2,y2;
	/**
	 * Constructor that store useful information of the reactions
	 * @param one
	 * 		First piece
	 * @param two
	 * 		Second piece
	 * @param direction
	 * 		Direction of the reaction
	 */
	public Pair (Piece one, Piece two,String direction){
		this.one = one;
		this.two = two;
		this.direction = direction;
	}
	/**
	 * Sets the positions of each from array coordinates
	 * @param x1
	 * 		One variable x position
	 * @param y1
	 * 		One variable y position
	 * @param x2
	 * 		Two variable x position
	 * @param y2
	 * 		Two variable y position
	 */
	public void setPositions(int x1,int y1,int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2; 
		this.y2 = y2;
	}
	/**
	 * Gets the direction of the reaction
	 * @return
	 */
	public String getDirection(){
		return direction; 
	}
	/**
	 * Gets the first piece
	 * @return
	 */
	public Piece getOne() {
		return one;
	}
	/**
	 * Gets the second piece
	 * @return
	 */
	public Piece getTwo() {
		return two;
	}
	/**
	 * Gets the bounding box of the middle of the two pieces
	 * @return
	 */
	public Rectangle getRect() {
		return rect;
	}
	/**
	 * Sets the bounding box of the middle of the two pieces
	 * @param rect
	 * 		The new bounding box
	 */
	public void setRect(Rectangle rect) {
		this.rect = rect;
	}
	/**
	 * Gets the column position in the array of first piece
	 * @return
	 */
	public int getX1() {
		return x1;
	}
	/**
	 * Gets the row position in the array of first piece
	 * @return
	 */
	public int getY1() {
		return y1;
	}

	/**
	 * Gets the column position in the array of second piece
	 * @return
	 */
	public int getX2() {
		return x2;
	}

	/**
	 * Gets the row position in the array of second piece
	 * @return
	 */
	public int getY2() {
		return y2;
	}

	@Override
	public String toString(){
		return one.toString()+"\n"+two.toString()+"\n"+direction;
	}
	@Override
	public int hashCode() {
		int result= x1*5+x2 *5;//so theres no adjacent reactions of the same pieces
		result += y1*7+y2*7;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if(hashCode() == obj.hashCode()){
			return true;
		}
		return false;
	}

}
