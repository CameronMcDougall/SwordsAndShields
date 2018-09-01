package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Class that holds all the logic to reactions inside
 * @author Cameron
 *
 */
public class Reaction {
	public static List<Pair> getAllPairs(BoardItem[][] board){
		List<Pair> pairs = new ArrayList<Pair>();
		for(int row = 0; row < board.length; row++){
			for(int col = 0; col < board[row].length;col++){
				if(board[row][col] instanceof Piece){
					Piece piece = (Piece)board[row][col];
					pairs.addAll(getReactions(row, col, piece, board));
				}
			}
		}
		return pairs;
	}
	/**
	 * 
	 * @param row
	 * 		row position on board
	 * @param col
	 * 		column position on board
	 * @param defender
	 * 		piece being compared on to neighbours for reactions
	 * @param board
	 * 		board with all pieces
	 * @return
	 */
	public static List<Pair> getReactions(int row, int col,Piece defender,BoardItem[][] board){
		//adds all the pieces to a list that have a reaction
		//goes counter clockwise from top being starting point
		List<Integer> positions = Arrays.asList(row-1,col,row,col-1,row+1,col,row, col+1);//adjacent positions 
		List<Pair> reactors = new ArrayList<Pair>();
		for(int direction = 0; direction < positions.size(); direction+=2){
			//every row and col
			int adjacentRow = positions.get(direction);
			int adjacentCol = positions.get(direction + 1);
			if(adjacentRow >= board.length || adjacentRow < 0){
				continue;
			}else if(adjacentCol >= board[0].length || adjacentCol < 0){
				continue;
			}else if(board[adjacentRow][adjacentCol] instanceof Piece){
				Piece attacker = (Piece)board[adjacentRow][adjacentCol];
				if(direction / 2 == 0){//front
					if(hasReactionFront(defender,attacker)){ 
						Pair pair = new Pair(defender, attacker, "up");
						pair.setPositions(col, row, adjacentCol, adjacentRow);
						reactors.add(pair);
					}
				}else if(direction / 2 == 1){//left
					if(hasReactionLeft(defender,attacker)){
						Pair pair = new Pair(defender, attacker, "left");
						pair.setPositions(col, row, adjacentCol, adjacentRow);
						reactors.add(pair);
					}
				}else if(direction / 2 == 2){//back
					if(hasReactionBack(defender,attacker)) {
						Pair pair = new Pair(defender, attacker, "down");
						pair.setPositions(col, row, adjacentCol, adjacentRow);
						reactors.add(pair);
					}
				}else{//right
					if(hasReactionRight(defender,attacker)){
						Pair pair = new Pair(defender, attacker, "right");
						pair.setPositions(col, row, adjacentCol, adjacentRow);
						reactors.add(pair);
					}
				}
			}
		}
	return reactors;
}
/**
 * Checks if sword is touching pieces sword on left
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBothDeadLeft(Piece defend, Piece attack){
	if(defend.getLeft() == Piece.PieceSymbol.SWORD
			&& attack.getRight() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching pieces sword on front
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBothDeadFront(Piece defend, Piece attack){
	if(defend.getFront() == Piece.PieceSymbol.SWORD
			&& attack.getBack() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching pieces sword on right
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBothDeadRight(Piece defend, Piece attack){
	if(defend.getRight() == Piece.PieceSymbol.SWORD
			&& attack.getLeft() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching pieces sword on bottom
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBothDeadBack(Piece defend, Piece attack){
	if(defend.getBack() == Piece.PieceSymbol.SWORD
			&& attack.getFront() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with no weapon on left
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isDeadLeft(Piece defend, Piece attack){
	if(defend.getLeft() == Piece.PieceSymbol.NONE
			&& attack.getRight() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with no weapon on front
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isDeadFront(Piece defend, Piece attack){
	if(defend.getFront() == Piece.PieceSymbol.NONE
			&& attack.getBack() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with no weapon on right
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isDeadRight(Piece defend, Piece attack){
	if(defend.getRight() == Piece.PieceSymbol.NONE
			&& attack.getLeft() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with no weapon on back
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isDeadBack(Piece defend, Piece attack){
	if(defend.getBack() == Piece.PieceSymbol.NONE
			&& attack.getFront() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if shield is touching piece with a sword on left
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBlockedLeft(Piece defend, Piece attack){
	if(defend.getLeft() == Piece.PieceSymbol.SWORD
			&& attack.getRight() == Piece.PieceSymbol.SHIELD){
		return true;
	}
	return false;
}
/**
 * Checks if shield is touching piece with a sword on front
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBlockedFront(Piece defend, Piece attack){
	if(defend.getFront() == Piece.PieceSymbol.SWORD
			&& attack.getBack() == Piece.PieceSymbol.SHIELD){
		return true;
	}
	return false;
}
/**
 * Checks if shield is touching piece with a sword on right
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBlockedRight(Piece defend, Piece attack){
	if(defend.getRight() == Piece.PieceSymbol.SWORD
			&& attack.getLeft() == Piece.PieceSymbol.SHIELD){
		return true;
	}
	return false;
}
/**
 * Checks if shield is touching piece with a sword on bottom
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean isBlockedBack(Piece defend, Piece attack){
	if(defend.getBack() == Piece.PieceSymbol.SWORD
			&& attack.getFront() == Piece.PieceSymbol.SHIELD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with a shield on left
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean hasBlockedLeft(Piece defend, Piece attack){
	if(defend.getLeft() == Piece.PieceSymbol.SHIELD
			&& attack.getRight() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with a shield on front
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean hasBlockedFront(Piece defend, Piece attack){
	if(defend.getFront() == Piece.PieceSymbol.SHIELD
			&& attack.getBack() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with a shield on right
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean hasBlockedRight(Piece defend, Piece attack){
	if(defend.getRight() == Piece.PieceSymbol.SHIELD
			&& attack.getLeft() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks if sword is touching piece with a shield on back
 * @param defend
 * 		Current piece being checked
 * @param attack
 * 		Adjacent piece next to current piece
 * @return
 */
public static boolean hasBlockedBack(Piece defend, Piece attack){
	if(defend.getBack() == Piece.PieceSymbol.SHIELD
			&& attack.getFront() == Piece.PieceSymbol.SWORD){
		return true;
	}
	return false;
}
/**
 * Checks all possible reactions in that front direction and returns true if there is one
 * @param defender
 * 		piece being checked
 * @param attacker
 * 		adjacent piece
 * @return
 */
private static boolean hasReactionFront(Piece defender, Piece attacker){
	if(isDeadFront(defender, attacker)) return true;
	else if(isBlockedFront(defender, attacker)) return true;
	else if(isBothDeadFront(defender, attacker))return true;
	else if(hasBlockedFront(defender, attacker))return true;
	else return false;
}
/**
 * Checks all possible reactions in that left direction and returns true if there is one
 * @param defender
 * 		piece being checked
 * @param attacker
 * 		adjacent piece
 * @return
 */
private static boolean hasReactionLeft(Piece defender, Piece attacker){
	if(isDeadLeft(defender, attacker))return true;
	else if(isBlockedLeft(defender, attacker))return true;
	else if(isBothDeadLeft(defender, attacker)) return true;
	else if(hasBlockedLeft(defender, attacker))return true;
	else return false;
}
/**
 * Checks all possible reactions in that back direction and returns true if there is one
 * @param defender
 * 		piece being checked
 * @param attacker
 * 		adjacent piece
 * @return
 */
private static boolean hasReactionBack(Piece defender, Piece attacker){
	if(isDeadBack(defender, attacker)) return true;
	else if(isBlockedBack(defender, attacker)) return true;
	else if(isBothDeadBack(defender, attacker)) return true;
	else if(hasBlockedBack(defender, attacker)) return true;
	else return false;
}
/**
 * Checks all possible reactions in that right direction and returns true if there is one
 * @param defender
 * 		piece being checked
 * @param attacker
 * 		adjacent piece
 * @return
 */
private static boolean hasReactionRight(Piece defender, Piece attacker){
	if(isDeadRight(defender, attacker)) return true;
	else if(isBlockedRight(defender, attacker)) return true;
	else if(isBothDeadRight(defender, attacker)) return true;
	else if(hasBlockedRight(defender, attacker)) return true;
	else return false;
}
}
