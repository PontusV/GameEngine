import Engine.*; //Import GameEngine
import java.awt.image.BufferedImage;

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
		ImageComponent comp = new ImageComponent(img, 70, 240);
		go.addComponent(comp);
		//-----------------------------------------
		GameObject go2 = new GameObject();
		Resource<BufferedImage> img2 = Loader.getInstance().getImage("mario.png");
		ImageComponent comp2 = new ImageComponent(img2, 400, 370);
		go2.addComponent(comp2);
		//----------------------------------------
		Resource<Clip> testSound = Loader.getInstance().getSound("Footstep01.wav");
		Sound sound = new Sound(testSound, "footstep1");

		go.addComponent(sound);
		go.addComponent(new PlayerScript());
		go2.setWeight(500000);
		engine.addGameObject(go);
		engine.addGameObject(go2);
		System.out.println( PhysicsEngine.getInstance().isColliding(go, go2) ); //Collision test
		
		SoundManager sm = SoundManager.getInstance();
		sm.playSound("footstep1");
		
		Loader.getInstance().loadGameObject(go);
		
		engine.initiate();
	}
}
