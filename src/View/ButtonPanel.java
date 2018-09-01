package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Creates the top panel that contains all of the buttons
 * @author Cameron
 *
 */
public class ButtonPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private GameFrame frame;
	/**
	 * Constructor for the button panel that contains the JToolbar with JButtons
	 * @param frame
	 * 	  Frame that this panel is going on to
	 */
	public ButtonPanel(GameFrame frame){
		this.frame = frame;
		this.setFocusable(false);
		this.setJButtons();
		this.setSize( new Dimension(1280, 100));
		this.setBackground(Color.white);
	}


	@Override
	public Dimension getPreferredSize() {return new Dimension(1280, 10);}

	@Override
	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		@SuppressWarnings("unused")
		Graphics2D g = (Graphics2D) _g;

	}
	/**
	 * Creates all the JButtons which are put onto the JToolBar and added to the panel
	 */
	private void setJButtons(){
		JToolBar a= new JToolBar();
		a.add(undo());
		a.add(pass());
		a.add(surrender());
		this.add(a);
		a.setFocusable(false);
	}
	/**
	 * Creates the undo buttons that will appear on the JToolbar
	 * @return
	 */
	private JButton undo(){
		JButton undo = new JButton("Undo");
		undo.setFocusable(false);//avoids the keys being focused
		undo.setEnabled(true);//has ability to use listeners
		undo.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				frame.getGamePanel().setSelectedRectangle();// resets all of the user selection on board
				frame.getGamePanel().setSelectedRotation(null);
				frame.getGamePanel().setType();
				frame.undo();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return undo;
	}
	private JButton pass(){
		JButton pass = new JButton("Pass");
		pass.setFocusable(false);//avoids the keys being focused
		pass.setEnabled(true);//has ability to use listeners
		pass.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				frame.getGame().pass();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		return pass;
	}
	/**
	 * Surrender button that gets added to the JToolbar
	 * @return
	 */
	private JButton surrender(){
		JButton surrender = new JButton("Surrender");
		surrender.setFocusable(false);//avoids the keys being focused
		surrender.setEnabled(true);//has ability to use listeners
		surrender.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				frame.surrender();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		return surrender;
	}

}
