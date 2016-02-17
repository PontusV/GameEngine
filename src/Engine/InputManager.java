package Engine;
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
	private HashMap<Integer, Integer> keysPressed; //0 = not pressed, 1 = pressed, 2 = pressed and event called
	private LinkedList<Integer> keysReleased;
	private HashMap<EventKey, Script> commandsPressed;
	private HashMap<EventKey, Script> commandsReleased;

	private static InputManager thisInstance;
	public static InputManager getInstance() {
		if (thisInstance == null)
			thisInstance = new InputManager();
		return thisInstance;
	}

	private InputManager() {
		//Constructor
		keysPressed = new HashMap<Integer, Integer>();
		keysReleased = new LinkedList<Integer>();
		commandsPressed = new HashMap<EventKey, Script>();
		commandsReleased = new HashMap<EventKey, Script>();
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
		Map.Entry<EventKey, Script> pair;
		EventKey event;
		Iterator<Entry<EventKey, Script>> it;
		//Key Pressed
		it = commandsPressed.entrySet().iterator();
		while (it.hasNext()) {
			pair = (Map.Entry<EventKey, Script>)it.next();
			event = pair.getKey();
			callEvent = true;

			for (Integer keyCode : event.getKeyCodes()) {
				if (!keysPressed.containsKey(keyCode) || keysPressed.get(keyCode) == 0 || keysPressed.get(keyCode) == 2) {
					callEvent = false;
					break;
				}
			}
			if (callEvent) { //If all keys are pressed, calls event
				pair.getValue().keyEvent(event);
				for (Integer keyCode : event.getKeyCodes())
					keysPressed.put(keyCode, 2); //Set to "event called"
			}
		}
		//Key Released
		boolean released;
		it = commandsReleased.entrySet().iterator();
		while (it.hasNext()) {
			pair = (Map.Entry<EventKey, Script>)it.next();
			event = pair.getKey();
			callEvent = true;
			released = false;

			for (Integer keyCode : event.getKeyCodes()) {
				if (!keysReleased.contains(keyCode) && (!keysPressed.containsKey(keyCode) || keysPressed.get(keyCode) == 0 )) { //If Key is isnt Released and isnt currently Pressed
					callEvent = false;
					break;
				} else if (keysReleased.contains(keyCode)) //If key is released
					released = true;
			}
			if (released && callEvent) //If at least 1 key of the combination is released, calls event
				pair.getValue().keyEvent(event);
		}
		//Clear list of released keys
		keysReleased.clear();
	}

	/**
	 * Links Pressed KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is pressed an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @see EventKey
	 * @param codes List of codes needed to be pressed for event to be sent.
	 * @param go GameObject the event will be sent to.
	 */
	public void addCommandPressed(LinkedList<Integer> codes, Script script) {
		commandsPressed.put(new EventKey(codes, true), script);
	}
	/**
	 * Links Released KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is released an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @see EventKey
	 * @param codes List of codes needed to be released for event to be sent.
	 * @param go GameObject the event will be sent to.
	 */
	public void addCommandReleased(LinkedList<Integer> codes, Script script) {
		commandsReleased.put(new EventKey(codes, false), script);
	}

	/**
	 * Links Pressed KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is pressed an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @param code needed to be pressed for event to be sent.
	 * @param go GameObject the event will be sent to.
	 * @see EventKey
	 */
	public void addCommandPressed(int code, Script script) {
		LinkedList<Integer> codes = new LinkedList<Integer>();
		codes.add(code);
		addCommandPressed(codes, script);
	}
	/**
	 * Links Released KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is released an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @see EventKey
	 * @param code to be released for event to be sent.
	 * @param go GameObject the event will be sent to.
	 */
	public void addCommandReleased(int code, Script script) {
		LinkedList<Integer> codes = new LinkedList<Integer>();
		codes.add(code);
		addCommandReleased(codes, script);
	}
	/**
	 * Links Released and Pressed KeyEvents with GameObjects.
	 * 
	 * <p>When the specified keycode combination is pressed or released an EventKey
	 * containing information of the event will be sent to the specified object.
	 * 
	 * @see EventKey
	 * @param code to be released or pressed for event to be sent.
	 * @param go GameObject the event will be sent to.
	 */
	public void addCommand(int code, Script script) {
		LinkedList<Integer> codes = new LinkedList<Integer>();
		codes.add(code);
		addCommandPressed(codes, script);
		addCommandReleased(codes, script);
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
		if (!keysPressed.containsKey(keyEvent.getKeyCode()) || keysPressed.get(keyEvent.getKeyCode()) == 0)
			keysPressed.put(keyEvent.getKeyCode(), 1);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		keysPressed.put(keyEvent.getKeyCode(), 0);
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
