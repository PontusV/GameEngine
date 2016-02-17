package Engine;
import java.util.LinkedList;
/**
 * Contains information of a KeyEvent.
 * 
 * <p>Used to send information of a KeyEvent to GameObjects.
 * 
 * @see GameObjects
 * @see java.awt.event.KeyEvent
 */
public class EventKey {
	
	private LinkedList<Integer> keyCodes;
	private boolean keyDown;
	
	public EventKey(LinkedList<Integer> codes, boolean keyDown) {
		if (codes.isEmpty())
			throw new IllegalArgumentException("The list of keycodes cannot be empty!");
		keyCodes = codes;
		this.keyDown = keyDown;
	}
	
	public LinkedList<Integer> getKeyCodes() {
		return keyCodes;
	}
	
	public boolean isKeyDown() {
		return keyDown;
	}
}
