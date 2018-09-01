package Tests;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.junit.Test;
import Model.Game;
import Model.Pair;
import Model.Piece;
import Model.Reaction;
import View.GameFrame;
import Model.BoardItem.PieceSymbol;
/**
 * Tests necessary aspects of the game
 * @author Cameron
 *
 */
public class Assignment2Tests {
	GameFrame frame;
	/**
	 * Destroys the frame so the next test can be used
	 * @throws InterruptedException
	 */
	void killIn() throws InterruptedException{
		SwingUtilities.invokeLater(()->{
			new Timer(1000,e->{
				if(frame!=null){
					frame.dispose();
					frame = null;
				}}).start();
		});
		Thread.sleep(1000);
	}
	@Test
	public void creationTest_1() throws InterruptedException{
		//Tests the creation of a yellow piece
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		frame.repaint();
		killIn();		
	}
	@Test
	public void creationTest_2() throws InterruptedException{
		//Tests the creation of a yellow piece and green
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		game.turnOver();
		game.createTile('A', 0);
		frame.repaint();
		killIn();		
	}
	@Test
	public void movementTest_1() throws InterruptedException{
		//Tests the down movement of piece
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		game.pass();
		game.movePiece('a', "down");
		Thread.sleep(1000);
		frame.repaint();
		killIn();		
	}
	@Test
	public void movementTest_2() throws InterruptedException{
		//Tests the left movement of piece
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		game.pass();
		game.movePiece('a', "left");
		Thread.sleep(1000);
		frame.repaint();
		killIn();		
	}
	@Test
	public void movementTest_3() throws InterruptedException{
		//Tests the right movement of piece
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		game.pass();
		game.movePiece('a', "right");
		Thread.sleep(1000);
		frame.repaint();
		killIn();		
	}
	@Test
	public void movementTest_4() throws InterruptedException{
		//Tests the up movement of piece
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		game.createTile('a', 0);
		game.pass();
		game.movePiece('a', "up");
		Thread.sleep(1000);
		frame.repaint();
		killIn();		
	}
	@Test
	public void reactionTest_1() throws InterruptedException{
		//Tests the sword on nothing reaction
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		Piece attack = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.SWORD),'8',true);
		game.addPiece(7, 8, defend);
		game.addPiece(7, 9, attack);
		Thread.sleep(500);
		List<Pair> reactions = Reaction.getAllPairs(game.getBoard().getBoard());
		game.pieceReaction(reactions.get(0));
		Thread.sleep(2000);
		frame.repaint();
		killIn();		
	}
	@Test
	public void reactionTest_2() throws InterruptedException{
		//Tests the sword on shield reaction
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SHIELD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		Piece attack = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.SWORD),'8',true);
		game.addPiece(7, 8, defend);
		game.addPiece(7, 9, attack);
		Thread.sleep(500);
		List<Pair> reactions = Reaction.getAllPairs(game.getBoard().getBoard());
		game.pieceReaction(reactions.get(0));
		frame.repaint();
		Thread.sleep(8000);
		killIn();		
	}
	
	@Test
	public void reactionTest_3() throws InterruptedException{
		//Tests the sword on sword reaction
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SWORD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		Piece attack = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.NONE,PieceSymbol.SWORD),'8',true);
		game.addPiece(7, 8, defend);
		game.addPiece(7, 9, attack);
		Thread.sleep(500);
		List<Pair> reactions = Reaction.getAllPairs(game.getBoard().getBoard());
		game.pieceReaction(reactions.get(0));
		frame.repaint();
		Thread.sleep(2000);
		killIn();		
	}
	
	@Test
	public void rotateTest_1() throws InterruptedException{
		//Tests rotating 90 degrees
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SWORD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		game.addPiece(7, 8, defend);
		game.pass();
		Thread.sleep(500);
		game.rotatePiece('\0', 90);
		frame.repaint();
		Thread.sleep(1000);
		killIn();		
	}
	
	@Test
	public void rotateTest_2() throws InterruptedException{
		//Tests rotating 180 degrees
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SWORD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		game.addPiece(7, 8, defend);
		game.pass();
		Thread.sleep(500);
		game.rotatePiece('\0', 180);
		frame.repaint();
		Thread.sleep(1000);
		killIn();		
	}
	@Test
	public void rotateTest_3() throws InterruptedException{
		//Tests rotating 270 degrees
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SWORD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		game.addPiece(7, 8, defend);
		game.pass();
		Thread.sleep(500);
		game.rotatePiece('\0', 270);
		frame.repaint();
		Thread.sleep(1000);
		killIn();		
	}
	@Test
	public void rotateTest_4() throws InterruptedException{
		//Test rotation 360 degrees
		Game game = new Game();	
		frame = new GameFrame(game,null);
		Thread.sleep(1000);
		Piece defend = new Piece(Arrays.asList(PieceSymbol.NONE,PieceSymbol.SWORD,PieceSymbol.NONE,PieceSymbol.NONE),'\0',true);
		game.addPiece(7, 8, defend);
		game.pass();
		Thread.sleep(500);
		game.rotatePiece('\0', 0);
		frame.repaint();
		Thread.sleep(1000);
		killIn();	
	
	}
	

}
