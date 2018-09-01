package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import javax.swing.JPanel;
import Model.Piece;
/**
 * Graveyard panel of one of two players which will contain dead pieces
 * @author Cameron
 *
 */
public class GraveyardPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GameFrame frame;
	private int currentSize=40;
	private int width;
	private int height;
	private boolean isYellow;
	/**
	 * Constructor to create graveyard panel will back reference to frame
	 * @param frame
	 * 		Frame of window in game mode
	 * @param isYellow
	 * 		If this is the yellow panel
	 */
	public GraveyardPanel(GameFrame frame, boolean isYellow) {
		this.frame = frame;
		this.isYellow = isYellow;
	}
	/**
	 * Gets the initial window size for scaling
	 */
	private void getWindowSize(){
		if(this.getHeight()==0)return;// avoids initialization problems
		if(this.height == 0){
			this.width = this.getWidth();
			this.height = this.getHeight();
		}

	}
	/**
	 * Sets the scaled piece size
	 */
	private void setPieceSize(){
		if(this.height == 0)return;
		float ratioY =  (float) this.getHeight() / this.height;
		float ratioX =  (float) this.getWidth() / this.width;
		float averageRatio = (ratioY+ratioX)/2;//takes average
		currentSize = Math.round(averageRatio*40);//40 is original piece size

	}
	/**
	 * Draws the cemetery full of removed pieces of certain player
	 * @param x
	 * 		starting x position of cemetery on graphics pane
	 * @param y
	 * 		starting y position of cemetery on graphics pane
	 * @param g
	 * 		Graphics pane of this panel
	 */
	private void drawCemetery(int x, int y,Graphics g){
		int h = getHeight();
		Color color1 = new Color(200,200,200);
		Color color2 = new Color(50,50,50);
		GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
		Graphics2D gr = (Graphics2D)g;
		gr.setPaint(gp);
		g.fillRect(x, 0,this.getWidth(), this.getHeight());//draws the rectangle layout behind
		List<Piece> grave;
		grave = (isYellow) ? frame.getGame().getBoard().getYellowCemetery():
			frame.getGame().getBoard().getGreenCemetery();
		this.drawPieces(grave, g, y);//gets and draws all dead pieces

	}
	/**
	 * Draws the pieces side by side in seperate rows
	 * @param grave
	 * 		Pieces removed from play
	 * @param g
	 * 		Graphics pane of panel
	 * @param y
	 * 		y position offset
	 */
	private void drawPieces(List<Piece> grave,Graphics g,int y){
		Color color;
		int dx = 5;
		for(Piece piece : grave){
			color = (isYellow) ? Color.yellow : Color.green;
			g.setColor(color);
			Draw draw = new Draw();
			draw.drawPiece(g, piece, dx, y,currentSize,currentSize,255);
			dx += 50;
			if(dx-40 > this.getWidth()){//if at the end of row go down a column
				dx=0;
				y += currentSize + currentSize/4;
			}
		}
	}
	@Override
	public Dimension getPreferredSize() {return new Dimension(100, 200);}

	@Override
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D) _g;
		this.getWindowSize();
		this.setPieceSize();
		drawCemetery(0, 5, g);
	}

}
