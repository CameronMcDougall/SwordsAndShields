package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JSplitPane;

import Model.Piece;
/**
 * Create a object for the final split pane that will be added to the game frame
 * @author Cameron
 *
 */
public class SplitPanes extends JSplitPane{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Piece piece;
	private int pieceWidth;
	private int pieceHeight;
	/**
	 * Sets the final split pane with all of the panes needed for gameframe
	 * @param newOrientation
	 * 		If side to side or above and below
	 * @param newLeftComponent
	 * 		left/top componenet
	 * @param newRightComponent
	 * 		right or bottom component
	 */
	public SplitPanes(int newOrientation,
			Component newLeftComponent,
			Component newRightComponent) {
		super(newOrientation, newLeftComponent,
				newRightComponent);
	}
	@Override
	public void paint(Graphics g)  {
		super.paint(g);
		if(piece == null)return;
		if(piece != null) {
			if(Character.isUpperCase(piece.getName())) {
				g.setColor(Color.green);
			}else {
				g.setColor(Color.yellow);
			}
			Draw draw = new Draw();//draws piece
			draw.drawPiece(g, piece, piece.getX(), piece.getY(), pieceWidth , pieceHeight, piece.getOpacity());
		}
	}
	/**
	 * Gets the piece that is floatig over pane
	 * @return
	 */
	public Piece getPiece() {
		return piece;
	}
	/**
	 * Sets the piece thats floating on the pane
	 * @param piece
	 * 		Floating piece
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	/**
	 * Gets the width dimensions of floating piece
	 * @return
	 */
	public int getPieceWidth() {
		return pieceWidth;
	}
	/**
	 * Sets the width dimensions of floating piece
	 * @param pieceWidth
	 * 		New piece width
	 */
	public void setPieceWidth(int pieceWidth) {
		this.pieceWidth = pieceWidth;
	}
	/**
	 * Gets the height dimensions of floating piece
	 * @return
	 */
	public int getPieceHeight() {
		return pieceHeight;
	}
	/**
	 * Sets the height dimensions of floating piece
	 * @param pieceHeight
	 *  	New piece height
	 */
	public void setPieceHeight(int pieceHeight) {
		this.pieceHeight = pieceHeight;

	}

}
