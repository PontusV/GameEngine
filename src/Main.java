import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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
		//Adding a command makes the Game Engine send chosen key events to specified Game Object
		LinkedList<Integer> keyCodes = new LinkedList<Integer>();
		keyCodes.add(KeyEvent.VK_SPACE);
		keyCodes.add(KeyEvent.VK_W);
		InputManager.getInstance().addCommandReleased(keyCodes, go); //Sends SPACE to go
		
		engine.initiate();
	}
}
