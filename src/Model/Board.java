package Model;

import java.util.LinkedList;
import java.util.List;

import View.GamePanel;
/**
 * Board class that contains all of the pieces
 * @author Cameron
 *
 */
public class Board implements Cloneable{
	private BoardItem[][] board;
	private List<Piece> greenCemetery;
	private List<Piece> yellowCemetery;


	/**
	 * Sets the default pieces and initiates cemetery
	 */
	public Board() {
		this.board = new BoardItem[10][10];
		this.board[1][1] = new PlayerToken('0');
		this.board[8][8] = new PlayerToken('1');
		this.board[0][0] = new OutOfBounds();
		this.board[0][1] = new OutOfBounds();
		this.board[1][0] = new OutOfBounds();
		this.board[9][9] = new OutOfBounds();
		this.board[9][8] = new OutOfBounds();
		this.board[8][9] = new OutOfBounds();
		greenCemetery = new LinkedList<Piece>();
		yellowCemetery = new LinkedList<Piece>();
	}
	/**
	 * Gets the cemetery with the removed players in order of first to last removed
	 * @return
	 */
	public List<Piece> getGreenCemetery(){
		return greenCemetery;
	}

	public List<Piece> getYellowCemetery(){
		return yellowCemetery;
	}
	/**
	 * Sets the board of pieces to a specfic board
	 * @param board
	 * 		board to be set to
	 */
	public void setBoard(BoardItem[][] board){
		this.board = board;
	}
	/**
	 * Sets the cemetery of removed pieces
	 * @param cemetery
	 * 		cemetery of removed pieces to be set to
	 */
	public void setGreenCemetery(List<Piece> cemetery){
		this.greenCemetery = cemetery;
	}
	public void setYellowCemetery(List<Piece> cemetery){
		this.yellowCemetery = cemetery;
	}
	/**
	 * gets the board of pieces
	 * @return
	 */
	public BoardItem[][] getBoard(){
		return board;
	}
	/**
	 * Copies the board array so it can allow a deep clone
	 * @return
	 */
	private BoardItem[][] copyBoard(){
		BoardItem[][] newBoard = new BoardItem[10][10];
		for(int row = 0; row < 10; row++){
			for(int col = 0; col < 10; col++){
				if(board[row][col] != null){
					if(board[row][col] instanceof Piece){
						Piece piece = ((Piece) board[row][col]).clone();
						newBoard[row][col] = piece;
					}else{
						newBoard[row][col] = board[row][col];
					}
				}

			}
		}
		return newBoard;
	}
	/**
	 * copies the cemetery for a deep clone
	 * @return
	 */
	private List<Piece> copyCemetery(List<Piece> cemetery){
		List<Piece> copy = new LinkedList<Piece>();
		for(Piece item: cemetery){
			Piece piece =  item.clone();
			copy.add(piece);
		}
		return copy;
	}


	/**
	 * Add piece directly to the board. Returns true if successfully added
	 * @param x
	 * 		column position
	 * @param y
	 * 		row position
	 * @param piece
	 * 		piece to be added
	 * @return
	 */
	public boolean addTile(int x,int y, Piece piece){
		if(board[x][y] == null){
			board[x][y] = piece;
			return true;
		}else{
			System.out.println("ITEM ALREADY ON CREATION");
		}
		return false;
	}

	public boolean isPieceUsed(char piece){
		//checks piece to be used so it cannot be moved or rotated for the round again
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					Piece current = (Piece) board[row][col];
					if(current.getName() == piece) {
						if(current.isUsed()){
							return true;
						}
					}
				}
			}
		}
		return false;
	}


	/**
	 * Returns true if the piece was successfully move off the board or into a empty spot
	 * @param piece
	 * 		Piece wanting to be moved
	 * @param dx
	 * 		The movement on the board in the column position
	 * @param dy
	 * 		The movement on the board in the row position
	 * @return
	 */
	public boolean movePiece(char piece, int dx,int dy){
		for(int y = 0; y < board.length; y++){
			for(int x = 0; x < board[0].length; x++){
				if(board[y][x] instanceof Piece){
					Piece temp = (Piece)board[y][x];
					if(temp.getName() == piece){
						if(pieceOutOfBounds(x + dx,y + dy,temp)){//piece is removed if out of bounds
							//this.destroyPiece(temp.getName());
							temp.setIsDead(true);
							temp.setHasAudio(true);
							return true;
						}else if(board[y + dy][x + dx] != null){ // checks if a piece is in the way
							Piece next = (Piece) board[y + dy][x + dx];
							temp.setDestX(temp.getX()+(GamePanel.colSpacing*dx));
							temp.setDestY(temp.getY()+(GamePanel.rowSpacing*dy));
							temp.setMoving(true);
							next = next.clone();//clones so original is uneffected
							movePiece(next.getName(), dx,dy);//recursively moves all the board pieces
							board[y + dy][x + dx] = board[y][x];//moves the piece to correct location
							board[y][x] = null;
							return true;
						}else if(board[y + dy][x + dx] == null){//empty tile
							temp.setDestX(temp.getX()+(GamePanel.colSpacing*dx));
							temp.setDestY(temp.getY()+(GamePanel.rowSpacing*dy));
							temp.setMoving(true);
							board[y + dy][x + dx] = board[y][x];
							board[y][x] = null;
							return true;//succesfully movedd
						}
					}
				}
			}
		}

		return false;
	}
	/**
	 * Sets a piece to be moved so it cannot have multiple movements per round
	 * @param letter
	 * 		Piece that will be set to have been moved
	 */
	public void setPieceMoved(char letter){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					Piece current = (Piece) board[row][col];
					if(current.getName() == letter){
						current.setIsUsed(true);
					}
				}
			}
		}
	}
	/**
	 * Checks if a piece has moved into a spot that it is not allowed to be moved to
	 * @param row
	 * 		row position on the board
	 * @param col
	 * 		column position on the board
	 * @return
	 */
	private boolean pieceOutOfBounds(int row, int col,Piece piece){
		if(row <= -1 || row  >=  board[0].length){//gone of side of board
			piece.setDeath("Fallen");
			return true;
		}else if(col  <= -1 || col  >=  board.length){//gone of top or bottom of board
			piece.setDeath("Fallen");
			return true;
		}else if(board[row][col] instanceof OutOfBounds){// if its out of bounds it can be removed
			piece.setDeath("Fallen");
			return true;
		}else if(board[row][col] instanceof PlayerToken){//cannot move onto piece

			piece.setDeath("Eatten");
			return true;
		}
		return false;
	}
	public void resetAudio(char c){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					Piece current = (Piece) board[row][col];
					current.setHasAudio(false);
				}
			}
		}
	}
	/**
	 * Checks if there is a piece in a specific location
	 * @param row
	 * 		row position on the board
	 * @param col
	 * 		col position on the board
	 * @return
	 */
	public boolean hasPiece(int row,int col) {
		return board[row][col] != null;
	}
	/**
	 * Returns true if the board piece exists
	 * @param piece
	 * 		Piece wanting to be rotated
	 * @param rotation
	 * 		rotation angle
	 * @param isCreation
	 * 		if the piece is in creation mode
	 * @return
	 */
	public boolean rotateBoardPiece(char piece, int rotation){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					BoardItem current = (Piece) board[row][col];
					if(current.getName() == piece) {
						current.rotate(rotation);
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * Resets all pieces after a turn is over so they can be used next turn
	 */
	public void resetUsedPieces(){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					Piece current = (Piece) board[row][col];
					current.setIsUsed(false);
				}
			}
		}
	}



	/**
	 * destroys a piece that has been effected by reactions or is off the board
	 * @param name
	 * 		letter of piece
	 */
	public void destroyPiece(char name){
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[0].length; col++){
				if(board[row][col] instanceof Piece){
					Piece currentPiece = (Piece) board[row][col];
					if(currentPiece.getName() == name){
						currentPiece.setIsDead(true);
						if(currentPiece.getIsDead() && currentPiece.getOpacity()==0){
							board[row][col] = null;
							if(currentPiece.getColour()){
								yellowCemetery.add(currentPiece);
							}else{
								greenCemetery.add(currentPiece);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public Board clone(){
		Board newBoard = new Board();
		BoardItem[][] board;
		board = this.copyBoard();
		newBoard.setBoard(board);
		newBoard.setGreenCemetery(copyCemetery(greenCemetery));
		newBoard.setYellowCemetery(copyCemetery(yellowCemetery));
		return newBoard;
	}

}
