package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.Timer;
import Controller.GamePanelController;
import Model.Game;
import Model.OutOfBounds;
import Model.Pair;
import Model.Piece;
import Model.PlayerToken;
import Model.Reaction;
import Model.BoardItem;
import resources.ImgResources;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
/**
 * Creates the main panel for the game
 * @author Cameron
 *
 */
public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GameFrame frame;
	private String type = "";
	private int height = 0;
	private int width = 0;
	private double scaleSize = 1;
	public static  int xSpacing = 200;
	public static  int rowSpacing = 50;
	public static  int colSpacing = 50;
	private  int xSpacingOrig = 200;
	private  int rowSpacingOrig = 50;
	private   int colSpacingOrig = 50;
	private Rectangle selectedRectangle;
	private Piece selectedRotation;
	private Set<Pair> pairs= new HashSet<Pair>();


	/**
	 * Constructor to create the game panel
	 * @param frame
	 * 		back reference to frame
	 */
	public GamePanel(GameFrame frame) {
		this.frame = frame;
		GamePanelController controller = new GamePanelController(this);
		this.addMouseListener(controller);
		this.height = this.getHeight();
		this.width = this.getWidth();
		this.setValues();
		this.setFocusable(true);
		this.addKeyListener(controller);
		requestFocusInWindow();
		new Timer(500, (e)->{
			if(!frame.getGame().creatable){
				pairs= new HashSet<Pair>();
				pairs.addAll(Reaction.getAllPairs(frame.getGame().getBoard().getBoard()));
			}
		}	
				).start();

	}
	@Override
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		this.setValues();
		Graphics2D g = (Graphics2D) _g;
		drawBoard(g);
		drawBoardItems(g);//draws board completely
		drawMode(g);//gives text for the mode for user
		drawReactions(g);
		drawSelectedTile(g);
		if(selectedRotation!=null && type.equals("rotate")){//if currently rotating and not create mode
			g.setColor(new Color(50,50,50,100));
			g.fillRect(xSpacing, 0, colSpacing*10, rowSpacing*10);
			drawSelectedPiece(g);
		}
	}
	/**
	 * Sets the values of the scale from the window
	 */
	private void setValues() {
		if(width == 0||height == 0) {//set the initial width and height of window
			this.width= getWidth();
			this.height = getHeight();
		}
		double scaleX = (double)this.getWidth()/(double)this.width;
		double scaleY = (double)this.getHeight()/(double)this.height;
		this.scaleSize= (scaleX+scaleY)/2;
		this.xSpacing = (int) (xSpacingOrig* scaleSize);//scale position and width/height
		this.colSpacing = (int) (colSpacingOrig* scaleSize);
		this.rowSpacing = (int) (rowSpacingOrig* scaleSize);
	}
	/**
	 * Gets the selected rotation that is being displayed
	 * @return
	 */
	public Piece getSelectedRotation() {
		return selectedRotation;
	}

	/**
	 * Sets the selected piece that was rotated
	 * @param selectedRotation
	 */
	public void setSelectedRotation(Piece selectedRotation) {
		this.selectedRotation = selectedRotation;
	}

	/**
	 * resets the bordered rectangle
	 */
	public void setSelectedRectangle() {
		this.selectedRectangle = null;
	}


	/**
	 * Gets the rectangle that is on the screen when selected
	 * @return
	 */
	public Rectangle getSelectedRectangle() {
		return selectedRectangle;
	}
	/**
	 * gets all reaction pairs
	 * @return
	 */
	public Set<Pair> getPair(){
		return pairs;
	}

	/**
	 * Draws the boxes that are able to interact for reactions
	 * @param g
	 * 		Graphics pane of this panel
	 */
	private void drawReactions(Graphics g){
		Graphics2D gr= (Graphics2D)g;
		for(Pair pair: pairs){
			if(pair.getRect() == null){
				makeRect(pair);//puts rectangle inside pair memory
			}
			g.setColor(Color.RED);
			gr.draw(pair.getRect());
		}
	}
	/**
	 * makes the rectangle whether its a y and x reaction
	 * @param pair
	 * 		the two pieces that react
	 */
	private void makeRect(Pair pair){
		int x = Math.min(pair.getX1(), pair.getX2());//gets starting point
		int y = Math.min(pair.getY1(), pair.getY2());
		Rectangle rect;
		if(pair.getDirection().equals("up") || pair.getDirection().equals("down")){
			rect = new Rectangle(xSpacing+x*colSpacing, y*rowSpacing+(int)(45*scaleSize), (int)(50*scaleSize), (int)(10*scaleSize));
		}else{
			int width = Math.max(pair.getX2() - pair.getX1(), pair.getX1() - pair.getX2());
			int height =  Math.max(pair.getY1()-pair.getY2(), pair.getY2()-pair.getY1());
			rect = new Rectangle(xSpacing+x*colSpacing+45, y*rowSpacing, (int)((height*(40*scaleSize))+10),(int)(width*(40*scaleSize))+10);
		}
		//distinguishes wheter or not the pieces are vertically or horizontally paired for the rectangle
		pair.setRect(rect);
	}
	public Dimension getPreferredSize() {return new Dimension(500, 720);}
	/**
	 * returns if the piece is rotating or moving
	 * @return
	 */
	public String getType(){
		return type;
	}
	/**
	 * Resets the type
	 */
	public void setType(){
		type = "";
	}
	/**
	 * Gets the selected piece
	 * @return
	 */
	public Piece getSelected(){
		return selectedRotation;
	}
	/**
	 * Sets the selectedPiece
	 * @param piece
	 */
	public void setSelected(Piece piece){
		this.selectedRotation = piece;
	}


	/**
	 * Gets the game object
	 * @return
	 */
	public Game getGame(){
		return frame.getGame();
	}
	/**
	 * Displays what mode the games currently in (create/ move or rotate)
	 * @param g
	 * 		Graphics pane of this panel
	 */
	private void drawMode(Graphics g){
		g.setColor(Color.BLACK);
		String color = (getGame().isYellowsTurn())? "Yellow- ": "Green- ";
		String mode = (getGame().getCreatable())? "CREATE/PASS!":"MOVE/ROTATE/PASS";
		g.drawString(color+mode, 30, 200);
	}
	/**
	 * Draws the selected piece
	 * @param g
	 * 		Graphics pane of this panel
	 */
	private void drawSelectedPiece(Graphics g){
		if(selectedRotation.getColour()){
			g.setColor(new Color(255,255,0,selectedRotation.getOpacity()));//draws the big piece once clicked in the middle
		}else{
			g.setColor(new Color(0,255,0,selectedRotation.getOpacity()));
		}
		Draw draw = new Draw();
		draw.drawPiece(g, selectedRotation, xSpacing+50, 50, colSpacing*8, rowSpacing*8, 255);
	}
	/**
	 * returns if the mouse has clicked on the enlarged piece
	 * @param x
	 * 		x position of the mouse
	 * @param y
	 * 		y position of mouse
	 * @return
	 */
	public boolean hasRotated(int x, int y){
		Rectangle rect = new Rectangle(xSpacing+50, 50, colSpacing*8, rowSpacing*8);
		if(rect.contains(x, y)){//checks if piece had being clicked in rotate mode
			return true;
		}
		return false;
	}
	/**
	 * Draws all of the pieces on the board
	 * @param g
	 * 		Graphics pane on this panel
	 */
	private void drawBoardItems(Graphics g){
		for(int row = 0; row < getGame().getBoard().getBoard().length; row++){
			for(int col = 0; col < getGame().getBoard().getBoard()[0].length; col++){
				if(getGame().getBoard().getBoard()[row][col] instanceof PlayerToken){//none active pieces
					PlayerToken token = (PlayerToken)getGame().getBoard().getBoard()[row][col];
					drawFace(g, token, xSpacing + col*colSpacing,  row*rowSpacing);
				}else if(getGame().getBoard().getBoard()[row][col] instanceof Piece){
					Piece piece = (Piece)getGame().getBoard().getBoard()[row][col];
					this.applyAnimations(piece);
					if(!checkOpacity(piece))continue;//if piece has just died
					this.setColor(piece, g);
					if(!piece.isMoving()) {//set the piece to be perfect spacing
						piece.setX(xSpacing + col*colSpacing+(int)(colSpacing/10.0));
						piece.setY(row*rowSpacing+(int)(rowSpacing/10.0));
					}
					Draw draw = new Draw();
					draw.drawPiece(g,piece, piece.getX(), piece.getY(),(int)(40*scaleSize),(int)(40*scaleSize),piece.getOpacity());
					if(!piece.isMoving() && piece.isUsed()) {
						g.setColor(new Color(70,70,70,100));//puts a semi transparent square on
						g.fillRect(xSpacing + col*colSpacing, row*rowSpacing, rowSpacing, colSpacing);
					}

				}
			}
		}
	}
	/**
	 * Sets the color on whos player the piece belongs too
	 * @param piece
	 * 		The piece
	 * @param g
	 * 		Graphics pane on this panel
	 */
	private void setColor(Piece piece, Graphics g){
		if(piece.getColour()){
			g.setColor(new Color(255,255,0,piece.getOpacity()));
		}else{
			g.setColor(new Color(0,255,0,piece.getOpacity()));
		}
	}
	/**
	 * Applys all animations and checks music
	 * @param piece
	 * 		The piece
	 */
	private void applyAnimations(Piece piece){
		if(piece.getDeath()!=null){//audio
			if(!piece.getDeath().equals("")){
				piece.setHasAudio(true);
			}
			if(piece.isHasAudio()){
				this.playAudio(piece);
			}
		}
		if(piece.isMoving()) {//movement
			piece.moving();
		}
	}
	/**
	 * Checks to see if the center has been pressed
	 * @param rect
	 * 		The rectangle on the board
	 * @param x
	 * 		x offset of the mouse click
	 * @param y
	 * 		y offset of the mouse click
	 * @return
	 */
	private String getType(Rectangle rect, int x, int y){
		int minX = (int)(rect.getCenterX()-(rect.getWidth()/6));//center circle coordiantes
		int minY = (int)(rect.getCenterY()-(rect.getHeight()/6));
		int maxX = (int)(rect.getCenterX()+(rect.getWidth()/6));
		int maxY = (int)(rect.getCenterY()+(rect.getHeight()/6));
		Rectangle center = new Rectangle(minX,minY,maxX-minX,maxY-minY);
		if(center.contains(x, y)){
			return "rotate";
		}else if(rect.contains(x,y)){
			return "move";
		}else{
			return "";
		}
	}
	/**
	 * Plays the audio of dead players if exists
	 * @param piece
	 * 		The piece that is being checked
	 */
	private void playAudio(Piece piece){
		String fileName=getAudioFileName(piece);
		if(fileName!=""){
			try {
				InputStream url = ImgResources.class.getResourceAsStream(fileName);
				Player playMP3 = new Player(url);
				if(piece.isHasAudio()){
					playMP3.play();
					this.playedAudio(playMP3,piece);
				}
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * Gets the audio file name associated with the death type
	 * @param piece
	 * 		Piece that is being checked
	 * @return
	 */
	private String getAudioFileName(Piece piece){
		String death = piece.getDeath();
		if(death.equals("Fallen")){
			return "fallen.mp3";
		}else if(death.equals("Killed")){
			return "sword.mp3";
		}else if(death.equals("Eatten")){
			return "ate.wav";
		}
		return "";
	}
	/**
	 * Resets the variables when playing the audio
	 * @param playMP3
	 * 		The mp3 player
	 * @param piece
	 * 		This piece
	 */
	private void playedAudio(Player playMP3, Piece piece){
		piece.setDeath("");
		piece.setHasAudio(false);
		playMP3.close();
		getGame().getBoard().resetAudio(piece.getName());
	}

	/**
	 * Decreases the opacity if dead or removes from board when opacitys 0
	 * @param piece
	 * 		Piece being checked if dead
	 * @return
	 */
	private boolean checkOpacity(Piece piece){
		if(piece.getOpacity() == 0){
			getGame().getBoard().destroyPiece(piece.getName());
			return false;
		}
		if(piece.getIsDead()){
			piece.decreaseOpacity();
		}
		return true;
	}
	/**
	 * Draws one of the two faces onto the graphics pane
	 * @param g
	 * 		Graphics pane of this object
	 * @param playerToken
	 * 		Player token on the board
	 * @param x
	 * 		x position of the piece
	 * @param y
	 * 		y position of the piece
	 */
	private void drawFace(Graphics g, PlayerToken playerToken, int x, int y){
		if(playerToken.getName() == '1'){
			g.drawImage(ImgResources.YELLOW.img, x, y,colSpacing,rowSpacing, null);
		}else{
			g.drawImage(ImgResources.GREEN.img, x, y,colSpacing,rowSpacing ,null);
		}
	}
	/**
	 * Returns the spacing of the rows
	 * @return
	 */
	public int getRowSpacing(){
		return rowSpacing;
	}
	/**
	 * Returns the spacing of the columns
	 * @return
	 */
	public int getColSpacing(){
		return colSpacing;
	}
	/**
	 * checks to see if piece is selected from the first click
	 * @param x
	 * 		x position of the click
	 * @param y
	 * 		y position of the click
	 * @return
	 */
	public boolean isSelected(int x,int y){
		for(int row = 0; row < getGame().getBoard().getBoard().length; row++){
			for(int col = 0; col < getGame().getBoard().getBoard()[0].length; col++){
				BoardItem piece = getGame().getBoard().getBoard()[row][col];
				Rectangle rectangle= new Rectangle(xSpacing + col*getColSpacing(),
						row*getRowSpacing(),getColSpacing(),getRowSpacing());
				if(rectangle.contains(x, y) && piece instanceof Piece){
					if(((Piece) piece).isUsed()) return false;
					selectedRectangle = rectangle;
					Piece clickedPiece = (Piece)piece;
					boolean isYellow = clickedPiece.getColour() && getGame().isYellowsTurn();
					boolean isGreen = !clickedPiece.getColour() && !getGame().isYellowsTurn();
					if(isGreen || isYellow){//if its the persons piece
						selectedRotation = ((Piece) piece);
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * sets the type of interaction with the piece if clicked
	 * @param x
	 * 		x position of click
	 * @param y
	 * 		y position of click
	 */
	public void clickedSelected(int x, int y ){
		if(!getGame().getCreatable()){
			type = getType(selectedRectangle, x, y);
			if(type.equals("rotate")){//if rotate clone so i can add to the past state to stack
				selectedRotation = selectedRotation.clone();
			}
		}
	}
	/**
	 * When clicked the reactions are applied
	 * @param x
	 * 		x position of mouse click
	 * @param y
	 * 		y position of mouse click
	 */
	public void applyReaction(int x, int y){
		for(Pair pair: pairs){		
			if(pair.getRect() == null) continue;
			if(pair.getRect().contains(x, y)){
				getGame().pieceReaction(pair);//applies reaction
				pairs.remove(pair);
				return;
			}
		}
	}
	/**
	 * Draws the selected rectangle that outline the piece selected
	 * @param g
	 * 		graphics pane of this panel
	 */
	private void drawSelectedTile(Graphics g){
		if(selectedRectangle!=null){
			g.setColor(Color.BLUE);
			g.drawRect(selectedRectangle.x, selectedRectangle.y,
					selectedRectangle.width, selectedRectangle.height);
		}
	}
	/**
	 * Draws the checkered boarded in the background
	 * @param g
	 * 		The graphics pane of this panel
	 */
	private void drawBoard(Graphics g){
		int count = 0;
		for(int row = 0; row < getGame().getBoard().getBoard().length; row++){
			for(int col = 0; col < getGame().getBoard().getBoard()[0].length; col++){
				count++;
				if(getGame().getBoard().getBoard()[row][col] instanceof OutOfBounds){// if out of bounds draw back square
					g.setColor(new Color(10,10,10));
				}else if(!(getGame().getBoard().getBoard()[row][col] instanceof PlayerToken)){// if not a player token set it to black or white
					if(count%2==0)g.setColor(new Color(100,100,100));
					else g.setColor(new Color(255,255,255));
				}else{//else set it to grey
					g.setColor(new Color(40,40,40));
				}
				g.fillRect(xSpacing + col*colSpacing, row*rowSpacing, colSpacing, rowSpacing);//board squares
				g.setColor(new Color(0,0,0));
				g.drawRect(xSpacing + col*colSpacing, row*rowSpacing, colSpacing, rowSpacing);//outline
			}
			count++;
		}
		this.drawCreationTiles(g);//draws creation tiles over it
	}
	/**
	 * Draws the green and yellow squares that are needed for drawing
	 * @param g
	 * 		Graphics pane of this panel
	 */
	private void drawCreationTiles(Graphics g){
		g.setColor(new Color(0,255,0));//creation squares
		g.fillRect(xSpacing + 2*colSpacing, 2*rowSpacing, colSpacing,rowSpacing);
		g.setColor(new Color(255,255,0));
		g.fillRect(xSpacing + 7*colSpacing, 7*rowSpacing, colSpacing,rowSpacing);
	}
}
