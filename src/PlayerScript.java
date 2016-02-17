import java.awt.event.KeyEvent;
import java.util.LinkedList;

import Engine.EventKey;
import Engine.GameObject;
import Engine.InputManager;
import Engine.Script;

public class PlayerScript extends Script {
	
	private static final long serialVersionUID = 1001L;
	private boolean right,left,up,down;
	private int speed = 100; //pixels per sec
	
	public PlayerScript() {
		right = false;
		left = false;
		up = false;
		down = false;
		//Add keylisteners
		InputManager.getInstance().addCommand(KeyEvent.VK_UP, this);
		InputManager.getInstance().addCommand(KeyEvent.VK_DOWN, this);
		InputManager.getInstance().addCommand(KeyEvent.VK_RIGHT, this);
		InputManager.getInstance().addCommand(KeyEvent.VK_LEFT, this);
	}
	
	@Override
	public void tick(long timePassed) {
		if (up && !down)
			getParent().setVelocityY(-speed);
		else if (down && !up)
			getParent().setVelocityY(speed);
		else
			getParent().setVelocityY(0);
		
		if (left && !right)
			getParent().setVelocityX(-speed);
		else if (right && !left)
			getParent().setVelocityX(speed);
		else
			getParent().setVelocityX(0);
	}
	
	
	public void collisionEvent(GameObject other) {
		System.out.println("Collision Detected in PlayerScript!");
	}
	
	/**
	 * Handles keyEvent for its GameObject.
	 * @param event contains information of keyEvent.
	 */
	public void keyEvent(EventKey event) {
		if (event.getKeyCodes().get(0) == KeyEvent.VK_UP && event.isKeyDown())
			up = true;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_UP && !event.isKeyDown())
			up = false;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_DOWN && event.isKeyDown())
			down = true;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_DOWN && !event.isKeyDown())
			down = false;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_RIGHT && event.isKeyDown())
			right = true;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_RIGHT && !event.isKeyDown())
			right = false;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_LEFT && event.isKeyDown())
			left = true;
		else if (event.getKeyCodes().get(0) == KeyEvent.VK_LEFT && !event.isKeyDown())
			left = false;
	}
}
