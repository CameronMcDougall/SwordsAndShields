package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JPanel;
import Controller.InventoryPanelController;
import Model.Piece;
import Model.Player;
/**
 * Inventory panel that will hold all the pieces available for the game
 * @author Cameron
 *
 */
public class InventoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GameFrame frame;
	private Piece selectedPiece;
	private boolean selected = false,isLeftPanel,moving,isYellow;
	private Color color;
	private int opacity = 255;
	private int rotation = 0;
	int size = 38;
	int changeableSize;
	int windowWidth;
	int windowHeight;

	/**
	 * Constructor to create the panel that shows the available pieces
	 * @param frame
	 * 		Frame that contains this panel
	 * @param isYellow
	 * 		If this is the yellow panel
	 * @param isLeftPanel
	 * 		If this is the left panel
	 */
	public InventoryPanel(GameFrame frame,boolean isYellow,boolean isLeftPanel){
		this.frame = frame;
		if(isYellow) color = new Color(255,255,0,opacity);
		else color = new Color(0,255,0,opacity);
		this.isYellow = isYellow;
		this.isLeftPanel = isLeftPanel;
		this.addMouseListener(new InventoryPanelController(this));
	}
	/**
	 * Sets the game frame
	 * @return
	 */
	public GameFrame getGameFrame() {
		return frame;
	}
	/**
	 * Gets the the first time size of the panel
	 */
	private void setDimensions() {
		if(this.getHeight() == 0 || this.getWidth() == 0) {//avoids init value
			return;
		}
		if(windowHeight != 0|| windowWidth != 0) {//avoids overriding first value
			return;
		}
		this.windowHeight = this.getHeight();
		this.windowWidth = this.getWidth();
	}
	/**
	 * Sets the values of the pieces from the scale of the window
	 */
	private void setScaled() {
		if(windowHeight == 0 || windowWidth == 0) {//avoids init values
			return;
		}
		double scaleX = (double)this.getWidth()/(double)this.windowWidth;
		double scaleY = (double)this.getHeight()/(double)windowHeight;
		double averageScale = (scaleX+scaleY)/2.0;//gets the average scale
		changeableSize = (int) (size*averageScale);
	}
	@Override
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D) _g;
		this.setScaled();
		if(selected && !moving){//if the selected out of ~24 pieces is picked and not moving
			drawSelected(g);
		}else if(!selected && !moving){// if all pieces available to pick and not moving
			opacity = 255;
			int height = getHeight();
			Color color1 = new Color(200,200,200);
			Color color2 = new Color(50,50,50);
			GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
			Graphics2D gr = (Graphics2D)g;//sets gradient background
			gr.setPaint(gp);
			if(isYellow){
				drawInventory(frame.getGame().getYellow(),true,0,0,g);
			}else{
				drawInventory(frame.getGame().getGreen(),false,0,0,g);
			}
		}
		if(selectedPiece != null && moving) {//if piece is moving for animation
			this.movePiece(g);
		}
		g.setColor(Color.red);
		this.setDimensions();
	}
	/**
	 * Sets the piece that is currently being picked from user
	 * @param selectedPiece
	 * 		the newly set piece
	 */
	public void setSelectedPiece(Piece selectedPiece) {
		this.selectedPiece = selectedPiece;
	}
	/**
	 * Sets if the piece has been selected or not
	 * @param selected
	 * 		new value if its been selected or not
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * Sets if the piece is moving or not
	 * @param moving
	 * 		value that its being set to
	 */
	public void setMoving(boolean moving) {
		this.moving = moving;
	}


	/**
	 * Draws the players inventory of all available pieces
	 * @param player
	 * 		Player object that has the inventory
	 * @param isYellow
	 * 		if it is the yellow panel or green
	 * @param x
	 * 		x position on graphic pane
	 * @param y
	 * 		y position on graphic pane
	 * @param g
	 */
	private void drawInventory(Player player,boolean isYellow,int x, int y,Graphics g){	
		g.fillRect(x, y, this.getWidth(), this.getHeight());//draws black box around pieces
		this.color = new Color(this.color.getRed(),this.color.getGreen(),
				this.color.getBlue(),this.opacity);
		this.drawPieces(x, y, g, player.getInventory().values());
	}
	/**
	 * Draws pieces 2 per row
	 * @param x
	 * 		x offset
	 * @param y
	 * 		y offset
	 * @param g
	 * 		Graphics object
	 * @param values
	 * 		Collection of pieces
	 */
	private void drawPieces(int x, int y,Graphics g, Collection<Piece> values){
		int dy = 0;
		int count = 0;
		for(Piece p: values){
			int dx = (changeableSize*2)*(count%2) +15;// starts offset at 15 and moves by 80 per piece
			g.setColor(this.color);
			Draw draw = new Draw();
			draw.drawPiece(g, p, x+dx, y+dy,changeableSize,changeableSize,255);
			count++;
			if(count%2 == 0)dy+=changeableSize+changeableSize/8;//new row when even
		}
	}


	/**
	 * Moves piece for animation on creation while its still not on the board
	 * @param g
	 * 		Graphic object that is from this panel
	 */
	private void movePiece(Graphics g) {
		if(!moving){//if its on the other panel and its moving
			return;
		}
		if(frame.getCompletePanel().getPiece() == null) {
			frame.getCompletePanel().setPiece(selectedPiece);
			frame.getCompletePanel().setPieceHeight(changeableSize);
			frame.getCompletePanel().setPieceWidth(changeableSize);
		}
		if(!selectedPiece.isMoving()) {//creats piece and reset
			this.createPiece();
			return;
		}
		if(isLeftPanel ) {//if its still on the green panel
			this.movingGreenPiece();
		}else if(!isLeftPanel){//if its still on the yellow panel
			this.movingYellowPiece();
		}
		g.setColor(this.color);
	}
	public Dimension getPreferredSize() {return new Dimension(100, 720);}

	/**
	 * Creates the piece after animation
	 */
	private void createPiece(){
		this.frame.getGamePanel().getGame().createTile(selectedPiece.getName(),0);//puts piece on board
		//variables get reset that are needed for movement
		this.selectedPiece = null;
		frame.getCompletePanel().setPiece(null);
		this.selected = false;
		this.moving = false;
		frame.getGame().pass();
	}
	/**
	 * Moves the green piece towards the game panel
	 */
	private void movingGreenPiece(){
		if(selectedPiece.isMoving()) {//if the piece its not off the inventory panel
			selectedPiece.moving(30,5);
		}
	}
	/**
	 * Moves the yellow piece towards game panel
	 */
	private void movingYellowPiece(){
		if(selectedPiece.isMoving()) {//if not off yellow panel
			selectedPiece.moving(-30,20);
		}
	}
	/**
	 * Checks to see if piece was chosen on creation
	 * @param x
	 * 		clicked x position
	 * @param y
	 * 		clicked y position
	 */
	private void choosePiece(int x, int y){
		if(frame.getGame().isYellowsTurn() &&!isYellow) return;// if clicking on wrong panel
		if(!frame.getGame().isYellowsTurn() && isYellow) return;
		int dy = 0;
		int count = 0;
		Player player= (isYellow) ? frame.getGame().getYellow() : frame.getGame().getGreen();// gets player based on turn
		for(Piece p: player.getInventory().values()){
			int dx = (changeableSize*2) * (count%2) +15;
			Rectangle a = new Rectangle(dx,dy,changeableSize,changeableSize);//bounding box
			if(a.contains(x, y)){
				selectedPiece = p;
				selected = true;
				opacity = 0;//transitions the viewability
				break;
			}
			count++;
			if(count % 2 == 0) dy+=changeableSize+changeableSize/8;
		}
	}
	/**
	 * if clicked on first or second mode of creation
	 * @param x
	 * 		x position of mouse click
	 * @param y
	 * 		y position of mouse click
	 */
	public void getSelected(int x, int y){
		if(!selected){
			this.choosePiece(x,y);
		}else{
			this.chooseRotation(x,y);
		}
	}
	/**
	 * Chooses out of the four possibles on click
	 * @param x
	 * 		x position of mouse click
	 * @param y
	 * 		y position of mouse click
	 */
	private void chooseRotation(int x, int y){
		int dy = 0;
		for(int i = 0; i < 4; i++){
			int dx = (changeableSize*2) *(i%2) +15;
			Rectangle a = new Rectangle(dx,dy,changeableSize,changeableSize);//bounding of piece
			if(a.contains(x, y)){
				rotation = i*90;
				selected = false;
				this.setMovementPosition(rotation, dx + this.getX() + 40, dy + this.getY() + 10 );
				int position = isLeftPanel ? 2 : 7;// creation x and y of green and yellow
				moving = true;
				selectedPiece.setMoving(true);
				selectedPiece.setDestX(frame.getGamePanel().getLocation().x + (GamePanel.xSpacing + position*GamePanel.colSpacing)+3);//sets place it needs to be after animation
				selectedPiece.setDestY(frame.getButtonPanel().getHeight() + frame.getCompletePanel().getDividerSize() -1 + (position * GamePanel.rowSpacing + 5));
				return;
			}
			if(i % 2 != 0) dy+=changeableSize+changeableSize/8;
		}
		selected = false;
		selectedPiece = null;
	}
	/**
	 * Sets the movement variables of the piece
	 * @param rotation
	 * @param x
	 * @param y
	 */
	private void setMovementPosition(int rotation, int x, int y){
		selectedPiece.rotate(rotation);// rotates the piece by the one clicked
		selectedPiece.setX(x);
		selectedPiece.setY(y);
		moving = true;
	}
	/**
	 * Draws the selected piece's possible rotations
	 * @param g
	 * 		Graphics object of this panel
	 */
	private void drawSelected(Graphics g){
		List<Piece> pieces = new ArrayList<Piece>();
		for(int angle = 0; angle < 360; angle += 90){//puts all possible rotations into a list
			Piece clone = selectedPiece.clone();
			clone.rotate(angle);
			pieces.add(clone);
		}
		if(isYellow){
			this.color= new Color(255,255,0,opacity);
		}else{
			this.color = new Color(0,255,0,opacity);
		}
		this.drawPieces(0, 0, g, pieces);//draws the list while animating the visiblity
		if(opacity <= 255-30){
			opacity += 30;
		}else{
			opacity = 255;
		}
	}

}
