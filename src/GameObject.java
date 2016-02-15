import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int x,y;
	private ArrayList<Component> components;
	//private int state; //ID for different states. Ex: jumping, injured, running, swimming, dead
	
	public GameObject() {
		components = new ArrayList<Component>();
	}
	
	/**
	 * Updates all components added to this object.
	 * 
	 * @param timePassed time since last update, in milliseconds.
	 * @see Component
	 */
	public void tick(long timePassed) {
		for (Component comp : components) {
			comp.tick(timePassed);
		}
	}
	
	public void inputEvent(EventKey event) { System.out.println("WTF"); }
	public void addComponent(Component comp) {
		components.add(comp);
		comp.setGameObject(this);
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}

	public int getX() { return x; }
	public int getY() { return y; }
}
