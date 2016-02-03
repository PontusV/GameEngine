import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

	private int pressedKey;
	
	//Keys bound to actions
	private HashMap<Integer, Action> bindings;
	
	//The objects that need to be notified when keys are pressed
	private ArrayList<GameObject> observers;
	
	//Keeps track of which keys are down
	private boolean[] keys = new boolean[4];
	
	private static InputManager thisInstance;
	public static InputManager getInstance() {
		if (thisInstance == null)
			thisInstance = new InputManager();
		return thisInstance;
	}
	
	public enum Action
	{
		UP, DOWN, LEFT, RIGHT
	}
	
	private InputManager() {
		//Constructor
	}
	
	private boolean keyDown(int keyCode){
		return keys[keyCode];
	}
	
	public void addObserver(GameObject gObj){
		observers.add(gObj);
	}
	
	//The observers are notified
	private void notifyObservers(Action action){

		for(GameObject gObj : observers){
//			gObj.notify(action);
		}
		
	}
	
	public void initKeyBindings(){
		bindings.put(KeyEvent.VK_UP, Action.UP);
		bindings.put(KeyEvent.VK_DOWN, Action.DOWN);
		bindings.put(KeyEvent.VK_LEFT, Action.LEFT);
		bindings.put(KeyEvent.VK_RIGHT, Action.RIGHT);
	}
	
	public void update(){

		if(keyDown(pressedKey)){
			if(bindings.get(pressedKey) == Action.UP ||
					bindings.get(pressedKey) == Action.DOWN ||
					bindings.get(pressedKey) == Action.LEFT ||
					bindings.get(pressedKey) == Action.RIGHT){
				notifyObservers(bindings.get(pressedKey));
			}
		}
		
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
		
		keys[keyEvent.getKeyCode()] = true;	
		update();
		
	}
	
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		
		keys[keyEvent.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

}
