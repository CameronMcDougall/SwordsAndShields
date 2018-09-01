package Model;

import java.util.HashMap;
import java.util.Map;
/**
 * Players class that stores all of their pieces
 * @author Cameron
 *
 */
public class Player {
	private Map<Character, Piece> inventory; // inventory of player and pieces
	private boolean isYellow;
	//map used for faster speed
	/**
	 * Player object which holds the pieces inside inventory
	 */
	public Player(boolean isYellow) {
		inventory= new HashMap<Character, Piece>();
		this.isYellow = isYellow;
	}
	/**
	 * returns character to piece map of inventory
	 * @return
	 */
	public Map<Character, Piece> getInventory(){
		return inventory;
	}
	public boolean isYellow() {
		return isYellow;
	}
	/**
	 * Sets all the pieces to change the pointers
	 * @param inventory
	 * 		The inventory to be set
	 */
	public void setInventory(Map<Character, Piece> inventory){
		for(Map.Entry<Character, Piece> pieces: inventory.entrySet()){
			this.inventory.put(pieces.getKey().charValue(), pieces.getValue().clone());
		}
	}
	@Override
	public Player clone(){
		Player player = new Player(isYellow);
		player.setInventory(this.inventory);
		return player;
	}
}
