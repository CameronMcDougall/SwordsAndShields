package Controller;


import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.GamePanel;
/**
 * Controller for the game panel to fit MVC pattern
 * @author Cameron
 *
 */
public class GamePanelController implements KeyListener,MouseListener{
	private GamePanel panel;
	private int rotation = 0;
	/**
	 * Game panel controller that handles all key and mouse movements
	 * @param p
	 * 		game panel that this objects for
	 */
	public GamePanelController(GamePanel p){
		this.panel = p;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(panel.getGame().getCreatable()) return;
		boolean successfulMove = false;
		if(panel.getSelected()!=null && !panel.getSelected().isUsed()){//if the piece is selected and moveable
			if(e.getKeyCode() == KeyEvent.VK_UP){
				successfulMove = panel.getGame().movePiece(panel.getSelected().getName(), "up");
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT){
				successfulMove = panel.getGame().movePiece(panel.getSelected().getName(), "left");
			}else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				successfulMove = panel.getGame().movePiece(panel.getSelected().getName(), "right");
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				successfulMove = panel.getGame().movePiece(panel.getSelected().getName(), "down");
			}
		}
		if(successfulMove){//if the piece was successfully moved

			this.resetSelected();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(panel.getGame().getCreatable()) return;// if not in second stage of turn
		if(panel.getSelected() == null){//if no piece is selected
			this.unSelected(e.getX(), e.getY());
		}else if(panel.getType().equals("") && !panel.getSelected().isUsed()){// sets if the piece has been clicked in middle 
																			 //or corner
			panel.clickedSelected(e.getX(), e.getY());
		}else if(panel.getType().equals("rotate")){//if middle was clicked
			this.selectedRotation(e.getX(), e.getY());
		}
		if(panel.getType().equals("move")){//if corners were clicked
			this.movedPiece(e.getX(), e.getY());
			this.resetSelected();
		}
	}
	/**
	 * When it it is unselected, the user will choose the reactions available or clicks the selected piece
	 * @param x
	 * 		x position of the clicked
	 * @param y
	 * 		y position of the clicked
	 */
	private void unSelected(int x, int y){
		if(!panel.getPair().isEmpty()){//if reaction
			panel.applyReaction(x, y);
		}else{//get piece
			panel.isSelected(x, y);
		}
	}
	/**
	 * Checks if the click was successful on the rotation mode
	 * @param x
	 * 		x position of click
	 * @param y
	 * 		y position of the click
	 */
	private void selectedRotation(int x, int y){
		if(panel.hasRotated(x, y) && !panel.getSelected().isUsed()){//keep rotating
			panel.getSelected().rotate(90);
			rotation++;
		}else{
			if(rotation != 0){//set rotation
				panel.getGame().rotatePiece(panel.getSelected().getName(),(rotation % 4)*90);// rotates on how many times it has been rotated
			}
			this.resetSelected();
		}
	}
	/**
	 * Resets the selected piece variables
	 */
	private void resetSelected(){
		panel.setType();
		panel.setSelected(null);
		panel.setSelectedRectangle();
		rotation = 0;
		
	}
	/**
	 * Checks the left corner of the piece
	 * @param x
	 * 		x position
	 * @param y
	 * 		y position
	 * @return
	 */
	private boolean isLeft(int x, int y){
		Rectangle boardSquare = panel.getSelectedRectangle();
		Rectangle segment = new Rectangle((int)boardSquare.getX()+5,(int)boardSquare.getY()+15,5,20);
		return segment.contains(x, y);
	}
	/**
	 * Checks the right corner of the piece
	 * @param x
	 * 		x position
	 * @param y
	 * 		y position
	 * @return
	 */
	private boolean isRight(int x, int y){
		Rectangle boardSquare = panel.getSelectedRectangle();
		Rectangle segment = new Rectangle((int)(boardSquare.getX()+boardSquare.getWidth()-10),(int)boardSquare.getY()+15
				,10,20);
		return segment.contains(x, y);
	}
	/**
	 * Checks the top corner of the piece
	 * @param x
	 * 		x position
	 * @param y
	 * 		y position
	 * @return
	 */
	private boolean isTop(int x, int y){
		Rectangle boardSquare = panel.getSelectedRectangle();
		Rectangle segment = new Rectangle((int)boardSquare.getX()+11,(int)boardSquare.getY()+5,28,5);
		return segment.contains(x, y);
	}
	/**
	 * Checks the bottom corner of the piece
	 * @param x
	 * 		x position
	 * @param y
	 * 		y position
	 * @return
	 */
	private boolean isBottom(int x, int y){
		Rectangle boardSquare = panel.getSelectedRectangle();
		Rectangle segment = new Rectangle((int)boardSquare.getX()+11,
				(int)(boardSquare.getY()+boardSquare.getHeight()-10),28,5);
		return segment.contains(x, y);
	}
	/**
	 * Checks the x and y position of move click to the corners of shape
	 * @param x
	 * 		x position of click
	 * @param y
	 * 		y position of click
	 * @return
	 */
	private boolean movedPiece(int x, int y){
		boolean hasMoved = false;
		if(isTop(x, y)){//top of piece clicked
			hasMoved = panel.getGame().movePiece(panel.getSelected().getName(), "down");
		}else if(isLeft(x, y)){//left of piece clicked
			hasMoved = panel.getGame().movePiece(panel.getSelected().getName(), "right");
		}else if(isRight(x, y)){//right of piece clicked
			hasMoved = panel.getGame().movePiece(panel.getSelected().getName(), "left");
		}else if(isBottom(x, y)){//bottom of piece clicked
			hasMoved = panel.getGame().movePiece(panel.getSelected().getName(), "up");
		}//movements invert as the clicks go opposite directions
		return hasMoved;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
