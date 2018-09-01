package resources;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum ImgResources {
  YELLOW("yellow.png"), //
  GREEN("green.png"),
  MENU("swords-shield.jpg");
  
  public final Image img;
  
  ImgResources(String resourceName) {
    try{ img = ImageIO.read(ImgResources.class.getResource(resourceName)); }
    catch (IOException e){ throw new Error(e); }
  }
}
