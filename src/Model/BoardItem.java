package Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/**
 * BoardItem interface that can keep all the possible board object on the board
 * @author Cameron
 *
 */
public abstract class BoardItem {
	private PieceSymbol front = PieceSymbol.NONE;
	private PieceSymbol left = PieceSymbol.NONE;
	private PieceSymbol right = PieceSymbol.NONE;
	private PieceSymbol back = PieceSymbol.NONE;
	private char pieceName;
	
	public static enum PieceSymbol{
		SWORD,SHIELD,NONE;
	}
	/**
	 * returns front view weapon of the piece
	 * @return
	 */
	public PieceSymbol getFront(){
		return front;
	}
	/**
	 * returns back view weapon of the piece
	 * @return
	 */
	public PieceSymbol getBack(){
		return back;
	}
	/**
	 * returns right view weapon of the piece
	 * @return
	 */
	public PieceSymbol getRight(){
		return right;
	}
	/**
	 * returns left view weapon of the piece
	 * @return
	 */
	public PieceSymbol getLeft(){
		return left;
	}
	/**
	 * sets front view weapon of the piece
	 * @return
	 */
	public void setFront(PieceSymbol front){
		 this.front = front;
	}
	/**
	 * sets back view weapon of the piece
	 * @return
	 */
	public void setBack(PieceSymbol back){
		this.back = back;
	}
	/**
	 * sets right view weapon of the piece
	 * @return
	 */
	public void setRight(PieceSymbol right){
		this.right = right;
	}
	/**
	 * sets left view weapon of the piece
	 * @return
	 */
	public void setLeft(PieceSymbol left){
			this.left = left;
	}
	/**
	 * Sets the identifier of the board piece
	 */
	public void setName(char name){
		this.pieceName = name;
	}
	/**
	 * Get the identifier of the board piece
	 * @return
	 */
	public char getName(){
		return pieceName;
	}
	/**
	 * Rotates around the pieces weapons
	 * @param angle
	 * 		Angle of rotation
	 * @return
	 */
	public boolean rotate(int angle){
		
		int amount = (angle/90);
		List<PieceSymbol> directions = Arrays.asList(front,right,back,left);
		Collections.rotate(directions, amount);
		//rotates flips the objects order clockwise and reassigns the values to data
		front= directions.get(0);
		right= directions.get(1);
		back= directions.get(2);
		left= directions.get(3);
	
		return true;
	}
	/**
	 * Draws the top of the piece to keep it on three lines per row
	 */
	public abstract void drawTop();
	/**
	 * Draws the middle of the piece to keep it on three lines per row
	 */
	public abstract void drawMiddle();
	/**
	 * Draws the bottom of the piece to keep it on three lines per row
	 */
	public abstract void drawBottom();
	
	
}
