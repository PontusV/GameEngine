import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.sound.sampled.Clip;

/**
 * Implementation of GameEngine.
 * Used for testing.
 */
public class Main {

	public static void main(String[] args) {
		GameEngine engine = GameEngine.getInstance();
		//------------------------------------
		GameObject go = new GameObject();
		Resource<BufferedImage> img = Loader.getInstance().getImage("mario.png");
		ImageComponent comp = new ImageComponent(img, 100, 100);
		go.addComponent(comp);
		//-----------------------------------------
		//Deklarerar vilket objekt som eventet ska skickas till
		//Objektet definierar vad som ska hända
		//addCommand(GameObject go)
		//Adding a command makes the Game Engine send chosen key events to specified Game Object
		LinkedList<Integer> keyCodes = new LinkedList<Integer>();
		keyCodes.add(KeyEvent.VK_SPACE);
		InputManager.getInstance().addCommandReleased(keyCodes, go); //Sends SPACE to go
		
		Resource<Clip> testSound = Loader.getInstance().getSound("Footstep01.wav");
		Sound sound = new Sound(testSound, "footstep1");

		go.addComponent(sound);
		engine.addGameObject(go);
		
		SoundManager sm = SoundManager.getInstance();
		sm.playSound("footstep1");
		
		Loader.getInstance().loadGameObject(go);
		
		engine.initiate();
	}
}
