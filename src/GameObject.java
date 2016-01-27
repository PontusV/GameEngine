import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Component> components;
	//private int state; //ID for different states. Ex: jumping, injured, running, swimming, dead
	
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
	
	public void addComponent(Component comp) {
		components.add(comp);
	}

}
