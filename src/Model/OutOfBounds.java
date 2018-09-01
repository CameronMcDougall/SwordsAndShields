package Model;

/**
 * Out of bounds object so it is not possible for pieces to go on these places
 * @author Cameron
 *
 */
public class OutOfBounds extends BoardItem {
	//exclamation mark illustrate that it should not be approached
	@Override
	public void drawTop() {
		// TODO Auto-generated method stub
		System.out.print("!!!");
	}

	@Override
	public void drawMiddle() {
		// TODO Auto-generated method stub
		System.out.print("!!!");
	}

	@Override
	public void drawBottom() {
		// TODO Auto-generated method stub
		System.out.print("!!!");
	}
}
