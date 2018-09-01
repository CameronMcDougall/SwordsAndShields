package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import View.InventoryPanel;
/**
 * Inventory panel controller that deals with mouse actions of the inventory panel
 * @author Cameron
 *
 */
public class InventoryPanelController implements MouseListener{
	private InventoryPanel panel;
	/**
	 * initalize the controller taking account mouse clicks
	 * @param panel
	 */
	public InventoryPanelController(InventoryPanel panel) {
		// TODO Auto-generated constructor stub
		this.panel = panel;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(panel.getGameFrame().getGame().creatable &&
				!panel.getGameFrame().getGame().hasPiece()) {//if in create phase and the location has a piece
			int x = e.getX();
			int y= e.getY();
			panel.getSelected(x, y);
			panel.repaint();
		}
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

}
