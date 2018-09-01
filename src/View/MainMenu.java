package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Model.Game;
import Model.Information;
import resources.ImgResources;
/**
 * Main menu frame that has the options to start the game
 * @author Cameron
 *
 */
public class MainMenu extends JFrame implements Observer{
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private boolean isInfo = false;
	/**
	 * Creates main menu frame
	 * @param game
	 * 		Game object that creates new Game
	 */
	MainMenu(){
		super("Sword and shields");
		this.setFocusable(true);
		this.createPanel();
		setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(1280,720));
		panel.setBackground(new Color(255,255,255));
		panel.add(startGame(),BorderLayout.CENTER);
		panel.add(infoGame(),BorderLayout.CENTER);
		panel.add(quitGame(),BorderLayout.CENTER);
		add(panel);
		pack();
		setVisible(true);
		new Timer(500,e->{
			if(this.isDisplayable()){
				this.repaint();
			}
		}).start();
	}

	/**
	 * creates a start game button that will open a new frame and close this one
	 * @return
	 */
	private JButton startGame(){
		JButton startGame = new JButton("Begin New Game");
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu.this.setVisible(false);//main menu not visable
				//makes new window
				dispatchEvent(new WindowEvent(new GameFrame(new Game(),MainMenu.this), WindowEvent.WINDOW_ACTIVATED));
			}
		});
		return startGame;
	}
	/**
	 * Closes this game window
	 * @return
	 */
	private JButton quitGame(){
		JButton quitGame = new JButton("Quit");
		quitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu.this.setVisible(false);
			}
		});
		return quitGame;
	}

	/**
	 * Creates the button that will display the information
	 * @return
	 */
	private JButton infoGame(){
		JButton infoGame = new JButton("Info");
		infoGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isInfo = !isInfo;
				panel.repaint();
			}
		});
		return infoGame;
	}

	/**
	 * Creates the panel that gets added to this frame
	 */
	private void createPanel(){
		panel = new JPanel(){
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				drawMainScreen(g);
				if(isInfo) {
					Information.getInformation(g);
				}
			}
		};
	}
	private void drawMainScreen(Graphics g) {
		g.setColor(Color.RED);
		g.drawImage(ImgResources.MENU.img, 0, -20, 1280, 720, null);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 75));
		g.drawString("SWORDS AND SHIELDS", 175 , 380);

	}
	/**
	 * Opens this window again after surrendering and losing
	 */
	public void openWindow(){
		this.setVisible(true);
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
