import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Implementation of GameEngine.
 * Used for testing.
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = GameEngine.getInstance();
		//------------------------------------
		GameObject go = new GameObject();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("mario.png"));
		} catch (IOException e) {}
		ImageComponent comp = new ImageComponent(img, 100, 100);
		go.addComponent(comp);
		engine.addGameObject(go);
		//-----------------------------------------
		//Deklarerar vilket objekt som eventet ska skickas till
		//Objektet definierar vad som ska hända
		//addCommand(GameObject go)
		
		
		engine.initiate();
	}
}
