package View;
import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import Model.Game;
/**
 * The main frame for the game class
 * @author Cameron
 *
 */
public class GameFrame extends JFrame implements Observer{
	private MainMenu menu;
	private static final long serialVersionUID = 1L;
	private Game game;
	private JPanel buttonPanel;
	private	GamePanel gamePanel;
	private JPanel greenGravePanel;
	private JPanel yellowGravePanel;
	private InventoryPanel greenInventPanel;
	private InventoryPanel yellowInventPanel;
	private boolean exited = false;
	private SplitPanes completePanel;
	/**
	 * Game that contains all of the panes combined
	 * @param game
	 * 		The game currently running
	 * @param menu
	 * 		The main menu
	 */
	public GameFrame(Game game, MainMenu menu){
		super("Sword and shields");
		this.menu = menu;
		this.setGame(game);
		this.createPanels();
		game.addObserver(this);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.createSplitPanes();
		pack();
		setVisible(true);
		new Timer(200, (e)->{
			if(!this.getGame().gameOver()){
				this.repaint();//repaint screens when not over
				this.revalidate();
			}else{
				gamePanel.repaint();
				gamePanel.revalidate();
				if(!exited){//show surrender message when sword on face
					this.surrender();
					exited = !exited;
				}
			}
		}
				).start();
	}
	/**
	 * Goes back to last state
	 */
	public void undo(){
		getGame().undo();
	}
	/**
	 * Gets the game panel
	 * @return
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	/**
	 * Sets the game panel
	 * @param gamePanel
	 * 		new game panel
	 */
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	/**
	 * Gets the button panel
	 * @return
	 */
	public JPanel getButtonPanel() {
		return buttonPanel;
	}
	/**
	 * Returns a JDialog for the person surrendering
	 */
	public void surrender(){
		String output = (game.isYellowsTurn()) ? "GREEN WINS" : "YELLOW WINS";
		JOptionPane.showOptionDialog(this,
				output,"GAME OVER",
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.QUESTION_MESSAGE,
				null,
				new Object[]{"OK"},
				"OK");
		menu.openWindow();
		this.setVisible(false);
		this.dispose();
	}
	/**
	 * Creates all of panels that are attached to the split pane
	 */
	private void createPanels(){
		buttonPanel = new ButtonPanel(this);
		gamePanel = new GamePanel(this);
		gamePanel.setFocusable(true);
		greenGravePanel = new GraveyardPanel(this,false);
		yellowGravePanel = new GraveyardPanel(this,true);
		greenInventPanel = new InventoryPanel(this,false,true);
		yellowInventPanel = new InventoryPanel(this,true,false);
	}
	/**
	 * Creates the full split pane using all of the created panels
	 */
	private void createSplitPanes(){
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,greenInventPanel, gamePanel);
		sp2.setResizeWeight(0.2);
		JSplitPane sp3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,sp2, yellowInventPanel );//puts inventorys to side of board
		sp3.setResizeWeight(0.8);
		JSplitPane sp4 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,buttonPanel, sp3 );//puts buttons on top
		JSplitPane sp5 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,greenGravePanel, yellowGravePanel );//puts graveyards together
		sp5.setResizeWeight(0.5);
		completePanel= new SplitPanes(JSplitPane.VERTICAL_SPLIT,sp4, sp5);//attaches all panels together
		completePanel.setResizeWeight(0.8);
		add(completePanel);
	}
	/**
	 * Gets the final split pane with all the panels
	 * @return
	 */
	public SplitPanes getCompletePanel() {
		return completePanel;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	/**
	 * Gets the current game running
	 * @return
	 */
	public Game getGame() {
		return game;
	}
	/**
	 * Sets the current game that is being played
	 * @param game
	 * 		The game being set
	 */
	public void setGame(Game game) {
		this.game = game;
	}


}
