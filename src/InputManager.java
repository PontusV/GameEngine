import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

	//Keeps track of which keys are down
	private HashMap<Integer, Boolean> keysPressed;
	private LinkedList<Integer> keysReleased;
	private HashMap<EventKey, GameObject> commandsPressed;
	private HashMap<EventKey, GameObject> commandsReleased;

	private static InputManager thisInstance;
	public static InputManager getInstance() {
		if (thisInstance == null)
			thisInstance = new InputManager();
		return thisInstance;
	}

	private InputManager() {
		//Constructor
		keysPressed = new HashMap<Integer, Boolean>();
		keysReleased = new LinkedList<Integer>();
		commandsPressed = new HashMap<EventKey, GameObject>();
		commandsReleased = new HashMap<EventKey, GameObject>();
	}

	/**
	 * Sends all input event to its corresponding GameObjects.
	 * 
	 * <p>Loops through all added commands and calls event if
	 * the keys needed to be pressed are pressed.
	 * 
	 * @see #keysPressed
	 * @see #commandsPressed
	 * @see #keysReleased
	 * @see #commandsReleased
	 */
	public void update(){
		boolean callEvent;
		Map.Entry<EventKey, GameObject> pair;
		EventKey event;
		Iterator<Entry<EventKey, GameObject>> it;
		//Key Pressed
		it = commandsPressed.entrySet().iterator();
		while (it.hasNext()) {
			pair = (Map.Entry<EventKey, GameObject>)it.next();
			event = pair.getKey();
			callEvent = true;

			for (Integer keyCode : event.getKeyCodes()) {
				if (!keysPressed.containsKey(keyCode) || !keysPressed.get(keyCode)) {
					callEvent = false;
					break;
				}
			}
			if (callEvent) //If all keys are pressed, calls event
				pair.getValue().inputEvent(event);
		}
		//Key Released
		it = commandsReleased.entrySet().iterator();
		while (it.hasNext()) {
			pair = (Map.Entry<EventKey, GameObject>)it.next();
			event = pair.getKey();
			callEvent = true;

			for (Integer keyCode : event.getKeyCodes()) {
				if (!keysReleased.contains(keyCode)) {
					callEvent = false;
					break;
				}
			}
			if (callEvent) //If all keys are pressed, calls event
				pair.getValue().inputEvent(event);
		}
		//Clear list of released keys
		keysReleased.clear();
	}

	/**
	 * Links KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is pressed an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @see EventKey
	 */
	public void addCommandPressed(LinkedList<Integer> codes, GameObject go) {
		commandsPressed.put(new EventKey(codes, true), go);
	}
	public void addCommandReleased(LinkedList<Integer> codes, GameObject go) {
		commandsReleased.put(new EventKey(codes, false), go);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		keysPressed.put(keyEvent.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		keysPressed.put(keyEvent.getKeyCode(), false);
		if (!keysReleased.contains(keyEvent.getKeyCode()))
			keysReleased.add(keyEvent.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

}
