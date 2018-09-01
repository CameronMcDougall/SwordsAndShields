package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Stack;
import View.GamePanel;
/**
 * Main game class which contains the board and players
 * @author Cameron
 *
 */
public class Game extends Observable implements Cloneable{
	private Board board;
	private Player yellow;
	private Player green;
	private int turn;
	private Stack<Game> gameStates;
	public boolean creatable = true;

	/**
	 * Game containing board and players
	 */
	public Game() {
		board = new Board();
		yellow = new Player(true);
		green = new Player(false);
		turn = 0;
		this.createInventories();
		gameStates = new Stack<Game>();

	}
	/**
	 * Gets the last game state and puts all of its data inside this game object
	 */
	public void undo(){
		if(gameStates.isEmpty()){
			System.out.println("NO PAST OCCURANCES");
			return;
		}
		Game game = gameStates.pop();
		this.board = game.getBoard();
		this.yellow = game.getYellow();
		this.green = game.getGreen();
		this.turn = game.getTurn();
		this.gameStates = game.getStack();
		this.creatable = game.getCreatable();
	}
	/**
	 * Returns if the current mode is in creation or not
	 * @return
	 */
	public boolean getCreatable(){
		return creatable;
	}
	/**
	 * Sets the board to the new board
	 * @param board
	 * 		The new board to be set to
	 */
	public void setBoard(Board board){
		this.board = board;
	}

	/**
	 * Sets the green player
	 * @param green
	 * 		The new green player object to set
	 */
	public void setGreen(Player green){
		this.green = green;
	}
	/**
	 * Sets the yellow player
	 * @param yellow
	 * 		The new yellow player object to set
	 */
	public void setYellow(Player yellow){
		this.yellow = yellow;
	}
	/**
	 * Sets the turn its currently on
	 * @param turn
	 * 		current turn
	 */
	public void setTurn(int turn){
		this.turn = turn;
	}
	/**
	 * Sets the past game stacks to the current stack
	 * @param gameStates
	 * 		New stack to set in the object
	 */
	public void setStack(Stack<Game> gameStates){
		this.gameStates = gameStates;
	}
	/**
	 * Gets the stack
	 * @return
	 */
	public Stack<Game> getStack(){
		return gameStates;
	}
	/**
	 * Gets the board
	 * @return
	 */
	public Board getBoard(){
		return board;
	}
	/**
	 * Gets the yellow player object with its inventory
	 * @return
	 */
	public Player getYellow(){
		return yellow;
	}
	/**
	 * Gets the green player object with its inventory
	 * @return
	 */
	public Player getGreen(){
		return green;
	}
	/**
	 * Gets the current turn
	 * @return
	 */
	public int getTurn(){
		return turn;
	}

	/**
	 * Adds the last game state to the stack
	 * @param game
	 * 		the last game state
	 */
	public void addGameState(Game game){
		gameStates.push(game);
	}
	/**
	 * Piece with sword being blocked by a shield
	 * @param piece
	 * 		piece with the sword
	 * @param dx
	 * 		the movement of column
	 * @param dy
	 * 		the movement of row
	 */
	private void swordOnShield(Piece piece, int dx, int dy){
		board.movePiece(piece.getName(), dx, dy);
	}
	/**
	 * Both pieces have sword so they must be removed from the board
	 * @param first
	 * 		First piece with a sword
	 * @param second
	 * 		Second piece with a sword
	 */
	private void swordOnSword(Piece first, Piece second){
		first.setDeath("Killed");
		second.setDeath("Killed");
		board.destroyPiece(first.getName());
		board.destroyPiece(second.getName());
	}
	/**
	 * Piece gets destroyed as has nothing against a sword
	 * @param piece
	 * 		piece with nothing against a sword
	 */
	private void swordOnNothing(Piece piece){

		piece.setDeath("Killed");
		board.destroyPiece(piece.getName());
	}
	/**
	 * Decides who the current player who is playing
	 * @return
	 */
	public boolean isYellowsTurn(){
		return turn % 2 == 0;
	}

	/**
	 * Create a piece in the yellow players inventory which will go on yellows creation zone
	 * @param letter
	 * 		Piece name that is in the yellow player inventory
	 * @param rotation
	 * 		Rotation that the piece will rotate by
	 */
	private void createYellowTile(char letter,int rotation){
		this.addGameState(this.clone());
		board.addTile(7, 7, yellow.getInventory().get(letter));
		yellow.getInventory().get(letter).rotate(rotation);
		yellow.getInventory().get(letter).setX(GamePanel.xSpacing + 7*GamePanel.colSpacing+5);
		yellow.getInventory().get(letter).setY(7*GamePanel.rowSpacing+5);
		yellow.getInventory().remove(letter);
	}
	/**
	 * Create a piece in the green players inventory which will go on greens creation zone
	 * @param letter
	 * 		Piece name that is in the green player inventory
	 * @param rotation
	 * 		Rotation that the piece will rotate by
	 */
	private void createGreenTile(char letter,int rotation){
		letter = Character.toUpperCase(letter);
		board.addTile(2, 2, green.getInventory().get(letter));
		green.getInventory().get(letter).rotate(rotation);
		green.getInventory().get(letter).setX(GamePanel.xSpacing + 2*GamePanel.colSpacing+5);
		green.getInventory().get(letter).setY(2*GamePanel.rowSpacing+5);
		green.getInventory().remove(letter);
	}
	/**
	 * returns true if a sword is pointing at the players token
	 * @return
	 */
	public boolean gameOver(){
		if(!creatable) return false;
		if(board.getBoard()[1][2] != null){//right of green player
			Piece piece= (Piece) board.getBoard()[1][2];
			if(piece.getLeft() == Piece.PieceSymbol.SWORD){
				return true;
			}
		}
		if(board.getBoard()[2][1] != null){//bottom of green player
			Piece piece= (Piece) board.getBoard()[2][1];
			if(piece.getFront() == Piece.PieceSymbol.SWORD){
				return true;
			}
		}
		if(board.getBoard()[7][8] != null){//top of yellow player
			Piece piece= (Piece) board.getBoard()[7][8];
			if(piece.getBack() == Piece.PieceSymbol.SWORD){
				return true;
			}
		}
		if(board.getBoard()[8][7] != null){//left of yellow player
			Piece piece= (Piece) board.getBoard()[8][7];
			if(piece.getRight() == Piece.PieceSymbol.SWORD){
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a tile on the creation zone with the input of the players character
	 * @param letter
	 * 		The players input of their piece letter
	 * @param rotation
	 * 		The rotation input of which the piece will rotate
	 */
	public boolean createTile(char letter,int rotation){
		//2,2 and 7,7 are the creation zones
		if(!creatable)return false;
		if(isYellowsTurn()){
			if(Character.isUpperCase(letter)){
				return false;
			}
			if(board.hasPiece(7, 7)) {
				System.out.println("Piece on creations");
				return false;
			}
			this.createYellowTile(letter, rotation);
			return true;
		}else{
			if(board.hasPiece(3, 3)) {
				System.out.println("Piece on creations");
				return false;
			}
			if(!Character.isUpperCase(letter)){
				return false;
			}
			this.createGreenTile(letter, rotation);
			this.addGameState(this.clone());
			return true;
		}
	}
	public boolean hasPiece() {
		if(isYellowsTurn()) {
			return board.hasPiece(7, 7);
		}else {
			return board.hasPiece(3, 3);
		}
	}
	/**
	 * Rotates the piece if the piece exists
	 * @param letter
	 * 		Piece identifier
	 * @param rotation
	 * 		rotation of the piece
	 * @param isCreation
	 * 		If the piece is being rotated on creation
	 * @return
	 */
	public boolean rotatePiece(char letter, int rotation){
		letter = (isYellowsTurn()) ? letter: Character.toUpperCase(letter);
		if(creatable){
			return false;
		}
		if(board.isPieceUsed(letter)){
			System.out.println("PIECE ALREADY IN USE");
			return false;
		}
		this.addGameState(this.clone());

		if(board.rotateBoardPiece(letter, rotation)){
			board.setPieceMoved(letter);
			return true;
		}
		return false;
	}

	/**
	 * Adds piece to the board with specific coordinates
	 * @param row
	 * 		Row in 2D board array
	 * @param col
	 * 		Column in 2D board array
	 * @param piece
	 * 		Piece that will be added to 2D board array
	 * @return
	 */
	public boolean addPiece(int row,int col,Piece piece){
		return board.addTile(row, col, piece);
	}
	/**
	 * Creates the inventory of each player with all the possible permuations and
	 * randomly picking 24 of those object. On players tiles will be in uppercase and one in
	 * lower case
	 */
	private void createInventories(){
		List<Piece> permuations= new ArrayList<Piece>();
		//Goes through all possible permuations and stores them in temporary memory
		//Set is used so the piece are random
		BoardItem.PieceSymbol[] weapons = BoardItem.PieceSymbol.values();
		for(int front = 0; front < 3; front++){
			for(int right = 0; right < 3; right++){
				for(int back = 0; back < 3; back++){
					for(int left = 0; left < 3; left++){
						permuations.add(new Piece(Arrays.asList(weapons[front],weapons[right],
								weapons[back],weapons[left]),'\0',false));
					}
				}
			}
		}
		Collections.shuffle(permuations);
		//Goes through the first 24 pieces out of the 81 permuations
		//one player will have uppercase and one lowercase
		char name = 'a';
		for(Piece yellowPiece:permuations){
			yellowPiece.setName(name);
			yellowPiece.setColour(true);
			yellow.getInventory().put(name, yellowPiece);
			Piece greenPiece = yellowPiece.clone();//cloned to create new pointer
			greenPiece.setColour(false);
			greenPiece.setName((Character.toUpperCase(name)));//put to uppercase to tell difference
			green.getInventory().put(Character.toUpperCase(name), greenPiece);
			name++;
			if(name >= 'a' + 24){//if 24 inputted
				break;
			}
		}

	}
	/**
	 * Gets a piece from a specific coordinate on the board
	 * @param row
	 * 		Row position on the board
	 * @param col
	 * 		Col position on the board
	 * @return
	 */
	public Piece getPiece(int row, int col){
		return (Piece) board.getBoard()[row][col];
	}

	/**
	 * After a move is made the piece is set to played
	 * @param pieceName
	 */
	public void setPlayedPiece(char pieceName){
		board.setPieceMoved(pieceName);
	}

	/**
	 * Incremented to tell whos turn it is
	 */
	public void turnOver(){
		turn++;
		board.resetUsedPieces();
		gameStates = new Stack<Game>();
		creatable = true;
	}
	public void pass() {
		if(creatable){
			creatable = false;
		}else{
			turnOver();
		}
	}
	/**
	 * Moves the player based on the string given by the user
	 * @param piece
	 * 		Identifier of the piece
	 * @param movement
	 * 		Direction which the piece is wanting to be move to
	 * @return
	 */
	public boolean movePiece(char piece,String movement){
		piece = (isYellowsTurn()) ? piece: Character.toUpperCase(piece);
		// converts the appropriate casing dependant on player
		if(creatable){
			return false;
		}
		if(board.isPieceUsed(piece)){
			System.out.println("PIECE ALREADY MOVED");
			return false;
		}
		this.addGameState(this.clone());
		boolean b=false;

		if(movement.equals("up")){
			b= board.movePiece(piece, 0, -1);
		}else if(movement.equals("down")){
			b= board.movePiece(piece, 0, 1);
		}else if(movement.equals("left")){
			b= board.movePiece(piece, -1, 0);
		}else if(movement.equals("right")){
			b= board.movePiece(piece, 1, 0);
		}
		if(b) {
			board.setPieceMoved(piece);
		}
		return b;
	}
	/**
	 * Compares only one piece to any other piece
	 * @param row
	 * 		Row position of the defending position
	 * @param col
	 * 		Column position of the defending position
	 * @param defender
	 * 		Defending piece being checked by the adjacent pieces
	 * @param attack
	 * 		The attacking piece's name
	 * @return
	 */
	public void pieceReaction(Pair current){
		gameStates.push(this.clone());
		if(current.getDirection().equals("up")){
			applyReactionsFront((Piece) board.getBoard()[current.getY1()][current.getX1()],
					(Piece) board.getBoard()[current.getY2()][current.getX2()]);
		}else if(current.getDirection().equals("left")){
			applyReactionsLeft( (Piece) board.getBoard()[current.getY1()][current.getX1()],
					(Piece) board.getBoard()[current.getY2()][current.getX2()]);
		}else if(current.getDirection().equals("down")){
			applyReactionsBack((Piece) board.getBoard()[current.getY1()][current.getX1()],
					(Piece) board.getBoard()[current.getY2()][current.getX2()]);
		}else{
			applyReactionsRight((Piece) board.getBoard()[current.getY1()][current.getX1()],
					(Piece) board.getBoard()[current.getY2()][current.getX2()]);
		}
	}





	/**
	 * Applies reactions on the left there are any
	 * @param defender
	 * @param attacker
	 */
	private void applyReactionsLeft(Piece defender,Piece attacker){
		if(Reaction.isDeadLeft(defender, attacker))  this.swordOnNothing(defender);
		else if(Reaction.isBlockedLeft(defender, attacker)) this.swordOnShield(defender, 1, 0);
		else if(Reaction.isBothDeadLeft(defender, attacker)) this.swordOnSword(defender, attacker);
		else if(Reaction.hasBlockedLeft(defender, attacker)) this.swordOnShield(attacker, -1, 0);
	}
	/**
	 * Applies reactions on the right there are any
	 * @param defender
	 * @param attacker
	 */
	private void applyReactionsRight(Piece defender,Piece attacker){
		if(Reaction.isDeadRight(defender, attacker)) this.swordOnNothing(defender);
		else if(Reaction.isBlockedRight(defender, attacker)) this.swordOnShield(defender, -1, 0);
		else if(Reaction.isBothDeadRight(defender, attacker)) this.swordOnSword(defender, attacker);
		else if(Reaction.hasBlockedRight(defender, attacker)) this.swordOnShield(attacker, 1, 0);
	}
	/**
	 * Applies reactions on the front there are any
	 * @param defender
	 * @param attacker
	 */
	private void applyReactionsFront(Piece defender,Piece attacker){
		if(Reaction.isDeadFront(defender, attacker)) this.swordOnNothing(defender);
		else if(Reaction.isBlockedFront(defender, attacker)) this.swordOnShield(defender, 0, 1);
		else if(Reaction.isBothDeadFront(defender, attacker)) this.swordOnSword(defender, attacker);
		else if(Reaction.hasBlockedFront(defender, attacker)) this.swordOnShield(attacker, 0, -1);
	}
	/**
	 * Applies reactions on the back there are any
	 * @param defender
	 * @param attacker
	 */
	private void applyReactionsBack(Piece defender,Piece attacker){
		if(Reaction.isDeadBack(defender, attacker)) this.swordOnNothing(defender);
		else if(Reaction.isBlockedBack(defender, attacker)) this.swordOnShield(defender, 0, -1);
		else if(Reaction.isBothDeadBack(defender, attacker)) this.swordOnSword(defender, attacker);
		else if(Reaction.hasBlockedBack(defender, attacker)) this.swordOnShield(attacker, 0, 1);
	}



	public void setCreatable(boolean c){
		this.creatable = c;
	}
	@Override
	public Game clone(){
		Game game = new Game();
		game.setBoard(this.board.clone());
		game.setGreen(this.green.clone());
		game.setYellow(this.yellow.clone());
		game.setTurn(this.turn);
		game.setCreatable(this.creatable);
		@SuppressWarnings("unchecked")
		Stack<Game> games = (Stack<Game>)this.gameStates.clone();
		game.setStack(games);
		return game;
	}


}
