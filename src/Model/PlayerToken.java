package Model;

/**
 * Player token which is indicated for the game being over if a sword is in its direction 
 * @author Cameron
 *
 */
public class PlayerToken extends BoardItem{
	
	/**
	 * Creates the player token that helps indicates that the game has been won
	 * @param playerSymbol
	 * 		Players identify with 0 or 1
	 */
	public PlayerToken(char playerSymbol){
		setName(playerSymbol);
	}
	@Override
	public void drawTop() {
		// TODO Auto-generated method stub
		System.out.print("   ");
	}
	
	@Override
	public void drawMiddle() {
		// TODO Auto-generated method stub
		System.out.print(" " + getName() + " ");// draws the 1 or 0 that is the player green or yellow
	}

	@Override
	public void drawBottom() {
		// TODO Auto-generated method stub
		this.drawTop();
	}
	

}
