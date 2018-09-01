package View;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

import Model.BoardItem.PieceSymbol;
import Model.Piece;
/**
 * Draw class to help draw the pieces
 * @author Cameron
 *
 */
public class Draw {
	/**
	 * Draws pieces on to the graphics pane
	 * @param g
	 * 		Graphics pane of panel
	 * @param piece
	 * 		Piece that is desired to be drawn
	 * @param x
	 * 		x offset on the graphics pane
	 * @param y
	 * 		y offset on the graphics pane
	 * @param width
	 * 		width of the piece
	 * @param height
	 * 		height of the piece
	 * @param opacity
	 * 		opacity of piece
	 */
	public void drawPiece(Graphics g,Piece piece, int x, int y,int width,int height,int opacity){
		PieceSymbol front = piece.getFront();
		PieceSymbol right = piece.getRight();
		PieceSymbol back = piece.getBack();
		PieceSymbol left = piece.getLeft();
		Graphics2D gr = (Graphics2D) g;
		AffineTransform at = gr.getTransform();//gets default transformation
		drawCircle(x, y, width, height, opacity, gr);
		List<PieceSymbol> directions = Arrays.asList(front,right,back,left);//gets all weapons
		gr.setColor(new Color(255,0,0,opacity));
		gr.setStroke(new BasicStroke(1));
		Rectangle sword = getWeapon(Math.round((float)(x+ (width/2.0)-width/8.0)), y ,Math.round((float)(width*(1/4.0))),Math.round((float)(height*(5.0/10.0)+height/10.0)));
		Rectangle sword2 = getWeapon(Math.round((float)(x+ (width/2.0)-width/8.0)), y ,Math.round((float)(width*(5.0/10.0)+width/10.0)),Math.round((float)(height*(1/4.0))));
		Rectangle shield = getWeapon(x, y,width,height/4);
		for(int i = 0; i < 4; i++){
			if(directions.get(i) == PieceSymbol.SWORD){
				if(i%2!=2) {
					gr.fill(sword);

				}else {
					gr.fill(sword2);
				}
			}else if(directions.get(i) == PieceSymbol.SHIELD){
				gr.fill(shield);
			}
			gr.rotate(Math.toRadians(90),Math.round(x+(width/2.0)),Math.round(y+(height/2.0)));//rotates the graphic pane on next object
			//by the midpoint of the circle
		}
		gr.setTransform(at);//resets back to default transformation
	}
	/**
	 * Draws the Circle of the piece with black outline
	 * @param x
	 * 		x offset on graphics pane
	 * @param y
	 * 		y offset on the graphics pane
	 * @param width
	 * 		width of piece
	 * @param height
	 * 		height of piece
	 * @param opacity
	 * 		opacity of piece
	 * @param gr
	 * 		graphic pane
	 */
	private  void drawCircle(int x, int y, int width, int height,int opacity,Graphics2D gr){
		gr.fillOval(x, y, width, height);
		gr.setColor(new Color(0,0,0,opacity));
		gr.drawOval(x, y, width, height);
	}
	/**
	 * Returns a rectangle for the weapon
	 * @param x
	 * 		x offset on graphics pane
	 * @param y
	 * 		y offset on the graphics pane
	 * @param width
	 * 		width of piece
	 * @param height
	 * 		height of piece
	 * @return
	 */
	private Rectangle getWeapon(int x,int y,int width, int height){
		return new Rectangle(x,y,width,height);
	}

}
