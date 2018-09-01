package Model;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.InputStreamReader;
import java.util.Scanner;
import resources.ImgResources;
/**
 * Class for getting the information from the text file and drawing it
 * @author Cameron
 *
 */
public class Information {
	/**
	 * Draws the information for the game from a text document
	 * @param g
	 * 		Main menu graphics pane
	 */
	public static void getInformation(Graphics g) {
		Scanner sc= new Scanner(new InputStreamReader(ImgResources.class.getResourceAsStream("information.txt")));
		g.setColor(Color.gray);//gets text from file inside imageresources package
		g.fillRect(0, 75, 1280, 250);
		int x = 100;
		int y = 100;
		g.setColor(Color.black);
		Font f = new Font ("Monospaced", Font.BOLD, 24);
		g.setFont(f);
		while(sc.hasNext()) {//gets every line and draws the text
			g.drawString(sc.nextLine(), x, y);
			y+=40;
		}
		sc.close();
	}
}
