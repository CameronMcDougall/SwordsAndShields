package Model;

import java.util.Arrays;
import java.util.List;

import View.GamePanel;
/**
 * Playing piece on the board that determines the game
 * @author Cameron
 *
 */
public class Piece extends BoardItem implements Cloneable  {

	private boolean isUsed = false;
	private boolean isYellow;
	private boolean isDead = false;
	private int opacity = 255;
	private String death;
	private boolean hasAudio= false;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private int destX;
	private int destY;
	private boolean isMoving =false;


	/**
	 * Creates the boards directional weapons and sets its identifier
	 * @param directions
	 * 		sets the directional weapon based on the the positions and the order of the enum
	 * @param pieceName
	 * 		sets identifier of name
	 */
	public Piece(List<PieceSymbol> directions,char name,boolean isYellow) {
		//list is used to get the permuations much easier
		//sets the pieces weapons and identifier
		setFront(directions.get(0));
		setRight(directions.get(1));
		setBack(directions.get(2));
		setLeft(directions.get(3));
		setName(name);
		this.isYellow = isYellow;

	}
	/**
	 * Returns if the piece is moving
	 * @return
	 */
	public boolean isMoving() {
		return isMoving;
	}
	/**
	 * Sets if the piece is moving
	 * @param isMoving
	 * 		is it moving
	 */
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	/**
	 * Moves the piece by a certain velocity if in animation
	 */
	public void moving() {
		if(isMoving) {
			setDx();
			setDy();//sets the velocity
			if(changeVelocityX(this.dx) || changeVelocityY(this.dy)){//if it has to be set to the exact spot
				isMoving = false;
			}else {
				if(destX != x) {//move the piece
					x += dx;

				}
				if(destY != y) {
					y += dy;
				}
			}
		}
	}
	/**
	 * Overloading of the function to set the specific speed
	 * @param dx
	 * 		velocity in x axis
	 * @param dy
	 * 		velocity in y axis
	 */
	public void moving(int dx,int dy) {
		if(isMoving) {
			this.dx = dx;
			this.dy = dy;//sets the velocitys
			changeVelocityX(dx);//checks for change
			changeVelocityY(dy);
			boolean moved = false;
			if(destX != x) {
				x += dx;
				moved = true;
			}
			if(destY != y) {//if moved
				y += dy;
				moved = true;
			}
			if(!moved) {//if not moved
				isMoving = false;//done
			}
		}
	}
	/**
	 * Gets the destination in the x axis
	 * @return
	 */
	public int getDestX() {
		return destX;
	}
	/**
	 * Sets the destination in the x axis
	 * @param destX
	 * 		Destination in the x axis
	 */
	public void setDestX(int destX) {
		this.destX = destX;
	}
	/**
	 * Gets the destination in the y axis
	 * @return
	 */
	public int getDestY() {
		return destY;
	}
	/**
	 * Sets the destination in the y axis
	 * @param destY
	 * 		destination in y axis
	 */
	public void setDestY(int destY) {
		this.destY = destY;
	}
	/**
	 * Sets the velocity in x axis allowing 10 movements
	 */
	public void setDx() {
		if(x - destX > 0) {//checks if in left or right direction
			dx = -(GamePanel.colSpacing/10);
		}else if(x - destX < 0) {
			dx = GamePanel.colSpacing/10;
		}
	}
	/**
	 * Sets the velocity in y axis allowing 10 movements
	 */
	public void setDy() {
		if(y - destY > 0) {//checks if going up or down
			dy = -(GamePanel.rowSpacing/10);
		}else if(y - destY < 0) {
			dy = (GamePanel.rowSpacing/10);;
		}
	}
	/**
	 * Gets the x position of the graphic object
	 * @return
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets the x position
	 * @param x
	 * 		The x position to be set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Gets the y position of the graphic object
	 * @return
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the y position
	 * @param y
	 * 		The y position to be set
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Checks if the x position is going to go over the position its meant to be
	 * by setting it to the destination
	 * @param dx
	 * 		Velcity in x axis
	 * @return
	 */
	public boolean changeVelocityX(int dx) {
		if((destX >= x + dx && x > destX)) {//moving from left
			x = destX;
			return true;
		}else if(x + dx >= destX  && x < destX){//moving from right
			x = destX;
			return true;
		}
		return false;
	}
	/**
	 * Checks if the y position is going to go over the position its meant to be
	 * by setting it to the destination
	 * @param dx
	 * 		Velcity in y axis
	 * @return
	 */
	public boolean changeVelocityY(int dy) {
		if((destY <= y + dy && y  < destY)) {//moving down
			y = destY;
			return true;
		}else if(destY >= y + dy && y > destY){//moving up
			y = destY;
			return true;
		}
		return false;
	}
	/**
	 * Gets if the pieces death for sounds 
	 * @return
	 */
	public String getDeath(){
		return death;
	}
	public void setDeath(String death){
		this.death = death;
	}
	public void setIsDead(boolean isDead){
		this.isDead = isDead;
	}
	/**
	 * Gets if the piece is dead or not for animations and removal of the piece
	 * @return
	 */
	public boolean getIsDead(){
		return isDead;
	}
	public void setColour(boolean isYellow){
		this.isYellow = isYellow;
	}
	/**
	 * Gets the opacity
	 * @return
	 */
	public int getOpacity(){
		return opacity;
	}
	public void setOpacity(int opacity){
		this.opacity = opacity;
	}
	/**
	 * decreases the opacity/ the alpha of the graphics of this object
	 */
	public void decreaseOpacity(){
		if(this.opacity-20 > 0 && isDead){//Opacity lowered if possible
			this.opacity -= 20;
		}else{//sets to be perfect
			this.opacity = 0;
		}
	}
	/**
	 * Gets if the piece is yellow or not
	 * @return
	 */
	public boolean getColour(){
		return this.isYellow;
	}
	/**
	 * returns if the object has been used in a turn
	 * @return
	 */
	public boolean isUsed(){
		return isUsed;
	}
	/**
	 * Sets if the piece has in a turn
	 */
	public void setIsUsed(boolean isUsed){
		this.isUsed = isUsed;
	}


	@Override
	public void drawTop() {
		// direction determines how the sword is drawn
		if(getFront() == PieceSymbol.SWORD) {
			System.out.print(" | ");
		}else if(getFront() == PieceSymbol.SHIELD) {
			System.out.print(" # ");
		}else {
			System.out.print("   ");
		}

	}
	@Override
	public void drawMiddle() {
		// direction determines how the sword is drawn
		//left hand side of board object
		if(getLeft() == PieceSymbol.SWORD) {
			System.out.print("-");
		}else if(getLeft() == PieceSymbol.SHIELD) {
			System.out.print("#");
		}else {
			System.out.print(" ");
		}
		System.out.print(getName());
		//draws the identifier in middle
		if(getRight() == PieceSymbol.SWORD) {
			System.out.print("-");
		}else if(getRight() == PieceSymbol.SHIELD) {
			System.out.print("#");
		}else {
			System.out.print(" ");
		}
		//draws right hand side
	}
	@Override
	public void drawBottom() {
		//draws bottom third of piece
		if(getBack() == PieceSymbol.SWORD) {
			System.out.print(" | ");
		}else if(getBack() == PieceSymbol.SHIELD) {
			System.out.print(" # ");
		}else {
			System.out.print("   ");
		}
	}

	public boolean isHasAudio() {
		return hasAudio;
	}

	public void setHasAudio(boolean hasAudio) {
		this.hasAudio = hasAudio;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	@Override
	public Piece clone(){
		//creates a new instance so pointers are changed
		Piece clone = new Piece(Arrays.asList(getFront(),getRight(),getBack(),getLeft()),getName(),false);
		clone.setIsUsed(this.isUsed);
		clone.setColour(isYellow);
		clone.setIsDead(isDead);
		clone.setOpacity(this.opacity);
		clone.setDeath(death);
		clone.setX(x);
		clone.setY(y);
		clone.setDy(dy);
		clone.setDx(dx);
		clone.setDestX(destX);
		clone.setDestY(destY);
		clone.setHasAudio(hasAudio);
		clone.setMoving(isMoving);
		return clone;
	}
	@Override
	public String toString() {
		return "["+getName()+", "+getFront().name()+", "+getRight().name()+", "+getBack().name()+", "+getLeft().name()+"]";
	}

}
