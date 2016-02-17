package Engine;
import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private double x,y;
	private ArrayList<Component> components;
	//private int state; //ID for different states. Ex: jumping, injured, running, swimming, dead
	//Physics properties
	private boolean stationary = false; //Can it move?
	private boolean solid = true; //Solid surfaces stop motion of other solid surfaces
	private boolean gravity = false; //Affected by gravity?
	private int weight; //weight in gram, used for calculating power transmision
	private double powerX, powerY; //Rörelsemängd : p = mv
	
	private final static int DEFAULT_WEIGHT = 50000;
	
	public GameObject() {
		components = new ArrayList<Component>();
		weight = DEFAULT_WEIGHT;
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
		//Update movement
		if (!stationary) {
			//x += (velocityX/1000)*timePassed;
			//y += (velocityY/1000)*timePassed;

			if (gravity) { //If affected by gravity
				accelerateY((double)(PhysicsEngine.GRAVITATIONAL_CONSTANT*timePassed)/1000);
			}
			x += (powerX/((double)weight/1000)*timePassed)/1000;
			y += (powerY/((double)weight/1000)*timePassed)/1000;
		}
	}
	
	/**
	 * Collision with a solid object. Transmitt power to other object.
	 * @param other
	 */
	public void collision(GameObject other) {
		for (Component comp : components) { //Let scripts handle collision events
			if (comp instanceof Script)
				((Script)comp).collisionEvent(other);
		}
	}
	
	public void addComponent(Component comp) {
		components.add(comp);
		comp.setGameObject(this);
	}
	
	public ArrayList<ImageComponent> getImageComponents() {
		ArrayList<ImageComponent> comps = new ArrayList<ImageComponent>();
		for (Component comp : components) {
			if (comp instanceof ImageComponent)
				comps.add((ImageComponent)comp);
		}
		return comps;
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}

	public int getX() { return (int)x; }
	public int getY() { return (int)y; }
	
	//Physics
	public void pushX(double amount) {
		if (Math.abs(amount) >= Math.abs(powerX))
			powerX = amount;
	}
	
	public void pushY(double amount) {
		if (Math.abs(amount) >= Math.abs(powerY))
			powerY = amount;
	}
	
	public void setVelocityX(double amount) {
		powerX = weight*amount/1000;
	}
	
	public void setVelocityY(double amount) {
		powerY = weight*amount/1000;
	}
	
	public void accelerateY(double amount) {
		powerY += weight*amount/1000;
		if (powerY/weight > PhysicsEngine.TERMINAL_VELOCITY) powerY = PhysicsEngine.TERMINAL_VELOCITY*weight;
	}
	
	public double getPowerX() { return powerX; }
	public double getPowerY() { return powerY; }
	
	/**
	 * Returns true if this object is solid. Solid objects affect other objects physically.
	 * @return solid
	 */
	public boolean isSolid() { return solid; }
	/**
	 * Returns true if this object is stationary. Stationary objects cannot move/get pushed.
	 * @return stationary
	 */
	public boolean isStationary() { return stationary; }
	/**
	 * Sets weight in gram. Ex: 1000 = 1kg
	 * @param amount
	 */
	public void setWeight(int amount) { weight = amount; }
}
